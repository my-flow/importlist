package com.moneydance.modules.features.importlist.view;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
public class LabelRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 7553393304980261323L;

    public final Component getTableCellRendererComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        ColorSchemeHelper.applyColorScheme(label, row);
        if (value != null) {
            label.setText(value.toString());
        }
        return label;
    }
}
