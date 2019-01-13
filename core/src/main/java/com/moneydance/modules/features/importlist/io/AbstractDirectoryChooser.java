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

import com.moneydance.modules.features.importlist.util.Preferences;

import java.io.File;
import java.util.Optional;

/**
 * This abstract class provides the functionality to choose, access, and reset
 * the extension's base directory.
 * This class is abstract because the runtime environment requires access to
 * the file system that is specific to the operating system and the runtime
 * mode (sandboxed vs full access).
 * @author Florian J. Breunig
 */
public abstract class AbstractDirectoryChooser {

    private final Preferences prefs;

    AbstractDirectoryChooser(final Preferences argPrefs) {
        super();
        this.prefs = argPrefs;
    }

    abstract void chooseBaseDirectory();

    final Optional<File> getBaseDirectory() {
        return this.prefs.getBaseDirectory();
    }

    final void reset() {
        this.prefs.setBaseDirectory(null);
    }

    protected final Preferences getPrefs() {
        return this.prefs;
    }
}
