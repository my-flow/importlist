// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2013 Florian J. Breunig
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

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;


/**
 * An implementation strategy specific to Mac OS that presents a file dialog
 * in a sandboxed runtime environment.
 *
 * @author Florian J. Breunig
 */
final class MacOSDirectoryChooser extends AbstractDirectoryChooser {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(MacOSDirectoryChooser.class.getName());

    /**
     * @param argBaseDirectory set the base directory when executed as a stand-
     * alone application
     */
    MacOSDirectoryChooser(final String argBaseDirectory) {
        super(argBaseDirectory);
    }

    @Override
    void chooseBaseDirectory() {
        System.setProperty("apple.awt.fileDialogForDirectories", "true");
        final FileDialog fileDialog = new FileDialog(
                (Frame) null,
                this.getLocalizable().getDirectoryChooserTitle(),
                FileDialog.LOAD);
        fileDialog.setModal(true);
        fileDialog.setFilenameFilter(DirectoryValidator.INSTANCE);

        try {
            fileDialog.setDirectory(
                    FileUtils.getUserDirectory().getAbsolutePath());
        } catch (SecurityException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
        }

        if (this.getBaseDirectory() != null) {
            final File parentDirectory =
                   this.getBaseDirectory().getParentFile();
            if (parentDirectory != null) {
                fileDialog.setDirectory(parentDirectory.getAbsolutePath());
            }
        }
        fileDialog.setVisible(true);
        System.setProperty("apple.awt.fileDialogForDirectories", "false");

        if (fileDialog.getFile() == null) {
            return;
        }

        this.getPrefs().setBaseDirectory(new File(
                fileDialog.getDirectory(),
                fileDialog.getFile()).getAbsolutePath());

        LOG.info(String.format(
                "Base directory is %s",
                this.getPrefs().getBaseDirectory()));
    }
}
