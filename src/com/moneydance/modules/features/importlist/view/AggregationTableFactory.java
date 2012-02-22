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

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

/**
 * @author Florian J. Breunig
 */
public final class AggregationTableFactory implements ComponentFactory {

    private Preferences prefs;
    private Settings settings;
    private JTable table;
    private final FileAdmin fileAdmin;
    private final TableModel tableModel;
    private ColumnFactory columnFactory;

    public AggregationTableFactory(final TableModel argTableModel,
            final FileAdmin argFileAdmin) {
        this.tableModel = argTableModel;
        this.fileAdmin  = argFileAdmin;
    }

    private void init() {
        this.prefs       = Helper.getPreferences();
        this.settings    = Helper.getSettings();

        this.table = new JTable(this.tableModel);
        this.table.setOpaque(false);
        this.table.setShowGrid(false);
        this.table.setShowVerticalLines(false);
        this.table.setShowHorizontalLines(false);
        this.table.setIntercellSpacing(
                new Dimension(
                        0,
                        this.settings.getTableHeightOffset()));
        this.table.setColumnSelectionAllowed(false);
        this.table.setRowSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);

        TableColumnModel aggrColumnModel = this.table.getColumnModel();
        JTableHeader tableHeader         = this.table.getTableHeader();

        this.columnFactory = new ColumnFactory(
                this.fileAdmin,
                this.prefs.getForeground(),
                this.prefs.getBackground(),
                this.prefs.getBackgroundAlt(),
                tableHeader.getDefaultRenderer(),
                this.prefs.getDateFormatter(),
                this.prefs.getTimeFormatter());


        TableColumn nameCol = this.table.getColumn(this.settings.getDescName());
        int nameColNo = aggrColumnModel.getColumnIndex(
                this.settings.getDescName());
        nameCol.setIdentifier(this.settings.getDescName());
        nameCol.setCellRenderer(
                this.columnFactory.getLabelNameAllRenderer());
        nameCol.setMinWidth(this.settings.getMinColumnWidth());
        nameCol.setPreferredWidth(this.prefs.getColumnWidths(nameColNo));

        TableColumn modifiedCol = this.table.getColumn(
                this.settings.getDescModified());
        int modifiedColNo = aggrColumnModel.getColumnIndex(
                this.settings.getDescModified());
        modifiedCol.setIdentifier(this.settings.getDescModified());
        modifiedCol.setCellRenderer(
                this.columnFactory.getLabelModifiedAllRenderer());
        modifiedCol.setMinWidth(this.settings.getMinColumnWidth());
        modifiedCol.setPreferredWidth(
                this.prefs.getColumnWidths(modifiedColNo));

        TableColumn importCol = this.table.getColumn(
                this.settings.getDescImport());
        int importColNo = aggrColumnModel.getColumnIndex(
                this.settings.getDescImport());
        importCol.setIdentifier(this.settings.getDescImport());
        importCol.setCellRenderer(this.columnFactory.getButtonAllRenderer());
        importCol.setCellEditor(this.columnFactory.getImportAllEditor());
        importCol.setResizable(this.settings.isButtonResizable());
        importCol.setMinWidth(this.settings.getMinColumnWidth());
        importCol.setPreferredWidth(this.prefs.getColumnWidths(importColNo));

        TableColumn deleteCol = this.table.getColumn(
                this.settings.getDescDelete());
        int deleteColNo = aggrColumnModel.getColumnIndex(
                this.settings.getDescDelete());
        deleteCol.setIdentifier(this.settings.getDescDelete());
        deleteCol.setCellRenderer(this.columnFactory.getButtonAllRenderer());
        deleteCol.setCellEditor(this.columnFactory.getDeleteAllEditor());
        deleteCol.setResizable(this.settings.isButtonResizable());
        deleteCol.setMinWidth(this.settings.getMinColumnWidth());
        deleteCol.setPreferredWidth(this.prefs.getColumnWidths(deleteColNo));
    }

    @Override
    public JTable getComponent() {
        if (this.table == null) {
            this.init();
        }
        this.table.setBackground(this.prefs.getBackground());
        this.table.setFont(this.prefs.getBodyFont());
        this.table.setRowHeight(
                this.prefs.getBodyRowHeight()
                + this.settings.getTableHeightOffset());
        this.table.setMinimumSize(
                new Dimension(
                        this.settings.getMinimumTableWidth(),
                        this.prefs.getBodyRowHeight()
                        + this.settings.getTableHeightOffset()));
        this.table.setPreferredSize(
                new Dimension(
                        this.prefs.getPreferredTableWidth(),
                        this.prefs.getBodyRowHeight()
                        + this.settings.getTableHeightOffset()));
        this.table.setMaximumSize(
                new Dimension(
                        this.prefs.getMaximumTableWidth(),
                        this.prefs.getBodyRowHeight()
                        + this.settings.getTableHeightOffset()));

        return this.table;
    }

    public ColumnFactory getColumnFactory() {
        if (this.columnFactory == null) {
            this.init();
        }
        return this.columnFactory;
    }
}
