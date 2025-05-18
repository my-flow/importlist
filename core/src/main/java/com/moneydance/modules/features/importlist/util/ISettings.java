package com.moneydance.modules.features.importlist.util;

import java.awt.Image;
import java.io.InputStream;
import java.util.List;

/**
 * Interface for settings that defines all methods required by the application.
 *
 * @author Florian J. Breunig
 */
public interface ISettings {

    /**
     * @return The descriptive name of this extension.
     */
    String getExtensionName();

    /**
     * @return The ID string for this extension.
     */
    String getExtensionIdentifier();

    /**
     * @return The icon image that represents this extension.
     */
    Image getIconImage();

    /**
     * @return The resource that contains the configuration of the logger.
     */
    InputStream getLoggingPropertiesResource();

    /**
     * @return The resource in the JAR file to read the language strings from.
     */
    String getLocalizableResource();

    /**
     * @return The suffix of the application event that lets the user change the
     * base directory.
     */
    String getChooseBaseDirSuffix();

    /**
     * @return The scheme of the application event that imports a given file.
     */
    String getTransactionFileImportUriScheme();

    /**
     * @return The scheme of the application event that imports a given CSV file
     * using the text file importer plugin.
     */
    String getTextFileImportUriScheme();

    /**
     * @return The amount of milliseconds to wait between two runs of the file
     * scanner.
     */
    int getMonitorInterval();

    /**
     * @return Valid extensions of transaction files that can be imported
     * (case-insensitive).
     */
    List<String> getTransactionFileExtensions();

    /**
     * @return Maximum length of a filename displayed in an error message.
     */
    int getMaxFilenameLength();

    /**
     * @return Unique descriptor of the "name" column.
     */
    String getDescName();

    /**
     * @return Unique descriptor of the "modified" column.
     */
    String getDescModified();

    /**
     * @return Unique descriptor of the "import" column.
     */
    String getDescImport();

    /**
     * @return Unique descriptor of the "delete" column.
     */
    String getDescDelete();

    /**
     * @return Indentation prefix for table header and values.
     */
    String getIndentationPrefix();

    /**
     * @return Determines if the button columns can have different widths.
     */
    boolean isButtonResizable();

    /**
     * @return Minimum width of all columns.
     */
    int getMinColumnWidth();

    /**
     * @return Determines if reordering of the columns is allowed.
     */
    boolean isReorderingAllowed();

    /**
     * @return Minimum width of the file table.
     */
    int getMinimumTableWidth();

    /**
     * @return Minimum height of the file table.
     */
    int getMinimumTableHeight();

    /**
     * @return Constant offset to determine the preferred table height.
     */
    int getTableHeightOffset();

    /**
     * @return The default width of the columns
     */
    int getColumnWidth();

    /**
     * @return Preferred width of the message box when no displayable file is
     * found.
     */
    int getPreferredEmptyMessageWidth();

    /**
     * @return Preferred height of the message box when no displayable file is
     * found.
     */
    int getPreferredEmptyMessageHeight();

    /**
     * The filename in the confirmation message is split in several lines. This
     * value defines the maximum length of each line.
     * @return The maximum length of each line.
     */
    int getMessageFilenameLineMaxLength();

    /**
     * @return The default foreground color.
     */
    int getColorValueFgDef();

    /**
     * @return The default background color.
     */
    int getColorValueBgDef();

    /**
     * @return Default alternative background color.
     */
    int getColorValueBgAltDef();

    /**
     * @return The height of the header row by multiplying this value with
     * the font size.
     */
    double getFactorRowHeightHeader();

    /**
     * @return The height of a table row by adding this value to the font size.
     */
    double getSummandRowHeightBody();

    /**
     * @return Keyboard shortcut to import files.
     */
    String getKeyboardShortcutImport();

    /**
     * @return Keyboard shortcut to delete files.
     */
    String getKeyboardShortcutDelete();
}
