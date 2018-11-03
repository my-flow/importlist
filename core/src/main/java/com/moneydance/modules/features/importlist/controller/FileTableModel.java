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

package com.moneydance.modules.features.importlist.controller;

import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;


/**
 * This class provides a <code>TableModel</code> implementation for a given
 * <code>List</code> of <code>File</code>s. It takes care of the formatting and
 * caching of its table values. The first two columns represent the name of the
 * file and the date of its last modification, the last two columns represent
 * the action buttons to import and delete the file, respectively.
 * @author Florian J. Breunig
 */
public final class FileTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final Settings settings;
    private final Preferences prefs;
    private final List<File> files;

    public FileTableModel(final List<File> argFiles) {
        super();
        this.settings = Helper.INSTANCE.getSettings();
        this.prefs = Helper.INSTANCE.getPreferences();
        // ESCA-JAVA0256: argFiles is readonly by design
        this.files = argFiles;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        final String columnName = this.getColumnName(columnIndex);

        if (this.settings.getDescName().equals(columnName)) {
            return String.class;
        }
        if (this.settings.getDescModified().equals(columnName)) {
            return Date.class;
        }
        if (this.settings.getDescImport().equals(columnName)) {
            return String.class;
        }
        if (this.settings.getDescDelete().equals(columnName)) {
            return String.class;
        }
        throw new IllegalArgumentException(String.format(
                "Could not find column name for index %d", columnIndex));
    }

    @Override
    public Comparable<? extends Serializable> getValueAt(final int row, final int column) {
        if (row >= this.files.size()) {
            this.fireTableDataChanged();
            throw new IllegalArgumentException(String.format(
                    "Could not find value for row %d, column %d", row, column));
        }
        String columnName = this.getColumnName(column);

        if (this.settings.getDescName().equals(columnName)) {
            final File file = this.files.get(row);
            return file.getName();
        }
        if (this.settings.getDescModified().equals(columnName)) {
            final File file = this.files.get(row);
            return new Date(file.lastModified());
        }
        if (this.settings.getDescImport().equals(columnName)) {
            return Helper.INSTANCE.getLocalizable().getLabelImportOneButton();
        }
        if (this.settings.getDescDelete().equals(columnName)) {
            return Helper.INSTANCE.getLocalizable().getLabelDeleteOneButton();
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
        return this.settings.getDescImport().equals(columnName)
                || this.settings.getDescDelete().equals(columnName);
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
        return this.files.size();
    }
}
