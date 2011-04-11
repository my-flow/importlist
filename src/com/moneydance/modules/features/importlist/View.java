package com.moneydance.modules.features.importlist;

import java.awt.Dimension;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.Validate;

import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.awt.JTextPanel;
import com.moneydance.modules.features.importlist.io.FileAdministration;
import com.moneydance.modules.features.importlist.swing.BackgroundColorRenderer;
import com.moneydance.modules.features.importlist.swing.ButtonDeleteEditor;
import com.moneydance.modules.features.importlist.swing.ButtonImportEditor;
import com.moneydance.modules.features.importlist.swing.ButtonRenderer;
import com.moneydance.modules.features.importlist.swing.ListTableModel;

/**
 * User interface that is displayed on the home page.
 *
 * @author Florian J. Breunig, http://www.my-flow.com
 */
public class View implements HomePageView, Runnable {

    private final FileAdministration fileAdministration;
    private final DefaultTableModel  defaultTableModel;
    private final JTable             table;
    private final JScrollPane        scrollPane;
    private       boolean            running;


    public View(final FileAdministration argFileAdministration) {

       Validate.notNull(argFileAdministration,
               "argFileAdministration can't be null");
       this.fileAdministration = argFileAdministration;

       this.defaultTableModel = new ListTableModel(
             new Object[] {
                 Constants.DESCRIPTOR_NAME,
                 Constants.DESCRIPTOR_MODIFIED,
                 "I", // I means Import, use the String only as a reference
                 "D"  // D means Delete, use the String only as a reference
             },
             0 // rowCount
       );

       this.table = new JTable(this.defaultTableModel);
       this.table.setShowGrid(false);
       this.table.setShowVerticalLines(false);
       this.table.setShowHorizontalLines(false);

       this.table.getColumn("I").setCellRenderer(new ButtonRenderer());
       this.table.getColumn("I").setCellEditor(
             new ButtonImportEditor(this.fileAdministration));
       this.table.getColumn("I").setPreferredWidth(Constants.BUTTON_WIDTH);
       this.table.getColumn("I").setResizable(Constants.BUTTON_RESIZABLE);
       this.table.getColumn("I").setHeaderValue(Constants.DESCRIPTOR_IMPORT);

       this.table.getColumn("D").setCellRenderer(new ButtonRenderer());
       this.table.getColumn("D").setCellEditor(
             new ButtonDeleteEditor(this.fileAdministration));
       this.table.getColumn("D").setPreferredWidth(Constants.BUTTON_WIDTH);
       this.table.getColumn("D").setResizable(Constants.BUTTON_RESIZABLE);
       this.table.getColumn("D").setHeaderValue(Constants.DESCRIPTOR_DELETE);

       this.table.setAutoCreateRowSorter(Constants.SORT_ROWS);
       if (this.table.getRowSorter() != null) {
           this.table.getRowSorter().toggleSortOrder(0);
       }
       this.table.getTableHeader().setReorderingAllowed(
             Constants.ALLOW_REORDERING);
       this.table.setPreferredScrollableViewportSize(
             new Dimension(Constants.LIST_WIDTH, Constants.LIST_HEIGHT));

       this.scrollPane = new JScrollPane();
       //see moneydance.com/pipermail/moneydance-dev/2006-September/000075.html
       try {
          this.scrollPane.setBorder(MoneydanceLAF.homePageBorder);
       } catch (Throwable e) {
         e.printStackTrace();
       }
    }


    @Override
    public final void refresh() {
       Preferences preferences = this.fileAdministration.getPreferences();
       DefaultTableCellRenderer defaultTableCellRenderer =
          new BackgroundColorRenderer(
              preferences.getForegroundColor(),
              preferences.getBackgroundColor(),
              preferences.getBackgroundColorAlt());

       if (this.fileAdministration.isDirty() && this.scrollPane.isVisible()) {
          this.fileAdministration.setDirty(false);
          this.stop();
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

       this.table.getColumn(Constants.DESCRIPTOR_NAME).setCellRenderer(
               defaultTableCellRenderer);
       this.table.getColumn(Constants.DESCRIPTOR_MODIFIED).setCellRenderer(
           defaultTableCellRenderer);
       this.scrollPane.setBackground(preferences.getBackgroundColor());
       this.scrollPane.getViewport().setBackground(
           preferences.getBackgroundColor());
    }


    public final void run() {
       this.running = true;

       final Preferences preferences = this.fileAdministration.getPreferences();

       DateFormat dateFormatter = preferences.getDateFormatter();
       DateFormat timeFormatter = preferences.getTimeFormatter();

       this.defaultTableModel.setRowCount(0);

       for (File file : this.fileAdministration.getFiles()) {
          if (!this.running) {
             return;
          }

          final Date date  = new Date(file.lastModified());
          final String dateString = dateFormatter.format(date)
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

       if (this.defaultTableModel.getRowCount() == 0) {
          String label = "There are currently no files to import in "
              + this.fileAdministration.getImportDir();
          JComponent textPanel = new JTextPanel(label);
          textPanel.setBackground(preferences.getBackgroundColor());
          this.scrollPane.setViewportView(textPanel);
          this.scrollPane.setPreferredSize(new Dimension(
                  Constants.MESSAGE_WIDTH, Constants.MESSAGE_HEIGHT));

       } else {
           this.scrollPane.setViewportView(this.table);
           this.scrollPane.setPreferredSize(new Dimension(
                  Constants.LIST_WIDTH, Constants.LIST_HEIGHT));
       }
    }


    private void stop() {
        this.running = false;
    }


    @Override
    public final JComponent getGUIView(final RootAccount rootAccount) {
        return this.scrollPane;
    }


    @Override
    public final void reset() {
        this.stop();
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
