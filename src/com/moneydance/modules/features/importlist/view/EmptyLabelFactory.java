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

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * @author Florian J. Breunig
 */
public final class EmptyLabelFactory implements ComponentFactory {

    private Preferences prefs;
    private JLabel emptyLabel;

    private void init() {
        this.prefs      = Helper.INSTANCE.getPreferences();
        this.emptyLabel = new JLabel();
        this.emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.emptyLabel.setBorder(MoneydanceLAF.homePageBorder);
    }

    @Override
    public JLabel getComponent() {
        if (this.emptyLabel == null) {
            this.init();
        }
        this.emptyLabel.setBackground(this.prefs.getBackground());
        this.emptyLabel.setFont(this.prefs.getBodyFont());
        return this.emptyLabel;
    }
}
