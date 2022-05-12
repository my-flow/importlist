package com.moneydance.modules.features.importlist.table;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Florian J. Breunig
 */
final class LabelNameRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private final ColorScheme colorScheme;
    private final String indentationPrefix;

    LabelNameRenderer(
            final ColorScheme argColorScheme,
        final String argIndentationPrefix) {
        super();
        this.colorScheme = argColorScheme;
        this.indentationPrefix = argIndentationPrefix;
    }

    // ESCA-JAVA0138: abstract method from interface TableCellRenderer
    @Override
    public Component getTableCellRendererComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {
        this.setOpaque(false);
        this.colorScheme.applyColorScheme(this, row);
        String label = null;
        if (value != null) {
            label = String.format("%s%s", this.indentationPrefix, value);
        }
        super.getTableCellRendererComponent(
                table,
                label,
                isSelected,
                hasFocus,
                row,
                column);
        this.setBorder(noFocusBorder);
        return this;
    }
}
