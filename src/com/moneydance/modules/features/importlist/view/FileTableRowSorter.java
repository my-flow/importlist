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

import java.util.Comparator;

import javax.swing.DefaultRowSorter;
import javax.swing.table.TableModel;

import org.apache.commons.lang.Validate;

import com.moneydance.modules.features.importlist.util.AlphanumComparator;
import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * An implementation of <code>RowSorter</code> that provides sorting for a
 * <code>ListTableModel</code>.
 */
class FileTableRowSorter extends DefaultRowSorter<TableModel, Integer> {

    private final FileTableModel fileTableModel;
    private final Comparator<?>  comparator;
    private final Preferences    prefs;

    /**
     * @param argListTableModel The table model that is to be sorted
     */
    FileTableRowSorter(final FileTableModel argListTableModel) {
        super();
        Validate.notNull(argListTableModel, "argListTableModel can't be null");
        this.fileTableModel = argListTableModel;
        this.comparator     = new AlphanumComparator();
        this.prefs          = Preferences.getInstance();
        this.setModelWrapper(new FileModelWrapper());
    }

    @Override
    public final boolean isSortable(final int column) {
        String columnName = this.fileTableModel.getColumnName(column);
        return this.prefs.getDescName().equals(columnName)
        || this.prefs.getDescModified().equals(columnName);
    }

    @Override
    public Comparator<?> getComparator(final int column) {
        return this.comparator;
    }

    private class FileModelWrapper
    extends DefaultRowSorter.ModelWrapper<TableModel, Integer> {

        @Override
        public final int getColumnCount() {
            return FileTableRowSorter.this.fileTableModel.getColumnCount();
        }

        @Override
        public final Integer getIdentifier(final int columnIndex) {
            return columnIndex;
        }

        @Override
        public final TableModel getModel() {
            return FileTableRowSorter.this.fileTableModel;
        }

        @Override
        public final int getRowCount() {
            return FileTableRowSorter.this.fileTableModel.getRowCount();
        }

        @Override
        public final Object getValueAt(final int row, final int column) {
            FileTableModel model = FileTableRowSorter.this.fileTableModel;
            String columnName    = model.getColumnName(column);

            Object value = model.getValueAt(row, column);
            if (FileTableRowSorter.this.prefs.getDescModified().equals(
                    columnName)) {
                return model.getFileAt(row).lastModified() + "";
            }
            return value;
        }
    }
}
