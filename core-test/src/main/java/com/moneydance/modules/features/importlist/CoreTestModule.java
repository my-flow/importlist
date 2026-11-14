package com.moneydance.modules.features.importlist;

/**
 * This module provides static methods to create test components.
 * It used to be a Dagger module, now converted to use ServiceLocator instead.
 *
 * @author Florian J. Breunig
 */
public final class CoreTestModule {

    private CoreTestModule() {
        // Prevents instantiation
    }

    /**
     * Creates a new StandardCoreTestComponent instance.
     *
     * @return A new CoreTestComponent instance for testing
     */
    public static CoreTestComponent provideCoreTestComponent() {
        return new StandardCoreTestComponent();
    }
}
