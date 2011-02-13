package com.moneydance.modules.features.importlist;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.awt.JTextPanel;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class Main extends FeatureModule implements HomePageView {

    private final DateFormat        dateFormat  =
       DateFormat.getDateTimeInstance();
    private final JScrollPane       jScrollPane = new JScrollPane();
    private       DirectoryChooser  directoryChooser;
    private       File[]            files;
    private       DefaultTableModel defaultTableModel;

    public final void init() {

       UserPreferences userPreferences = null;
        if (this.getContext() != null) {
            this.getContext().registerHomePageView(this, this);
            userPreferences = ((com.moneydance.apps.md.controller.Main)
                  this.getContext()).getPreferences();
        }

        this.directoryChooser = new DirectoryChooser(userPreferences);

        //see moneydance.com/pipermail/moneydance-dev/2006-September/000075.html
        try {
          this.jScrollPane.setBorder(MoneydanceLAF.homePageBorder);
        } catch (Throwable e) {
          e.printStackTrace();
        }

        this.refresh();
    }


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

      File directory = new File(this.getImportDir());

      this.files = directory.listFiles(new ListItemFilenameFilter());

      if (this.files == null || this.files.length == 0) {
         String label = "There are currently no files to display in "
             + this.getImportDir();
         JComponent jTextPanel = new JTextPanel(label);
         this.jScrollPane.setViewportView(jTextPanel);
         this.jScrollPane.setPreferredSize(
             new Dimension(Constants.MESSAGE_WIDTH, Constants.MESSAGE_HEIGHT));
         return;
      }

      this.defaultTableModel = new ListTableModel(
            new Object[][] {},
            new Object[] {
                Constants.NAME_DESCRIPTOR,
                Constants.MODIFIED_DESCRIPTOR,
                "Import", // use the String only as a reference
                "Delete"
            }
      );

      JTable jTable = new JTable(this.defaultTableModel);
      jTable.setShowGrid(false);
      jTable.setShowVerticalLines(false);
      jTable.setShowHorizontalLines(false);

      BackgroundColorRenderer backgroundColorRenderer =
         new BackgroundColorRenderer();

      jTable.getColumn(Constants.NAME_DESCRIPTOR).setCellRenderer(
            backgroundColorRenderer);

      jTable.getColumn(Constants.MODIFIED_DESCRIPTOR).setCellRenderer(
            backgroundColorRenderer);

      jTable.getColumn("Import").setCellRenderer(new ButtonRenderer());
      jTable.getColumn("Import").setCellEditor(
              new ButtonImportEditor(this, new JCheckBox()));
      jTable.getColumn("Import").setPreferredWidth(Constants.BUTTON_WIDTH);
      jTable.getColumn("Import").setResizable(Constants.BUTTON_RESIZABLE);
      jTable.getColumn("Import").setHeaderValue(Constants.IMPORT_DESCRIPTOR);

      jTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
      jTable.getColumn("Delete").setCellEditor(
              new ButtonDeleteEditor(this, new JCheckBox()));
      jTable.getColumn("Delete").setPreferredWidth(Constants.BUTTON_WIDTH);
      jTable.getColumn("Delete").setResizable(Constants.BUTTON_RESIZABLE);
      jTable.getColumn("Delete").setHeaderValue(Constants.DELETE_DESCRIPTOR);

      jTable.setAutoCreateRowSorter(Constants.SORT_ROWS);
      jTable.getTableHeader().setReorderingAllowed(Constants.ALLOW_REORDERING);
      jTable.setPreferredScrollableViewportSize(
              new Dimension(Constants.LIST_WIDTH, Constants.LIST_HEIGHT));

      for (int i = 0; i < this.files.length; i++) {
          this.defaultTableModel.addRow(
              new Object[] {
                  this.files[i].getName(),
                  this.dateFormat.format(new Date(
                        this.files[i].lastModified())),
                  Constants.IMPORT_BUTTON_LABEL,
                  Constants.DELETE_BUTTON_LABEL
          });
      }

      this.jScrollPane.setViewportView(jTable);
      this.jScrollPane.validate();
    }


    protected final void importFile(final int row) {

        File file = this.getFile(row);

        if (!file.canRead()) {
             String errorMessage = "Could not read file \""
                + file.getAbsolutePath() + "\"";

             JOptionPane.showMessageDialog(
                 null,
                 errorMessage,
                 "Error",
                 JOptionPane.ERROR_MESSAGE);
             System.err.println(errorMessage);

             this.refresh();
             return;
        }

        if (this.getContext() == null) {
            return;
        }

        String callUri = Constants.IMPORT_URI_PREFIX
            + file.getAbsolutePath();

        // Import the file identified by the file parameter
        this.getContext().showURL(callUri);
    }


    protected final void deleteFile(final int rowNumber) {

       File file = this.getFile(rowNumber);

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
        return this.jScrollPane;
    }


    public final void setActive(final boolean active) {
        this.jScrollPane.setVisible(active);
    }


    public void invoke(final String uri) {
        // this extension is event-based and cannot be invoked
        // explicitly.
    }


    private String getImportDir() {
       return this.directoryChooser.getDirectory();
    }


    private File getFile(final int row) {
        return this.files[row];
    }


    public static void main(final String[] args) {

       JFrame frame = new JFrame();
       Main main = new Main();
       main.init();
       frame.setContentPane(main.getGUIView(null));
       frame.setSize(main.getGUIView(null).getPreferredSize());

       frame.addWindowListener(new WindowAdapter() {
          public void windowClosing(final WindowEvent e) {
              System.exit(0);
          }
       });
       frame.setVisible(true);
   }
}
