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
