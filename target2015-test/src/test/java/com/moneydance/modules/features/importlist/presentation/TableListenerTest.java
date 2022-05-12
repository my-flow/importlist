package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;
import com.moneydance.modules.features.importlist.controller.FileTableModel;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.TableModel;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class TableListenerTest {

    private Settings settings;
    private Preferences prefs;
    private JTable table;
    private TableListener tableListener;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = DaggerTargetTestComponent.builder().build();
        this.settings = testComponent.settings();
        this.prefs = testComponent.preferences();
        this.table = new JTable(new FileTableModel(testComponent.fileContainer(), this.settings, this.prefs));
        this.tableListener = new TableListener(this.table, this.prefs);
    }

    @Test
    public void testColumnMarginChanged() {
        this.tableListener.columnMarginChanged(null);
    }

    @Test
    public void testColumnMoved() {
        final RowSorter<TableModel> rowSorter =
                new FileTableRowSorter(
                        this.table.getModel(),
                        this.settings.getDescName(),
                        this.settings.getDescModified());
        final List<RowSorter.SortKey> sortKeys =
                new ArrayList<>();
        sortKeys.add(this.prefs.getSortKey());
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
                new FileTableRowSorter(
                        this.table.getModel(),
                        this.settings.getDescName(),
                        this.settings.getDescModified());
        final List<RowSorter.SortKey> sortKeys =
                new ArrayList<>();
        sortKeys.add(this.prefs.getSortKey());
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
