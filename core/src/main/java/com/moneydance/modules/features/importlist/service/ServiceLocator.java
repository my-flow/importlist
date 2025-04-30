package com.moneydance.modules.features.importlist.service;

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
import java.util.logging.LogManager;

/**
 * Service locator that provides access to core application services.
 *
 * @author Florian J. Breunig
 */
public final class ServiceLocator {

    private static CoreComponent coreComponent;
    private static final Observable OBSERVABLE = new ServiceObservable();

    private ServiceLocator() {
        // Private constructor to prevent instantiation
    }

    public static void initialize(final CoreComponent component) {
        if (component != null) {
            coreComponent = component;
        }
    }

    public static Preferences getPreferences() {
        if (coreComponent != null) {
            return coreComponent.preferences();
        }
        return null;
    }

    public static Localizable getLocalizable() {
        if (coreComponent != null) {
            return coreComponent.localizable();
        }
        return null;
    }

    public static Context getContext() {
        if (coreComponent != null) {
            return coreComponent.context();
        }
        return null;
    }

    public static ViewController getViewController() {
        if (coreComponent != null) {
            return coreComponent.viewController();
        }
        return null;
    }

    public static void addObserver(final Observer observer) {
        if (observer != null) {
            OBSERVABLE.addObserver(observer);
        }
    }

    public static void notifyObservers(final Object arg) {
        ((ServiceObservable) OBSERVABLE).setChanged();
        OBSERVABLE.notifyObservers(arg);
    }

    public static void loadLoggerConfiguration(final Settings settings) {
        if (settings == null) {
            return;
        }

        try {
            try (InputStream inputStream = settings.getLoggingPropertiesResource()) {
                if (inputStream != null) {
                    LogManager.getLogManager().readConfiguration(inputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Observable implementation with access to protected setChanged method.
     */
    private static final class ServiceObservable extends Observable {
        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }
    }
}
