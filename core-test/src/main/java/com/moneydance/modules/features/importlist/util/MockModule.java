package com.moneydance.modules.features.importlist.util;

/**
 * Mock module for utilities used in tests.
 * This class provides factory methods for mocked utilities.
 *
 * @author Florian J. Breunig
 */
public final class MockModule {

    private MockModule() {
        // Prevents instantiation
    }

    /**
     * Creates and returns a mock preferences instance.
     *
     * @return a new PreferencesMock instance
     */
    public static Preferences providePreferences() {
        return new PreferencesMock();
    }
}
