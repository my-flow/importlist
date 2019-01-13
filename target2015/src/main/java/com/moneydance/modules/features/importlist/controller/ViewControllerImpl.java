// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2019 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.controller;

import com.infinitekind.moneydance.model.AccountBook;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;

/**
 * The user interface that is displayed on the homepage.
 *
 * @author Florian J. Breunig
 */
public final class ViewControllerImpl extends DefaultViewController implements HomePageView {

    public ViewControllerImpl(
            final FileAdmin fileAdmin,
            final AbstractTableModel baseTableModel,
            final AbstractTableModel aggrTableModel,
            final Settings settings,
            final Preferences prefs) {
        super(fileAdmin, baseTableModel, aggrTableModel, settings, prefs);
    }


    @Override
    @SuppressWarnings("nullness")
    public synchronized void refresh() {
        super.refresh();
    }


    @Override
    public JComponent getGUIView(final AccountBook accountBook) {
        return super.getGUIView();
    }


    @Override
    public void reset() {
        // ignore
    }
}
