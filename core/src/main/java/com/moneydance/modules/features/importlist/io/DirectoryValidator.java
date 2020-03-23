// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2020 Florian J. Breunig
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

import javax.inject.Inject;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * @author Florian J. Breunig
 */
public final class DirectoryValidator implements IOFileFilter {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(DirectoryValidator.class.getName());

    @Inject
    DirectoryValidator() {
        super();
    }

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
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.WARNING, message, e);
            }
            return false;
        }
    }

    @Override
    public boolean accept(final File dir, final String name) {
        return this.accept(new File(dir, name));
    }
}
