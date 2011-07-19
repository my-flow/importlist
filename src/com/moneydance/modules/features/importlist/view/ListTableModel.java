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

package com.moneydance.modules.features.importlist.view;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.Validate;

import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.util.CustomDateFormat;

/**
 * This class provides a <code>TableModel</code> implementation for a given
 * <code>List</code> of <code>File</code>s. It takes care of the formatting and
 * caching of its table values. The first two columns represent the name of the
 * file and the date of its last modification, the last two columns represent
 * the action buttons to import and delete the file, respectively.
 */
class ListTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 3552703741263935211L;
    private final transient Preferences  prefs;
    private final List<File>             files;
    private CustomDateFormat             dateFormatter;
    private DateFormat                   timeFormatter;

    ListTableModel(final List<File> argFiles) {
        Validate.notNull(argFiles, "argFiles can't be null");
        this.prefs               = Preferences.getInstance();
        this.files               = argFiles;
    }

    @Override
    public final String getValueAt(final int row, final int column) {
        String columnName = this.getColumnName(column);

        if (this.prefs.getDescName().equals(columnName)) {
            File file = this.getFileAt(row);
            if (file == null) {
                return null;
            }
            return this.prefs.getIndentationPrefix() + file.getName();
        }

        if (this.prefs.getDescModified().equals(columnName)) {
            File file = this.getFileAt(row);
            if (file == null) {
                return null;
            }
            Date fileDate   = new Date(file.lastModified());
            return this.prefs.getIndentationPrefix()
            + this.dateFormatter.format(fileDate)
            + " " + this.timeFormatter.format(fileDate);
        }

        if (this.prefs.getDescImport().equals(columnName)) {
            return this.prefs.getLabelImportButton();
        }

        if (this.prefs.getDescDelete().equals(columnName)) {
            return this.prefs.getLabelDeleteButton();
        }

        return null;
    }

    /**
     * Only the last two columns contain buttons and are therefore editable.
     *
     * @param row    the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true  if the column is the last or penultimate one in the model
     */
    @Override
    public final boolean isCellEditable(final int row, final int column) {
        String columnName = this.getColumnName(column);
        return this.prefs.getDescImport().equals(columnName)
        || this.prefs.getDescDelete().equals(columnName);
    }

    @Override
    public final int getColumnCount() {
        return this.prefs.getColumnCount();
    }

    @Override
    public final String getColumnName(final int column) {
        return this.prefs.getColumnName(column);
    }

    @Override
    public final int getRowCount() {
        if (this.files == null) {
            return 0;
        }
        return this.files.size();
    }

    final File getFileAt(final int row) {
        if (this.files == null || row >= this.files.size()) {
            return null;
        }
        return this.files.get(row);
    }

    final void setDateFormatter(
            final CustomDateFormat argDateFormatter) {
        Validate.notNull(argDateFormatter, "argDateFormatter can't be null");
        this.dateFormatter = argDateFormatter;
    }

    final void setTimeFormatter(final DateFormat argTimeFormatter) {
        Validate.notNull(argTimeFormatter, "argTimeFormatter can't be null");
        this.timeFormatter = argTimeFormatter;
    }
}
