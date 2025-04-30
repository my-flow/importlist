package com.moneydance.modules.features.importlist.test;

import com.moneydance.modules.features.importlist.CoreComponent;
import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
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
        final TargetTestComponent component = DaggerTargetTestComponent.builder().build();
        initializeServiceLocator(component);
        return component;
    }

    /**
     * Initializes the ServiceLocator with the given component.
     *
     * @param component The component to use for initialization
     */
    public static void initializeServiceLocator(final CoreComponent component) {
        ServiceLocator.initialize(component);

        // Ensure localizable is available
        if (ServiceLocator.getLocalizable() == null) {
            // If we can't get localizable from ServiceLocator,
            // tests depending on it will fail
            try {
                final Localizable localizable = component.localizable();
                if (localizable != null) {
                    // Re-initialize to ensure it's set
                    ServiceLocator.initialize(component);
                }
            } catch (Exception e) {
                // Log the exception but continue
                LOG.log(Level.FINE, "Failed to initialize localizable", e);
            }
        }
    }
}
