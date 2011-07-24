/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.moneydance.modules.features.importlist.view;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableCellRenderer;

class ButtonRenderer implements TableCellRenderer {

    private static final long serialVersionUID = -5002244293774549298L;
    private final ColorSchemeHelper colorSchemeHelper;

    ButtonRenderer(final ColorSchemeHelper argColorSchemeHelper) {
        this.colorSchemeHelper = argColorSchemeHelper;
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
        this.colorSchemeHelper.applyColorScheme(button, row);
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
