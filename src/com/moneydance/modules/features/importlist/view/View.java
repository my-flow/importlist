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

package com.moneydance.modules.features.importlist.view;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * The user interface that is displayed on the homepage.
 */
public class View implements HomePageView, Runnable {

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(View.class);

    private final Preferences       prefs;
    private final FileAdmin         fileAdmin;
    private final ListTableModel    listTableModel;
    private final JLabel            emptyLabel;
    private final JTable            table;
    private final ColumnFactory     columnFactory;
    private final JScrollPane       scrollPane;


    public View(final FileAdmin argFileAdmin) {
        Validate.notNull(argFileAdmin, "argFileAdmin can't be null");
        this.fileAdmin = argFileAdmin;

        this.prefs = Preferences.getInstance();

        this.emptyLabel = new JLabel();
        this.emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.listTableModel = new ListTableModel(this.fileAdmin.getFiles());

        this.table = new JTable(this.listTableModel);
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
        nameCol.setHeaderValue(this.prefs.getHeaderValueName());
        nameCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        nameCol.setCellRenderer(this.columnFactory.getLabelRenderer());
        nameCol.setMinWidth(this.prefs.getMinColumnWidth());
        nameCol.setPreferredWidth(this.prefs.getColumnWidths(nameColNo));

        TableColumn modifiedCol = this.table.getColumn(
                this.prefs.getDescModified());
        int modifiedColNo = columnModel.getColumnIndex(
                this.prefs.getDescModified());
        modifiedCol.setIdentifier(this.prefs.getDescModified());
        modifiedCol.setHeaderValue(this.prefs.getHeaderValueModified());
        modifiedCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        modifiedCol.setCellRenderer(this.columnFactory.getLabelRenderer());
        modifiedCol.setMinWidth(this.prefs.getMinColumnWidth());
        modifiedCol.setPreferredWidth(
                this.prefs.getColumnWidths(modifiedColNo));

        TableColumn importCol = this.table.getColumn(
                this.prefs.getDescImport());
        int importColNo = columnModel.getColumnIndex(
                this.prefs.getDescImport());
        importCol.setIdentifier(this.prefs.getDescImport());
        importCol.setHeaderValue(this.prefs.getHeaderValueImport());
        importCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        importCol.setCellRenderer(this.columnFactory.getButtonRenderer());
        importCol.setCellEditor(this.columnFactory.getImportEditor());
        importCol.setResizable(this.prefs.getButtonResizable());
        importCol.setMinWidth(this.prefs.getMinColumnWidth());
        importCol.setPreferredWidth(this.prefs.getColumnWidths(importColNo));

        TableColumn deleteCol = this.table.getColumn(
                this.prefs.getDescDelete());
        int deleteColNo = columnModel.getColumnIndex(
                this.prefs.getDescDelete());
        deleteCol.setIdentifier(this.prefs.getDescDelete());
        deleteCol.setHeaderValue(this.prefs.getHeaderValueDelete());
        deleteCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        deleteCol.setCellRenderer(this.columnFactory.getButtonRenderer());
        deleteCol.setCellEditor(this.columnFactory.getDeleteEditor());
        deleteCol.setResizable(this.prefs.getButtonResizable());
        deleteCol.setMinWidth(this.prefs.getMinColumnWidth());
        deleteCol.setPreferredWidth(this.prefs.getColumnWidths(deleteColNo));

        // resizing the columns
        TableListener tableListener = new TableListener(this.table);
        columnModel.addColumnModelListener(tableListener);

        // reordering the columns
        tableHeader.setReorderingAllowed(this.prefs.getAllowReordering());

        // sorting the columns
        RowSorter<TableModel> rowSorter =
            new FileTableRowSorter(this.listTableModel);
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(this.prefs.getSortKey());
        rowSorter.setSortKeys(sortKeys);
        rowSorter.addRowSorterListener(tableListener);
        this.table.setRowSorter(rowSorter);

        this.table.setPreferredScrollableViewportSize(
                new Dimension(
                        this.prefs.getPreferredTableWidth(),
                        this.prefs.getPreferredTableHeight()));

        this.scrollPane = new JCustomScrollPane();
        //see moneydance.com/pipermail/moneydance-dev/2006-September/000075.html
        try {
            this.scrollPane.setBorder(MoneydanceLAF.homePageBorder);
        } catch (Throwable e) {
            log.warn(e.getMessage(), e);
        }
    }


    @Override
    public final void refresh() {
        if (!this.scrollPane.isVisible()) {
            return;
        }

        this.prefs.reload();
        this.listTableModel.setDateFormatter(this.prefs.getDateFormatter());
        this.listTableModel.setTimeFormatter(this.prefs.getTimeFormatter());
        this.columnFactory.setForeground(this.prefs.getForeground());
        this.columnFactory.setBackground(this.prefs.getBackground());
        this.columnFactory.setBackgroundAlt(this.prefs.getBackgroundAlt());
        this.emptyLabel.setBackground(this.prefs.getBackground());
        this.emptyLabel.setFont(this.prefs.getBodyFont());
        this.table.setBackground(this.prefs.getBackground());
        this.table.setRowHeight(this.prefs.getBodyRowHeight());
        this.scrollPane.setBackground(this.prefs.getBackground());

        if (this.fileAdmin.isDirty()) {
            try {
                if (SwingUtilities.isEventDispatchThread()) {
                    this.run();
                } else {
                    SwingUtilities.invokeAndWait(this);
                }
                this.fileAdmin.setDirty(false);
            } catch (InterruptedException e) {
                log.warn(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }


    @Override
    public final void run() {
        this.fileAdmin.reloadFiles();
        this.listTableModel.fireTableDataChanged();

        if (this.listTableModel.getRowCount() == 0) {
            String emptyMessage = this.prefs.getEmptyMessage(
                    this.fileAdmin.getBaseDirectory());
            this.emptyLabel.setText(emptyMessage);
            this.scrollPane.setViewportView(this.emptyLabel);
            this.scrollPane.setPreferredSize(
                    new Dimension(
                            this.prefs.getPreferredEmptyMessageWidth(),
                            this.prefs.getPreferredEmptyMessageHeight()));
        } else {
            this.scrollPane.setViewportView(this.table);
            this.scrollPane.setPreferredSize(
                    new Dimension(
                            this.prefs.getPreferredTableWidth(),
                            this.prefs.getPreferredTableHeight()));
        }
    }


    @Override
    public final JComponent getGUIView(final RootAccount rootAccount) {
        return this.scrollPane;
    }


    @Override
    public final void reset() {
    }


    @Override
    public final void setActive(final boolean active) {
        this.scrollPane.setVisible(active);
        this.refresh();
    }


    @Override
    public final String getID() {
        return this.prefs.getId();
    }


    @Override
    public final String toString() {
        return this.prefs.getExtensionName();
    }
}
