package com.moneydance.modules.features.importlist;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.io.FileAdministration;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class Main extends FeatureModule {

   private FileAdministration fileAdministration;
   private HomePageView       homePageView;


   /**
    * Standard constructor must be available in the Moneydance context.
    */
   public Main() {
      super();
   }


   public Main(final String baseDirectory) {
      super();
      this.fileAdministration = new FileAdministration(this, baseDirectory);
      this.init();
   }


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

           this.fileAdministration = new FileAdministration(
                   this,
                   userPreferences);
      }

      if (this.fileAdministration == null) {
         this.fileAdministration = new FileAdministration(this);
      }

      this.homePageView = new View(this.fileAdministration);
      this.fileAdministration.setHomePageView(this.homePageView);

      if (this.getContext() != null) {
         // register this module's home page view
         this.getContext().registerHomePageView(this, this.homePageView);
      }
   }


   @Override
   public final String getName() {
       return Constants.EXTENSION_NAME;
   }


   @Override
   public final Image getIconImage() {
       Image image             = null;
       ClassLoader cl          = this.getClass().getClassLoader();
       InputStream inputStream = cl.getResourceAsStream(Constants.ICON);
       File imageFile          = new File("src" + Constants.ICON);
       try {
           if (inputStream != null) {
              image = ImageIO.read(inputStream);
           } else {
              image = ImageIO.read(imageFile);
           }
       } catch (IOException e) {
           e.printStackTrace(System.err);
       } catch (IllegalArgumentException e) {
           e.printStackTrace(System.err);
       }
       return image;
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
         this.fileAdministration.reset();
      }

      if (Constants.RELOAD_CONTEXT_SUFFIX.equals(command)) {
          this.fileAdministration.setContext(this.getContext());
      }
   }


   @Override
   public final void cleanup() {
      this.fileAdministration.stopMonitor();
   }


   public final HomePageView getHomePageView() {
      return this.homePageView;
   }
}
