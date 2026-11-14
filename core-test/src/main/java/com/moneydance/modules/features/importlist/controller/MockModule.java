package com.moneydance.modules.features.importlist.controller;

/**
 * Mock module for controller related components used in tests.
 * This class provides factory methods for mocked controllers.
 *
 * @author Florian J. Breunig
 */
public final class MockModule {

    private MockModule() {
        // Prevents instantiation
    }

    /**
     * Creates and returns a mock context.
     *
     * @return a new ContextMock instance
     */
    public static Context provideContext() {
        return new ContextMock();
    }

    /**
     * This method is not implemented for test environment.
     *
     * @return Never returns as this throws an exception
     * @throws IllegalStateException always thrown as this is not implemented for tests
     */
    public static ViewController provideViewController() {
        throw new IllegalStateException("Cannot provide ViewController instance in core-test project");
    }
}
