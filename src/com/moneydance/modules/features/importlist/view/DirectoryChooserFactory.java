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

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.moneydance.apps.md.view.gui.MoneydanceLAF;

/**
 * @author Florian J. Breunig
 */
public final class DirectoryChooserFactory implements ComponentFactory {

    private final ActionListener actionListener;
    private       JButton chooserButton;

    public DirectoryChooserFactory(final ActionListener argActionListener) {
        this.actionListener = argActionListener;
    }

    private void init() {
        this.chooserButton = new JButton();
        this.chooserButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.chooserButton.setBorder(MoneydanceLAF.homePageBorder);
        this.chooserButton.setOpaque(false);
        this.chooserButton.addActionListener(this.actionListener);
    }

    @Override
    public JButton getComponent() {
        if (this.chooserButton == null) {
            this.init();
        }
        return this.chooserButton;
    }
}
