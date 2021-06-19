// Import List - https://www.my-flow.com/importlist/
// Copyright (C) 2011-2021 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

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
