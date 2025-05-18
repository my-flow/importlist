package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.datetime.DateFormatter;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.ISettings;

/**
 * Mock module for table related components used in tests.
 * This class provides factory methods for mocked table components.
 *
 * @author Florian J. Breunig
 */
public final class MockModule {

    private MockModule() {
        // Prevents instantiation
    }

    /**
     * Creates and returns a mock column factory.
     *
     * @param fileAdmin the file admin instance
     * @param colorScheme the color scheme to use
     * @param settings the settings instance
     * @return a new ColumnFactory instance
     */
    public static ColumnFactory provideColumnFactory(
            final FileAdmin fileAdmin,
            final ColorScheme colorScheme,
            final ISettings settings) {
        return new ColumnFactory(
                fileAdmin,
                null,
                null,
                colorScheme,
                colorScheme,
                settings);
    }

    /**
     * Creates and returns a mock label name renderer.
     *
     * @param colorScheme the color scheme to use
     * @param settings the settings instance
     * @return a new LabelNameRenderer instance
     */
    public static LabelNameRenderer provideLabelNameRenderer(
            final ColorScheme colorScheme,
            final ISettings settings) {
        return new LabelNameRenderer(
                colorScheme,
                settings.getIndentationPrefix());
    }

    /**
     * Creates a mock label modified renderer.
     * @param colorScheme color scheme
     * @param dateFormatter date formatter
     * @param timeFormatter time formatter
     * @param settings settings
     * @return LabelModifiedRenderer instance
     */
    public static LabelModifiedRenderer provideLabelModifiedRenderer(
            final ColorScheme colorScheme,
            final DateFormatter dateFormatter,
            final DateFormatter timeFormatter,
            final ISettings settings) {
        return new LabelModifiedRenderer(
                colorScheme,
                dateFormatter,
                timeFormatter,
                settings.getIndentationPrefix());
    }

    /**
     * Creates and returns a mock color scheme for even rows.
     *
     * @return a new ColorSchemeMock instance
     */
    public static ColorScheme provideEvenColorSchemeImpl() {
        return new ColorSchemeMock();
    }

    /**
     * Creates and returns a mock color scheme for odd rows.
     *
     * @return a new ColorSchemeMock instance
     */
    public static ColorScheme provideOddColorSchemeImpl() {
        return new ColorSchemeMock();
    }

    /**
     * Creates and returns a mock import all editor.
     *
     * @param fileAdmin the file admin instance
     * @param buttonRenderer the button renderer
     * @return a new ImportAllEditor instance
     */
    public static AbstractEditor provideImportAllEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        return new ImportAllEditor(fileAdmin, buttonRenderer, "");
    }

    /**
     * Creates and returns a mock import one editor.
     *
     * @param fileAdmin the file admin instance
     * @param buttonRenderer the button renderer
     * @return a new ImportOneEditor instance
     */
    public static AbstractEditor provideImportOneEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        return new ImportOneEditor(fileAdmin, buttonRenderer, "");
    }

    /**
     * Creates and returns a mock delete all editor.
     *
     * @param fileAdmin the file admin instance
     * @param buttonRenderer the button renderer
     * @return a new DeleteAllEditor instance
     */
    public static AbstractEditor provideDeleteAllEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        return new DeleteAllEditor(fileAdmin, buttonRenderer, "");
    }

    /**
     * Creates and returns a mock delete one editor.
     *
     * @param fileAdmin the file admin instance
     * @param buttonRenderer the button renderer
     * @return a new DeleteOneEditor instance
     */
    public static AbstractEditor provideDeleteOneEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        return new DeleteOneEditor(fileAdmin, buttonRenderer, "");
    }
}
