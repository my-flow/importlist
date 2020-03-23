// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2020 Florian J. Breunig
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

import javax.swing.JComponent;
import javax.swing.JSplitPane;

/**
 * @author Florian J. Breunig
 */
final class JCustomSplitPane extends JSplitPane {

    private static final long serialVersionUID = 1L;

    JCustomSplitPane(final int newOrientation) {
        super(newOrientation);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (this.getTopComponent() != null) {
            ((JComponent) this.getTopComponent()).setOpaque(false);
            this.getTopComponent().setBackground(this.getBackground());
        }

        if (this.getBottomComponent() != null) {
            ((JComponent) this.getBottomComponent()).setOpaque(false);
            this.getBottomComponent().setBackground(this.getBackground());
        }
    }
}
