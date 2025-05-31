package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.bootstrap.MainHelper;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.datetime.DateFormatter;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.io.FileOperation;
import com.moneydance.modules.features.importlist.io.MockFileOperation;
import com.moneydance.modules.features.importlist.service.ServiceLocator;
import com.moneydance.modules.features.importlist.table.AbstractEditor;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.table.LabelModifiedRenderer;
import com.moneydance.modules.features.importlist.table.MockAbstractEditor;
import com.moneydance.modules.features.importlist.util.ISettings;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.PreferencesMock;
import com.moneydance.modules.features.importlist.util.SettingsMock;


import javax.swing.table.AbstractTableModel;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * Standard implementation of the CoreTestComponent interface.
 * This class is final to prevent finalizer attacks.
 *
 * @author Florian J. Breunig
 */
public final class StandardCoreTestComponent implements CoreTestComponent {

    private final ISettings settingsInstance;
    private final Preferences preferencesInstance;
    private final Localizable localizableInstance;
    private final FileContainer fileContainerInstance;
    private final FileAdmin fileAdminInstance;
    private final Context contextInstance;
    private final ColumnFactory columnFactoryInstance;
    private final MockFileOperation importOneOperationInstance;
    private final MockFileOperation importAllOperationInstance;
    private final MockAbstractEditor importOneEditorInstance;
    private final MockAbstractEditor importAllEditorInstance;
    private final MockAbstractEditor deleteOneEditorInstance;
    private final MockAbstractEditor deleteAllEditorInstance;

    // Static initializer that resets the ServiceLocator state
    // before any instance is created.
    static {
        resetServiceLocator();
    }

    /**
     * Creates a new StandardCoreTestComponent which initializes the ServiceLocator.
     */
    public StandardCoreTestComponent() {
        // Initialize all final fields in constructor since they're declared final
        resetServiceLocator();

        // Create components with simplified constructor
        final IOFileFilter fileFilter = TrueFileFilter.TRUE;
        // Initialize all the instances
        this.settingsInstance = new SettingsMock();
        this.preferencesInstance = new PreferencesMock();
        this.localizableInstance = new Localizable(this.settingsInstance, this.preferencesInstance.getLocale());
        this.contextInstance = new com.moneydance.modules.features.importlist.controller.ContextMock();
        this.fileContainerInstance = new FileContainer(fileFilter);
        this.fileAdminInstance = new FileAdmin(
                fileFilter, this.contextInstance, this.settingsInstance, this.preferencesInstance);
        this.columnFactoryInstance = null; // ColumnFactory is final, can't be mocked
        this.importOneOperationInstance = new MockFileOperation("importOne");
        this.importAllOperationInstance = new MockFileOperation("importAll");
        this.importOneEditorInstance = new MockAbstractEditor();
        this.importAllEditorInstance = new MockAbstractEditor();
        this.deleteOneEditorInstance = new MockAbstractEditor();
        this.deleteAllEditorInstance = new MockAbstractEditor();

        try {
            // Register services with ServiceLocator
            this.registerServicesWithLocator();
            // Initialize Helper
            this.initializeHelperWithErrorHandling();
            // Verify initialization
            this.verifyServicesNotNull();
        // Catch broad exception in test infrastructure to ensure robust error handling
        } catch (RuntimeException exception) { // NOPMD - AvoidCatchingGenericException - test infrastructure
            this.logSevere("Error initializing StandardCoreTestComponent: " + exception.getMessage(), exception);
            throw new IllegalStateException("Failed to initialize StandardCoreTestComponent", exception);
        }
    }

    /**
     * Register services with ServiceLocator.
     */
    private void registerServicesWithLocator() {
        ServiceLocator.setSettings(this.settingsInstance);
        ServiceLocator.setPreferences(this.preferencesInstance);
        ServiceLocator.setLocalizable(this.localizableInstance);
        ServiceLocator.setContext(this.contextInstance);
        ServiceLocator.setFileContainer(this.fileContainerInstance);
        ServiceLocator.setFileAdmin(this.fileAdminInstance);
    }

    /**
     * Initialize Helper with error handling.
     */
    private void initializeHelperWithErrorHandling() {
        try {
            Helper.INSTANCE.init(this);
        // Catch broad exception in test infrastructure to ensure robust error handling
        } catch (RuntimeException exception) { // NOPMD - AvoidCatchingGenericException - test infrastructure
            this.logWarning("Error initializing Helper: " + exception.getMessage(), exception);
        }

        ServiceLocator.initialize(this);
    }

    /**
     * Verify services are not null.
     */
    private void verifyServicesNotNull() {
        if (ServiceLocator.getSettings() == null) {
            this.logWarning("Settings is null after initialization", null);
        }
        if (ServiceLocator.getPreferences() == null) {
            this.logWarning("Preferences is null after initialization", null);
        }
        if (ServiceLocator.getLocalizable() == null) {
            this.logWarning("Localizable is null after initialization", null);
        }
        if (ServiceLocator.getContext() == null) {
            this.logWarning("Context is null after initialization", null);
        }
    }

    /**
     * Log a warning message.
     */
    private void logWarning(final String message, final Exception exception) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StandardCoreTestComponent.class.getName());
        if (logger.isLoggable(java.util.logging.Level.WARNING)) {
            logger.warning(message);
        }
        if (exception != null) {
            logger.log(java.util.logging.Level.WARNING, message, exception);
        }
    }

    /**
     * Log a severe message.
     */
    private void logSevere(final String message, final Exception exception) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StandardCoreTestComponent.class.getName());
        if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
            logger.severe(message);
        }
        if (exception != null) {
            logger.log(java.util.logging.Level.SEVERE, message, exception);
        }
    }

    /**
     * Resets the static state of the ServiceLocator to prevent test interference.
     * This can be called before tests to ensure a clean state.
     * Instead of using reflection, we now call public setter methods directly.
     */
    public static void resetServiceLocator() {
        try {
            // Use the public ServiceLocator API to reset all services
            ServiceLocator.setSettings(null);
            ServiceLocator.setPreferences(null);
            ServiceLocator.setLocalizable(null);
            ServiceLocator.setContext(null);
            ServiceLocator.setViewController(null);
            ServiceLocator.setMainHelper(null);
            ServiceLocator.setFileAdmin(null);
            ServiceLocator.setFileContainer(null);
            ServiceLocator.setBaseTableModel(null);
            ServiceLocator.setAggregationTableModel(null);
            ServiceLocator.setEvenColorScheme(null);
            ServiceLocator.setOddColorScheme(null);

            // Note: We're not resetting the Observable field which is private and final,
            // but that shouldn't affect test isolation since we're clearing all observers
        // Catch broad exception in test cleanup to ensure robust error handling
        } catch (RuntimeException exception) { // NOPMD - AvoidCatchingGenericException - test cleanup
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
                StandardCoreTestComponent.class.getName());
            if (logger.isLoggable(java.util.logging.Level.WARNING)) {
                logger.warning("Error resetting ServiceLocator state: " + exception.getMessage());
            }
        }
    }

    /**
     * Returns the settings instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public ISettings settings() {
        return this.settingsInstance;
    }

    /**
     * Returns the preferences instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public Preferences preferences() {
        return this.preferencesInstance;
    }

    /**
     * Returns the localizable instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public Localizable localizable() {
        return this.localizableInstance;
    }

    /**
     * Returns the context instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public Context context() {
        return this.contextInstance;
    }

    /**
     * Returns null for view controller as it's not needed for tests.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public ViewController viewController() {
        return null; // Not needed for tests
    }

    /**
     * Returns null for main helper as it's not needed for tests.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public MainHelper mainHelper() {
        return null; // Not needed for tests
    }

    /**
     * Returns null for even color scheme as it's not needed for tests.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public ColorScheme evenColorScheme() {
        return com.moneydance.modules.features.importlist.table.MockModule.provideEvenColorSchemeImpl();
    }

    /**
     * Returns a mock color scheme for odd rows.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public ColorScheme oddColorScheme() {
        return com.moneydance.modules.features.importlist.table.MockModule.provideOddColorSchemeImpl();
    }

    /**
     * Returns null for base table model as it's not needed for tests.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public AbstractTableModel baseTableModel() {
        return null; // Not needed for tests
    }

    /**
     * Returns null for aggregation table model as it's not needed for tests.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public AbstractTableModel aggregationTableModel() {
        return null; // Not needed for tests
    }

    /**
     * Returns the column factory instance for tests.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public ColumnFactory columnFactory() {
        // Initialize columnFactory if it's null
        if (this.columnFactoryInstance == null) {
            DateFormatter dateFormatter =
                com.moneydance.modules.features.importlist.datetime.MockModule.provideDateFormatter();
            DateFormatter timeFormatter =
                com.moneydance.modules.features.importlist.datetime.MockModule.provideTimeFormatter();
            return new ColumnFactory(
                this.fileAdmin(),
                dateFormatter,
                timeFormatter,
                this.evenColorScheme(),
                this.oddColorScheme(),
                this.settings());
        }
        return this.columnFactoryInstance;
    }

    /**
     * Returns the file admin instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public FileAdmin fileAdmin() {
        return this.fileAdminInstance;
    }

    /**
     * Returns the file container instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public FileContainer fileContainer() {
        return this.fileContainerInstance;
    }

    /**
     * Returns null for label name renderer as tests don't need it.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public LabelModifiedRenderer labelNameRenderer() {
        // Return mock LabelModifiedRenderer to satisfy interface
        ISettings settings = this.settings();
        ColorScheme colorScheme = this.oddColorScheme();
        DateFormatter dateFormatter =
            com.moneydance.modules.features.importlist.datetime.MockModule.provideDateFormatter();
        DateFormatter timeFormatter =
            com.moneydance.modules.features.importlist.datetime.MockModule.provideTimeFormatter();
        return com.moneydance.modules.features.importlist.table.MockModule.provideLabelModifiedRenderer(
            colorScheme, dateFormatter, timeFormatter, settings);
    }

    /**
     * Returns a mock label modified renderer for tests.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public LabelModifiedRenderer labelModifiedRenderer() {
        ISettings settings = this.settings();
        ColorScheme colorScheme = this.oddColorScheme();
        DateFormatter dateFormatter =
            com.moneydance.modules.features.importlist.datetime.MockModule.provideDateFormatter();
        DateFormatter timeFormatter =
            com.moneydance.modules.features.importlist.datetime.MockModule.provideTimeFormatter();
        return com.moneydance.modules.features.importlist.table.MockModule.provideLabelModifiedRenderer(
            colorScheme, dateFormatter, timeFormatter, settings);
    }

    /**
     * Returns the import one operation instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public FileOperation importOneOperation() {
        return this.importOneOperationInstance;
    }

    /**
     * Returns the import all operation instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public FileOperation importAllOperation() {
        return this.importAllOperationInstance;
    }

    /**
     * Returns the import all editor instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public AbstractEditor importAllEditor() {
        return this.importAllEditorInstance;
    }

    /**
     * Returns the import one editor instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public AbstractEditor importOneEditor() {
        return this.importOneEditorInstance;
    }

    /**
     * Returns the delete all editor instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public AbstractEditor deleteAllEditor() {
        return this.deleteAllEditorInstance;
    }

    /**
     * Returns the delete one editor instance.
     * Implementation of the CoreTestComponent interface.
     */
    @Override
    public AbstractEditor deleteOneEditor() {
        return this.deleteOneEditorInstance;
    }

    // Finalizer not needed - class is properly using try-catch
    // in constructor and has no resources that need cleaning up
}
