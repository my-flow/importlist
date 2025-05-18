package com.moneydance.modules.features.importlist.test;

import com.moneydance.modules.features.importlist.CoreComponent;
import com.moneydance.modules.features.importlist.StandardTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;
import com.moneydance.modules.features.importlist.service.ServiceLocator;
import com.moneydance.modules.features.importlist.util.Localizable;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to help with test setup.
 * Provides methods to create and initialize test components.
 *
 * @author Florian J. Breunig
 */
public final class HelperUtils {

    private static final Logger LOG = Logger.getLogger(HelperUtils.class.getName());
    private static TargetTestComponent sharedTestComponent;

    private HelperUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Gets or creates a shared test component.
     * Using a shared component ensures consistency across tests.
     *
     * @return A shared test component
     */
    public static synchronized TargetTestComponent getSharedTestComponent() {
        if (sharedTestComponent == null) {
            sharedTestComponent = createTestComponent();
        }
        return sharedTestComponent;
    }

    /**
     * Creates a new test component and initializes the ServiceLocator.
     *
     * @return A new test component
     */
    public static TargetTestComponent createTestComponent() {
        try {
            LOG.log(Level.INFO, "Starting to create StandardTargetTestComponent...");
            final TargetTestComponent component = new StandardTargetTestComponent();
            LOG.log(Level.INFO, "Successfully created StandardTargetTestComponent");
            initializeServiceLocator(component);
            LOG.log(Level.INFO, "Successfully initialized ServiceLocator");
            return component;
        } catch (IllegalStateException e) {
            LOG.log(Level.SEVERE, "Failed to create StandardTargetTestComponent", e);
            if (LOG.isLoggable(Level.SEVERE)) {
                LOG.log(Level.SEVERE, "Root cause: ", e.getCause());
            }
            throw e; // Re-throw the exception after logging
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unexpected error creating StandardTargetTestComponent", e);
            throw new IllegalStateException("Failed to create test component", e);
        }
    }

    /**
     * Initializes the ServiceLocator with the given component.
     *
     * @param component The component to use for initialization
     */
    public static void initializeServiceLocator(final CoreComponent component) {
        try {
            LOG.log(Level.INFO, "Resetting ServiceLocator before initialization");
            ServiceLocator.reset(); // Reset ServiceLocator first to avoid conflicts
            LOG.log(Level.INFO, "Initializing ServiceLocator with component");
            ServiceLocator.initialize(component);
            LOG.log(Level.INFO, "ServiceLocator initialized successfully");

            // Ensure localizable is available
            if (ServiceLocator.getLocalizable() == null) {
                LOG.log(Level.INFO, "Localizable not set, trying to get from component");
                // If we can't get localizable from ServiceLocator,
                // tests depending on it will fail
                try {
                    final Localizable localizable = component.localizable();
                    if (localizable != null) {
                        LOG.log(Level.INFO, "Setting Localizable in ServiceLocator");
                        // Set the localizable directly
                        ServiceLocator.setLocalizable(localizable);
                    } else {
                        LOG.log(Level.WARNING, "Component.localizable() returned null");
                    }
                } catch (Exception e) {
                    // Log the exception but continue
                    LOG.log(Level.WARNING, "Failed to initialize localizable", e);
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Failed to initialize ServiceLocator", e);
            throw new IllegalStateException("Failed to initialize ServiceLocator", e);
        }
    }
}
