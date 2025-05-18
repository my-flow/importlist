package com.moneydance.modules.features.importlist;

/**
 * Factory class for creating test components.
 * This factory replaces the previously generated DaggerCoreTestComponent builder.
 *
 * @author Florian J. Breunig
 */
public final class TestComponentFactory {

    private TestComponentFactory() {
        // Prevents instantiation
    }

    /**
     * Creates a new instance of CoreTestComponent for tests.
     *
     * @return a new StandardCoreTestComponent instance
     */
    public static CoreTestComponent createTestComponent() {
        return new StandardCoreTestComponent();
    }
}
