package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.bootstrap.MainHelper;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.service.ServiceLocator;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.util.ISettings;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;

import javax.swing.table.AbstractTableModel;

/**
 * Standard implementation of the CoreComponent interface
 * that uses ServiceLocator for dependencies.
 *
 * @author Florian J. Breunig
 */
public class StandardCoreComponent implements CoreComponent {

    /**
     * Returns the settings instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid Settings instance.
     *
     * @return The settings instance
     */
    @Override
    public ISettings settings() {
        return ServiceLocator.getSettings();
    }

    /**
     * Returns the preferences instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid Preferences instance.
     *
     * @return The preferences instance
     */
    @Override
    public Preferences preferences() {
        return ServiceLocator.getPreferences();
    }

    /**
     * Returns the localizable instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid Localizable instance.
     *
     * @return The localizable instance
     */
    @Override
    public Localizable localizable() {
        return ServiceLocator.getLocalizable();
    }

    /**
     * Returns the context instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid Context instance.
     *
     * @return The context instance
     */
    @Override
    public Context context() {
        return ServiceLocator.getContext();
    }

    /**
     * Returns the main helper instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid MainHelper instance.
     *
     * @return The main helper instance
     */
    @Override
    public MainHelper mainHelper() {
        return ServiceLocator.getMainHelper();
    }

    /**
     * Returns the view controller instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid ViewController instance.
     *
     * @return The view controller instance
     */
    @Override
    public ViewController viewController() {
        return ServiceLocator.getViewController();
    }

    /**
     * Returns the file admin instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid FileAdmin instance.
     *
     * @return The file admin instance
     */
    @Override
    public FileAdmin fileAdmin() {
        return ServiceLocator.getFileAdmin();
    }

    /**
     * Returns the file container instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid FileContainer instance.
     *
     * @return The file container instance
     */
    @Override
    public FileContainer fileContainer() {
        return ServiceLocator.getFileContainer();
    }

    /**
     * Returns the base table model instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid AbstractTableModel instance.
     *
     * @return The base table model instance
     */
    @Override
    public AbstractTableModel baseTableModel() {
        return ServiceLocator.getBaseTableModel();
    }

    /**
     * Returns the aggregation table model instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid AbstractTableModel instance.
     *
     * @return The aggregation table model instance
     */
    @Override
    public AbstractTableModel aggregationTableModel() {
        return ServiceLocator.getAggregationTableModel();
    }

    /**
     * Returns the even color scheme instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid ColorScheme instance.
     *
     * @return The even color scheme instance
     */
    @Override
    public ColorScheme evenColorScheme() {
        return ServiceLocator.getEvenColorScheme();
    }

    /**
     * Returns the odd color scheme instance from the service locator.
     * Subclasses that override this method should ensure they maintain the contract
     * of returning a valid ColorScheme instance.
     *
     * @return The odd color scheme instance
     */
    @Override
    public ColorScheme oddColorScheme() {
        return ServiceLocator.getOddColorScheme();
    }
}
