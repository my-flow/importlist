package com.moneydance.modules.features.importlist;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.modules.features.importlist.io.DirectoryChooser;
import com.moneydance.modules.features.importlist.io.ListItemFilenameFilter;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class Main extends FeatureModule {

   private DirectoryChooser   directoryChooser;
   private File[]             files;
   private View               view;


   @Override
   public final void init() {

      UserPreferences userPreferences = null;

      if (this.getContext() != null) {
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

       this.view             = new View(this);

       if (this.getContext() != null) {
          // register this module's home page view
          this.getContext().registerHomePageView(this, this.view);
       }
   }


   @Override
   public final String getName() {
       return Constants.EXTENSION_NAME;
   }


   @Override
   public final Image getIconImage() {
       try {
           ClassLoader cl          = getClass().getClassLoader();
           InputStream inputStream = cl.getResourceAsStream(Constants.ICON);
           Image image = ImageIO.read(inputStream);
           return image;
       } catch (IOException e) {
           e.printStackTrace(System.err);
       } catch (IllegalArgumentException e) {
           e.printStackTrace(System.err);
       }
       return null;
   }


   @Override
   public final void invoke(final String uri) {

      if (uri == null) {
         return;
      }

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
         this.view.refresh();
      }
   }


   public final ActionListener getImportActionListener(final int rowNumber) {
      return this.new ImportActionListener(rowNumber);
   }


   public final ActionListener getDeleteActionListener(final int rowNumber) {
      return this.new DeleteActionListener(rowNumber);
   }


   final File[] getFiles() {
      File directory = new File(this.getImportDir());
      this.files = directory.listFiles(new ListItemFilenameFilter());
      return this.files;
   }


   final View getView() {
      return this.view;
   }


   final String getImportDir() {
      return this.directoryChooser.getDirectory();
   }


   final Preferences getPreferences() {
      return new Preferences(this.getContext());
   }


   /**
    * Command pattern: Return an action that imports a file identified by its
    * position in the list.
    */
   private class ImportActionListener implements ActionListener {

      private final int rowNumber;


      public ImportActionListener(final int argRowNumber) {
         this.rowNumber = argRowNumber;
      }


      @Override
      public void actionPerformed(final ActionEvent actionEvent) {

         if (Main.this.files == null) {
            return;
         }

         File file = Main.this.files[this.rowNumber];

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

         if (Main.this.getContext() == null) {
             return;
         }

         String callUri = Constants.IMPORT_URI_PREFIX + file.getAbsolutePath();

         // Import the file identified by the file parameter
         Main.this.getContext().showURL(callUri);
     }
   }


   /**
    * Command pattern: Return an action that deletes a file identified by its
    * position in the list.
    */
   private class DeleteActionListener implements ActionListener {

      private final int rowNumber;


      public DeleteActionListener(final int argRowNumber) {
         this.rowNumber = argRowNumber;
      }


      @Override
      public void actionPerformed(final ActionEvent actionEvent) {

         if (Main.this.files == null) {
            return;
         }

         File file = Main.this.files[this.rowNumber];

         Icon icon = null;
         String message = "Are you sure you want to "
             + "delete the file \"" + file.getName() + "\"?";
         if (Main.this.getIconImage() != null) {
             icon = new ImageIcon(Main.this.getIconImage());
         }
         Object[] options = {"Delete File", "Cancel"};

         int choice = JOptionPane.showOptionDialog(
               null,
               message,
               "", // no title
               JOptionPane.DEFAULT_OPTION,
               JOptionPane.WARNING_MESSAGE,
               icon,
               options,
               options[1]);

         if (choice == 0 && !file.delete()) {
              String errorMessage = "The file \"" + file.getName()
                  + "\" could not be deleted.";

              JOptionPane.showMessageDialog(
                  null,
                  errorMessage,
                  "", // no title
                  JOptionPane.ERROR_MESSAGE);
              System.err.println(errorMessage);
          }
          Main.this.view.refresh();
      }
   }
}
