package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * @author Florian J. Breunig
 */
abstract class AbstractTableFactory implements ComponentFactory {

    private final JTable table;
    private final TableColumnModel columnModel;
    private final Settings settings;

    AbstractTableFactory(
            final TableModel argTableModel,
            final Settings argSettings) {
        this.table = new JTable(argTableModel);
        this.table.setOpaque(false);
        this.table.setShowGrid(false);
        this.table.setShowVerticalLines(false);
        this.table.setShowHorizontalLines(false);
        this.table.setColumnSelectionAllowed(false);
        this.table.setRowSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);
        this.columnModel = this.table.getColumnModel();
        this.settings = argSettings;
    }

    JTable getTable() {
        return this.table;
    }

    TableColumnModel getColumnModel() {
        return this.columnModel;
    }

    final TableColumn buildColumn(final String description) {
        TableColumn column = this.table.getColumn(description);
        int colNo = this.columnModel.getColumnIndex(description);
        column.setIdentifier(description);
        column.setMinWidth(this.settings.getMinColumnWidth());
        column.setPreferredWidth(Helper.INSTANCE.getPreferences().getColumnWidths(colNo));
        return column;
    }

    protected final Settings getSettings() {
        return this.settings;
    }
}
