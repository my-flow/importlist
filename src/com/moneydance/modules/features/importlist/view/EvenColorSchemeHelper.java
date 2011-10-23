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
public final class EvenColorSchemeHelper implements ColorSchemeHelper {

    private static final long serialVersionUID = -2724848115953702911L;
    private Color foreground;
    private Color background;
    private Color backgroundAlt;

    @Override
    public void applyColorScheme(final Component component, final int row) {
        component.setForeground(this.foreground);
        component.setBackground(this.background);
        if (row % 2 == 0) {
            if (component instanceof JComponent) {
                JComponent jComponent = (JComponent) component;
                jComponent.setOpaque(true);
            }
            component.setBackground(this.backgroundAlt);
        }
    }

    @Override
    public void setForeground(final Color argForeground) {
        this.foreground = argForeground;
    }

    @Override
    public void setBackground(final Color argBackground) {
        this.background = argBackground;
    }

    @Override
    public void setBackgroundAlt(final Color argBackgroundAlt) {
        this.backgroundAlt = argBackgroundAlt;
    }
}
