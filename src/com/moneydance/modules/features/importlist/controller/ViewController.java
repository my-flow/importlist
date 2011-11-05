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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.AbstractEditor;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

/**
 * The user interface that is displayed on the homepage.
 *
 * @author Florian J. Breunig
 */
public final class ViewController implements HomePageView, Observer {

    private final Preferences           prefs;
    private final Settings              settings;
    private final Localizable           localizable;
    private final FileAdmin             fileAdmin;
    private final Tracker               tracker;
    private       boolean               initialized;
    private       JViewport             viewport;
    private       JLabel                emptyLabel;
    private       JSplitPane            splitPane;
    private       JScrollPane           scrollPane;
    private       AbstractTableModel    baseTableModel;
    private       JTable                baseTable;
    private       AbstractTableModel    aggrTableModel;
    private       JTable                aggrTable;
    private       ColumnFactory         columnFactory;
    private       boolean               dirty;


    public ViewController(
            final String baseDirectory,
            final FeatureModuleContext context,
            final int build) {
        this.prefs       = Helper.getPreferences();
        this.settings    = Helper.getSettings();
        this.localizable = Helper.getLocalizable();
        this.fileAdmin   = new FileAdmin(baseDirectory, context);
        this.fileAdmin.addObserver(this);
        this.tracker     = new Tracker(
                this.prefs.getFullVersion(),
                build,
                this.settings.getTrackingCode());
    }


    private void init() {
        this.viewport = new JViewport();
        this.viewport.setOpaque(false);

        this.emptyLabel = new JLabel();
        this.emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.emptyLabel.setBorder(MoneydanceLAF.homePageBorder);

        this.splitPane = new JCustomSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.splitPane.setResizeWeight(1.0);
        this.splitPane.setDividerSize(0);
        this.splitPane.setContinuousLayout(true);
        this.splitPane.setOpaque(false);
        this.splitPane.setBorder(MoneydanceLAF.homePageBorder);

        this.baseTableModel = new FileTableModel(this.fileAdmin.getFiles());

        this.baseTable = new JTable(this.baseTableModel);
        this.baseTable.setOpaque(false);
        this.baseTable.setShowGrid(false);
        this.baseTable.setShowVerticalLines(false);
        this.baseTable.setShowHorizontalLines(false);
        this.baseTable.setIntercellSpacing(new Dimension(0, 0));
        this.baseTable.setColumnSelectionAllowed(false);
        this.baseTable.setRowSelectionAllowed(false);
        this.baseTable.setCellSelectionEnabled(false);

        this.aggrTableModel = new AggregationTableModel();
        this.aggrTable = new JTable(this.aggrTableModel);
        this.aggrTable.setOpaque(false);
        this.aggrTable.setShowGrid(false);
        this.aggrTable.setShowVerticalLines(false);
        this.aggrTable.setShowHorizontalLines(false);
        this.aggrTable.setIntercellSpacing(
                new Dimension(
                        0,
                        this.settings.getTableHeightOffset()));
        this.aggrTable.setColumnSelectionAllowed(false);
        this.aggrTable.setRowSelectionAllowed(false);
        this.aggrTable.setCellSelectionEnabled(false);

        JTableHeader tableHeader         = this.baseTable.getTableHeader();
        TableColumnModel baseColumnModel = this.baseTable.getColumnModel();
        TableColumnModel aggrColumnModel = this.aggrTable.getColumnModel();

        this.columnFactory = new ColumnFactory(
                this.fileAdmin,
                tableHeader.getDefaultRenderer());

        TableColumn nameCol = this.baseTable.getColumn(
                this.settings.getDescName());
        int nameColNo = baseColumnModel.getColumnIndex(
                this.settings.getDescName());
        nameCol.setIdentifier(this.settings.getDescName());
        nameCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        nameCol.setCellRenderer(
                this.columnFactory.getLabelNameOneRenderer());
        nameCol.setMinWidth(this.settings.getMinColumnWidth());
        nameCol.setPreferredWidth(this.prefs.getColumnWidths(nameColNo));

        nameCol = this.aggrTable.getColumn(this.settings.getDescName());
        nameColNo = aggrColumnModel.getColumnIndex(this.settings.getDescName());
        nameCol.setIdentifier(this.settings.getDescName());
        nameCol.setCellRenderer(
                this.columnFactory.getLabelNameAllRenderer());
        nameCol.setMinWidth(this.settings.getMinColumnWidth());
        nameCol.setPreferredWidth(this.prefs.getColumnWidths(nameColNo));


        TableColumn modifiedCol = this.baseTable.getColumn(
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

        modifiedCol = this.aggrTable.getColumn(
                this.settings.getDescModified());
        modifiedColNo = aggrColumnModel.getColumnIndex(
                this.settings.getDescModified());
        modifiedCol.setIdentifier(this.settings.getDescModified());
        modifiedCol.setCellRenderer(
                this.columnFactory.getLabelModifiedAllRenderer());
        modifiedCol.setMinWidth(this.settings.getMinColumnWidth());
        modifiedCol.setPreferredWidth(
                this.prefs.getColumnWidths(modifiedColNo));


        TableColumn importCol = this.baseTable.getColumn(
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

        importCol = this.aggrTable.getColumn(
                this.settings.getDescImport());
        importColNo = aggrColumnModel.getColumnIndex(
                this.settings.getDescImport());
        importCol.setIdentifier(this.settings.getDescImport());
        importCol.setCellRenderer(this.columnFactory.getButtonAllRenderer());
        importCol.setCellEditor(this.columnFactory.getImportAllEditor());
        importCol.setResizable(this.settings.isButtonResizable());
        importCol.setMinWidth(this.settings.getMinColumnWidth());
        importCol.setPreferredWidth(this.prefs.getColumnWidths(importColNo));


        TableColumn deleteCol = this.baseTable.getColumn(
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

        deleteCol = this.aggrTable.getColumn(
                this.settings.getDescDelete());
        deleteColNo = aggrColumnModel.getColumnIndex(
                this.settings.getDescDelete());
        deleteCol.setIdentifier(this.settings.getDescDelete());
        deleteCol.setCellRenderer(this.columnFactory.getButtonAllRenderer());
        deleteCol.setCellEditor(this.columnFactory.getDeleteAllEditor());
        deleteCol.setResizable(this.settings.isButtonResizable());
        deleteCol.setMinWidth(this.settings.getMinColumnWidth());
        deleteCol.setPreferredWidth(this.prefs.getColumnWidths(deleteColNo));


        // resizing the columns
        TableListener tableListener = new TableListener(this.baseTable);
        baseColumnModel.addColumnModelListener(tableListener);

        // reordering the columns
        tableHeader.setReorderingAllowed(this.settings.isReorderingAllowed());

        // sorting the columns
        RowSorter<TableModel> rowSorter =
            new FileTableRowSorter(this.baseTableModel);
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(this.prefs.getSortKey());
        rowSorter.setSortKeys(sortKeys);
        rowSorter.addRowSorterListener(tableListener);
        this.baseTable.setRowSorter(rowSorter);

        this.baseTable.setPreferredScrollableViewportSize(
                new Dimension(
                        this.settings.getMinimumTableWidth(),
                        this.settings.getMinimumTableHeight()));

        this.scrollPane = new JCustomScrollPane();

        this.setDirty(true);
        this.refresh();
        this.fileAdmin.startMonitor();

        this.tracker.track("Display");
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
        TableColumn nameCol = this.baseTable.getColumn(
                this.settings.getDescName());
        nameCol.setHeaderValue(this.localizable.getHeaderValueName());
        TableColumn modifiedCol = this.baseTable.getColumn(
                this.settings.getDescModified());
        modifiedCol.setHeaderValue(this.localizable.getHeaderValueModified());
        TableColumn importCol = this.baseTable.getColumn(
                this.settings.getDescImport());
        importCol.setHeaderValue(this.localizable.getHeaderValueImport());
        TableColumn deleteCol = this.baseTable.getColumn(
                this.settings.getDescDelete());
        deleteCol.setHeaderValue(
                this.localizable.getHeaderValueDelete());

        if (this.isDirty()) {
            this.setDirty(false);
            this.fileAdmin.reloadFiles();
            this.baseTableModel.fireTableDataChanged();
            this.aggrTableModel.fireTableDataChanged();
        }

        // display a label iff there are no files in the list
        if (this.baseTableModel.getRowCount() == 0) {
            String emptyMessage = this.localizable.getEmptyMessage(
                    this.fileAdmin.getBaseDirectory().getAbsolutePath());
            this.emptyLabel.setText(emptyMessage);
            this.emptyLabel.setBackground(this.prefs.getBackground());
            this.emptyLabel.setFont(this.prefs.getBodyFont());
            this.viewport.setView(this.emptyLabel);
            this.viewport.setMinimumSize(
                    new Dimension(
                            this.settings.getPreferredEmptyMessageWidth(),
                            this.settings.getPreferredEmptyMessageHeight()));
            this.viewport.setPreferredSize(
                    new Dimension(
                            this.settings.getPreferredEmptyMessageWidth(),
                            this.settings.getPreferredEmptyMessageHeight()));
            this.viewport.setMaximumSize(
                    new Dimension(
                            this.settings.getPreferredEmptyMessageWidth(),
                            this.settings.getPreferredEmptyMessageHeight()));
            return;
        }

        this.baseTable.setBackground(this.prefs.getBackground());
        this.baseTable.setFont(this.prefs.getBodyFont());
        this.baseTable.setRowHeight(this.prefs.getBodyRowHeight());
        this.scrollPane.setViewportView(this.baseTable);

        this.scrollPane.setMinimumSize(
                new Dimension(
                        this.settings.getMinimumTableWidth(),
                        this.settings.getMinimumTableHeight()));
        this.scrollPane.setPreferredSize(
                new Dimension(
                        this.prefs.getPreferredTableWidth(),
                                this.prefs.getPreferredTableHeight(
                                        this.baseTableModel.getRowCount())));
        this.scrollPane.setMaximumSize(
                new Dimension(
                        this.prefs.getMaximumTableWidth(),
                        this.prefs.getMaximumTableHeight()));

        // display only scroll pane iff there is exactly one file in the list
        if (this.baseTableModel.getRowCount() == 1) {
            this.scrollPane.setBorder(MoneydanceLAF.homePageBorder);
            this.viewport.setView(this.scrollPane);
            this.viewport.setMinimumSize(
                    new Dimension(
                            this.settings.getMinimumTableWidth(),
                            this.settings.getMinimumTableHeight()));
            this.viewport.setPreferredSize(
                    new Dimension(
                            this.prefs.getPreferredTableWidth(),
                            this.prefs.getPreferredTableHeight(
                                    this.baseTableModel.getRowCount())));
            this.viewport.setMaximumSize(
                    new Dimension(
                            this.prefs.getMaximumTableWidth(),
                            this.prefs.getMaximumTableHeight()));
            this.registerKeyboardShortcuts4One();
        }

        // display scroll pane and an additional import all/delete all table
        // iff there is more than one file in the list
        if (this.baseTableModel.getRowCount() > 1) {
            this.scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

            this.aggrTable.setBackground(this.prefs.getBackground());
            this.aggrTable.setRowHeight(
                    this.prefs.getBodyRowHeight()
                    + this.settings.getTableHeightOffset());
            this.aggrTable.setMinimumSize(
                    new Dimension(
                            this.settings.getMinimumTableWidth(),
                            this.prefs.getBodyRowHeight()
                            + this.settings.getTableHeightOffset()));
            this.aggrTable.setPreferredSize(
                    new Dimension(
                            this.prefs.getPreferredTableWidth(),
                            this.prefs.getBodyRowHeight()
                            + this.settings.getTableHeightOffset()));
            this.aggrTable.setMaximumSize(
                    new Dimension(
                            this.prefs.getMaximumTableWidth(),
                            this.prefs.getBodyRowHeight()
                            + this.settings.getTableHeightOffset()));

            this.splitPane.setTopComponent(this.scrollPane);
            this.splitPane.setBottomComponent(this.aggrTable);

            this.viewport.setView(this.splitPane);
            this.viewport.setMinimumSize(null);
            this.viewport.setPreferredSize(null);
            this.viewport.setMaximumSize(null);
            this.registerKeyboardShortcuts4All();
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
        return this.viewport;
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
        return this.settings.getExtensionIdentifier();
    }


    @Override
    public String toString() {
        return this.settings.getExtensionName();
    }


    public void resetBaseDirectory() {
        this.fileAdmin.resetBaseDirectory();
    }


    public void cleanup() {
        this.viewport.setEnabled(false);
        this.viewport.removeAll();
        this.fileAdmin.stopMonitor();
    }


    public void setDirty(final boolean argDirty) {
        this.dirty = argDirty;
    }


    public boolean isDirty() {
        return this.dirty;
    }


    private void registerKeyboardShortcuts4One() {
        AbstractEditor importOneEditor =
            this.columnFactory.getImportOneEditor();
        importOneEditor.registerKeyboardShortcut(this.scrollPane);

        AbstractEditor deleteOneEditor =
            this.columnFactory.getDeleteOneEditor();
        deleteOneEditor.registerKeyboardShortcut(this.scrollPane);
    }


    private void registerKeyboardShortcuts4All() {
        AbstractEditor importAllEditor =
            this.columnFactory.getImportAllEditor();
        importAllEditor.registerKeyboardShortcut(this.scrollPane);

        AbstractEditor deleteAllEditor =
            this.columnFactory.getDeleteAllEditor();
        deleteAllEditor.registerKeyboardShortcut(this.scrollPane);
    }
}
