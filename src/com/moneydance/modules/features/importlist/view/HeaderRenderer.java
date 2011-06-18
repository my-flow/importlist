package com.moneydance.modules.features.importlist.view;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * This decorator class sets header-specific attributes to a
 * <code>TableCellRenderer</code>.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
class HeaderRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 3121884943197710031L;
    private final TableCellRenderer defaultHeaderTableCellRenderer;
    private final Preferences       prefs;

    HeaderRenderer(
            final TableCellRenderer argDefaultHeaderTableCellRenderer) {
        this.defaultHeaderTableCellRenderer = argDefaultHeaderTableCellRenderer;
        this.prefs                          = Preferences.getInstance();
    }

    @Override
    public final Component getTableCellRendererComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {
        Component component =
            this.defaultHeaderTableCellRenderer.getTableCellRendererComponent(
                    table, value, hasFocus, hasFocus, row, column);
        ColorSchemeHelper.applyColorScheme(component, row);

        if (component instanceof JComponent) {
            JComponent jComponent = (JComponent) component;
            jComponent.setBorder(new EmptyBorder(1, 1, 1, 1));
        }

        if (component instanceof JLabel) {
            JLabel jLabel = (JLabel) component;
            jLabel.setHorizontalAlignment(SwingConstants.LEFT);
        }

        component.setFont(this.prefs.getHeaderFont());
        component.setSize(
                component.getWidth(),
                this.prefs.getHeaderRowHeight());

        return component;
    }
}
