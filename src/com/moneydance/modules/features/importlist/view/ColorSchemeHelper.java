/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2013 Florian J. Breunig
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

/**
 * This template class defines the abstract methods required to apply a color
 * scheme to the components of the GUI framework.
 *
 * @author Florian J. Breunig
 */
public interface ColorSchemeHelper {

    void applyColorScheme(final Component component, final int row);

    void setForeground(final Color argForeground);

    void setBackground(final Color argBackground);

    void setBackgroundAlt(final Color argBackgroundAlt);
}
