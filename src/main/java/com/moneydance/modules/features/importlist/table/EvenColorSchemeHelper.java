// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2015 Florian J. Breunig
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

import javax.swing.JComponent;

import org.apache.commons.lang3.Validate;

/**
 * This helper class applies a given color scheme to a <code>Component</code>.
 * It is used by different <code>TableCellRenderer</code>s to guarantee a
 * consistent style in a table.
 *
 * @author Florian J. Breunig
 */
final class EvenColorSchemeHelper implements ColorSchemeHelper {

    private Color foreground;
    private Color background;
    private Color backgroundAlt;

    EvenColorSchemeHelper(
            final Color argForeground,
            final Color argBackground,
            final Color argBackgroundAlt) {
        this.setForeground(argForeground);
        this.setBackground(argBackground);
        this.setBackgroundAlt(argBackgroundAlt);
    }

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
        Validate.notNull(argForeground, "argForeground must not be null");
        this.foreground = argForeground;
    }

    @Override
    public void setBackground(final Color argBackground) {
        Validate.notNull(argBackground, "argBackground must not be null");
        this.background = argBackground;
    }

    @Override
    public void setBackgroundAlt(final Color argBackgroundAlt) {
        Validate.notNull(argBackgroundAlt, "argBackgroundAlt must not be null");
        this.backgroundAlt = argBackgroundAlt;
    }
}
