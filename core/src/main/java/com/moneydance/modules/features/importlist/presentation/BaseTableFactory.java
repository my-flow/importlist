// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2020 Florian J. Breunig
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
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * @author Florian J. Breunig
 */
public final class BaseTableFactory extends AbstractTableFactory {

    private final TableModel tableModel;
    private final ColumnFactory columnFactory;
    private final JScrollPane scrollPane;

    public BaseTableFactory(
            final TableModel argTableModel,
            final FileAdmin argFileAdmin,
            final ColorScheme evenColorScheme,
            final ColorScheme oddColorScheme,
            final Settings argSettings,
            final Preferences argPrefs) {
        super(argTableModel, argSettings);
        this.tableModel = argTableModel;

        final JTable table = this.getTable();

        table.setIntercellSpacing(new Dimension(0, 0));
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setOpaque(false);

        this.columnFactory = new ColumnFactory(
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
        nameCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        nameCol.setCellRenderer(this.columnFactory.getLabelNameOneRenderer());

        // modified column
        final String descModified = argSettings.getDescModified();
        final TableColumn modifiedCol = buildColumn(descModified);
        modifiedCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        modifiedCol.setCellRenderer(this.columnFactory.getLabelModifiedOneRenderer());

        // import column
        final String descImport = argSettings.getDescImport();
        final TableColumn importCol = buildColumn(descImport);
        importCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        importCol.setCellRenderer(this.columnFactory.getButtonOneRenderer());
        importCol.setCellEditor(this.columnFactory.getImportOneEditor());
        importCol.setResizable(argSettings.isButtonResizable());

        // delete column
        final String descDelete = argSettings.getDescDelete();
        final TableColumn deleteCol = buildColumn(descDelete);
        deleteCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        deleteCol.setCellRenderer(this.columnFactory.getButtonOneRenderer());
        deleteCol.setCellEditor(this.columnFactory.getDeleteOneEditor());
        deleteCol.setResizable(argSettings.isButtonResizable());

        // resizing the columns
        TableListener tableListener = new TableListener(table, argPrefs);
        this.getColumnModel().addColumnModelListener(tableListener);

        // reordering the columns
        tableHeader.setReorderingAllowed(argSettings.isReorderingAllowed());

        // sorting the columns
        final RowSorter<TableModel> rowSorter =
                new FileTableRowSorter(this.tableModel, argSettings.getDescName(), argSettings.getDescModified());
        final List<RowSorter.SortKey> sortKeys =
                new ArrayList<>();
        sortKeys.add(argPrefs.getSortKey());
        rowSorter.setSortKeys(sortKeys);
        rowSorter.addRowSorterListener(tableListener);
        table.setRowSorter(rowSorter);

        table.setPreferredScrollableViewportSize(
                new Dimension(
                        argSettings.getMinimumTableWidth(),
                        argSettings.getMinimumTableHeight()));

        this.scrollPane = new JCustomScrollPane();
    }

    @Override
    public JScrollPane getComponent() {
        final Settings settings = this.getSettings();
        final Preferences prefs = Helper.INSTANCE.getPreferences();
        final Localizable localizable = Helper.INSTANCE.getLocalizable();

        final JTable table = this.getTable();
        table.setRowHeight(prefs.getBodyRowHeight());
        this.columnFactory.setDateFormatter(prefs.getDateFormatter());
        this.columnFactory.setTimeFormatter(prefs.getTimeFormatter());
        this.columnFactory.setForeground(prefs.getForeground());
        this.columnFactory.setBackground(prefs.getBackground());
        this.columnFactory.setBackgroundAlt(prefs.getBackgroundAlt());

        TableColumn nameCol = table.getColumn(settings.getDescName());
        nameCol.setHeaderValue(localizable.getHeaderValueName());
        TableColumn modifiedCol = table.getColumn(settings.getDescModified());
        modifiedCol.setHeaderValue(localizable.getHeaderValueModified());
        TableColumn importCol = table.getColumn(settings.getDescImport());
        importCol.setHeaderValue(localizable.getHeaderValueImport());
        TableColumn deleteCol = table.getColumn(settings.getDescDelete());
        deleteCol.setHeaderValue(localizable.getHeaderValueDelete());

        this.scrollPane.setViewportView(table);

        this.scrollPane.setMinimumSize(
                new Dimension(
                        settings.getMinimumTableWidth(),
                        settings.getMinimumTableHeight()));
        this.scrollPane.setPreferredSize(
                new Dimension(
                        prefs.getPreferredTableWidth(),
                        prefs.getPreferredTableHeight(
                                this.tableModel.getRowCount())));
        this.scrollPane.setMaximumSize(
                new Dimension(
                        prefs.getMaximumTableWidth(),
                        prefs.getMaximumTableHeight()));

        return this.scrollPane;
    }

    public ColumnFactory getColumnFactory() {
        return this.columnFactory;
    }
}
