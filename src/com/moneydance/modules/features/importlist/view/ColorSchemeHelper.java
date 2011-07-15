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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;

/**
 * This helper class applies a given color scheme to a <code>Component</code>.
 * It is used by different <code>TableCellRenderer</code>s to guarantee a
 * consistent style in a table.
 */
final class ColorSchemeHelper {

    private static final long serialVersionUID = -2724848115953702911L;
    private static Color foreground;
    private static Color background;
    private static Color backgroundAlt;

    /**
     * Private constructor prevents this class from being instantiated.
     */
    private ColorSchemeHelper() {
    }

    static void applyColorScheme(
            final Component component,
            final int row) {
        component.setForeground(foreground);
        component.setBackground(background);
        if (row % 2 == 0) {
            if (component instanceof JComponent) {
                JComponent jComponent = (JComponent) component;
                jComponent.setOpaque(true);
            }
            component.setBackground(backgroundAlt);
        }
    }

    static void setForeground(final Color argForeground) {
        foreground = argForeground;
    }

    static void setBackground(final Color argBackground) {
        background = argBackground;
    }

    static void setBackgroundAlt(final Color argBackgroundAlt) {
        backgroundAlt = argBackgroundAlt;
    }
}
