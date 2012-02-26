/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.moneydance.modules.features.importlist.io;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Localizable;

/**
 * @author Florian J. Breunig
 */
final class DeleteOneOperation implements FileOperation {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(DeleteOneOperation.class);

    private final Localizable localizable;

    DeleteOneOperation() {
        this.localizable = Helper.getLocalizable();
    }

    @Override
    public void showWarningAndPerform(final List<File> files) {
        final File file = files.iterator().next();
        final String message =
                this.localizable.getConfirmationMessageDeleteOneFile(
                        file.getName());
        final Object confirmationLabel = new JLabel(message);
        final Image image = Helper.getIconImage();
        Icon  icon  = null;
        if (image != null) {
            icon = new ImageIcon(image);
        }
        final Object[] options = {
                this.localizable.getOptionDeleteFile(),
                this.localizable.getOptionCancel()
        };

        final int choice = JOptionPane.showOptionDialog(
                null, // no parent component
                confirmationLabel,
                null, // no title
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                options,
                options[1]);

        if (choice == 0) {
            this.perform(files);
        } else {
            LOG.info("Canceled deleting file " + file.getAbsoluteFile());
        }
    }

    @Override
    public void perform(final List<File> files) {
        final File file = files.iterator().next();
        try {
            LOG.info("Deleting file " + file.getAbsoluteFile());
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            LOG.warn("Could not delete file " + file.getAbsoluteFile());
            final String errorMessage =
                    this.localizable.getErrorMessageDeleteFile(file.getName());
            final Object errorLabel = new JLabel(errorMessage);
            JOptionPane.showMessageDialog(
                    null, // no parent component
                    errorLabel,
                    null, // no title
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
