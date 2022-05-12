package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.datetime.DateFormatter;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.inject.Named;
import javax.inject.Singleton;

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
