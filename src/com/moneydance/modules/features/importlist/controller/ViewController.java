/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.controller;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.view.ColumnFactory;
import com.moneydance.modules.features.importlist.view.JCustomScrollPane;

/**
 * The user interface that is displayed on the homepage.
 */
public final class ViewController implements HomePageView, Observer {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(ViewController.class);

    private final Preferences           prefs;
    private final FileAdmin             fileAdmin;
    private       boolean               initialized;
    private       FileTableModel        fileTableModel;
    private       JLabel                emptyLabel;
    private       JTable                table;
    private       ColumnFactory         columnFactory;
    private       JScrollPane           scrollPane;
    private       boolean               dirty;


    public ViewController(
            final String argBaseDirectory,
            final FeatureModuleContext argContext) {
        this.prefs      = Helper.getPreferences();
        this.fileAdmin  = new FileAdmin(argBaseDirectory, argContext);
        this.fileAdmin.addObserver(this);
    }


    private void init() {
        this.fileTableModel = new FileTableModel(this.fileAdmin.getFiles());

        this.emptyLabel = new JLabel();
        this.emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.table = new JTable(this.fileTableModel);
        this.table.setOpaque(false);
        this.table.setShowGrid(false);
        this.table.setShowVerticalLines(false);
        this.table.setShowHorizontalLines(false);
        this.table.setIntercellSpacing(new Dimension(0, 0));
        this.table.setColumnSelectionAllowed(false);
        this.table.setRowSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);

        JTableHeader tableHeader     = this.table.getTableHeader();
        TableColumnModel columnModel = this.table.getColumnModel();

        this.columnFactory = new ColumnFactory(
                this.fileAdmin,
                tableHeader.getDefaultRenderer());

        TableColumn nameCol = this.table.getColumn(this.prefs.getDescName());
        int nameColNo = columnModel.getColumnIndex(this.prefs.getDescName());
        nameCol.setIdentifier(this.prefs.getDescName());
        nameCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        nameCol.setCellRenderer(
                this.columnFactory.getLabelNameRenderer());
        nameCol.setMinWidth(this.prefs.getMinColumnWidth());
        nameCol.setPreferredWidth(this.prefs.getColumnWidths(nameColNo));

        TableColumn modifiedCol = this.table.getColumn(
                this.prefs.getDescModified());
        int modifiedColNo = columnModel.getColumnIndex(
                this.prefs.getDescModified());
        modifiedCol.setIdentifier(this.prefs.getDescModified());
        modifiedCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        modifiedCol.setCellRenderer(
                this.columnFactory.getLabelModifiedRenderer());
        modifiedCol.setMinWidth(this.prefs.getMinColumnWidth());
        modifiedCol.setPreferredWidth(
                this.prefs.getColumnWidths(modifiedColNo));

        TableColumn importCol = this.table.getColumn(
                this.prefs.getDescImport());
        int importColNo = columnModel.getColumnIndex(
                this.prefs.getDescImport());
        importCol.setIdentifier(this.prefs.getDescImport());
        importCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        importCol.setCellRenderer(this.columnFactory.getButtonRenderer());
        importCol.setCellEditor(this.columnFactory.getImportEditor());
        importCol.setResizable(this.prefs.isButtonResizable());
        importCol.setMinWidth(this.prefs.getMinColumnWidth());
        importCol.setPreferredWidth(this.prefs.getColumnWidths(importColNo));

        TableColumn deleteCol = this.table.getColumn(
                this.prefs.getDescDelete());
        int deleteColNo = columnModel.getColumnIndex(
                this.prefs.getDescDelete());
        deleteCol.setIdentifier(this.prefs.getDescDelete());
        deleteCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        deleteCol.setCellRenderer(this.columnFactory.getButtonRenderer());
        deleteCol.setCellEditor(this.columnFactory.getDeleteEditor());
        deleteCol.setResizable(this.prefs.isButtonResizable());
        deleteCol.setMinWidth(this.prefs.getMinColumnWidth());
        deleteCol.setPreferredWidth(this.prefs.getColumnWidths(deleteColNo));

        // resizing the columns
        TableListener tableListener = new TableListener(this.table);
        columnModel.addColumnModelListener(tableListener);

        // reordering the columns
        tableHeader.setReorderingAllowed(this.prefs.isReorderingAllowed());

        // sorting the columns
        RowSorter<TableModel> rowSorter =
            new FileTableRowSorter(this.fileTableModel);
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(this.prefs.getSortKey());
        rowSorter.setSortKeys(sortKeys);
        rowSorter.addRowSorterListener(tableListener);
        this.table.setRowSorter(rowSorter);

        this.table.setPreferredScrollableViewportSize(
                new Dimension(
                        this.prefs.getMinimumTableWidth(),
                        this.prefs.getMinimumTableHeight()));

        this.scrollPane = new JCustomScrollPane();
        //see moneydance.com/pipermail/moneydance-dev/2006-September/000075.html
        try {
            this.scrollPane.setBorder(MoneydanceLAF.homePageBorder);
        } catch (Throwable e) {
            LOG.warn(e.getMessage(), e);
        }

        this.setDirty(true);
        this.refresh();
        this.fileAdmin.startMonitor();
    }


    @Override
    public synchronized void refresh() {
        if (this.scrollPane == null || !this.scrollPane.isVisible()) {
            return;
        }

        this.prefs.reload();
        this.columnFactory.setDateFormatter(this.prefs.getDateFormatter());
        this.columnFactory.setTimeFormatter(this.prefs.getTimeFormatter());
        this.columnFactory.setForeground(this.prefs.getForeground());
        this.columnFactory.setBackground(this.prefs.getBackground());
        this.columnFactory.setBackgroundAlt(this.prefs.getBackgroundAlt());
        this.scrollPane.setBackground(this.prefs.getBackground());
        TableColumn nameCol = this.table.getColumn(this.prefs.getDescName());
        nameCol.setHeaderValue(this.prefs.getHeaderValueName());
        TableColumn modifiedCol = this.table.getColumn(
                this.prefs.getDescModified());
        modifiedCol.setHeaderValue(this.prefs.getHeaderValueModified());
        TableColumn importCol = this.table.getColumn(
                this.prefs.getDescImport());
        importCol.setHeaderValue(this.prefs.getHeaderValueImport());
        TableColumn deleteCol = this.table.getColumn(
                this.prefs.getDescDelete());
        deleteCol.setHeaderValue(
                this.prefs.getHeaderValueDelete());

        if (this.isDirty()) {
            this.setDirty(false);
            this.fileAdmin.reloadFiles();
            this.fileTableModel.fireTableDataChanged();
        }

        if (this.fileTableModel.getRowCount() == 0) {
            String emptyMessage = this.prefs.getEmptyMessage(
                    this.fileAdmin.getBaseDirectory().getAbsolutePath());
            this.emptyLabel.setText(emptyMessage);
            this.emptyLabel.setBackground(this.prefs.getBackground());
            this.emptyLabel.setFont(this.prefs.getBodyFont());
            this.scrollPane.setViewportView(this.emptyLabel);
            this.scrollPane.setMinimumSize(
                    new Dimension(
                            this.prefs.getPreferredEmptyMessageWidth(),
                            this.prefs.getPreferredEmptyMessageHeight()));
            this.scrollPane.setPreferredSize(
                    new Dimension(
                            this.prefs.getPreferredEmptyMessageWidth(),
                            this.prefs.getPreferredEmptyMessageHeight()));
            this.scrollPane.setMaximumSize(
                    new Dimension(
                            this.prefs.getPreferredEmptyMessageWidth(),
                            this.prefs.getPreferredEmptyMessageHeight()));
        } else {
            this.table.setBackground(this.prefs.getBackground());
            this.table.setRowHeight(this.prefs.getBodyRowHeight());
            this.scrollPane.setViewportView(this.table);
            this.scrollPane.setMinimumSize(
                    new Dimension(
                            this.prefs.getMinimumTableWidth(),
                            this.prefs.getMinimumTableHeight()));
            this.scrollPane.setPreferredSize(
                    new Dimension(
                            this.prefs.getPreferredTableWidth(
                                    this.fileTableModel.getRowCount()),
                            this.prefs.getPreferredTableHeight(
                                    this.fileTableModel.getRowCount())));
            this.scrollPane.setMaximumSize(
                    new Dimension(
                            this.prefs.getMaximumTableWidth(),
                            this.prefs.getMaximumTableHeight()));
        }
    }


    @Override
    public void update(final Observable observable, final Object object) {
        this.setDirty(true);
        this.refresh();
    }


    @Override
    public JComponent getGUIView(final RootAccount rootAccount) {
        if (!this.initialized) {
            this.initialized = true;
            this.init();
        }
        return this.scrollPane;
    }


    @Override
    public void reset() {
        // ignore
    }


    @Override
    public void setActive(final boolean active) {
        this.scrollPane.setVisible(active);
        this.refresh();
    }


    @Override
    public String getID() {
        return this.prefs.getExtensionIdentifier();
    }


    @Override
    public String toString() {
        return this.prefs.getExtensionName();
    }


    public void resetBaseDirectory() {
        this.fileAdmin.resetBaseDirectory();
    }


    public void cleanup() {
        this.fileAdmin.stopMonitor();
    }


    public void setDirty(final boolean argDirty) {
        this.dirty = argDirty;
    }


    public boolean isDirty() {
        return this.dirty;
    }
}
