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
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
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
            final FileAdmin argFileAdmin,
            final ColorScheme evenColorScheme,
            final ColorScheme oddColorScheme,
            final Settings argSettings,
            final Preferences argPrefs) {
        super(argTableModel, argSettings);

        final JTable table = this.getTable();

        table.setIntercellSpacing(
                new Dimension(
                        0,
                        argSettings.getTableHeightOffset()));

        JTableHeader tableHeader = table.getTableHeader();

        ColumnFactory columnFactory = new ColumnFactory(
                argFileAdmin,
                tableHeader.getDefaultRenderer(),
                argPrefs.getDateFormatter(),
                argPrefs.getTimeFormatter(),
                evenColorScheme,
                oddColorScheme,
                argSettings);

        // name column
        final String descName = argSettings.getDescName();
        final TableColumn nameCol = buildColumn(descName);
        nameCol.setCellRenderer(columnFactory.getLabelNameAllRenderer());

        // modified column
        final String descModified = argSettings.getDescModified();
        final TableColumn modifiedCol = buildColumn(descModified);
        modifiedCol.setCellRenderer(columnFactory.getLabelModifiedAllRenderer());

        // import column
        final String descImport = argSettings.getDescImport();
        final TableColumn importCol = buildColumn(descImport);
        importCol.setCellRenderer(columnFactory.getButtonAllRenderer());
        importCol.setCellEditor(columnFactory.getImportAllEditor());
        importCol.setResizable(argSettings.isButtonResizable());

        // delete column
        final String descDelete = argSettings.getDescDelete();
        final TableColumn deleteCol = buildColumn(descDelete);
        deleteCol.setCellRenderer(columnFactory.getButtonAllRenderer());
        deleteCol.setCellEditor(columnFactory.getDeleteAllEditor());
        deleteCol.setResizable(argSettings.isButtonResizable());
    }

    @Override
    public JTable getComponent() {
        final JTable table = this.getTable();
        final Settings settings = this.getSettings();

        table.setBackground(Helper.INSTANCE.getPreferences().getBackground());

        int bodyRowHeight = Helper.INSTANCE.getPreferences().getBodyRowHeight();
        int tableHeightOffset = settings.getTableHeightOffset();

        table.setRowHeight(
                bodyRowHeight
                + tableHeightOffset);
        table.setMinimumSize(
                new Dimension(
                        settings.getMinimumTableWidth(),
                        bodyRowHeight
                        + tableHeightOffset));
        table.setPreferredSize(
                new Dimension(
                        Helper.INSTANCE.getPreferences().getPreferredTableWidth(),
                        bodyRowHeight
                        + tableHeightOffset));
        table.setMaximumSize(
                new Dimension(
                        Helper.INSTANCE.getPreferences().getMaximumTableWidth(),
                        bodyRowHeight
                        + tableHeightOffset));

        return table;
    }
}
