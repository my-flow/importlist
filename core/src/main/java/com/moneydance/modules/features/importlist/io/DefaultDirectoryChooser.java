package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JFileChooser;

import java8.util.Optional;
import org.apache.commons.io.FileUtils;


/**
 * A default implementation strategy using a <code>JFileChooser</code>.
 *
 * @author Florian J. Breunig
 */
@Singleton
final class DefaultDirectoryChooser extends AbstractDirectoryChooser {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(DefaultDirectoryChooser.class.getName());

    @Inject
    DefaultDirectoryChooser(final Preferences prefs) {
        super(prefs);
    }

    @Override
    @SuppressWarnings("nullness")
    void chooseBaseDirectory() {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(Helper.INSTANCE.getLocalizable().getDirectoryChooserTitle());
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
        }

        final int dialogRet = fileChooser.showOpenDialog(null);
        if (dialogRet != JFileChooser.APPROVE_OPTION) {
            return;
        }

        this.getPrefs().setBaseDirectory(fileChooser.getSelectedFile());

        this.getPrefs().getBaseDirectory().ifPresent(file ->
            LOG.info(String.format("Base directory is %s", file.getAbsolutePath()))
        );
    }
}
