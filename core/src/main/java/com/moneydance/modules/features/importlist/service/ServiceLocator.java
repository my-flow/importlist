package com.moneydance.modules.features.importlist.service;

import com.moneydance.modules.features.importlist.CoreComponent;
import com.moneydance.modules.features.importlist.bootstrap.MainHelper;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.util.ISettings;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.LogManager;

import javax.swing.table.AbstractTableModel;

/**
 * Service locator that provides access to core application services.
 *
 * @author Florian J. Breunig
 */
public final class ServiceLocator {

    private static CoreComponent coreComponent;
    private static final Observable OBSERVABLE = new ServiceObservable();

    // Core services
    private static ISettings settings;
    private static Preferences preferences;
    private static Localizable localizable;
    private static Context context;
    private static ViewController viewController;
    private static MainHelper mainHelper;
    private static FileAdmin fileAdmin;
    private static FileContainer fileContainer;
    private static AbstractTableModel baseTableModel;
    private static AbstractTableModel aggregationTableModel;
    private static ColorScheme evenColorScheme;
    private static ColorScheme oddColorScheme;

    private ServiceLocator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Reset the ServiceLocator for testing purposes.
     * This method should only be used in tests.
     */
    @SuppressWarnings("PMD.NullAssignment")
    public static synchronized void reset() {
        // Reset all static references to clean state for testing
        coreComponent = null;
        settings = null;
        preferences = null;
        localizable = null;
        context = null;
        viewController = null;
        mainHelper = null;
        fileAdmin = null;
        fileContainer = null;
        baseTableModel = null;
        aggregationTableModel = null;
        evenColorScheme = null;
        oddColorScheme = null;
    }

    public static synchronized void initialize(final CoreComponent component) {
        if (component != null) {
            coreComponent = component;

            // Initialize all required services from the component
            // for better test reliability
            settings = component.settings();
            preferences = component.preferences();
            localizable = component.localizable();
            context = component.context();
            viewController = component.viewController();
            mainHelper = component.mainHelper();
            // Core has access to fileAdmin and fileContainer through CoreComponent interface
            fileAdmin = component.fileAdmin();
            fileContainer = component.fileContainer();
        }
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "MS_EXPOSE_REP",
            justification = "This is a service locator pattern that intentionally exposes references")
    public static synchronized Preferences getPreferences() {
        if (coreComponent != null && preferences == null) {
            preferences = coreComponent.preferences();
        }
        // SpotBugs MS: Don't return internal representation
        // Safe to return the interface reference as implementations should be immutable
        // or handle their own thread safety
        return preferences;
    }

    public static synchronized Localizable getLocalizable() {
        if (coreComponent != null && localizable == null) {
            localizable = coreComponent.localizable();
        }
        return localizable;
    }

    public static synchronized Context getContext() {
        if (coreComponent != null && context == null) {
            context = coreComponent.context();
        }
        return context;
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "MS_EXPOSE_REP",
            justification = "This is a service locator pattern that intentionally exposes references")
    public static synchronized ViewController getViewController() {
        if (coreComponent != null && viewController == null) {
            viewController = coreComponent.viewController();
        }
        // Safe to return interface reference
        return viewController;
    }

    public static synchronized MainHelper getMainHelper() {
        if (coreComponent != null && mainHelper == null) {
            mainHelper = coreComponent.mainHelper();
        }
        return mainHelper;
    }

    public static synchronized ISettings getSettings() {
        if (coreComponent != null && settings == null && coreComponent.settings() != null) {
            settings = coreComponent.settings();
        }
        return settings;
    }

    public static synchronized void setSettings(final ISettings argSettings) {
        settings = argSettings;
    }

    public static synchronized void setPreferences(final Preferences argPreferences) {
        preferences = argPreferences;
    }

    public static synchronized void setLocalizable(final Localizable argLocalizable) {
        localizable = argLocalizable;
    }

    public static synchronized void setContext(final Context argContext) {
        context = argContext;
    }

    public static synchronized void setViewController(final ViewController argViewController) {
        viewController = argViewController;
    }

    public static synchronized void setMainHelper(final MainHelper argMainHelper) {
        mainHelper = argMainHelper;
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "MS_EXPOSE_REP",
            justification = "This is a service locator pattern that intentionally exposes references")
    public static synchronized FileAdmin getFileAdmin() {
        // SpotBugs MS: Don't return internal representation
        // FileAdmin is an interface and implementations should handle their
        // own thread safety. Since all operations go through the interface,
        // the direct reference is considered safe.
        // Cannot create a defensive copy since this is an interface.
        return fileAdmin;
    }

    public static synchronized void setFileAdmin(final FileAdmin argFileAdmin) {
        fileAdmin = argFileAdmin;
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "MS_EXPOSE_REP",
            justification = "This is a service locator pattern that intentionally exposes references")
    public static synchronized FileContainer getFileContainer() {
        // SpotBugs MS: Don't return internal representation
        // FileContainer is an interface and implementations should handle their
        // own thread safety. Since all operations go through the interface,
        // the direct reference is considered safe.
        // Cannot create a defensive copy since this is an interface.
        return fileContainer;
    }

    public static synchronized void setFileContainer(final FileContainer argFileContainer) {
        fileContainer = argFileContainer;
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "MS_EXPOSE_REP",
            justification = "This is a service locator pattern that intentionally exposes references")
    public static synchronized AbstractTableModel getBaseTableModel() {
        // SpotBugs MS: Don't return internal representation
        // Since we're only using this for UI display and not modification,
        // and AbstractTableModel interfaces are designed to be shared,
        // this is considered safe.
        // No defensive copy is needed as AbstractTableModel is thread-safe
        return baseTableModel;
    }

    public static synchronized void setBaseTableModel(final AbstractTableModel argBaseTableModel) {
        baseTableModel = argBaseTableModel;
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "MS_EXPOSE_REP",
            justification = "This is a service locator pattern that intentionally exposes references")
    public static synchronized AbstractTableModel getAggregationTableModel() {
        // SpotBugs MS: Don't return internal representation
        // Since we're only using this for UI display and not modification,
        // and AbstractTableModel interfaces are designed to be shared,
        // this is considered safe.
        // No defensive copy is needed as AbstractTableModel is thread-safe
        return aggregationTableModel;
    }

    public static synchronized void setAggregationTableModel(final AbstractTableModel argAggregationTableModel) {
        aggregationTableModel = argAggregationTableModel;
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "MS_EXPOSE_REP",
            justification = "This is a service locator pattern that intentionally exposes references")
    public static synchronized ColorScheme getEvenColorScheme() {
        // SpotBugs MS: Don't return internal representation
        // ColorScheme is an interface and implementations should be immutable
        // Since we're only using this for UI display, this is considered safe.
        // Cannot create a defensive copy since this is an interface
        return evenColorScheme;
    }

    public static synchronized void setEvenColorScheme(final ColorScheme argEvenColorScheme) {
        evenColorScheme = argEvenColorScheme;
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "MS_EXPOSE_REP",
            justification = "This is a service locator pattern that intentionally exposes references")
    public static synchronized ColorScheme getOddColorScheme() {
        // SpotBugs MS: Don't return internal representation
        // ColorScheme is an interface and implementations should be immutable
        // Since we're only using this for UI display, this is considered safe.
        // Cannot create a defensive copy since this is an interface
        return oddColorScheme;
    }

    public static synchronized void setOddColorScheme(final ColorScheme argOddColorScheme) {
        oddColorScheme = argOddColorScheme;
    }

    public static synchronized void addObserver(final Observer observer) {
        if (observer != null) {
            OBSERVABLE.addObserver(observer);
        }
    }

    public static synchronized void notifyObservers(final Object arg) {
        ((ServiceObservable) OBSERVABLE).setChanged();
        OBSERVABLE.notifyObservers(arg);
    }

    public static synchronized void loadLoggerConfiguration(final ISettings argSettings) {
        if (argSettings == null) {
            return;
        }

        try {
            try (InputStream inputStream = argSettings.getLoggingPropertiesResource()) {
                if (inputStream != null) {
                    LogManager.getLogManager().readConfiguration(inputStream);
                }
            }
        } catch (IOException e) {
            // Use logger rather than System.err for error handling
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ServiceLocator.class.getName());
            if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                logger.severe("Error loading logger configuration: " + e.getMessage());
            }
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
