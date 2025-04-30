package com.moneydance.modules.features.importlist.test;

import com.moneydance.modules.features.importlist.CoreComponent;
import com.moneydance.modules.features.importlist.service.ServiceLocator;

/**
 * Helper class for initializing ServiceLocator in test environments.
 * This ensures that tests using ServiceLocator will function correctly.
 *
 * @author Florian J. Breunig
 */
public final class ServiceLocatorTestHelper {

    private ServiceLocatorTestHelper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Initialize ServiceLocator with the given component.
     * This method should be called in the setUp method of tests.
     *
     * @param component The CoreComponent to use for initialization
     */
    public static void initialize(final CoreComponent component) {
        ServiceLocator.initialize(component);
    }
}
