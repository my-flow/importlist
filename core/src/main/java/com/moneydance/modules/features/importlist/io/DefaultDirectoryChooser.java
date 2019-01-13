// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2019 Florian J. Breunig
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

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.io.File;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JFileChooser;

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
