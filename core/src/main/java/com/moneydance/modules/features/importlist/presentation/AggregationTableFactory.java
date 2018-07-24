// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2018 Florian J. Breunig
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

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * @author Florian J. Breunig
 */
public final class AggregationTableFactory extends AbstractTableFactory {

    public AggregationTableFactory(
            final TableModel argTableModel,
            final FileAdmin argFileAdmin) {
        super(argTableModel);

        final Settings settings = Helper.INSTANCE.getSettings();
        final JTable table = this.getTable();

        table.setIntercellSpacing(
                new Dimension(
                        0,
                        settings.getTableHeightOffset()));

        JTableHeader tableHeader = table.getTableHeader();

        ColumnFactory columnFactory = new ColumnFactory(
                argFileAdmin,
                tableHeader.getDefaultRenderer(),
                Helper.INSTANCE.getPreferences().getDateFormatter(),
                Preferences.getTimeFormatter());

        // name column
        final String descName = settings.getDescName();
        final TableColumn nameCol = buildColumn(descName);
        nameCol.setCellRenderer(columnFactory.getLabelNameAllRenderer());

        // modified column
        final String descModified = settings.getDescModified();
        final TableColumn modifiedCol = buildColumn(descModified);
        modifiedCol.setCellRenderer(columnFactory.getLabelModifiedAllRenderer());

        // import column
        final String descImport = settings.getDescImport();
        final TableColumn importCol = buildColumn(descImport);
        importCol.setCellRenderer(columnFactory.getButtonAllRenderer());
        importCol.setCellEditor(columnFactory.getImportAllEditor());
        importCol.setResizable(settings.isButtonResizable());

        // delete column
        final String descDelete = settings.getDescDelete();
        final TableColumn deleteCol = buildColumn(descDelete);
        deleteCol.setCellRenderer(columnFactory.getButtonAllRenderer());
        deleteCol.setCellEditor(columnFactory.getDeleteAllEditor());
        deleteCol.setResizable(settings.isButtonResizable());
    }

    @Override
    public JTable getComponent() {
        final JTable table = this.getTable();
        int bodyRowHeight = Helper.INSTANCE.getPreferences().getBodyRowHeight();
        int tableHeightOffset = Helper.INSTANCE.getSettings().getTableHeightOffset();

        table.setRowHeight(
                bodyRowHeight
                + tableHeightOffset);
        table.setMinimumSize(
                new Dimension(
                        Helper.INSTANCE.getSettings().getMinimumTableWidth(),
                        bodyRowHeight
                        + tableHeightOffset));
        table.setPreferredSize(
                new Dimension(
                        Helper.INSTANCE.getPreferences().getPreferredTableWidth(),
                        bodyRowHeight
                        + tableHeightOffset));
        table.setMaximumSize(
                new Dimension(
                        Preferences.getMaximumTableWidth(),
                        bodyRowHeight
                        + tableHeightOffset));

        return table;
    }
}
