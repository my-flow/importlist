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

import java.awt.Color;
import java.awt.Component;

import org.apache.commons.lang3.Validate;

/**
 * This helper class applies a given color scheme to a <code>Component</code>.
 * It is used by different <code>TableCellRenderer</code>s to guarantee a
 * consistent style in a table.
 *
 * @author Florian J. Breunig
 */
public final class ColorSchemeImpl implements ColorScheme {

    private static final long serialVersionUID = 1L;

    private Color foreground;

    public ColorSchemeImpl(final Color argForeground) {
        this.setForeground(argForeground);
    }

    @Override
    public void applyColorScheme(final Component component, final int row) {
        component.setForeground(this.foreground);
    }

    @Override
    public void setForeground(final Color argForeground) {
        Validate.notNull(argForeground, "argForeground must not be null");
        this.foreground = argForeground;
    }

    @Override
    public void setBackground(final Color argBackground) {
        // no manual setting required
    }

    @Override
    public void setBackgroundAlt(final Color argBackgroundAlt) {
        // no manual setting required
    }
}
