package com.moneydance.modules.features.importlist.bootstrap;

import com.moneydance.modules.features.importlist.CoreComponent;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This singleton provides public convenience methods.
 *
 * @author Florian J. Breunig
 */
@SuppressWarnings("nullness")
public enum Helper {

    /**
     * Helper instance.
     */
    INSTANCE;

    private final HelperObservable observable;
    private CoreComponent coreComponent;

    Helper() {
        this.observable = new HelperObservable();
    }

    /**
     * Initializes the helper with a core component.
     * This method should only be called during application startup.
     *
     * @param argCoreComponent The core component to use
     * @throws IllegalStateException if core component is already initialized
     */
    public void init(final CoreComponent argCoreComponent) {
        synchronized (Helper.class) {
            if (this.coreComponent != null && argCoreComponent != null) {
                Logger logger = Logger.getLogger(Helper.class.getName());
                if (logger.isLoggable(Level.WARNING)) {
                    logger.warning("CoreComponent already initialized - resetting and reinitializing");
                    logger.warning("Previous component: " + this.coreComponent.getClass().getName());
                    logger.warning("New component: " + argCoreComponent.getClass().getName());
                }

                // Instead of throwing exception, reset and allow reinitialization
                // for test environments
                this.reset();
            }
            this.coreComponent = argCoreComponent;
        }
    }

    /**
     * Resets the helper for testing purposes.
     * This method should only be used in tests.
     */
    @SuppressWarnings("PMD.NullAssignment")
    public void reset() {
        synchronized (Helper.class) {
            this.coreComponent = null; // Acceptable null assignment for test reset
        }
    }

    // For test setup only
    public void setPreferences(final Preferences preferences) {
        // For test initialization
    }

    // For test setup only
    public void setLocalizable(final Localizable localizable) {
        // For test initialization
    }

    public Preferences getPreferences() {
        if (this.coreComponent == null) {
            Logger.getLogger(Helper.class.getName()).warning(
                "Helper.getPreferences() called with null coreComponent");
            return null;
        }
        return this.coreComponent.preferences();
    }

    public Localizable getLocalizable() {
        if (this.coreComponent == null) {
            Logger.getLogger(Helper.class.getName()).warning(
                "Helper.getLocalizable() called with null coreComponent");
            return null;
        }
        return this.coreComponent.localizable();
    }

    public Context getContext() {
        if (this.coreComponent == null) {
            Logger.getLogger(Helper.class.getName()).warning(
                "Helper.getContext() called with null coreComponent");
            return null;
        }
        return this.coreComponent.context();
    }

    public void addObserver(final Observer observer) {
        this.observable.addObserver(observer);
    }

    public void setChanged() {
        this.observable.setChanged();
    }

    public void notifyObservers(final Object arg) {
        this.observable.notifyObservers(arg);
    }

    public ViewController getViewController() {
        if (this.coreComponent == null) {
            Logger.getLogger(Helper.class.getName()).warning(
                "Helper.getViewController() called with null coreComponent");
            return null;
        }
        return this.coreComponent.viewController();
    }

    public static void loadLoggerConfiguration(final Settings settings) {
        try {
            try (InputStream inputStream = settings.getLoggingPropertiesResource()) {
                LogManager.getLogManager().readConfiguration(inputStream);
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(Helper.class.getName());
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error loading logger configuration: " + e.getMessage(), e);
            }
        }
    }

    /**
     * @author Florian J. Breunig
     */
    private static final class HelperObservable extends Observable {
        @Override
        public synchronized void setChanged() { // increase visiblity
            super.setChanged();
        }
    }
}
