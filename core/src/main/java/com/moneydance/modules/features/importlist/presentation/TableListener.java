// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2017 Florian J. Breunig
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

import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.util.Hashtable;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

/**
 * This <code>EventListener</code> is notified upon rearrangement, resorting,
 * and resizing of the table. It saves its state in the
 * <code>Preferences</code>.
 *
 * @author Florian J. Breunig
 */
final class TableListener
implements TableColumnModelListener, RowSorterListener {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(TableListener.class.getName());

    private final Preferences prefs;
    private final JTable table;
    private int lastFrom;
    private int lastTo;

    TableListener(final JTable argTable) {
        this.prefs = Helper.INSTANCE.getPreferences();
        this.table = argTable;
    }

    @Override
    public void columnMarginChanged(final ChangeEvent event) {
        LOG.config("Margin of a column changed.");
        this.saveColumnWidths();
    }

    @Override
    public void columnMoved(final TableColumnModelEvent event) {
        if (event.getFromIndex() == this.lastFrom
                && event.getToIndex() == this.lastTo) {
            return;
        }
        LOG.config("Order of columns changed.");
        this.lastFrom = event.getFromIndex();
        this.lastTo   = event.getToIndex();

        this.saveColumnOrder();
        this.saveSortKey();
        this.saveColumnWidths();
    }

    @Override
    public void sorterChanged(final RowSorterEvent event) {
        LOG.config("Sort order of a column changed.");
        this.saveSortKey();
    }

    private void saveColumnOrder() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>(
                this.table.getColumnCount());
        int columnCount = this.table.getColumnCount();
        for (int column = 0; column < columnCount; column++) {
            String columnName = this.table.getColumnName(column);
            hashtable.put(Integer.toString(column), columnName);
        }
        this.prefs.setColumnNames(hashtable);
    }

    private void saveColumnWidths() {
        int columnCount = this.table.getColumnCount();
        for (int column = 0; column < columnCount; column++) {
            String columnName = this.table.getColumnName(column);
            int columnWidth   = this.table.getColumn(columnName).getWidth();
            this.prefs.setColumnWidths(column, columnWidth);
        }
    }

    private void saveSortKey() {
        if (this.table.getRowSorter().getSortKeys().isEmpty()) {
            return;
        }
        RowSorter.SortKey originalSortKey =
                this.table.getRowSorter().getSortKeys().get(0);
        int adjustedColumn = this.table.convertColumnIndexToView(
                originalSortKey.getColumn());
        RowSorter.SortKey adjustedSortKey =
                new RowSorter.SortKey(
                        adjustedColumn,
                        originalSortKey.getSortOrder());
        this.prefs.setSortKey(adjustedSortKey);
    }

    @Override
    public void columnAdded(final TableColumnModelEvent event) {
        // columns cannot be added
    }

    @Override
    public void columnRemoved(final TableColumnModelEvent event) {
        // columns cannot be removed
    }

    @Override
    public void columnSelectionChanged(final ListSelectionEvent event) {
        // ignore selected columns
    }
}
