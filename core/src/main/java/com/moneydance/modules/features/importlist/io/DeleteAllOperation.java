package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.Image;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author Florian J. Breunig
 */
final class DeleteAllOperation implements FileOperation {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(DeleteAllOperation.class.getName());

    private final FileOperation deleteOneOperation;
    private final Settings settings;
    private final Localizable localizable;

    DeleteAllOperation(
            @Named("delete one") final FileOperation argDeleteOneOperation,
            final Settings argSettings,
            final Localizable argLocalizable) {
        this.deleteOneOperation = argDeleteOneOperation;
        this.settings = argSettings;
        this.localizable = argLocalizable;
    }

    @Override
    public void showWarningAndExecute(final List<File> files) {
        final String message =
                this.localizable.getConfirmationMessageDeleteAllFiles(
                        files.size());
        final JLabel confirmationLabel = new JLabel(message);
        confirmationLabel.setLabelFor(null);

        final Image image = this.settings.getIconImage();
        final Icon icon = new ImageIcon(image);
        final Object[] options = {
                this.localizable.getOptionDeleteFile(),
                this.localizable.getOptionCancel(),
        };

        @SuppressWarnings("nullness")
        final int choice = JOptionPane.showOptionDialog(
                null, // no parent component
                confirmationLabel,
                null, // no title
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                options,
                options[options.length - 1]);

        if (choice == 0) {
            this.execute(files);
        } else {
            LOG.info("Canceled deleting all files");
        }
    }

    @Override
    public void execute(final List<File> files) {
        for (File file : files) {
            this.deleteOneOperation.execute(Collections.singletonList(file));
        }
    }
}
