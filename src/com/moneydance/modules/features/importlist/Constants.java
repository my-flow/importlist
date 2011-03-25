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

   /**
    * Representation of the event name to react on.
    */
   public static final String EXTENSION_NAME          = "ImportList";

   public static final String  ID                     =
      "moneydance:fmodule:importlist";

   public static final String  CHOOSE_BASE_DIR_SUFFIX = "chooseimportdir";

   public static final String  IMPORT_URI_PREFIX      =
      "moneydance:importprompt:?file=";

   public static final String  BASE_DIR_PREFERENCE    = "importlist.import_dir";

   public static final String  HOME_DIRECTORY         =
      System.getProperty("user.home");

   public static final String[] FILE_EXTENSIONS       =
      {"qif", "ofx", "qfx", "ofc"};

   public static final String  NAME_DESCRIPTOR        = "Name";

   public static final String  MODIFIED_DESCRIPTOR    = "Date Modified";

   public static final String  IMPORT_DESCRIPTOR      = "";

   public static final String  IMPORT_BUTTON_LABEL    = "Import";

   public static final String  DELETE_DESCRIPTOR      = "";

   public static final String  DELETE_BUTTON_LABEL    = "Delete";

   public static final int     BUTTON_WIDTH           = 20;

   public static final boolean BUTTON_RESIZABLE       = false;

   public static final boolean SORT_ROWS              = true;

   public static final boolean ALLOW_REORDERING       = false;

   public static final int     LIST_WIDTH             = 500;

   public static final int     LIST_HEIGHT            = 100;

   public static final int     MESSAGE_HEIGHT         = 80;

   public static final int     MESSAGE_WIDTH          = 400;

   public static final Color   FOREGROUND_COLOR       = Color.BLACK;

   public static final Color   TRANSPARENT_COLOR      =
      new Color(0, 0, 0, 0);

   public static final Color   BACKGROUND_COLOR_ALT   =
      new Color(245, 245, 245);
}
