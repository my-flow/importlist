package com.moneydance.modules.features.importlist;

import java.awt.Color;
import java.text.DateFormat;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.apps.md.view.gui.MoneydanceGUI;

/**
 * @author Florian J. Breunig, http://www.my-flow.com
 */
public class Preferences {

   private final DateFormat   dateFormatter;
   private final DateFormat   timeFormatter;
   private final Color        foregroundColor;
   private final Color        backgroundColor;
   private final Color        backgroundColorAlt;


   public Preferences(final FeatureModuleContext featureModuleContext) {

      int backgroundColorValue    = Constants.COLOR_VALUE_BG_DEF;
      int backgroundColorAltValue = Constants.COLOR_VALUE_BG_ALT_DEF;

      UserPreferences userPreferences = null;
      if (featureModuleContext != null) {
         userPreferences = ((com.moneydance.apps.md.controller.Main)
            featureModuleContext).getPreferences();

         //see moneydance.com/forum/YaBB.pl?num=1194898420
         com.moneydance.apps.md.controller.Main main =
            (com.moneydance.apps.md.controller.Main) featureModuleContext;
         MoneydanceGUI moneydanceGUI = (MoneydanceGUI) main.getUI();
         this.foregroundColor        = moneydanceGUI.getColors().homePageFG;
      } else {
         this.foregroundColor        = Constants.COLOR_FG_DEF;
      }

      if (userPreferences != null) {
         backgroundColorValue    = userPreferences.getIntSetting(
                Constants.PREF_BG_COLOR,     backgroundColorValue);
         backgroundColorAltValue = userPreferences.getIntSetting(
                Constants.PREF_BG_COLOR_ALT, backgroundColorAltValue);
         this.dateFormatter   = new CustomDateFormatWrapper(
               userPreferences.getShortDateFormatter());
         this.timeFormatter   = DateFormat.getTimeInstance();
      } else {
         this.dateFormatter   = DateFormat.getDateInstance();
         this.timeFormatter   = DateFormat.getTimeInstance();
      }

      this.backgroundColor    = new Color(backgroundColorValue);
      this.backgroundColorAlt = new Color(backgroundColorAltValue);
   }


   public final DateFormat getDateFormatter() {
      return this.dateFormatter;
   }


   public final DateFormat getTimeFormatter() {
      return this.timeFormatter;
   }


   public final Color getForegroundColor() {
      return this.foregroundColor;
   }


   public final Color getBackgroundColor() {
      return this.backgroundColor;
   }


   public final Color getBackgroundColorAlt() {
      return this.backgroundColorAlt;
   }
}
