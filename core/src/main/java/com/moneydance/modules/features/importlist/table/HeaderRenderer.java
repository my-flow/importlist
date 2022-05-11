package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 * This decorator class sets header-specific attributes to a
 * <code>TableCellRenderer</code>.
 *
 * @author Florian J. Breunig
 */
final class HeaderRenderer implements TableCellRenderer {

    private final TableCellRenderer defaultHeaderTableCellRenderer;
    private final ColorScheme colorScheme;

    HeaderRenderer(
            final ColorScheme argColorScheme,
            final TableCellRenderer argDefaultHeaderTableCellRenderer) {
        this.colorScheme = argColorScheme;
        this.defaultHeaderTableCellRenderer = argDefaultHeaderTableCellRenderer;
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
        Component component =
                this.defaultHeaderTableCellRenderer.getTableCellRendererComponent(
                        table, value, hasFocus, hasFocus, row, column);
        this.colorScheme.applyColorScheme(component, row);
        if (component instanceof JComponent) {
            JComponent jComponent = (JComponent) component;
            jComponent.setBorder(new EmptyBorder(1, 1, 1, 1));
            jComponent.setOpaque(false);

            if (jComponent instanceof JLabel) {
                JLabel jLabel = (JLabel) jComponent;
                jLabel.setHorizontalAlignment(SwingConstants.LEFT);
            }
        }

        final Preferences prefs = Helper.INSTANCE.getPreferences();

        component.setFont(prefs.getHeaderFont());
        component.setForeground(prefs.getHeaderForeground());

        component.setSize(new Dimension(
                component.getWidth(),
                prefs.getHeaderRowHeight()));
        component.setMinimumSize(new Dimension(
                component.getWidth(),
                prefs.getHeaderRowHeight()));
        component.setPreferredSize(new Dimension(
                component.getWidth(),
                prefs.getHeaderRowHeight()));
        component.setMaximumSize(new Dimension(
                component.getWidth(),
                prefs.getHeaderRowHeight()));

        return component;
    }
}
