package com.moneydance.modules.features.importlist.view;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableCellRenderer;

/**
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
class ButtonRenderer implements TableCellRenderer {

    private static final long serialVersionUID = -5002244293774549298L;

    /**
     * Standard constructor to restrict visibility.
     */
    ButtonRenderer() {
    }

    @Override
    public final Component getTableCellRendererComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {
        return this.getTableCellRendererButton(
                table, value, hasFocus, hasFocus, row, column);
    }

    final AbstractButton getTableCellRendererButton(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {
        JButton button = new JButton();
        button.setOpaque(false);
        ColorSchemeHelper.applyColorScheme(button, row);
        Border border = BorderFactory.createEtchedBorder(
                EtchedBorder.LOWERED,
                button.getBackground().brighter(),
                button.getBackground().darker());
        if (row % 2 == 0) {
            border = BorderFactory.createEtchedBorder(
                    EtchedBorder.RAISED,
                    button.getBackground().darker(),
                    button.getBackground().brighter());
        }
        button.setBorder(border);
        if (value != null) {
            button.setText(value.toString());
        }
        return button;
    }
}
