package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.service.ServiceLocator;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;


/**
 * A default implementation strategy using a <code>JFileChooser</code>.
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

    DefaultDirectoryChooser(final Preferences prefs) {
        super(prefs);
    }

    @Override
    @SuppressWarnings("nullness")
    void chooseBaseDirectory() {
        if (System.getProperty("os.name", "").startsWith("Mac")) {
            this.chooseWithFileDialog();
            return;
        }

        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(ServiceLocator.getLocalizable().getDirectoryChooserTitle());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // disable the "All files" option.
        fileChooser.setAcceptAllFileFilterUsed(false);

        try {
            fileChooser.setCurrentDirectory(FileUtils.getUserDirectory());
        } catch (SecurityException e) {
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.WARNING, message, e);
            }
        }

        final Optional<File> baseDirectory = this.getBaseDirectory();
        if (baseDirectory.isPresent()) {
            final File parentDirectory = baseDirectory.get().getParentFile();
            fileChooser.setCurrentDirectory(parentDirectory);
        } else {
            this.getPrefs().getMoneydanceImportDirectory().ifPresent(fileChooser::setCurrentDirectory);
        }

        final int dialogRet = fileChooser.showOpenDialog(null);
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
    private void chooseWithFileDialog() {
        final FileDialog dialog = new FileDialog((Frame) null,
                ServiceLocator.getLocalizable().getDirectoryChooserTitle(), FileDialog.LOAD);

        final Optional<File> baseDirectory = this.getBaseDirectory();
        if (baseDirectory.isPresent()) {
            dialog.setDirectory(baseDirectory.get().getParent());
            dialog.setFile(baseDirectory.get().getName());
        } else {
            final File startDirectory = this.getPrefs().getMoneydanceImportDirectory().
                    orElseGet(FileUtils::getUserDirectory);
            dialog.setDirectory(startDirectory.getAbsolutePath());
        }

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
