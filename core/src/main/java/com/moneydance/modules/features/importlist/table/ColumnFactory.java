package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.datetime.DateFormatter;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.Color;

import javax.swing.table.TableCellRenderer;

/**
 * This factory provides unified access to the required renderer classes.
 *
 * @author Florian J. Breunig
 */
public final class ColumnFactory {

    private final ColorScheme evenColorScheme;
    private final ColorScheme oddColorScheme;
    private final TableCellRenderer headerRenderer;
    private final TableCellRenderer labelNameOneRenderer;
    private final TableCellRenderer labelNameAllRenderer;
    private final LabelModifiedRenderer labelModifiedOneRenderer;
    private final LabelModifiedRenderer labelModifiedAllRenderer;
    private final ButtonRenderer buttonOneRenderer;
    private final ButtonRenderer buttonAllRenderer;
    private final AbstractEditor importOneEditor;
    private final AbstractEditor importAllEditor;
    private final AbstractEditor deleteOneEditor;
    private final AbstractEditor deleteAllEditor;

    public ColumnFactory(
            final FileAdmin fileAdmin,
            final TableCellRenderer defaultHeaderTableCellRenderer,
            final DateFormatter argDateFormatter,
            final DateFormatter argTimeFormatter,
            final ColorScheme argEvenColorSchemeHelper,
            final ColorScheme argOddColorSchemeHelper,
            final Settings argSettings) {

        this.evenColorScheme = argEvenColorSchemeHelper;
        this.oddColorScheme = argOddColorSchemeHelper;
        this.headerRenderer = new HeaderRenderer(
                this.oddColorScheme,
                defaultHeaderTableCellRenderer);
        this.labelNameOneRenderer = new LabelNameRenderer(
                this.oddColorScheme,
                argSettings.getIndentationPrefix());
        this.labelNameAllRenderer = new LabelNameRenderer(
                this.oddColorScheme,
                argSettings.getIndentationPrefix());
        this.labelModifiedOneRenderer = new LabelModifiedRenderer(
                this.oddColorScheme,
                argDateFormatter,
                argTimeFormatter,
                argSettings.getIndentationPrefix());
        this.labelModifiedAllRenderer = new LabelModifiedRenderer(
                this.oddColorScheme,
                argDateFormatter,
                argTimeFormatter,
                argSettings.getIndentationPrefix());
        this.buttonOneRenderer = new ButtonRenderer(this.oddColorScheme);
        this.buttonAllRenderer = new ButtonRenderer(this.oddColorScheme);
        this.importOneEditor = new ImportOneEditor(
                fileAdmin,
                this.buttonOneRenderer,
                argSettings.getKeyboardShortcutImport());
        this.importAllEditor = new ImportAllEditor(
                fileAdmin,
                this.buttonAllRenderer,
                argSettings.getKeyboardShortcutImport());
        this.deleteOneEditor = new DeleteOneEditor(
                fileAdmin,
                this.buttonOneRenderer,
                argSettings.getKeyboardShortcutDelete());
        this.deleteAllEditor = new DeleteAllEditor(
                fileAdmin,
                this.buttonAllRenderer,
                argSettings.getKeyboardShortcutDelete());
    }

    public TableCellRenderer getHeaderRenderer() {
        return this.headerRenderer;
    }

    public TableCellRenderer getLabelNameOneRenderer() {
        return this.labelNameOneRenderer;
    }

    public TableCellRenderer getLabelNameAllRenderer() {
        return this.labelNameAllRenderer;
    }

    public TableCellRenderer getLabelModifiedOneRenderer() {
        return this.labelModifiedOneRenderer;
    }

    public TableCellRenderer getLabelModifiedAllRenderer() {
        return this.labelModifiedAllRenderer;
    }

    public TableCellRenderer getButtonOneRenderer() {
        return this.buttonOneRenderer;
    }

    public TableCellRenderer getButtonAllRenderer() {
        return this.buttonAllRenderer;
    }

    public AbstractEditor getImportOneEditor() {
        return this.importOneEditor;
    }

    public AbstractEditor getImportAllEditor() {
        return this.importAllEditor;
    }

    public AbstractEditor getDeleteOneEditor() {
        return this.deleteOneEditor;
    }

    public AbstractEditor getDeleteAllEditor() {
        return this.deleteAllEditor;
    }

    public void setForeground(final Color foreground) {
        this.evenColorScheme.setForeground(foreground);
        this.oddColorScheme.setForeground(foreground);
    }

    public void setBackground(final Color background) {
        this.evenColorScheme.setBackground(background);
        this.oddColorScheme.setBackground(background);
    }

    public void setBackgroundAlt(final Color backgroundAlt) {
        this.evenColorScheme.setBackgroundAlt(backgroundAlt);
        this.oddColorScheme.setBackgroundAlt(backgroundAlt);
    }

    public void setDateFormatter(final DateFormatter argDateFormatter) {
        this.labelModifiedOneRenderer.setDateFormatter(argDateFormatter);
    }

    public void setTimeFormatter(final DateFormatter argTimeFormatter) {
        this.labelModifiedOneRenderer.setTimeFormatter(argTimeFormatter);
    }
}
