// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2016 Florian J. Breunig
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

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * @author Florian J. Breunig
 */
enum DirectoryValidator implements IOFileFilter {

    /**
     * Helper instance.
     */
    INSTANCE;

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(DirectoryValidator.class.getName());

    boolean isValidDirectory(final File file) {
        if (file == null) {
            return false;
        }

        return this.accept(file);
    }

    @Override
    public boolean accept(final File file) {
        try {
            return FileFilterUtils.and(
                    CanReadFileFilter.CAN_READ,
                    FileFilterUtils.directoryFileFilter()).accept(file);
        } catch (SecurityException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean accept(final File dir, final String name) {
        return this.accept(new File(dir, name));
    }
}
