// Import List - https://www.my-flow.com/importlist/
// Copyright (C) 2011-2021 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.datetime.DateFormatter;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.table.DefaultTableCellRenderer;

import dagger.Module;
import dagger.Provides;

/**
 * @author Florian J. Breunig
 */
@Module
public final class TestModule {

    @Provides
    ColumnFactory provideColumnFactory(
            final FileAdmin fileAdmin,
            @Named("odd color scheme") final ColorScheme colorScheme,
            final Settings settings) {
        return new ColumnFactory(
                fileAdmin,
                new DefaultTableCellRenderer(),
                null,
                null,
                colorScheme,
                colorScheme,
                settings);
    }

    @Provides
    LabelNameRenderer provideLabelNameRenderer(
            @Named("odd color scheme") final ColorScheme colorScheme,
            final Settings settings) {
        return new LabelNameRenderer(
                colorScheme,
                settings.getIndentationPrefix());
    }

    @Provides
    LabelModifiedRenderer provideLabelModifiedRenderer(
            @Named("odd color scheme") final ColorScheme colorScheme,
            @Named("date") final DateFormatter dateFormatter,
            @Named("time") final DateFormatter timeFormatter,
            final Settings settings) {
        return new LabelModifiedRenderer(
                colorScheme,
                dateFormatter,
                timeFormatter,
                settings.getIndentationPrefix());
    }

    @Provides
    @Singleton
    @Named("even color scheme")
    ColorScheme provideEvenColorSchemeImpl() {
        return new ColorSchemeMock();
    }

    @Provides
    @Singleton
    @Named("odd color scheme")
    ColorScheme provideOddColorSchemeImpl() {
        return new ColorSchemeMock();
    }

    @Provides
    @Named("import all")
    AbstractEditor provideImportAllEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        return new ImportAllEditor(fileAdmin, buttonRenderer, "");
    }

    @Provides
    @Named("import one")
    AbstractEditor provideImportOneEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        return new ImportOneEditor(fileAdmin, buttonRenderer, "");
    }

    @Provides
    @Named("delete all")
    AbstractEditor provideDeleteAllEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        return new DeleteAllEditor(fileAdmin, buttonRenderer, "");
    }

    @Provides
    @Named("delete one")
    AbstractEditor provideDeleteOneEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        return new DeleteOneEditor(fileAdmin, buttonRenderer, "");
    }
}
