package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This decorator class sets header-specific attributes to a
 * <code>TableCellRenderer</code>.
 *
 * @author Florian J. Breunig
 */
final class HeaderRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private final ColorScheme colorScheme;

    HeaderRenderer(final ColorScheme argColorScheme) {
        super();
        this.colorScheme = argColorScheme;
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
        this.colorScheme.applyColorScheme(this, row);
        this.setBorder(new EmptyBorder(1, 1, 1, 1));
        this.setOpaque(false);
        this.setHorizontalAlignment(SwingConstants.LEFT);
        String label = null;
        if (value != null) {
            label = String.format("%s", value);
        }
        super.getTableCellRendererComponent(
                table,
                label,
                isSelected,
                hasFocus,
                row,
                column);

        final Preferences prefs = Helper.INSTANCE.getPreferences();

        this.setFont(prefs.getHeaderFont());
        this.setForeground(prefs.getHeaderForeground());

        this.setSize(new Dimension(
                this.getWidth(),
                prefs.getHeaderRowHeight()));
        this.setMinimumSize(new Dimension(
                this.getWidth(),
                prefs.getHeaderRowHeight()));
        this.setPreferredSize(new Dimension(
                this.getWidth(),
                prefs.getHeaderRowHeight()));
        this.setMaximumSize(new Dimension(
                this.getWidth(),
                prefs.getHeaderRowHeight()));
        return this;
    }
}
