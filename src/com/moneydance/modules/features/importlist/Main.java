package com.moneydance.modules.features.importlist;

import java.io.File;

import javax.swing.JOptionPane;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.modules.features.importlist.io.DirectoryChooser;
import com.moneydance.modules.features.importlist.io.ListItemFilenameFilter;

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


   public final void importFile(final int rowNumber) {

       if (this.files == null) {
          return;
       }

       File file = this.files[rowNumber];

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

      if (this.files == null) {
         return;
      }

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
       this.view.refresh();
   }


   protected final File[] getFiles() {
      File directory = new File(this.getImportDir());
      this.files = directory.listFiles(new ListItemFilenameFilter());
      return this.files;
   }

   protected final View getView() {
      return this.view;
   }


   protected final String getImportDir() {
      return this.directoryChooser.getDirectory();
   }


   protected final Preferences getPreferences() {
      return new Preferences(this.getContext());
   }
}
