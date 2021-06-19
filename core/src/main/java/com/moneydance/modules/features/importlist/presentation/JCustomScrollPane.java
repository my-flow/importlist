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

package com.moneydance.modules.features.importlist.presentation;

import javax.swing.JScrollPane;

/**
 * @author Florian J. Breunig
 */
final class JCustomScrollPane extends JScrollPane {

    private static final long serialVersionUID = 1L;

    @Override
    public void addNotify() {
        super.addNotify();
        if (this.getColumnHeader() != null) {
            this.getColumnHeader().setOpaque(false);
            this.getColumnHeader().setBackground(this.getBackground());
        }

        if (this.getViewport() != null) {
            this.getViewport().setOpaque(false);
            this.getViewport().setBackground(this.getBackground());
        }
    }
}
