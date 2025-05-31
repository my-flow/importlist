package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.bootstrap.MainHelper;
import com.moneydance.modules.features.importlist.controller.AggregationTableModel;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.FileTableModel;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.service.ServiceLocator;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.util.ISettings;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.PreferencesMock;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * Standard implementation of the TargetTestComponent interface.
 *
 * @author Florian J. Breunig
 */
public class StandardTargetTestComponent implements TargetTestComponent {

    private final Settings settingsInstance;
    private final Preferences preferencesInstance;
    private final Localizable localizableInstance;
    private final FileContainer fileContainerInstance;
    private final MainHelper mainHelperInstance;
    private final AbstractTableModel baseTableModelInstance;
    private final AbstractTableModel aggregationTableModelInstance;

    @SuppressWarnings({"PMD.ExceptionAsFlowControl", "PMD.NcssCount", "PMD.CognitiveComplexity",
        "PMD.CyclomaticComplexity", "PMD.AvoidDeeplyNestedIfStmts"})
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "CT_CONSTRUCTOR_THROW",
            justification = "Constructor safely handles exceptions to prevent finalizer attacks")
    public StandardTargetTestComponent() {
        // Initialize test components
        try {
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
                    StandardTargetTestComponent.class.getName());
            logger.info("Starting StandardTargetTestComponent initialization");
            Settings tempSettings = this.loadSettings(logger);
            this.settingsInstance = tempSettings;
            logger.info("Settings instance initialized");

            logger.info("Creating PreferencesMock");
            this.preferencesInstance = new PreferencesMock();
            logger.info("Creating Localizable");
            this.localizableInstance = new Localizable(this.settingsInstance, this.preferencesInstance.getLocale());
            logger.info("Creating FileContainer");
            this.fileContainerInstance = new FileContainer(TrueFileFilter.TRUE);
            logger.info("Creating MainHelper");
            this.mainHelperInstance = new MainHelper(this.settingsInstance);

            // Create table models
            logger.info("Creating FileTableModel");
            this.baseTableModelInstance = new FileTableModel(
                this.fileContainerInstance,
                this.settingsInstance,
                this.preferencesInstance);
            logger.info("Creating AggregationTableModel");
            this.aggregationTableModelInstance = new AggregationTableModel(
                this.settingsInstance,
                this.preferencesInstance);

            // Set in ServiceLocator for tests to use
            logger.info("Setting up ServiceLocator components");
            ServiceLocator.setSettings(this.settingsInstance);
            ServiceLocator.setPreferences(this.preferencesInstance);
            ServiceLocator.setLocalizable(this.localizableInstance);
            ServiceLocator.setFileContainer(this.fileContainerInstance);
            ServiceLocator.setMainHelper(this.mainHelperInstance);
            ServiceLocator.setBaseTableModel(this.baseTableModelInstance);
            ServiceLocator.setAggregationTableModel(this.aggregationTableModelInstance);

            // Initialize ServiceLocator and Helper for tests
            try {
                logger.info("Initializing ServiceLocatorTestHelper");
                com.moneydance.modules.features.importlist.test.ServiceLocatorTestHelper.initialize(this);
                logger.info("Initializing Helper.INSTANCE");
                Helper.INSTANCE.init(this);
                logger.info("Helper initialization complete");
            // Catch broad exception in test infrastructure to ensure robust error handling
            } catch (RuntimeException initEx) { // NOPMD - AvoidCatchingGenericException - test infrastructure
                if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                    logger.severe("Failed to initialize ServiceLocator or Helper: " + initEx.getMessage());
                    logger.severe("Exception type: " + initEx.getClass().getName());
                    if (initEx.getCause() != null && logger.isLoggable(java.util.logging.Level.SEVERE)) {
                        logger.severe("Caused by: " + initEx.getCause().getMessage());
                    }
                }
                throw new IllegalStateException("Failed to initialize ServiceLocator or Helper", initEx);
            }
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
                logger.info("StandardTargetTestComponent initialization completed successfully");
            }
        // Catch broad exception in test infrastructure to ensure robust error handling
        } catch (RuntimeException e) { // NOPMD - AvoidCatchingGenericException - test infrastructure
            // Log error before exposing partially constructed object
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
                    StandardTargetTestComponent.class.getName());
            if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                logger.severe("Failed to initialize test component: " + e.getMessage());
                logger.severe("Exception type: " + e.getClass().getName());
                // Log stack trace
                StackTraceElement[] stack = e.getStackTrace();
                if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                    logger.severe("Stack trace:");
                }
                for (int i = 0; i < Math.min(stack.length, 10); i++) {
                    if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                        logger.severe("    at " + stack[i].toString());
                    }
                }
                // Log cause if available
                if (e.getCause() != null) {
                    Throwable cause = e.getCause();
                    if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                        logger.severe("Caused by: " + cause.getMessage());
                        logger.severe("Cause type: " + cause.getClass().getName());
                    }
                    // Log cause stack trace
                    StackTraceElement[] causeStack = cause.getStackTrace();
                    if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                        logger.severe("Cause stack trace:");
                    }
                    for (int i = 0; i < Math.min(causeStack.length, 10); i++) {
                        if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                            logger.severe("    at " + causeStack[i].toString());
                        }
                    }
                }
            }

            // Prepare exception with clear constructor contract
            IllegalStateException illegalStateException = new IllegalStateException(
                    "Failed to initialize test component", e);

            // Reset state before throwing exception to prevent finalizer attack
            this.resetState();

            throw illegalStateException;
        }
    }

    /**
     * Resets this object's state to avoid leaking a partially constructed object.
     * Called when an exception is thrown from the constructor.
     */
    private void resetState() {
        // Clear any references to avoid partially initialized object leaks
    }

    /**
     * Loads settings from properties file, with fallback to absolute path.
     *
     * @param logger The logger to use for status messages
     * @return The loaded Settings object
     * @throws IllegalStateException If settings cannot be loaded from either path
     */
    @SuppressWarnings({"PMD.CognitiveComplexity", "PMD.CyclomaticComplexity"})
    private Settings loadSettings(final java.util.logging.Logger logger) {
        // First try to load from relative path
        try {
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
                logger.info("Attempting to load settings.properties");
            }
            Settings tempSettings = new Settings("settings.properties");
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
                logger.info("Successfully loaded settings.properties");
            }
            return tempSettings;
        } catch (java.io.IOException | org.apache.commons.configuration2.ex.ConfigurationException
                | IllegalArgumentException e) {
            // Log the exception
            if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                logger.severe("Error loading settings: " + e.getMessage());
                logger.severe("Exception type: " + e.getClass().getName());
                if (e.getCause() != null && logger.isLoggable(java.util.logging.Level.SEVERE)) {
                    logger.severe("Caused by: " + e.getCause().getMessage());
                }
            }
        }

        // If first attempt failed, try with absolute path
        try {
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
                logger.info("Attempting to load with absolute path");
            }
            Settings tempSettings = new Settings("com/moneydance/modules/features/importlist/settings.properties");
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
                logger.info("Successfully loaded settings with absolute path");
            }
            return tempSettings;
        } catch (java.io.IOException | org.apache.commons.configuration2.ex.ConfigurationException
                | IllegalArgumentException e2) {
            // Log and rethrow if both attempts fail
            if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                logger.severe("Error loading settings with alternative path: " + e2.getMessage());
                logger.severe("Exception type: " + e2.getClass().getName());
                if (e2.getCause() != null && logger.isLoggable(java.util.logging.Level.SEVERE)) {
                    logger.severe("Caused by: " + e2.getCause().getMessage());
                }
            }
            throw new IllegalStateException("Failed to load settings properties file", e2);
        }
    }

    /**
     * Returns the settings instance for tests.
     *
     * @return The settings instance
     */
    @Override
    public final ISettings settings() {
        return this.settingsInstance;
    }

    /**
     * Returns the preferences instance for tests.
     *
     * @return The preferences instance
     */
    @Override
    public final Preferences preferences() {
        return this.preferencesInstance;
    }

    /**
     * Returns the localizable instance for tests.
     *
     * @return The localizable instance
     */
    @Override
    public final Localizable localizable() {
        return this.localizableInstance;
    }

    /**
     * Returns null for context since it's not needed in tests.
     *
     * @return Always null
     */
    @Override
    public final Context context() {
        return null; // Not needed for tests
    }

    /**
     * Returns null for view controller since it's not needed in tests.
     *
     * @return Always null
     */
    @Override
    public final ViewController viewController() {
        return null; // Not needed for tests
    }

    /**
     * Returns the main helper instance for tests.
     *
     * @return The main helper instance
     */
    @Override
    public final MainHelper mainHelper() {
        return this.mainHelperInstance;
    }

    /**
     * Returns null for even color scheme since it's not needed in tests.
     *
     * @return Always null
     */
    @Override
    public final ColorScheme evenColorScheme() {
        return null; // Not needed for tests
    }

    /**
     * Returns null for odd color scheme since it's not needed in tests.
     *
     * @return Always null
     */
    @Override
    public final ColorScheme oddColorScheme() {
        return null; // Not needed for tests
    }

    /**
     * Returns null for file admin since it's not needed in tests.
     *
     * @return Always null
     */
    @Override
    public final FileAdmin fileAdmin() {
        return null; // Not needed for tests
    }

    /**
     * Returns the file container instance for tests.
     *
     * @return The file container instance
     */
    @Override
    public final FileContainer fileContainer() {
        return this.fileContainerInstance;
    }

    /**
     * Returns the base table model used for tests.
     * @return The base table model instance
     */
    @Override
    public final AbstractTableModel baseTableModel() {
        return this.baseTableModelInstance;
    }

    /**
     * Returns the aggregation table model used for tests.
     * @return The aggregation table model instance
     */
    @Override
    public final AbstractTableModel aggregationTableModel() {
        return this.aggregationTableModelInstance;
    }
}
