// Import List - https://www.my-flow.com/importlist/
// Copyright (C) 2011-2021 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

/**
 * @author Florian J. Breunig
 */
final class DeleteOneOperation implements FileOperation {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(DeleteOneOperation.class.getName());

    private final Settings settings;
    private final Localizable localizable;

    DeleteOneOperation(
            final Settings argSettings,
            final Localizable argLocalizable) {
        this.settings = argSettings;
        this.localizable = argLocalizable;
    }

    @Override
    public void showWarningAndExecute(final List<File> files) {
        final File file = files.get(0);
        final String message =
                this.localizable.getConfirmationMessageDeleteOneFile(
                        file.getName());
        final JLabel confirmationLabel = new JLabel(message);
        confirmationLabel.setLabelFor(null);

        final Image image = this.settings.getIconImage();
        final Icon icon = new ImageIcon(image);
        final Object[] options = {
                this.localizable.getOptionDeleteFile(),
                this.localizable.getOptionCancel(),
        };

        @SuppressWarnings("nullness") final int choice = JOptionPane.showOptionDialog(
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
            LOG.info(() -> String.format("Canceled deleting file %s", file.getAbsoluteFile()));
        }
    }

    @Override
    @SuppressWarnings("nullness")
    public void execute(final List<File> files) {
        final File file = files.get(0);
        try {
            LOG.info(() -> String.format("Deleting file %s", file.getAbsoluteFile()));
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            LOG.log(Level.WARNING, e, e::getMessage);
            final String errorMessage =
                    this.localizable.getErrorMessageDeleteFile(file.getName());
            final JLabel errorLabel = new JLabel(errorMessage);
            errorLabel.setLabelFor(null);
            JOptionPane.showMessageDialog(
                    null, // no parent component
                    errorLabel,
                    null, // no title
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
