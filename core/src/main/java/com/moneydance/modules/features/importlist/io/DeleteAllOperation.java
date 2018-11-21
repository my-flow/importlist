// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2018 Florian J. Breunig
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
        files.forEach(file -> this.deleteOneOperation.execute(Collections.singletonList(file)));
    }
}
