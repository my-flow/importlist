package com.moneydance.modules.features.importlist;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.awt.JTextPanel;
import com.moneydance.modules.features.importlist.swing.BackgroundColorRenderer;
import com.moneydance.modules.features.importlist.swing.ButtonDeleteEditor;
import com.moneydance.modules.features.importlist.swing.ButtonImportEditor;
import com.moneydance.modules.features.importlist.swing.ButtonRenderer;
import com.moneydance.modules.features.importlist.swing.ListTableModel;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class View implements HomePageView {

    private final Main main;
    private final DefaultTableModel       defaultTableModel;
    private final JTable                  jTable;
    private final JScrollPane             jScrollPane;


    public View(final Main argMain) {

       this.main = argMain;

       this.defaultTableModel = new ListTableModel(
             new Object[][] {},
             new Object[] {
                 Constants.DESCRIPTOR_NAME,
                 Constants.DESCRIPTOR_MODIFIED,
                 "I", // I means Import, use the String only as a reference
                 "D"  // D means Delete, use the String only as a reference
             }
       );

       this.jTable = new JTable();
       this.jTable.setShowGrid(false);
       this.jTable.setShowVerticalLines(false);
       this.jTable.setShowHorizontalLines(false);
       this.jTable.setModel(this.defaultTableModel);

       this.jTable.getColumn("I").setCellRenderer(new ButtonRenderer());
       this.jTable.getColumn("I").setCellEditor(
             new ButtonImportEditor(this.main));
       this.jTable.getColumn("I").setPreferredWidth(Constants.BUTTON_WIDTH);
       this.jTable.getColumn("I").setResizable(Constants.BUTTON_RESIZABLE);
       this.jTable.getColumn("I").setHeaderValue(Constants.DESCRIPTOR_IMPORT);

       this.jTable.getColumn("D").setCellRenderer(new ButtonRenderer());
       this.jTable.getColumn("D").setCellEditor(
             new ButtonDeleteEditor(this.main));
       this.jTable.getColumn("D").setPreferredWidth(Constants.BUTTON_WIDTH);
       this.jTable.getColumn("D").setResizable(Constants.BUTTON_RESIZABLE);
       this.jTable.getColumn("D").setHeaderValue(Constants.DESCRIPTOR_DELETE);

       this.jTable.setAutoCreateRowSorter(Constants.SORT_ROWS);
       this.jTable.getTableHeader().setReorderingAllowed(
             Constants.ALLOW_REORDERING);
       this.jTable.setPreferredScrollableViewportSize(
             new Dimension(Constants.LIST_WIDTH, Constants.LIST_HEIGHT));

       this.jScrollPane = new JScrollPane();

       //see moneydance.com/pipermail/moneydance-dev/2006-September/000075.html
       try {
          this.jScrollPane.setBorder(MoneydanceLAF.homePageBorder);
       } catch (Throwable e) {
         e.printStackTrace();
       }
    }


    public final String getID() {
       return Constants.ID;
    }


    public final void refresh() {

      Preferences preferences = this.main.getPreferences();
      Color backgroundColor   = preferences.getBackgroundColor();
      this.jScrollPane.setBackground(backgroundColor);

      File[] files            = this.main.getFiles();

      if (files == null || files.length == 0) {
         String label = "There are currently no files to import in "
             + this.main.getImportDir();
         JComponent jTextPanel = new JTextPanel(label);
         jTextPanel.setBackground(backgroundColor);
         this.jScrollPane.setViewportView(jTextPanel);
         this.jScrollPane.getViewport().setBackground(backgroundColor);
         this.jScrollPane.setPreferredSize(
             new Dimension(Constants.MESSAGE_WIDTH, Constants.MESSAGE_HEIGHT));
         return;
      }

      DefaultTableCellRenderer defaultTableCellRenderer =
         new BackgroundColorRenderer(
               preferences.getForegroundColor(),
               preferences.getBackgroundColor(),
               preferences.getBackgroundColorAlt());

      this.jTable.getColumn(Constants.DESCRIPTOR_NAME).setCellRenderer(
            defaultTableCellRenderer);
      this.jTable.getColumn(Constants.DESCRIPTOR_MODIFIED).setCellRenderer(
            defaultTableCellRenderer);

      DateFormat dateFormatter = preferences.getDateFormatter();
      DateFormat timeFormatter = preferences.getTimeFormatter();

      this.defaultTableModel.setRowCount(0);
      for (File file : files) {

         Date date = new Date(file.lastModified());
         String dateString = dateFormatter.format(date)
                             + " "
                             + timeFormatter.format(date);

          this.defaultTableModel.addRow(
              new Object[] {
                  file.getName(),
                  dateString,
                  Constants.LABEL_IMPORT_BUTTON,
                  Constants.LABEL_DELETE_BUTTON
              }
          );
      }

      this.jScrollPane.setViewportView(this.jTable);
      this.jScrollPane.getViewport().setBackground(backgroundColor);
    }


    public final JComponent getGUIView(final RootAccount rootAccount) {
        return this.jScrollPane;
    }


    public final void reset() {
       this.jScrollPane.invalidate();
    }


    public final void setActive(final boolean active) {
       this.jScrollPane.setVisible(active);
    }


    @Override
    public final String toString() {
       return this.main.getName();
    }
}
