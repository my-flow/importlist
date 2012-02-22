/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.TableModel;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.modules.features.importlist.controller.FileTableModel;
import com.moneydance.modules.features.importlist.controller.FileTableRowSorter;
import com.moneydance.modules.features.importlist.util.Helper;

/**
 * @author Florian J. Breunig
 */
public final class TableListenerTest {

    private JTable table;
    private TableListener tableListener;

    @Before
    public void setUp() {
        this.table = new JTable(new FileTableModel(new ArrayList<File>()));
        this.tableListener = new TableListener(this.table);
    }

    @Test
    public void testColumnMarginChanged() {
        this.tableListener.columnMarginChanged(null);
    }

    @Test
    public void testColumnMoved() {
        final RowSorter<TableModel> rowSorter =
                new FileTableRowSorter(this.table.getModel());
        final List<RowSorter.SortKey> sortKeys =
                new ArrayList<RowSorter.SortKey>();
        sortKeys.add(Helper.getPreferences().getSortKey());
        rowSorter.setSortKeys(sortKeys);
        rowSorter.addRowSorterListener(this.tableListener);
        this.table.setRowSorter(rowSorter);
        TableColumnModelEvent event = new TableColumnModelEvent(
                this.table.getColumnModel(), 0, 1);
        this.tableListener.columnMoved(event);
        this.tableListener.columnMoved(event); // return second time
    }

    @Test
    public void testSorterChanged() {
        final RowSorter<TableModel> rowSorter =
                new FileTableRowSorter(this.table.getModel());
        final List<RowSorter.SortKey> sortKeys =
                new ArrayList<RowSorter.SortKey>();
        sortKeys.add(Helper.getPreferences().getSortKey());
        rowSorter.setSortKeys(sortKeys);
        rowSorter.addRowSorterListener(this.tableListener);
        this.table.setRowSorter(rowSorter);
        this.tableListener.sorterChanged(null);
    }

    @Test
    public void testColumnAdded() {
        this.tableListener.columnAdded(null);
    }

    @Test
    public void testColumnRemoved() {
        this.tableListener.columnRemoved(null);
    }

    @Test
    public void testColumnSelectionChanged() {
        this.tableListener.columnSelectionChanged(null);
    }

}
