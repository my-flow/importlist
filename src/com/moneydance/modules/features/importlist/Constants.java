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

    /**
     * The descriptive name of this extension.
     */
    public static final String  EXTENSION_NAME
    = "Import List";

    /**
     * The ID string for this extension.
     */
    public static final String  ID
    = "moneydance:fmodule:importlist";

    /**
     * The icon image that represents this extension.
     */
    public static final String  ICON
    = "/com/moneydance/modules/features/importlist/icon.png";

    /**
     * The suffix of the application event that lets the user change the base
     * directory.
     */
    public static final String  CHOOSE_BASE_DIR_SUFFIX
    = "chooseimportdir";

    /**
     * The suffix of the application event that reloads the context in which
     * this extension is running.
     */
    public static final String  RELOAD_CONTEXT_SUFFIX
    = "reloadcontext";

    /**
     * The prefix of the application event that imports a given file.
     */
    public static final String  IMPORT_URI_PREFIX
    = "moneydance:importprompt:?file=";

    /**
     * The amount of milliseconds to wait between running the file scanner.
     */
    public static final int     MONITOR_INTERVAL
    = 10000;

    /**
     * Valid file extensions that can be imported (case-insensitive).
     */
    public static final String[] FILE_EXTENSIONS
    = {"qif", "ofx", "qfx", "ofc"};

    /**
     * Identifier of the preference that stores the current base directory.
     */
    public static final String  PREF_BASE_DIR
    = "importlist.import_dir";

    /**
     * Identifier of the preference that stores the current column widths.
     */
    public static final String  PREF_COLUMN_WIDTHS
    = "importlist.column_widths";

    /**
     * Identifier of the preference that stores the current order of the
     * columns.
     */
    public static final String  PREF_COLUMN_ORDER
    = "importlist.column_order";

    /**
     * Identifier of the preference that stores the current sort order.
     */
    public static final String  PREF_SORT_ORDER
    = "importlist.sort_order";

    /**
     * Identifier of the preference that stores the current foreground color.
     */
    public static final String  PREF_FG_COLOR
    = UserPreferences.GUI_HOMEPG_FG;

    /**
     * Identifier of the preference that stores the current background color.
     */
    public static final String  PREF_BG_COLOR
    = UserPreferences.GUI_HOMEPG_BG;

    /**
     * Identifier of the preference that stores the current alternative
     * background color.
     */
    public static final String  PREF_BG_COLOR_ALT
    = "gui.home_alt_bg";

    /**
     * Unique descriptor of the "name" column.
     */
    public static final String  DESC_NAME
    = "Name";

    /**
     * Unique descriptor of the "modified" column.
     */
    public static final String  DESC_MODIFIED
    = "Date Modified";

    /**
     * Unique descriptor of the "import" column.
     */
    public static final String  DESC_IMPORT
    = "Import";

    /**
     * Unique descriptor of the "delete" column.
     */
    public static final String  DESC_DELETE
    = "Delete";

    /**
     * Indentation prefix for table header and values.
     */
    public static final String  INDENTATION_PREFIX
    = " ";

    /**
     * Header of the "name" column.
     */
    public static final String  HEADER_VALUE_NAME
    = INDENTATION_PREFIX + "Name";

    /**
     * Header of the "modified" column.
     */
    public static final String  HEADER_VALUE_MODIFIED
    = INDENTATION_PREFIX + "Date Modified";

    /**
     * Header of the "import" column.
     */
    public static final String  HEADER_VALUE_IMPORT
    = INDENTATION_PREFIX + "";

    /**
     * Header of the "delete" column.
     */
    public static final String  HEADER_VALUE_DELETE
    = INDENTATION_PREFIX + "";

    /**
     * Label of the "import" button.
     */
    public static final String  LABEL_IMPORT_BUTTON
    = "Import";

    /**
     * Label of the "delete" button.
     */
    public static final String  LABEL_DELETE_BUTTON
    = "Delete";

    /**
     * Width of the buttons.
     */
    public static final int     BUTTON_WIDTH
    = 60;

    /**
     * Determines if the button columns can have different widths.
     */
    public static final boolean BUTTON_RESIZABLE
    = false;

    /**
     * Mininum width of all columns.
     */
    public static final int     MIN_COLUMN_WIDTH
    = 60;

    /**
     * Determines if reordering of the columns is allowed.
     */
    public static final boolean ALLOW_REORDERING
    = true;

    /**
     * Preferred width of the file table.
     */
    public static final int     PREFERRED_TABLE_WIDTH
    = 500;

    /**
     * Preferred height of the file table.
     */
    public static final int     PREFERRED_TABLE_HEIGHT
    = 150;

    /**
     * Preferred width of the message box when no displayable file is found.
     */
    public static final int     PREFERRED_EMPTY_MESSAGE_WIDTH
    = 400;

    /**
     * Preferred height of the message box when no displayable file is found.
     */
    public static final int     PREFERRED_EMPTY_MESSAGE_HEIGHT
    = 80;

    /**
     * Determines the height of a table row by multiplying this value with the
     * font size.
     */
    public static final double  FACTOR_ROW_HEIGHT
    = 1.7;

    /**
     * Identifier of the <code>UIManager</code>s font to be used for the table
     * header.
     */
    public static final String  HEADER_KEY_FONT
    = "OptionPane.font";

    /**
     * Identifier of the <code>UIManager</code>s font to be used for the table
     * body.
     */
    public static final String  BODY_KEY_FONT
    = "Label.font";

    /**
     * Default foreground color.
     */
    public static final int     COLOR_VALUE_FG_DEF
    = -16777216;

    /**
     * Default background color.
     */
    public static final int     COLOR_VALUE_BG_DEF
    = -103;

    /**
     * Default alternative background color.
     */
    public static final int     COLOR_VALUE_BG_ALT_DEF
    = -657931;
}
