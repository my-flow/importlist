// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2013 Florian J. Breunig
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

import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.swing.table.AbstractTableModel;

/**
 * This class provides a <code>TableModel</code> implementation for a given
 * <code>List</code> of <code>File</code>s. It takes care of formatting and
 * caching of its table values. The first two columns represent the name of the
 * file and the date of its last modification, the last two columns represent
 * the action buttons to import and delete the file, respectively.
 * @author Florian J. Breunig
 */
public final class AggregationTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final transient Settings settings;
    private final transient Preferences prefs;
    private final transient Localizable localizable;

    public AggregationTableModel() {
        this.settings    = Helper.INSTANCE.getSettings();
        this.prefs       = Helper.INSTANCE.getPreferences();
        this.localizable = Helper.INSTANCE.getLocalizable();
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return String.class;
    }

    @Override
    public Object getValueAt(final int row, final int column) {
        String columnName = this.getColumnName(column);

        if (this.settings.getDescName().equals(columnName)) {
            return null;
        }
        if (this.settings.getDescModified().equals(columnName)) {
            return null;
        }
        if (this.settings.getDescImport().equals(columnName)) {
            return this.localizable.getLabelImportAllButton();
        }
        if (this.settings.getDescDelete().equals(columnName)) {
            return this.localizable.getLabelDeleteAllButton();
        }
        throw new IllegalArgumentException(String.format(
                "Could not find value for row %d, column %d", row, column));
    }

    /**
     * Only the last two columns contain buttons and are therefore editable.
     *
     * @param row    the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true  if the column is the last or penultimate one in the model
     */
    @Override
    public boolean isCellEditable(final int row, final int column) {
        String columnName = this.getColumnName(column);
        if (this.settings.getDescImport().equals(columnName)) {
            return true;
        }
        if (this.settings.getDescDelete().equals(columnName)) {
            return true;
        }
        return false;
    }

    @Override
    public int getColumnCount() {
        return this.prefs.getColumnCount();
    }

    @Override
    public String getColumnName(final int column) {
        return this.prefs.getColumnName(column);
    }

    @Override
    public int getRowCount() {
        return 1;
    }
}
