// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2018 Florian J. Breunig
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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableCellRenderer;

/**
 * @author Florian J. Breunig
 */
final class ButtonRenderer implements TableCellRenderer {

    // ESCA-JAVA0138: abstract method from interface TableCellRenderer
    @Override
    public Component getTableCellRendererComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {
        return this.getTableCellRendererButton(
                value, row);
    }

    AbstractButton getTableCellRendererButton(
            final Object value,
            final int row) {
        JButton button = new JButton();
        button.setOpaque(false);
        Border border = BorderFactory.createEtchedBorder(
                EtchedBorder.LOWERED,
                button.getBackground().brighter(),
                button.getBackground().darker());
        button.setBorder(border);

        if (value != null) {
            button.setText(value.toString());
        }
        button.addMouseListener(getMouseListener(button));
        return button;
    }

    private static MouseListener getMouseListener(final AbstractButton button) {
        return new MouseAdapter() {
            private boolean wasOpaque;
            @Nullable private Color foreground;
            @Nullable private Color background;

            @Override
            @SuppressWarnings("nullness")
            public void mousePressed(final MouseEvent mouseevent) {
                this.wasOpaque = button.isOpaque();

                this.foreground = button.getForeground();
                this.background = button.getBackground();

                Color newForeground = this.foreground.brighter();
                Color newBackground = this.background.brighter();
                if (newForeground.equals(this.foreground)) {
                    newForeground = this.foreground.darker();
                }
                if (newBackground.equals(this.background)) {
                    newBackground = this.background.darker();
                }

                button.setOpaque(true);
                button.setForeground(newForeground);
                button.setBackground(newBackground);
            }

            @Override
            public void mouseReleased(final MouseEvent mouseevent) {
                button.setOpaque(this.wasOpaque);

                assert this.foreground != null : "@AssumeAssertion(nullness)";
                button.setForeground(this.foreground);

                assert this.background != null : "@AssumeAssertion(nullness)";
                button.setBackground(this.background);
            }
        };
    }
}
