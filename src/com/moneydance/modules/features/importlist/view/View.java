package com.moneydance.modules.features.importlist.view;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultRowSorter;
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

import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.modules.features.importlist.AlphanumComparator;
import com.moneydance.modules.features.importlist.Constants;
import com.moneydance.modules.features.importlist.Preferences;
import com.moneydance.modules.features.importlist.io.FileAdministration;

/**
 * The user interface that is displayed on the homepage.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
public class View implements HomePageView, Runnable {

    private final FileAdministration    fileAdministration;
    private final ListTableModel        listTableModel;
    private final JTable                table;
    private final ColumnFactory         columnFactory;
    private final JScrollPane           scrollPane;


    public View(final FileAdministration argFileAdministration) {
        Validate.notNull(argFileAdministration,
        "argFileAdministration can't be null");
        this.fileAdministration = argFileAdministration;

        this.listTableModel = new ListTableModel(
                this.fileAdministration.getFiles());

        this.table = new JTable(this.listTableModel);
        this.table.setShowGrid(false);
        this.table.setShowVerticalLines(false);
        this.table.setShowHorizontalLines(false);
        this.table.setIntercellSpacing(new Dimension(0, 0));
        this.table.setColumnSelectionAllowed(false);
        this.table.setRowSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);

        Preferences prefs            = Preferences.getInstance();
        JTableHeader tableHeader     = this.table.getTableHeader();
        TableColumnModel columnModel = this.table.getColumnModel();

        this.columnFactory = new ColumnFactory(
                this.fileAdministration,
                tableHeader.getDefaultRenderer());

        TableColumn nameCol = this.table.getColumn(Constants.DESC_NAME);
        int nameColNo = columnModel.getColumnIndex(Constants.DESC_NAME);
        nameCol.setIdentifier(Constants.DESC_NAME);
        nameCol.setHeaderValue(Constants.HEADER_VALUE_NAME);
        nameCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        nameCol.setCellRenderer(this.columnFactory.getLabelRenderer());
        nameCol.setMinWidth(Constants.MIN_COLUMN_WIDTH);
        nameCol.setPreferredWidth(prefs.getColumnWidths(nameColNo));

        TableColumn modifiedCol = this.table.getColumn(Constants.DESC_MODIFIED);
        int modifiedColNo = columnModel.getColumnIndex(Constants.DESC_MODIFIED);
        modifiedCol.setIdentifier(Constants.DESC_MODIFIED);
        modifiedCol.setHeaderValue(Constants.HEADER_VALUE_MODIFIED);
        modifiedCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        modifiedCol.setCellRenderer(this.columnFactory.getLabelRenderer());
        modifiedCol.setMinWidth(Constants.MIN_COLUMN_WIDTH);
        modifiedCol.setPreferredWidth(prefs.getColumnWidths(modifiedColNo));

        TableColumn importCol = this.table.getColumn(Constants.DESC_IMPORT);
        int importColNo = columnModel.getColumnIndex(Constants.DESC_IMPORT);
        importCol.setIdentifier(Constants.DESC_IMPORT);
        importCol.setHeaderValue(Constants.HEADER_VALUE_IMPORT);
        importCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        importCol.setCellRenderer(this.columnFactory.getButtonRenderer());
        importCol.setCellEditor(this.columnFactory.getImportEditor());
        importCol.setResizable(Constants.BUTTON_RESIZABLE);
        importCol.setMinWidth(Constants.MIN_COLUMN_WIDTH);
        importCol.setPreferredWidth(prefs.getColumnWidths(importColNo));

        TableColumn deleteCol = this.table.getColumn(Constants.DESC_DELETE);
        int deleteColNo = columnModel.getColumnIndex(Constants.DESC_DELETE);
        deleteCol.setIdentifier(Constants.DESC_DELETE);
        deleteCol.setHeaderValue(Constants.HEADER_VALUE_DELETE);
        deleteCol.setHeaderRenderer(this.columnFactory.getHeaderRenderer());
        deleteCol.setCellRenderer(this.columnFactory.getButtonRenderer());
        deleteCol.setCellEditor(this.columnFactory.getDeleteEditor());
        deleteCol.setResizable(Constants.BUTTON_RESIZABLE);
        deleteCol.setMinWidth(Constants.MIN_COLUMN_WIDTH);
        deleteCol.setPreferredWidth(prefs.getColumnWidths(deleteColNo));

        // resizing the columns
        TableListener tableListener =
            new TableListener(this.table);
        columnModel.addColumnModelListener(tableListener);

        // reordering the columns
        tableHeader.setReorderingAllowed(Constants.ALLOW_REORDERING);

        // sorting the columns
        DefaultRowSorter<TableModel, Integer> rowSorter =
            new FileTableRowSorter(this.listTableModel);
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(Preferences.getInstance().getSortKey());
        rowSorter.setSortKeys(sortKeys);

        rowSorter.addRowSorterListener(tableListener);

        Comparator<String> comparator = new AlphanumComparator();
        rowSorter.setComparator(nameColNo, comparator);
        this.table.setRowSorter(rowSorter);

        this.table.setPreferredScrollableViewportSize(
                new Dimension(
                        Constants.PREFERRED_TABLE_WIDTH,
                        Constants.PREFERRED_TABLE_HEIGHT));

        this.scrollPane = new JScrollPane();
        //see moneydance.com/pipermail/moneydance-dev/2006-September/000075.html
        try {
            this.scrollPane.setBorder(MoneydanceLAF.homePageBorder);
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        }
    }


    @Override
    public final void refresh() {
        Preferences prefs = Preferences.getInstance();
        this.listTableModel.setDateFormatter(prefs.getDateFormatter());
        this.listTableModel.setTimeFormatter(prefs.getTimeFormatter());
        this.columnFactory.setForegroundColor(prefs.getForegroundColor());
        this.columnFactory.setBackgroundColor(prefs.getBackgroundColor());
        this.columnFactory.setBackgroundColorAlt(prefs.getBackgroundColorAlt());
        this.table.setRowHeight(prefs.getBodyRowHeight());
        this.table.getTableHeader().setFont(prefs.getHeaderFont());
        this.table.getTableHeader().setSize(
                this.table.getTableHeader().getWidth(),
                prefs.getHeaderRowHeight());
        this.scrollPane.setBackground(prefs.getBackgroundColor());
        this.scrollPane.getViewport().setBackground(prefs.getBackgroundColor());

        if (this.fileAdministration.isDirty() && this.scrollPane.isVisible()) {
            this.fileAdministration.setDirty(false);
            try {
                if (SwingUtilities.isEventDispatchThread()) {
                    this.run();
                } else {
                    SwingUtilities.invokeAndWait(this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            } catch (InvocationTargetException e) {
                e.printStackTrace(System.err);
            }
        }
    }


    @Override
    public final void run() {
        this.fileAdministration.reloadFiles();
        this.listTableModel.fireTableDataChanged();

        if (this.listTableModel.getRowCount() == 0) {
            String message = "<html><center><em>There are currently no files "
                + "to import<br>in " + this.fileAdministration.getImportDir()
                + "</em><center></html>";
            Preferences preferences = Preferences.getInstance();
            JLabel textPanel = new JLabel(message);
            textPanel.setHorizontalAlignment(SwingConstants.CENTER);
            textPanel.setBackground(preferences.getBackgroundColor());
            this.scrollPane.setViewportView(textPanel);
            this.scrollPane.setPreferredSize(
                    new Dimension(
                            Constants.PREFERRED_EMPTY_MESSAGE_WIDTH,
                            Constants.PREFERRED_EMPTY_MESSAGE_HEIGHT));
        } else {
            this.scrollPane.setViewportView(this.table);
            this.scrollPane.setPreferredSize(
                    new Dimension(
                            Constants.PREFERRED_TABLE_WIDTH,
                            Constants.PREFERRED_TABLE_HEIGHT));
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
        return Constants.ID;
    }


    @Override
    public final String toString() {
        return Constants.EXTENSION_NAME;
    }
}
