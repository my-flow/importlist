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

package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.util.AlphanumComparator;

import java.util.Comparator;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 * An implementation of <code>RowSorter</code> that provides natural sorting
 * for a <code>ListTableModel</code>.
 *
 * @author Florian J. Breunig
 */
final class FileTableRowSorter extends TableRowSorter<TableModel> {

    private final String descName;
    private final String descModified;

    FileTableRowSorter(final TableModel argTableModel, final String argDescName, final String argDescModified) {
        super(argTableModel);
        this.descName = argDescName;
        this.descModified = argDescModified;
    }

    @Override
    public boolean isSortable(final int column) {
        String columnName = this.getModel().getColumnName(column);
        return this.descName.equals(columnName)
                || this.descModified.equals(columnName);
    }

    @Override
    public Comparator<?> getComparator(final int column) {
        final String columnName = this.getModel().getColumnName(column);
        Comparator<?> comparator;
        if (this.descName.equals(columnName)) {
            comparator = AlphanumComparator.ALPHANUM;
        } else if (this.descModified.equals(columnName)) {
            comparator = super.getComparator(column);
        } else {
            throw new IllegalArgumentException(String.format(
                    "Could not find comparator for column %d", column));
        }
        return comparator;
    }
}
