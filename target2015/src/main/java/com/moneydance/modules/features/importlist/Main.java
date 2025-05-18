package com.moneydance.modules.features.importlist;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.bootstrap.FactoryModule;
import com.moneydance.modules.features.importlist.bootstrap.MainHelper;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ContextImpl;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.controller.ViewControllerImpl;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.io.FileOperation;
import com.moneydance.modules.features.importlist.service.ServiceLocator;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.ColorSchemeImpl;
import com.moneydance.modules.features.importlist.util.ISettings;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.PreferencesImpl;

import java.awt.Image;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * The main class of the extension, instantiated by Moneydance's class loader.
 *
 * @author Florian J. Breunig
 */
@SuppressWarnings("nullness")
public final class Main extends FeatureModule implements Observer {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private final TargetComponent targetComponent;
    private final MainHelper mainHelper;
    private ViewControllerImpl viewController;

    /**
     * Public standard constructor must be available in the Moneydance context.
     */
    public Main() {
        super();
        // Initialize settings using the safe method that handles exceptions internally
        ISettings settings = com.moneydance.modules.features.importlist.util.FactoryModule.createSettingsSafe();
        ServiceLocator.setSettings(settings);
        ServiceLocator.loadLoggerConfiguration(settings);

        // Create standard component
        this.targetComponent = new StandardTargetComponent();
        this.mainHelper = FactoryModule.createMainHelper(settings);
        ServiceLocator.setMainHelper(this.mainHelper);
    }

    @Override
    public void init() {
        // Create and set up services
        ISettings settings = ServiceLocator.getSettings();
        FeatureModuleContext moduleContext = this.getContext();
        Context context = new ContextImpl(this, moduleContext);
        ServiceLocator.setContext(context);

        final com.moneydance.apps.md.controller.Main main = (com.moneydance.apps.md.controller.Main) moduleContext;
        Preferences preferences = new PreferencesImpl(settings, main);
        ServiceLocator.setPreferences(preferences);

        Localizable localizable = new Localizable(settings, preferences.getLocale());
        ServiceLocator.setLocalizable(localizable);

        // Set up file-related components
        // Create factory for file operations
        IOFileFilter transactionFileFilter = com.moneydance.modules.features.importlist.io.FactoryModule.
                createTransactionFileFilter(settings);
        IOFileFilter readableFileFilter = com.moneydance.modules.features.importlist.io.FactoryModule.
                createReadableFileFilter(transactionFileFilter);

        FileOperation deleteOneOperation = com.moneydance.modules.features.importlist.io.FactoryModule.
                createDeleteOneOperation(settings, localizable);
        // Create DeleteAllOperation
        // (not stored in variable as we use the FileAdmin API)
        com.moneydance.modules.features.importlist.io.FactoryModule.
                createDeleteAllOperation(deleteOneOperation, settings, localizable);

        FileOperation importOneOperation = com.moneydance.modules.features.importlist.io.FactoryModule.
                createImportOneOperation(context, transactionFileFilter, settings);
        // Create ImportAllOperation
        // (not stored in variable as we use the FileAdmin API)
        com.moneydance.modules.features.importlist.io.FactoryModule.
                createImportAllOperation(importOneOperation);

        FileAdmin fileAdmin = com.moneydance.modules.features.importlist.io.FactoryModule.
                createFileAdmin(readableFileFilter, context, settings, preferences);
        ServiceLocator.setFileAdmin(fileAdmin);
        ServiceLocator.setFileContainer(fileAdmin.getFileContainer());

        // Create controllers
        AbstractTableModel baseTableModel = com.moneydance.modules.features.importlist.controller.FactoryModule.
                createBaseTableModel(fileAdmin, settings, preferences);
        AbstractTableModel aggregationTableModel = com.moneydance.modules.features.importlist.controller.FactoryModule.
                createAggregationTableModel(settings, preferences);
        ServiceLocator.setBaseTableModel(baseTableModel);
        ServiceLocator.setAggregationTableModel(aggregationTableModel);

        // Set up color schemes
        ColorScheme evenColorScheme = new ColorSchemeImpl(preferences.getForeground());
        ColorScheme oddColorScheme = new ColorSchemeImpl(preferences.getForeground());
        ServiceLocator.setEvenColorScheme(evenColorScheme);
        ServiceLocator.setOddColorScheme(oddColorScheme);

        // Create view controller
        ViewController controllerView = new ViewControllerImpl(
                fileAdmin,
                baseTableModel,
                aggregationTableModel,
                evenColorScheme,
                oddColorScheme,
                settings,
                preferences);
        ServiceLocator.setViewController(controllerView);

        // Initialize main helper
        this.mainHelper.init(this.targetComponent, this);
        // Register view controller
        this.viewController = (ViewControllerImpl) this.targetComponent.viewController();
        moduleContext.registerHomePageView(this, this.viewController);
    }

    @Override
    public String getName() {
        return this.mainHelper.getName();
    }

    @Override
    public Image getIconImage() {
        return this.mainHelper.getIconImage();
    }

    @Override
    public void invoke(final String uri) {
        this.mainHelper.invoke(uri);
    }

    @Override
    public void update(final Observable observable, final Object updateAll) {
        ServiceLocator.notifyObservers(updateAll);
    }

    @Override
    public void unload() {
        this.mainHelper.unload();
    }

    @Override
    public void cleanup() {
        this.mainHelper.cleanup();
    }

    HomePageView getHomePageView() {
        return this.viewController;
    }
}
