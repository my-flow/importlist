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
