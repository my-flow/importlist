package com.moneydance.modules.features.importlist.bootstrap;

import com.moneydance.modules.features.importlist.util.ISettings;

/**
 * Utility class for creating MainHelper instances.
 *
 * @author Florian J. Breunig
 */
public final class FactoryModule {

    private FactoryModule() {
        // Private constructor to prevent instantiation
    }

    public static MainHelper createMainHelper(final ISettings settings) {
        return new MainHelper(settings);
    }
}
