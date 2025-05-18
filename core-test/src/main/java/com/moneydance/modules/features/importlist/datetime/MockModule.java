package com.moneydance.modules.features.importlist.datetime;

/**
 * Mock module for date/time related components used in tests.
 * This class provides factory methods for mocked date and time formatters.
 *
 * @author Florian J. Breunig
 */
public final class MockModule {

    private MockModule() {
        // Prevents instantiation
    }

    /**
     * Creates and returns a mock date formatter.
     *
     * @return a new DateFormatterMock instance for date formatting
     */
    public static DateFormatter provideDateFormatter() {
        return new DateFormatterMock();
    }

    /**
     * Creates and returns a mock time formatter.
     *
     * @return a new TimeFormatterMock instance for time formatting
     */
    public static DateFormatter provideTimeFormatter() {
        return new TimeFormatterMock();
    }
}
