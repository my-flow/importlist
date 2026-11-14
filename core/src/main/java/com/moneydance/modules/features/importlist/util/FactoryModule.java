package com.moneydance.modules.features.importlist.util;

/**
 * Utility class for creating Settings instances.
 *
 * @author Florian J. Breunig
 */
public final class FactoryModule {

    private static final String SETTINGS_RESOURCE = "settings.properties";

    private FactoryModule() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a new Settings instance from the default settings resource.
     *
     * @return A new Settings instance
     * @throws java.io.IOException if an I/O error occurs
     * @throws org.apache.commons.configuration2.ex.ConfigurationException
     *     if a configuration error occurs
     */
    public static ISettings createSettings() throws java.io.IOException,
            org.apache.commons.configuration2.ex.ConfigurationException {
        return new Settings(SETTINGS_RESOURCE);
    }

    /**
     * Creates a new Settings instance with exception handling.
     *
     * <p>Wraps checked exceptions in an IllegalStateException.</p>
     *
     * @return A new Settings instance
     * @throws IllegalStateException if settings cannot be loaded
     */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS",
            justification = "This method specifically constrains the exception types to IllegalStateException")
    public static ISettings createSettingsSafe() {
        try {
            return createSettings();
        } catch (java.io.IOException | org.apache.commons.configuration2.ex.ConfigurationException e) {
            // Log the exception
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FactoryModule.class.getName());
            if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                logger.severe("Error creating settings: " + e.getMessage());
            }
            throw new IllegalStateException("Failed to create settings", e);
        }
    }
}
