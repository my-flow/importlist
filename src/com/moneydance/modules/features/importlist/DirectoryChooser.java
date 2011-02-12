package com.moneydance.modules.features.importlist;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.moneydance.apps.md.controller.UserPreferences;


/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 *
 */
public class DirectoryChooser extends JPanel {

   private static final long serialVersionUID = -8581693236906919725L;

   private final UserPreferences userPreferences;

   private String baseDirectory;


   public DirectoryChooser(final UserPreferences paramUserPreferences) {
      this.userPreferences = paramUserPreferences;
   }


   public final String getDirectory() {

      if (this.userPreferences != null) {
         this.baseDirectory = this.userPreferences.getSetting(
               Constants.IMPORT_DIR_PREFERENCE,
               this.baseDirectory);
      }

      if (this.baseDirectory == null) {
         this.displayFileChooser();
      }

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
            Constants.IMPORT_DIR_PREFERENCE,
            this.baseDirectory);
      }

      return this.baseDirectory;
   }


   public final void displayFileChooser() {

      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Please choose a base directory for ImportList "
            + "to monitor");
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      // disable the "All files" option.
      chooser.setAcceptAllFileFilterUsed(false);

      chooser.setCurrentDirectory(new File(Constants.HOME_DIRECTORY));
      if (this.baseDirectory != null) {
         chooser.setCurrentDirectory(new File(this.baseDirectory));
      }

      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
         this.baseDirectory = chooser.getSelectedFile().getAbsolutePath();
      }
   }
}
