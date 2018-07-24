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

package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.apps.md.view.gui.MoneydanceLAF;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Florian J. Breunig
 */
public final class EmptyLabelFactory implements ComponentFactory {

    private final JPanel emptyPanel;

    public EmptyLabelFactory(final String text) {
        this.emptyPanel = new JPanel(new GridBagLayout());

        final JLabel emptyLabel = new JLabel(text);
        emptyLabel.setLabelFor(this.emptyPanel);

        this.emptyPanel.add(emptyLabel);
        this.emptyPanel.setBorder(MoneydanceLAF.homePageBorder);
    }

    @Override
    public JPanel getComponent() {
        return this.emptyPanel;
    }
}
