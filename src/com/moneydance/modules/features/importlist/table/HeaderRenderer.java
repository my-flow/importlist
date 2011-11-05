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

package com.moneydance.modules.features.importlist.table;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.view.ColorSchemeHelper;

/**
 * This decorator class sets header-specific attributes to a
 * <code>TableCellRenderer</code>.
 *
 * @author Florian J. Breunig
 */
final class HeaderRenderer implements TableCellRenderer {

    private final TableCellRenderer defaultHeaderTableCellRenderer;
    private final Preferences       prefs;
    private final ColorSchemeHelper colorSchemeHelper;

    HeaderRenderer(
            final ColorSchemeHelper argColorSchemeHelper,
            final TableCellRenderer argDefaultHeaderTableCellRenderer) {
        this.colorSchemeHelper              = argColorSchemeHelper;
        this.defaultHeaderTableCellRenderer = argDefaultHeaderTableCellRenderer;
        this.prefs                          = Helper.getPreferences();
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
        this.colorSchemeHelper.applyColorScheme(component, row);

        if (component instanceof JComponent) {
            JComponent jComponent = (JComponent) component;
            jComponent.setBorder(new EmptyBorder(1, 1, 1, 1));
        }

        if (component instanceof JLabel) {
            JLabel jLabel = (JLabel) component;
            jLabel.setHorizontalAlignment(SwingConstants.LEFT);
        }

        component.setFont(this.prefs.getHeaderFont());
        component.setSize(new Dimension(
                component.getWidth(),
                this.prefs.getHeaderRowHeight()));
        component.setMinimumSize(new Dimension(
                component.getWidth(),
                this.prefs.getHeaderRowHeight()));
        component.setPreferredSize(new Dimension(
                component.getWidth(),
                this.prefs.getHeaderRowHeight()));
        component.setMaximumSize(new Dimension(
                component.getWidth(),
                this.prefs.getHeaderRowHeight()));

        return component;
    }
}
