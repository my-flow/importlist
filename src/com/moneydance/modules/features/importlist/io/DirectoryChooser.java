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

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;


/**
 * This class provides the functionality to choose, access and reset the
 * extension's base directory. The base directory is the directory in the file
 * system to be monitored. Choosing/resetting the base directory is reflected
 * in the user's preferences (if there are any).
 *
 * @author Florian J. Breunig
 */
final class DirectoryChooser {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(DirectoryChooser.class);

    private final   AbstractFileFilter validDirFilter;
    private final   Preferences prefs;
    private final   Localizable localizable;
    private File    baseDirectory;

    /**
     * @param argBaseDirectory set the base directory when executed as a stand-
     * alone application
     */
    DirectoryChooser(final String argBaseDirectory) {
        this.validDirFilter = new ValidDirFilter();

        this.prefs       = Helper.getPreferences();
        this.localizable = Helper.getLocalizable();
        if (argBaseDirectory != null) {
            this.baseDirectory  = new File(argBaseDirectory);
        } else if (this.prefs.getBaseDirectory() != null) {
            this.baseDirectory = new File(this.prefs.getBaseDirectory());
        }
    }

    void reset() {
        this.baseDirectory = null;
        this.validateBaseDirectory();
        LOG.info("Reset base directory to "
                + this.baseDirectory.getAbsolutePath());
    }

    File getBaseDirectory() {
        this.validateBaseDirectory();
        return this.baseDirectory;
    }

    private void validateBaseDirectory() {
        if (!this.validDirFilter.accept(this.baseDirectory)) {
            this.displayFileChooser();
        }

        if (!this.validDirFilter.accept(this.baseDirectory)
                && this.prefs.getImportDirectory() != null) {
            this.baseDirectory = new File(this.prefs.getImportDirectory());
        }

        if (!this.validDirFilter.accept(this.baseDirectory)) {
            this.baseDirectory = FileUtils.getUserDirectory();
        }
        LOG.info("Base directory is " + this.baseDirectory.getAbsolutePath());
        this.saveBaseDirectoryInPreferences();
    }

    private void displayFileChooser() {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(this.localizable.getDirectoryChooserTitle());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // disable the "All files" option.
        fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.setCurrentDirectory(FileUtils.getUserDirectory());
        if (this.baseDirectory != null) {
            final File parentDirectory = this.baseDirectory.getParentFile();
            fileChooser.setCurrentDirectory(parentDirectory);
        }

        if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        if (this.validDirFilter.accept(fileChooser.getSelectedFile())) {
            this.baseDirectory = fileChooser.getSelectedFile();
        }
    }

    private void saveBaseDirectoryInPreferences() {
        this.prefs.setBaseDirectory(this.baseDirectory.getAbsolutePath());
    }

    private static class ValidDirFilter extends AbstractFileFilter {
        @Override
        public boolean accept(final File file) {
            return file != null
            && DirectoryFileFilter.DIRECTORY.accept(file);
        }
    }
}
