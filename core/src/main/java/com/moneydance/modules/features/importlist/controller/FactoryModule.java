package com.moneydance.modules.features.importlist.controller;

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.ISettings;
import com.moneydance.modules.features.importlist.util.Preferences;

import javax.swing.table.AbstractTableModel;

/**
 * Utility class for creating table models.
 *
 * @author Florian J. Breunig
 */
public final class FactoryModule {

    private FactoryModule() {
        // Private constructor to prevent instantiation
    }

    public static AbstractTableModel createBaseTableModel(
            final FileAdmin fileAdmin,
            final ISettings settings,
            final Preferences prefs) {
        return new FileTableModel(fileAdmin.getFileContainer(), settings, prefs);
    }

    public static AbstractTableModel createAggregationTableModel(
            final ISettings settings,
            final Preferences prefs) {
        return new AggregationTableModel(settings, prefs);
    }
}
