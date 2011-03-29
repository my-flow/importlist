package com.moneydance.modules.features.importlist;

import java.awt.Color;


/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public final class Constants {

   /**
    * Private constructor prevents this class from being instantiated.
    */
   private Constants() {
   }


   public static final String  EXTENSION_NAME         = "Import List";

   public static final String  ID                     =
      "moneydance:fmodule:importlist";

   public static final String  CHOOSE_BASE_DIR_SUFFIX = "chooseimportdir";

   public static final String  IMPORT_URI_PREFIX      =
      "moneydance:importprompt:?file=";

   public static final String  PREF_BASE_DIR          = "importlist.import_dir";

   public static final String  PREF_BG_COLOR          = "gui.home_bg";

   public static final String  PREF_BG_COLOR_ALT      = "gui.home_alt_bg";

   public static final String  HOME_DIRECTORY         =
      System.getProperty("user.home");

   public static final String[] FILE_EXTENSIONS       =
      {"qif", "ofx", "qfx", "ofc"};

   public static final String  DESCRIPTOR_NAME        = "Name";

   public static final String  DESCRIPTOR_MODIFIED    = "Date Modified";

   public static final String  DESCRIPTOR_IMPORT      = "";

   public static final String  LABEL_IMPORT_BUTTON    = "Import";

   public static final String  DESCRIPTOR_DELETE      = "";

   public static final String  LABEL_DELETE_BUTTON    = "Delete";

   public static final int     BUTTON_WIDTH           = 20;

   public static final boolean BUTTON_RESIZABLE       = false;

   public static final boolean SORT_ROWS              = true;

   public static final boolean ALLOW_REORDERING       = false;

   public static final int     LIST_WIDTH             = 500;

   public static final int     LIST_HEIGHT            = 100;

   public static final int     MESSAGE_HEIGHT         = 80;

   public static final int     MESSAGE_WIDTH          = 400;

   public static final Color   COLOR_FG_DEF           = Color.BLACK;

   public static final int     COLOR_VALUE_BG_DEF     = -1;

   public static final int     COLOR_VALUE_BG_ALT_DEF = -657931;
}
