package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.service.ServiceLocator;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.awt.FileDialog;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;


/**
 * A default implementation strategy using the native
 * <code>FileDialog</code> on macOS, otherwise a <code>JFileChooser</code>.
 *
 * @author Florian J. Breunig
 */
final class DefaultDirectoryChooser extends AbstractDirectoryChooser {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(DefaultDirectoryChooser.class.getName());

    private static final String DIRECTORIES_PROPERTY = "apple.awt.fileDialogForDirectories";

    private final Context context;

    DefaultDirectoryChooser(final Preferences prefs, final Context argContext) {
        super(prefs);
        this.context = argContext;
    }

    /**
     * Shows the dialog on the event dispatch thread, as Moneydance may invoke
     * the extension from another thread.
     */
    @Override
    void chooseBaseDirectory() {
        if (SwingUtilities.isEventDispatchThread()) {
            this.showDialog();
            return;
        }

        try {
            SwingUtilities.invokeAndWait(this::showDialog);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.log(Level.WARNING, "Interrupted while choosing the base directory", e);
        } catch (InvocationTargetException e) {
            LOG.log(Level.WARNING, "Could not choose the base directory", e);
        }
    }

    /**
     * @return The directory the dialog starts in: the parent of the saved
     * folder, else Moneydance's import folder, else the user's home folder.
     */
    Optional<File> getStartDirectory() {
        final Optional<File> directory = this.getBaseDirectory().
                map(File::getParentFile).
                or(() -> this.getPrefs().getMoneydanceImportDirectory());
        if (directory.isPresent()) {
            return directory;
        }

        try {
            return Optional.of(FileUtils.getUserDirectory());
        } catch (SecurityException e) {
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.WARNING, message, e);
            }
            return Optional.empty();
        }
    }

    private void showDialog() {
        if (SystemUtils.IS_OS_MAC) {
            this.chooseWithFileDialog();
        } else {
            this.chooseWithFileChooser();
        }
    }

    @SuppressWarnings("nullness")
    private void chooseWithFileChooser() {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(ServiceLocator.getLocalizable().getDirectoryChooserTitle());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // disable the "All files" option.
        fileChooser.setAcceptAllFileFilterUsed(false);
        this.getStartDirectory().ifPresent(fileChooser::setCurrentDirectory);

        final int dialogRet = fileChooser.showOpenDialog(this.context.getMainFrame().orElse(null));
        if (dialogRet != JFileChooser.APPROVE_OPTION) {
            return;
        }

        this.saveBaseDirectory(fileChooser.getSelectedFile());
    }

    /**
     * Uses the native macOS folder chooser. Starts at the saved folder
     * (highlighted in its parent), else Moneydance's import folder,
     * else the user's home folder.
     */
    @SuppressWarnings("nullness")
    private void chooseWithFileDialog() {
        final FileDialog dialog = new FileDialog(this.context.getMainFrame().orElse(null),
                ServiceLocator.getLocalizable().getDirectoryChooserTitle(), FileDialog.LOAD);
        this.getStartDirectory().ifPresent(directory -> dialog.setDirectory(directory.getAbsolutePath()));
        this.getBaseDirectory().ifPresent(directory -> dialog.setFile(directory.getName()));

        // As Moneydance does: folders only while this dialog is open, then back to files
        System.setProperty(DIRECTORIES_PROPERTY, "true");
        try {
            dialog.setVisible(true);
        } finally {
            System.setProperty(DIRECTORIES_PROPERTY, "false");
        }

        if (dialog.getFile() == null || dialog.getDirectory() == null) {
            return;
        }

        this.saveBaseDirectory(new File(dialog.getDirectory(), dialog.getFile()));
    }

    private void saveBaseDirectory(final File directory) {
        this.getPrefs().setBaseDirectory(directory);

        this.getPrefs().getBaseDirectory().ifPresent(file ->
            LOG.info(String.format("Base directory is %s", file.getAbsolutePath()))
        );
    }
}
