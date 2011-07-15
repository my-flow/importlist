/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
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
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.moneydance.modules.features.importlist.util.Preferences;


/**
 * This class provides the functionality to choose, access and reset the
 * extension's base directory. The base directory is the directory in the file
 * system to be monitored. Choosing/resetting the base directory is reflected
 * in the user's preferences (if there are any).
 */
class DirectoryChooser {

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(DirectoryChooser.class);

    private static final long serialVersionUID = -8581693236906919725L;
    private final Preferences prefs;
    private String baseDirectory;

    /**
     * @param argBaseDirectory set the base directory when executed as a stand-
     * alone application
     */
    DirectoryChooser(final String argBaseDirectory) {
        this.prefs          = Preferences.getInstance();
        this.baseDirectory  = argBaseDirectory;
        if (argBaseDirectory != null) {
            try {
                File baseDirectoryFile = new File(argBaseDirectory);
                this.baseDirectory     = baseDirectoryFile.getCanonicalPath();
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }

    final void reset() {
        this.displayFileChooser();
        this.saveDirectoryInPreferences();
    }

    final String getDirectory() {
        if (this.baseDirectory == null) {
            this.baseDirectory = this.prefs.getBaseDirectory();
        }

        if (this.baseDirectory == null) {
            this.displayFileChooser();
        }

        this.saveDirectoryInPreferences();

        return this.baseDirectory;
    }

    private void displayFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(this.prefs.getDirectoryChooserTitle());
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);

        chooser.setCurrentDirectory(FileUtils.getUserDirectory());
        if (this.baseDirectory != null) {
            File parentDirectory = new File(this.baseDirectory).getParentFile();
            chooser.setCurrentDirectory(parentDirectory);
        }

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            this.baseDirectory = chooser.getSelectedFile().getCanonicalPath();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            this.baseDirectory = chooser.getSelectedFile().getAbsolutePath();
        }
    }

    private void saveDirectoryInPreferences() {
        if (this.baseDirectory == null) {
            this.baseDirectory = this.prefs.getImportDirectory();
        }

        if (this.baseDirectory == null) {
            this.baseDirectory = FileUtils.getUserDirectoryPath();
        }

        this.prefs.setBaseDirectory(this.baseDirectory);
    }
}
