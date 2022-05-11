package com.moneydance.modules.features.importlist.controller;

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.inject.Named;
import javax.swing.table.AbstractTableModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author Florian J. Breunig
 */
@Module
public final class FactoryModule {

    @Provides
    @Named("base")
    AbstractTableModel provideBaseTableModel(
            final FileAdmin fileAdmin,
            final Settings settings,
            final Preferences prefs) {
        return new FileTableModel(fileAdmin.getFileContainer(), settings, prefs);
    }

    @Provides
    @Named("aggregation")
    AbstractTableModel provideAggregationTableModel(
            final Settings settings,
            final Preferences prefs) {
        return new AggregationTableModel(settings, prefs);
    }
}
