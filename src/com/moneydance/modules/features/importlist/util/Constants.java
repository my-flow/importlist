package com.moneydance.modules.features.importlist.util;

import com.moneydance.apps.md.controller.UserPreferences;


/**
 * Constants which are used throughout the extension and are subject to
 * change.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
final class Constants {

    /**
     * Private constructor prevents this class from being instantiated.
     */
    private Constants() {
    }

    /**
     * The resource that contains the configuration of log4j.
     */
    static final String LOG4J_PROPERTIES_RESOURCE
    = "com/moneydance/modules/features/importlist/log4j.properties";

    /**
     * The resource in the JAR file to read the properties from.
     */
    static final String PROPERTIES_RESOURCE
    = "com/moneydance/modules/features/importlist/importlist.properties";

    /**
     * The descriptive name of this extension.
     */
    static final String  EXTENSION_NAME
    = "Import List";

    /**
     * The ID string for this extension.
     */
    static final String  ID
    = "moneydance:fmodule:importlist";

    /**
     * The resource of the icon image that represents this extension.
     */
    static final String  ICON_RESOURCE
    = "com/moneydance/modules/features/importlist/icon.png";

    /**
     * The suffix of the application event that lets the user change the base
     * directory.
     */
    static final String  CHOOSE_BASE_DIR_SUFFIX
    = "chooseimportdir";

    /**
     * The suffix of the application event that reloads the context in which
     * this extension is running.
     */
    static final String  RELOAD_CONTEXT_SUFFIX
    = "reloadcontext";

    /**
     * The prefix of the application event that imports a given file.
     */
    static final String  IMPORT_URI_PREFIX
    = "moneydance:importprompt:?file=";

    /**
     * The amount of milliseconds to wait between two runs of the file scanner.
     */
    static final int     MONITOR_INTERVAL
    = 10000;

    /**
     * Valid file extensions that can be imported (case-insensitive).
     */
    static final String[] FILE_EXTENSIONS
    = {"qif", "ofx", "qfx", "ofc"};

    /**
     * Maximum length of a filename displayed in an error message.
     */
    static final int MAX_FILENAME_LENGTH = 20;

    /**
     * Identifier of the preference that stores the current base directory.
     */
    static final String  PREF_BASE_DIR
    = "importlist.import_dir";

    /**
     * Identifier of the preference that stores the current column widths.
     */
    static final String  PREF_COLUMN_WIDTHS
    = "importlist.column_widths";

    /**
     * Identifier of the preference that stores the current order of the
     * columns.
     */
    static final String  PREF_COLUMN_ORDER
    = "importlist.column_order";

    /**
     * Identifier of the preference that stores the current sort order.
     */
    static final String  PREF_SORT_ORDER
    = "importlist.sort_order";

    /**
     * Identifier of the preference that stores the current foreground color.
     */
    static final String  PREF_FG_COLOR
    = UserPreferences.GUI_HOMEPG_FG;

    /**
     * Identifier of the preference that stores the current background color.
     */
    static final String  PREF_BG_COLOR
    = UserPreferences.GUI_HOMEPG_BG;

    /**
     * Identifier of the preference that stores the current alternative
     * background color.
     */
    static final String  PREF_BG_COLOR_ALT
    = "gui.home_alt_bg";

    /**
     * Unique descriptor of the "name" column.
     */
    static final String  DESC_NAME
    = "Name";

    /**
     * Unique descriptor of the "modified" column.
     */
    static final String  DESC_MODIFIED
    = "Date Modified";

    /**
     * Unique descriptor of the "import" column.
     */
    static final String  DESC_IMPORT
    = "Import";

    /**
     * Unique descriptor of the "delete" column.
     */
    static final String  DESC_DELETE
    = "Delete";

    /**
     * Indentation prefix for table header and values.
     */
    static final String  INDENTATION_PREFIX
    = " ";

    /**
     * Header of the "name" column.
     */
    static final String  HEADER_VALUE_NAME
    = "Name";

    /**
     * Header of the "modified" column.
     */
    static final String  HEADER_VALUE_MODIFIED
    = "Date Modified";

    /**
     * Header of the "import" column.
     */
    static final String  HEADER_VALUE_IMPORT
    = "";

    /**
     * Header of the "delete" column.
     */
    static final String  HEADER_VALUE_DELETE
    = "";

    /**
     * Label of the "import" button.
     */
    static final String  LABEL_IMPORT_BUTTON
    = "Import";

    /**
     * Label of the "delete" button.
     */
    static final String  LABEL_DELETE_BUTTON
    = "Delete";

    /**
     * Width of the buttons.
     */
    static final int     BUTTON_WIDTH
    = 60;

    /**
     * Determines if the button columns can have different widths.
     */
    static final boolean BUTTON_RESIZABLE
    = false;

    /**
     * Minimum width of all columns.
     */
    static final int     MIN_COLUMN_WIDTH
    = 60;

    /**
     * Determines if reordering of the columns is allowed.
     */
    static final boolean ALLOW_REORDERING
    = true;

    /**
     * Preferred width of the file table.
     */
    static final int     PREFERRED_TABLE_WIDTH
    = 300;

    /**
     * Preferred height of the file table.
     */
    static final int     PREFERRED_TABLE_HEIGHT
    = 160;

    /**
     * Preferred width of the message box when no displayable file is found.
     */
    static final int     PREFERRED_EMPTY_MESSAGE_WIDTH
    = 300;

    /**
     * Preferred height of the message box when no displayable file is found.
     */
    static final int     PREFERRED_EMPTY_MESSAGE_HEIGHT
    = 80;

    /**
     * Determines the height of a table row by multiplying this value with the
     * font size.
     */
    static final double  FACTOR_ROW_HEIGHT
    = 1.7;

    /**
     * Identifier of the <code>UIManager</code>s font to be used for the table
     * header.
     */
    static final String  HEADER_KEY_FONT
    = "OptionPane.font";

    /**
     * Identifier of the <code>UIManager</code>s font to be used for the table
     * body.
     */
    static final String  BODY_KEY_FONT
    = "Label.font";

    /**
     * Default foreground color.
     */
    static final int     COLOR_VALUE_FG_DEF
    = -16777216;

    /**
     * Default background color.
     */
    static final int     COLOR_VALUE_BG_DEF
    = -103;

    /**
     * Default alternative background color.
     */
    static final int     COLOR_VALUE_BG_ALT_DEF
    = -657931;

    /**
     * The title of the dialog that is displayed to choose the base directory.
     */
    static final String  DIRECTORY_CHOOSER_TITLE
    = "Please choose a base directory to monitor!";

    /**
     * The filename in the confirmation message is split in several lines. This
     * value defines the maximum length of each line.
     */
    static final int     MESSAGE_FILENAME_LINE_MAX_LENGTH
    = 32;

    /**
     * Error message to be displayed if a file cannot be read.
     */
    static final String  ERROR_MESSAGE_CANNOT_READ_FILE
    = "<html><b>File \"(0)\" could not be read.</b></html>";

    /**
     * Confirmation message to be displayed before a file will be deleted.
     */
    static final String  CONFIRMATION_MESSAGE_DELETE_FILE
    = "<html><b>Are you sure you want to delete<br>\"(0)\"?</b><br><br>"
        + "This item will be deleted immediately. You can't<br>"
        + "undo this action.</html>";

    /**
     * Error message to be displayed if a file cannot be deleted.
     */
    static final String  ERROR_MESSAGE_DELETE_FILE
    = "<html><b>File \"(0)\" could not be deleted.</b></html>";

    /**
     * Delete button in confirmation message.
     */
    static final String  OPTION_DELETE_FILE
    = "Delete";

    /**
     * Cancel button in confirmation message.
     */
    static final String  OPTION_CANCEL
    = "Cancel";

    /**
     * Message to display if the list of files is empty.
     */
    static final String  EMPTY_MESSAGE
    = "<html><center><em>There are currently no files to import<br>"
        + "in (0)</em><center></html>";
}
