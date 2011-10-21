/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.controller;

import javax.swing.table.AbstractTableModel;

import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * This class provides a <code>TableModel</code> implementation for a given
 * <code>List</code> of <code>File</code>s. It takes care of the formatting and
 * caching of its table values. The first two columns represent the name of the
 * file and the date of its last modification, the last two columns represent
 * the action buttons to import and delete the file, respectively.
 */
final class AggregationTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 3552703741263935211L;
    private final transient Preferences         prefs;

    AggregationTableModel() {
        super();
        this.prefs = Helper.getPreferences();
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return String.class;
    }

    @Override
    public Object getValueAt(final int row, final int column) {
        String columnName = this.getColumnName(column);

        if (this.prefs.getDescName().equals(columnName)) {
            return null;
        }
        if (this.prefs.getDescModified().equals(columnName)) {
            return null;
        }
        if (this.prefs.getDescImport().equals(columnName)) {
            return this.prefs.getLabelImportAllButton();
        }
        if (this.prefs.getDescDelete().equals(columnName)) {
            return this.prefs.getLabelDeleteAllButton();
        }
        throw new IllegalArgumentException(
                "Could not find value for row " + row + ", column " + column);
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
        if (this.prefs.getDescImport().equals(columnName)) {
            return true;
        }
        if (this.prefs.getDescDelete().equals(columnName)) {
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
