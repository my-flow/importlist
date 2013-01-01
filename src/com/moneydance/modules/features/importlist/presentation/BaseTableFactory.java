/*
 * Import List - http://my-flow.github.io/importlist/
 * Copyright (C) 2011-2013 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.presentation;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

/**
 * @author Florian J. Breunig
 */
public final class BaseTableFactory implements ComponentFactory {

    private final   TableModel      tableModel;
    private final   FileAdmin       fileAdmin;
    private         Preferences     prefs;
    private         Settings        settings;
    private         Localizable     localizable;
    private         JTable          table;
    private         ColumnFactory   columnFactory;
    private         JScrollPane     scrollPane;

    public BaseTableFactory(final TableModel argTableModel,
            final FileAdmin argFileAdmin) {
        this.tableModel = argTableModel;
        this.fileAdmin  = argFileAdmin;
    }

    private void init() {
        this.prefs       = Helper.INSTANCE.getPreferences();
        this.settings    = Helper.INSTANCE.getSettings();
        this.localizable = Helper.INSTANCE.getLocalizable();

        this.table = new JTable(this.tableModel);
        this.table.setOpaque(false);
        this.table.setShowGrid(false);
        this.table.setShowVerticalLines(false);
        this.table.setShowHorizontalLines(false);
        this.table.setIntercellSpacing(new Dimension(0, 0));
        this.table.setColumnSelectionAllowed(false);
        this.table.setRowSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);
        JTableHeader tableHeader         = this.table.getTableHeader();
        TableColumnModel baseColumnModel = this.table.getColumnModel();

        this.columnFactory = new ColumnFactory(
                this.fileAdmin,
                this.prefs.getForeground(),
                this.prefs.getBackground(),
                this.prefs.getBackgroundAlt(),
                tableHeader.getDefaultRenderer(),
                this.prefs.getDateFormatter(),
                this.prefs.getTimeFormatter());

        TableColumn nameCol = this.table.getColumn(
                this.settings.getDescName());
        int nameColNo = baseColumnModel.getColumnIndex(
                this.settings.getDescName());
        nameCol.setIdentifier(this.settings.getDescName());
        nameCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        nameCol.setCellRenderer(
                this.columnFactory.getLabelNameOneRenderer());
        nameCol.setMinWidth(this.settings.getMinColumnWidth());
        nameCol.setPreferredWidth(this.prefs.getColumnWidths(nameColNo));


        TableColumn modifiedCol = this.table.getColumn(
                this.settings.getDescModified());
        int modifiedColNo = baseColumnModel.getColumnIndex(
                this.settings.getDescModified());
        modifiedCol.setIdentifier(this.settings.getDescModified());
        modifiedCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        modifiedCol.setCellRenderer(
                this.columnFactory.getLabelModifiedOneRenderer());
        modifiedCol.setMinWidth(this.settings.getMinColumnWidth());
        modifiedCol.setPreferredWidth(
                this.prefs.getColumnWidths(modifiedColNo));


        TableColumn importCol = this.table.getColumn(
                this.settings.getDescImport());
        int importColNo = baseColumnModel.getColumnIndex(
                this.settings.getDescImport());
        importCol.setIdentifier(this.settings.getDescImport());
        importCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        importCol.setCellRenderer(this.columnFactory.getButtonOneRenderer());
        importCol.setCellEditor(this.columnFactory.getImportOneEditor());
        importCol.setResizable(this.settings.isButtonResizable());
        importCol.setMinWidth(this.settings.getMinColumnWidth());
        importCol.setPreferredWidth(this.prefs.getColumnWidths(importColNo));


        TableColumn deleteCol = this.table.getColumn(
                this.settings.getDescDelete());
        int deleteColNo = baseColumnModel.getColumnIndex(
                this.settings.getDescDelete());
        deleteCol.setIdentifier(this.settings.getDescDelete());
        deleteCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        deleteCol.setCellRenderer(this.columnFactory.getButtonOneRenderer());
        deleteCol.setCellEditor(this.columnFactory.getDeleteOneEditor());
        deleteCol.setResizable(this.settings.isButtonResizable());
        deleteCol.setMinWidth(this.settings.getMinColumnWidth());
        deleteCol.setPreferredWidth(this.prefs.getColumnWidths(deleteColNo));


        // resizing the columns
        TableListener tableListener = new TableListener(this.table);
        baseColumnModel.addColumnModelListener(tableListener);

        // reordering the columns
        tableHeader.setReorderingAllowed(this.settings.isReorderingAllowed());

        // sorting the columns
        final RowSorter<TableModel> rowSorter =
                new FileTableRowSorter(this.tableModel);
        final List<RowSorter.SortKey> sortKeys =
                new ArrayList<RowSorter.SortKey>();
        sortKeys.add(this.prefs.getSortKey());
        rowSorter.setSortKeys(sortKeys);
        rowSorter.addRowSorterListener(tableListener);
        this.table.setRowSorter(rowSorter);

        this.table.setPreferredScrollableViewportSize(
                new Dimension(
                        this.settings.getMinimumTableWidth(),
                        this.settings.getMinimumTableHeight()));

        this.scrollPane = new JCustomScrollPane();
    }

    @Override
    public JScrollPane getComponent() {
        if (this.table == null) {
            this.init();
        }
        this.table.setBackground(this.prefs.getBackground());
        this.table.setFont(this.prefs.getBodyFont());
        this.table.setRowHeight(this.prefs.getBodyRowHeight());
        this.columnFactory.setDateFormatter(this.prefs.getDateFormatter());
        this.columnFactory.setTimeFormatter(this.prefs.getTimeFormatter());
        this.columnFactory.setForeground(this.prefs.getForeground());
        this.columnFactory.setBackground(this.prefs.getBackground());
        this.columnFactory.setBackgroundAlt(this.prefs.getBackgroundAlt());

        TableColumn nameCol = this.table.getColumn(
                this.settings.getDescName());
        nameCol.setHeaderValue(this.localizable.getHeaderValueName());
        TableColumn modifiedCol = this.table.getColumn(
                this.settings.getDescModified());
        modifiedCol.setHeaderValue(this.localizable.getHeaderValueModified());
        TableColumn importCol = this.table.getColumn(
                this.settings.getDescImport());
        importCol.setHeaderValue(this.localizable.getHeaderValueImport());
        TableColumn deleteCol = this.table.getColumn(
                this.settings.getDescDelete());
        deleteCol.setHeaderValue(
                this.localizable.getHeaderValueDelete());

        this.scrollPane.setViewportView(this.table);

        this.scrollPane.setBackground(this.prefs.getBackground());
        this.scrollPane.setMinimumSize(
                new Dimension(
                        this.settings.getMinimumTableWidth(),
                        this.settings.getMinimumTableHeight()));
        this.scrollPane.setPreferredSize(
                new Dimension(
                        this.prefs.getPreferredTableWidth(),
                                this.prefs.getPreferredTableHeight(
                                        this.tableModel.getRowCount())));
        this.scrollPane.setMaximumSize(
                new Dimension(
                        this.prefs.getMaximumTableWidth(),
                        this.prefs.getMaximumTableHeight()));

        return this.scrollPane;
    }

    public ColumnFactory getColumnFactory() {
        if (this.columnFactory == null) {
            this.init();
        }
        return this.columnFactory;
    }
}
