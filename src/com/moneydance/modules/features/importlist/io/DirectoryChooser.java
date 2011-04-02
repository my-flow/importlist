package com.moneydance.modules.features.importlist.io;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.modules.features.importlist.Constants;


/**
 * Choose the base directory to be monitored and save the setting in the user's
 * preferences (if there are any).
 *
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class DirectoryChooser extends JPanel {

   private static final long serialVersionUID = -8581693236906919725L;
   private        final UserPreferences userPreferences;
   private              String          baseDirectory;


   /**
    * @param argBaseDirectory set the base directory when executed as a stand-
    * alone application
    */
   public DirectoryChooser(final String argBaseDirectory) {
      this.baseDirectory   = argBaseDirectory;
      this.userPreferences = null;
   }


   /**
    * @param argUserPreferences user preferences if the extension runs in
    * Moneydance's application context, otherwise null.
    */
   public DirectoryChooser(final UserPreferences argUserPreferences) {
      this.userPreferences = argUserPreferences;
   }


   public final void reset() {
      this.displayFileChooser();
      this.saveDirectoryInPreferences();
   }


   public final String getDirectory() {

      if (this.userPreferences != null) {
         this.baseDirectory = this.userPreferences.getSetting(
               Constants.PREF_BASE_DIR,
               this.baseDirectory);
      }

      if (this.baseDirectory == null) {
         this.displayFileChooser();
      }

      this.saveDirectoryInPreferences();

      return this.baseDirectory;
   }


   private void displayFileChooser() {

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Please choose a base directory to monitor");
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      // disable the "All files" option.
      fileChooser.setAcceptAllFileFilterUsed(false);

      fileChooser.setCurrentDirectory(new File(Constants.HOME_DIRECTORY));
      if (this.baseDirectory != null) {
         File parentDirectory = new File(this.baseDirectory).getParentFile();
         fileChooser.setCurrentDirectory(parentDirectory);
      }

      if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
         this.baseDirectory = fileChooser.getSelectedFile().getAbsolutePath();
      }
   }



   private void saveDirectoryInPreferences() {
      if (this.baseDirectory == null && this.userPreferences != null) {

         this.baseDirectory = this.userPreferences.getSetting(
               UserPreferences.IMPORT_DIR,
               this.baseDirectory);
      }

      if (this.baseDirectory == null) {
         this.baseDirectory = Constants.HOME_DIRECTORY;
      }

      if (this.userPreferences != null) {
         this.userPreferences.setSetting(
            Constants.PREF_BASE_DIR,
            this.baseDirectory);
      }
   }
}
