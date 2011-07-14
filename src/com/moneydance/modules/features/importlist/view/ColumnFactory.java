package com.moneydance.modules.features.importlist.view;

import java.awt.Color;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.lang.Validate;

import com.moneydance.modules.features.importlist.io.FileAdministration;

/**
 * This factory provides unified access to the required renderer classes.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
class ColumnFactory {

    private final HeaderRenderer    headerRenderer;
    private final LabelRenderer     labelRenderer;
    private final ButtonRenderer    buttonRenderer;
    private final ImportEditor      importEditor;
    private final DeleteEditor      deleteEditor;

    ColumnFactory(final FileAdministration argFileAdministration,
            final TableCellRenderer argDefaultHeaderTableCellRenderer) {
        Validate.notNull(argFileAdministration,
        "argFileAdministration can't be null");
        this.headerRenderer = new HeaderRenderer(
                argDefaultHeaderTableCellRenderer);
        this.labelRenderer  = new LabelRenderer();
        this.buttonRenderer = new ButtonRenderer();
        this.importEditor   = new ImportEditor(
                argFileAdministration,
                this.buttonRenderer);
        this.deleteEditor   = new DeleteEditor(
                argFileAdministration,
                this.buttonRenderer);
    }

    final TableCellRenderer getHeaderRenderer() {
        return this.headerRenderer;
    }

    final TableCellRenderer getLabelRenderer() {
        return this.labelRenderer;
    }

    final TableCellRenderer getButtonRenderer() {
        return this.buttonRenderer;
    }

    final TableCellEditor getImportEditor() {
        return this.importEditor;
    }

    final TableCellEditor getDeleteEditor() {
        return this.deleteEditor;
    }

    final void setForeground(final Color foreground) {
        ColorSchemeHelper.setForeground(foreground);
    }

    final void setBackground(final Color background) {
        ColorSchemeHelper.setBackground(background);
    }

    final void setBackgroundAlt(final Color backgroundAlt) {
        ColorSchemeHelper.setBackgroundAlt(backgroundAlt);
    }
}
