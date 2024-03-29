package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.util.Preferences;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.IntStream;

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
 * <code>PreferencesImpl</code>.
 *
 * @author Florian J. Breunig
 */
final class TableListener implements TableColumnModelListener, RowSorterListener {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(TableListener.class.getName());

    private final Preferences prefs;
    private final JTable table;
    private int lastFrom;
    private int lastTo;

    TableListener(final JTable argTable, final Preferences argPrefs) {
        this.table = argTable;
        this.prefs = argPrefs;
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
        this.lastTo = event.getToIndex();

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
        Map<String, String> map = new ConcurrentHashMap<>(this.table.getColumnCount());
        IntStream.range(0, this.table.getColumnCount()).forEach(column ->
            map.put(
                Integer.toString(column),
                this.table.getColumnName(column))
        );
        this.prefs.setColumnNames(map);
    }

    private void saveColumnWidths() {
        IntStream.range(0, this.table.getColumnCount()).forEach(column -> {
            final String columnName = this.table.getColumnName(column);
            this.prefs.setColumnWidths(
                    column,
                    this.table.getColumn(columnName).getWidth());
        });
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
