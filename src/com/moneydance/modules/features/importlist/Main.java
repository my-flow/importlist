package com.moneydance.modules.features.importlist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.awt.JTextPanel;
import com.moneydance.modules.features.importlist.io.DirectoryChooser;
import com.moneydance.modules.features.importlist.io.ListItemFilenameFilter;
import com.moneydance.modules.features.importlist.swing.BackgroundColorRenderer;
import com.moneydance.modules.features.importlist.swing.ButtonDeleteEditor;
import com.moneydance.modules.features.importlist.swing.ButtonImportEditor;
import com.moneydance.modules.features.importlist.swing.ButtonRenderer;
import com.moneydance.modules.features.importlist.swing.ListTableModel;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class Main extends FeatureModule implements HomePageView {

   private DirectoryChooser  directoryChooser;
   private File[]            files;
   private DefaultTableModel defaultTableModel;
   private JTable            jTable;
   private JScrollPane       jScrollPane;


    @Override
   public final void init() {

       UserPreferences userPreferences = null;

       if (this.getContext() != null) {

            // register this module's home page view
            this.getContext().registerHomePageView(this, this);

            // register this module to be invoked via the application toolbar
            this.getContext().registerFeature(
               this,
               Constants.CHOOSE_BASE_DIR_SUFFIX,
               null,
               this.getName());

            userPreferences = ((com.moneydance.apps.md.controller.Main)
                  this.getContext()).getPreferences();
       }

        // initialize base directory
        this.directoryChooser = new DirectoryChooser(userPreferences);

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
              new ButtonImportEditor(this, new JCheckBox()));
        this.jTable.getColumn("I").setPreferredWidth(Constants.BUTTON_WIDTH);
        this.jTable.getColumn("I").setResizable(Constants.BUTTON_RESIZABLE);
        this.jTable.getColumn("I").setHeaderValue(Constants.DESCRIPTOR_IMPORT);

        this.jTable.getColumn("D").setCellRenderer(new ButtonRenderer());
        this.jTable.getColumn("D").setCellEditor(
              new ButtonDeleteEditor(this, new JCheckBox()));
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

        this.refresh();
    }


    @Override
    public final String getName() {
        return Constants.EXTENSION_NAME;
    }


    public final String getID() {
       return Constants.ID;
    }


    public final void reset() {
        this.jScrollPane.invalidate();
    }


    public final void refresh() {

      this.jScrollPane.invalidate();

      Preferences preferences = new Preferences(this.getContext());

      File directory = new File(this.getImportDir());
      this.files = directory.listFiles(new ListItemFilenameFilter());

      if (this.files == null || this.files.length == 0) {
         String label = "There are currently no files to import in "
             + this.getImportDir();
         JComponent jTextPanel = new JTextPanel(label);
         jTextPanel.setBackground(preferences.getBackgroundColor());
         this.jScrollPane.setViewportView(jTextPanel);
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

      this.defaultTableModel.setRowCount(0);
      for (File file : this.files) {

          Date date = new Date(file.lastModified());
          String dateString = preferences.getDateFormatter().format(date)
                            + " "
                            + preferences.getTimeFormatter().format(date);

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
      this.jScrollPane.validate();
    }


    public final void importFile(final int row) {

        File file = this.files[row];

        if (!file.canRead()) {
             String errorMessage = "Could not read file \""
                + file.getAbsolutePath() + "\"";

             JOptionPane.showMessageDialog(
                 null,
                 errorMessage,
                 "Error",
                 JOptionPane.ERROR_MESSAGE);
             System.err.println(errorMessage);
             return;
        }

        if (this.getContext() == null) {
            return;
        }

        String callUri = Constants.IMPORT_URI_PREFIX + file.getAbsolutePath();

        // Import the file identified by the file parameter
        this.getContext().showURL(callUri);
    }


    public final void deleteFile(final int rowNumber) {

       File file = this.files[rowNumber];

       Object[] options = {"Delete File", "Cancel"};
       int choice = JOptionPane.showOptionDialog(
             null,
             "Are you sure you want to delete the file \""
                + file.getName() + "\"?",
             "Warning",
             JOptionPane.DEFAULT_OPTION,
             JOptionPane.WARNING_MESSAGE,
             null,
             options,
             options[0]);

       if (choice == 0 && !file.delete()) {
            String errorMessage = "Could not delete file \""
               + file.getAbsolutePath() + "\"";

            JOptionPane.showMessageDialog(
                null,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE);
            System.err.println(errorMessage);
        }
        this.refresh();
    }


    public final JComponent getGUIView(final RootAccount rootAccount) {
        Preferences preferences = new Preferences(this.getContext());
        Color backgroundColor   = preferences.getBackgroundColor();
        this.jScrollPane.setBackground(backgroundColor);
        this.jScrollPane.getViewport().setBackground(backgroundColor);
        return this.jScrollPane;
    }


    public final void setActive(final boolean active) {
        this.jScrollPane.setVisible(active);
    }


    @Override
    public final void invoke(final String uri) {
       String command = uri;
       int theIdx     = uri.indexOf('?');
       if (theIdx >= 0) {
         command = uri.substring(0, theIdx);
       } else {
         theIdx = uri.indexOf(':');
         if (theIdx >= 0) {
            command = uri.substring(0, theIdx);
         }
       }

       if (Constants.CHOOSE_BASE_DIR_SUFFIX.equals(command)) {
          this.directoryChooser.reset();
          this.refresh();
       }
    }


    private String getImportDir() {
       return this.directoryChooser.getDirectory();
    }


    public static void main(final String[] args) {

       JFrame frame = new JFrame();
       Main main = new Main();
       main.init();
       frame.setContentPane(main.getGUIView(null));
       frame.setSize(main.getGUIView(null).getPreferredSize());

       frame.addWindowListener(new WindowAdapter() {
          @Override
         public void windowClosing(final WindowEvent e) {
              System.exit(0);
          }
       });
       frame.setVisible(true);
   }
}
