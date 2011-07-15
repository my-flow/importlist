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

import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

import org.apache.log4j.Logger;

import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * This <code>EventListener</code> is notified upon rearrangement, resorting,
 * and resizing of the table. It saves the modifications in the
 * <code>Preferences</code>.
 */
class TableListener implements TableColumnModelListener, RowSorterListener {

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(TableListener.class);

    private final Preferences prefs;
    private final JTable table;
    private int lastFrom;
    private int lastTo;

    public TableListener(final JTable argTable) {
        this.prefs = Preferences.getInstance();
        this.table = argTable;
    }

    @Override
    public final void columnMarginChanged(final ChangeEvent e) {
        log.info("Margin of a column changed.");
        this.saveColumnWidths();
    }

    @Override
    public final void columnMoved(final TableColumnModelEvent e) {
        if (e.getFromIndex() == this.lastFrom
                && e.getToIndex() == this.lastTo) {
            return;
        }
        log.info("Order of columns changed.");
        this.lastFrom = e.getFromIndex();
        this.lastTo   = e.getToIndex();

        this.saveColumnOrder();
        this.saveSortKey();
        this.saveColumnWidths();
    }

    @Override
    public final void sorterChanged(final RowSorterEvent e) {
        log.info("Sort order of a column changed.");
        this.saveSortKey();
    }

    private void saveColumnOrder() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        for (int column = 0; column < this.table.getColumnCount(); column++) {
            String columnName = this.table.getColumnName(column);
            hashtable.put(column + "", columnName);
        }
        this.prefs.setColumnNames(hashtable);
    }

    private void saveColumnWidths() {
        for (int column = 0; column < this.table.getColumnCount(); column++) {
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
    public final void columnAdded(final TableColumnModelEvent e) {
    }

    @Override
    public final void columnRemoved(final TableColumnModelEvent e) {
    }

    @Override
    public final void columnSelectionChanged(final ListSelectionEvent e) {
    }
}
