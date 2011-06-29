package com.moneydance.modules.features.importlist.io;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * This core class coordinates and delegates operations on the file system.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
public class FileAdministration {

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(FileAdministration.class);

    private final Preferences               prefs;
    private final FeatureModule             featureModule;
    private FeatureModuleContext            context;
    private final DirectoryChooser          directoryChooser;
    private final IOFileFilter              fileFilter;
    private FileAlterationObserver          observer;
    private final TransactionFileListener   listener;
    private final FileAlterationMonitor     monitor;
    private final List<File>                files;
    private boolean                         dirty;
    private HomePageView                    homePageView;

    public FileAdministration(final FeatureModule argFeatureModule,
            final String baseDirectory) {
        Validate.notNull(argFeatureModule, "argFeatureModule can't be null");
        this.prefs            = Preferences.getInstance();
        this.featureModule    = argFeatureModule;
        this.directoryChooser = new DirectoryChooser(baseDirectory);
        this.fileFilter       = new AndFileFilter(
                CanReadFileFilter.CAN_READ,
                new SuffixFileFilter(
                        this.prefs.getFileExtensions(),
                        IOCase.INSENSITIVE)
        );

        this.listener = new TransactionFileListener(this);
        this.monitor  = new FileAlterationMonitor(
                this.prefs.getMonitorIntervall());
        this.setFileMonitorToCurrentImportDir();
        this.startMonitor();

        this.files = new ArrayList<File>();

        this.dirty = true;
    }

    public final void setContext(final FeatureModuleContext argContext) {
        this.context = argContext;
    }

    public final void reset() {
        log.info("Resetting base directory.");
        this.directoryChooser.reset();
        this.monitor.removeObserver(this.observer);
        this.setFileMonitorToCurrentImportDir();
        this.setDirty(true);
    }

    public final List<File> getFiles() {
        return this.files;
    }

    public final void reloadFiles() {
        this.files.clear();
        try {
            File importDir = new File(this.getImportDir());
            Collection<File> collection = FileUtils.listFiles(
                    importDir,
                    this.fileFilter,
                    null); // ignore subdirectories
            this.files.addAll(collection);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage(), e);
        }
    }

    public final String getImportDir() {
        return this.directoryChooser.getDirectory();
    }

    public final void setDirty(final boolean argDirty) {
        this.dirty = argDirty;
        if (argDirty && this.homePageView != null) {
            this.reloadFiles();
            this.homePageView.refresh();
        }
    }

    public final boolean isDirty() {
        return this.dirty;
    }

    /**
     * Start monitoring the current import directory.
     */
    public final void startMonitor() {
        log.debug("Starting the directory monitor.");
        try {
            this.monitor.start();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * Stop monitoring the current import directory.
     */
    public final void stopMonitor() {
        log.debug("Stopping the directory monitor.");
        try {
            this.monitor.stop();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    public final void setHomePageView(final HomePageView argHomePageView) {
        this.homePageView = argHomePageView;
    }

    public final ActionListener getImportActionListener(final int rowNumber) {
        return this.new ImportActionListener(rowNumber);
    }

    public final ActionListener getDeleteActionListener(final int rowNumber) {
        return this.new DeleteActionListener(rowNumber);
    }

    private void setFileMonitorToCurrentImportDir() {
        this.observer  = new FileAlterationObserver(
                this.getImportDir(),
                this.fileFilter,
                IOCase.SENSITIVE);
        this.observer.addListener(this.listener);
        this.monitor.addObserver(this.observer);
    }

    /**
     * Command pattern: Return an action that imports a file identified by its
     * position in the list.
     */
    private final class ImportActionListener implements ActionListener {

        private final int rowNumber;

        private ImportActionListener(final int argRowNumber) {
            this.rowNumber = argRowNumber;
        }

        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            File file = FileAdministration.this.getFiles().get(this.rowNumber);
            log.info("Importing file " + file.getAbsoluteFile());

            if (!file.canRead()) {
                log.warn("Could not read file " + file.getAbsoluteFile());
                final String errorMessage =
                    FileAdministration.this.prefs.getErrorMessageCannotReadFile(
                            file);
                Object errorLabel = new JLabel(errorMessage);
                JOptionPane.showMessageDialog(
                        null, // no parent component
                        errorLabel,
                        null, // no title
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (FileAdministration.this.context == null) {
                log.warn("Could not import file " + file
                        + " since context is not set.");
                return;
            }

            String callUri = FileAdministration.this.prefs.getImportUriPrefix()
            + file.getAbsolutePath();

            // Import the file identified by the file parameter
            FileAdministration.this.context.showURL(callUri);
        }
    }

    /**
     * Command pattern: Return an action that deletes a file identified by its
     * position in the list.
     */
    private final class DeleteActionListener implements ActionListener {

        private final int rowNumber;

        private DeleteActionListener(final int argRowNumber) {
            this.rowNumber = argRowNumber;
        }

        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            File file = FileAdministration.this.getFiles().get(this.rowNumber);

            final String confirmationMessage =
                FileAdministration.this.prefs.getConfirmationMessageDeleteFile(
                        file);
            Object confirmationLabel = new JLabel(confirmationMessage);
            Icon icon   = null;
            Image image = FileAdministration.this.featureModule.getIconImage();
            if (image != null) {
                icon = new ImageIcon(image);
            }
            Object[] options = {
                    FileAdministration.this.prefs.getOptionDeleteFile(),
                    FileAdministration.this.prefs.getOptionCancel()
            };

            int choice = JOptionPane.showOptionDialog(
                    null, // no parent component
                    confirmationLabel,
                    null, // no title
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    icon,
                    options,
                    options[1]);

            if (choice == 0) {
                log.info("Deleting file " + file.getAbsoluteFile());

                if (file.delete()) {
                    log.info("Deleted file " + file.getAbsoluteFile());
                } else {
                    log.warn("Could not delete file " + file.getAbsoluteFile());
                    final String errorMessage =
                        FileAdministration.this.prefs.getErrorMessageDeleteFile(
                                file);
                    Object errorLabel = new JLabel(errorMessage);
                    JOptionPane.showMessageDialog(
                            null, // no parent component
                            errorLabel,
                            null, // no title
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                log.info("Canceled deleting file " + file.getAbsoluteFile());
            }

            FileAdministration.this.setDirty(true);
        }
    }
}
