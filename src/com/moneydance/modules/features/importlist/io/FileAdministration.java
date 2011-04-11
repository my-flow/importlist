package com.moneydance.modules.features.importlist.io;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang.Validate;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.Constants;
import com.moneydance.modules.features.importlist.Preferences;

/**
 * @author Florian J. Breunig, http://www.my-flow.com
 */
public class FileAdministration {

   private final FeatureModule      featureModule;
   private FeatureModuleContext     context;
   private DirectoryChooser         directoryChooser;
   private IOFileFilter             fileFilter;
   private FileAlterationObserver   observer;
   private TransactionFileListener  listener;
   private FileAlterationMonitor    monitor;
   private List<File>               files;
   private boolean                  dirty;
   private HomePageView             homePageView;


   public FileAdministration(final FeatureModule argFeatureModule) {
      Validate.notNull(argFeatureModule, "argFeatureModule can't be null");
      this.featureModule = argFeatureModule;
      this.init();
   }


   public FileAdministration(final FeatureModule argFeatureModule,
           final String baseDirectory) {
      Validate.notNull(argFeatureModule, "argFeatureModule can't be null");
      this.featureModule    = argFeatureModule;
      this.directoryChooser = new DirectoryChooser(baseDirectory);
      this.init();
   }


   public FileAdministration(final FeatureModule argFeatureModule,
           final UserPreferences userPreferences) {
      Validate.notNull(argFeatureModule, "argFeatureModule can't be null");
      this.featureModule    = argFeatureModule;
      this.directoryChooser = new DirectoryChooser(userPreferences);
      this.init();
   }


   private void init() {
       if (this.directoryChooser == null) {
           this.directoryChooser = new DirectoryChooser();
       }

       this.fileFilter = new SuffixFileFilter(
               Constants.FILE_EXTENSIONS,
               IOCase.INSENSITIVE);

       this.listener = new TransactionFileListener(this);
       this.monitor  = new FileAlterationMonitor(Constants.INTERVAL);
       this.setFileMonitorToCurrentImportDir();
       this.startMonitor();

       this.dirty = true;
   }


   public final void setContext(final FeatureModuleContext argContext) {
       this.context = argContext;
   }


   public final void reset() {
      this.directoryChooser.reset();
      this.monitor.removeObserver(this.observer);
      this.setFileMonitorToCurrentImportDir();
      this.setDirty(true);
   }


   public final List<File> getFiles() {
       // initialize with empty list
       this.files = new ArrayList<File>();
       try {
           File importDir = new File(this.getImportDir());
           Collection<File> collection = FileUtils.listFiles(
                   importDir,
                   this.fileFilter,
                   null); // ignore subdirectories
           // use array list implementation for array
           this.files = new ArrayList<File>(collection);
       } catch (IllegalArgumentException e) {
           e.printStackTrace(System.err);
       }
       return this.files;
   }


   public final String getImportDir() {
      return this.directoryChooser.getDirectory();
   }


   /**
    * Refresh preferences from context.
    * @return New preferences object
    */
   public final Preferences getPreferences() {
      this.featureModule.invoke(Constants.RELOAD_CONTEXT_SUFFIX);
      return new Preferences(this.context);
   }


   public final void setDirty(final boolean argDirty) {
      this.dirty = argDirty;
      if (argDirty && this.homePageView != null) {
         this.homePageView.refresh();
      }
   }


   public final boolean isDirty() {
      return this.dirty;
   }


   /**
    * Start monitoring the current import directory.
    */
   public final void startMonitor() {
      try {
         this.monitor.start();
      } catch (Exception e) {
         e.printStackTrace(System.err);
      }
   }


   /**
    * Stop monitoring the current import directory.
    */
   public final void stopMonitor() {
      try {
         this.monitor.stop();
      } catch (Exception e) {
         e.printStackTrace(System.err);
      }
   }


   public final void setHomePageView(final HomePageView argHomePageView) {
      this.homePageView = argHomePageView;
   }


   public final ActionListener getImportActionListener(final int rowNumber) {
      return this.new ImportActionListener(rowNumber);
   }


   public final ActionListener getDeleteActionListener(final int rowNumber) {
      return this.new DeleteActionListener(rowNumber);
   }


   private void setFileMonitorToCurrentImportDir() {
      this.observer  = new FileAlterationObserver(
            this.getImportDir(),
            this.fileFilter,
            IOCase.SENSITIVE);
      this.observer.addListener(this.listener);
      this.monitor.addObserver(this.observer);
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

         final File file =
             FileAdministration.this.getFiles().get(this.rowNumber);

         if (!file.canRead()) {
              final String errorMessage = "Could not read file \""
                 + file.getAbsolutePath() + "\"";

              JOptionPane.showMessageDialog(
                  null,
                  errorMessage,
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
              System.err.println(errorMessage);
              return;
         }

         if (FileAdministration.this.context == null) {
             return;
         }

         String callUri = Constants.IMPORT_URI_PREFIX + file.getAbsolutePath();

         // Import the file identified by the file parameter
         FileAdministration.this.context.showURL(callUri);
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

         final File file =
             FileAdministration.this.getFiles().get(this.rowNumber);

         final String message = "Are you sure you want to "
             + "delete the file \"" + file.getName() + "\"?";
         Icon icon   = null;
         Image image = FileAdministration.this.featureModule.getIconImage();
         if (image != null) {
             icon = new ImageIcon(image);
         }
         final Object[] options = {"Delete File", "Cancel"};

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
         FileAdministration.this.setDirty(true);
      }
   }
}
