// Import List - https://www.my-flow.com/importlist/
// Copyright (C) 2011-2021 Florian J. Breunig
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
