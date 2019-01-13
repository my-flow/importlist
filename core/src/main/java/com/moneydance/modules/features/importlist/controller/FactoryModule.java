// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2019 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

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
