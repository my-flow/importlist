package com.moneydance.modules.features.importlist;

import com.moneydance.apps.md.controller.UserPreferences;


/**
 * Constants which are used throughout the extension and are subject to
 * change.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
public final class Constants {

    /**
     * Private constructor prevents this class from being instantiated.
     */
    private Constants() {
    }

    public static final String  EXTENSION_NAME
    = "Import List";

    public static final String  ID
    = "moneydance:fmodule:importlist";

    public static final String  ICON
    = "/com/moneydance/modules/features/importlist/icon.png";

    public static final String  CHOOSE_BASE_DIR_SUFFIX
    = "chooseimportdir";

    public static final String  RELOAD_CONTEXT_SUFFIX
    = "reloadcontext";

    public static final String  IMPORT_URI_PREFIX
    = "moneydance:importprompt:?file=";

    public static final int     MONITOR_INTERVAL
    = 10000;

    public static final String[] FILE_EXTENSIONS
    = {"qif", "ofx", "qfx", "ofc"};

    public static final String  PREF_BASE_DIR
    = "importlist.import_dir";

    public static final String  PREF_COLUMN_WIDTHS
    = "importlist.column_widths";

    public static final String  PREF_COLUMN_ORDER
    = "importlist.column_order";

    public static final String  PREF_SORT_ORDER
    = "importlist.sort_order";

    public static final String  PREF_FG_COLOR
    = UserPreferences.GUI_HOMEPG_FG;

    public static final String  PREF_BG_COLOR
    = UserPreferences.GUI_HOMEPG_BG;

    public static final String  PREF_BG_COLOR_ALT
    = "gui.home_alt_bg";

    public static final String  DESC_NAME
    = "Name";

    public static final String  DESC_MODIFIED
    = "Date Modified";

    public static final String  DESC_IMPORT
    = "Import";

    public static final String  DESC_DELETE
    = "Delete";

    public static final String  HEADER_VALUE_NAME
    = "Name";

    public static final String  HEADER_VALUE_MODIFIED
    = "Date Modified";

    public static final String  HEADER_VALUE_IMPORT
    = "";

    public static final String  HEADER_VALUE_DELETE
    = "";

    public static final String  LABEL_IMPORT_BUTTON
    = "Import";

    public static final String  LABEL_DELETE_BUTTON
    = "Delete";

    public static final int     BUTTON_WIDTH
    = 60;

    public static final boolean BUTTON_RESIZABLE
    = false;

    public static final int     MIN_COLUMN_WIDTH
    = 60;

    public static final boolean ALLOW_REORDERING
    = true;

    public static final int     LIST_WIDTH
    = 500;

    public static final int     LIST_HEIGHT
    = 150;

    public static final int     MESSAGE_HEIGHT
    = 80;

    public static final int     MESSAGE_WIDTH
    = 400;

     public static final double  FACTOR_ROW_HEIGHT
    = 1.4;

    public static final String  HEADER_KEY_FONT
    = "OptionPane.font";

    public static final String  BODY_KEY_FONT
    = "Label.font";

    public static final int     COLOR_VALUE_FG_DEF
    = -16777216;

    public static final int     COLOR_VALUE_BG_DEF
    = -103;

    public static final int     COLOR_VALUE_BG_ALT_DEF
    = -657931;
}
