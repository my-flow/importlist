// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2018 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package com.moneydance.modules.features.importlist.util;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


/**
 * This configuration class accesses all values that are read
 * from a settings file in plain text. The settings file cannot be
 * modified at runtime, so the <code>Settings</code> class is effectively
 * immutable.
 *
 * @author Florian J. Breunig
 */
public final class Settings {

    /**
     * The resource in the JAR file to read the settings from.
     */
    private static final String PROPERTIES_RESOURCE = "settings.properties";

    private final Configuration config;

    Settings() {
        try {
            InputStream inputStream = Helper.getInputStreamFromResource(
                    PROPERTIES_RESOURCE);

            final AbstractFileConfiguration abstractFileConfiguration =
                    new PropertiesConfiguration();
            abstractFileConfiguration.load(inputStream);
            this.config = abstractFileConfiguration;
        } catch (ConfigurationException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * @return The descriptive name of this extension.
     */
    public String getExtensionName() {
        return this.config.getString("extension_name"); //$NON-NLS-1$
    }

    /**
     * @return The ID string for this extension.
     */
    public String getExtensionIdentifier() {
        return this.config.getString("extension_identifier"); //$NON-NLS-1$
    }

    /**
     * @return The icon image that represents this extension.
     */
    public Image getIconImage() {
        return getImage(this.config.getString("icon_resource")); //$NON-NLS-1$
    }

    /**
     * @return The resource that contains the configuration of the logger.
     */
    public String getLoggingPropertiesResource() {
        return this.config.getString(
                "logging_properties_resource"); //$NON-NLS-1$
    }

    /**
     * @return The resource in the JAR file to read the language strings from.
     */
    public String getLocalizableResource() {
        return this.config.getString("localizable_resource"); //$NON-NLS-1$
    }

    /**
     * @return The suffix of the application event that lets the user change the
     * base directory.
     */
    public String getChooseBaseDirSuffix() {
        return this.config.getString("choose_base_dir_suffix"); //$NON-NLS-1$
    }

    /**
     * @return The scheme of the application event that imports a given file.
     */
    public String getTransactionFileImportUriScheme() {
        return this.config.getString(
                "transaction_file_import_uri_scheme"); //$NON-NLS-1$
    }

    /**
     * @return The scheme of the application event that imports a given CSV file
     * using the text file importer plugin.
     */
    public String getTextFileImportUriScheme() {
        return this.config.getString(
                "text_file_import_uri_scheme"); //$NON-NLS-1$
    }

    /**
     * @return The amount of milliseconds to wait between two runs of the file
     * scanner.
     */
    public int getMonitorInterval() {
        return this.config.getInt("monitor_interval");
    }

    /**
     * @return Valid extensions of transaction files that can be imported
     * (case-insensitive).
     */
    public String[] getTransactionFileExtensions() {
        String[] transactionFileExtensions = this.config.getStringArray(
                "transaction_file_extensions"); //$NON-NLS-1$
        return transactionFileExtensions;
    }

    /**
     * @return Valid extensions of text files that can be imported
     * (case-insensitive).
     */
    public String[] getTextFileExtensions() {
        String[] textFileExtensions =
                this.config.getStringArray(
                        "text_file_extensions"); //$NON-NLS-1$
        return textFileExtensions;
    }

    /**
     * @return Maximum length of a filename displayed in an error message.
     */
    public int getMaxFilenameLength() {
        return this.config.getInt("max_filename_length"); //$NON-NLS-1$
    }

    /**
     * @return Unique descriptor of the "name" column.
     */
    public String getDescName() {
        return this.config.getString("desc_name"); //$NON-NLS-1$
    }

    /**
     * @return Unique descriptor of the "modified" column.
     */
    public String getDescModified() {
        return this.config.getString("desc_modified"); //$NON-NLS-1$
    }

    /**
     * @return Unique descriptor of the "import" column.
     */
    public String getDescImport() {
        return this.config.getString("desc_import"); //$NON-NLS-1$
    }

    /**
     * @return Unique descriptor of the "delete" column.
     */
    public String getDescDelete() {
        return this.config.getString("desc_delete"); //$NON-NLS-1$
    }

    /**
     * @return Indentation prefix for table header and values.
     */
    public String getIndentationPrefix() {
        return this.config.getString("indentation_prefix"); //$NON-NLS-1$
    }

    /**
     * @return Determines if the button columns can have different widths.
     */
    public boolean isButtonResizable() {
        return this.config.getBoolean("button_resizable"); //$NON-NLS-1$
    }

    /**
     * @return Minimum width of all columns.
     */
    public int getMinColumnWidth() {
        return this.config.getInt("min_column_width"); //$NON-NLS-1$
    }

    /**
     * @return Determines if reordering of the columns is allowed.
     */
    public boolean isReorderingAllowed() {
        return this.config.getBoolean("reordering_allowed"); //$NON-NLS-1$
    }

    /**
     * @return Minimum width of the file table.
     */
    public int getMinimumTableWidth() {
        return this.config.getInt("minimum_table_width"); //$NON-NLS-1$
    }

    /**
     * @return Minimum height of the file table.
     */
    public int getMinimumTableHeight() {
        return this.config.getInt("minimum_table_height"); //$NON-NLS-1$
    }

    /**
     * @return Constant offset to determine the preferred table height.
     */
    public int getTableHeightOffset() {
        return this.config.getInt("table_height_offset"); //$NON-NLS-1$
    }

    /**
     * @return The default width of the columns
     */
    public int getColumnWidth() {
        return this.config.getInt("column_width"); //$NON-NLS-1$
    }

    /**
     * @return Preferred width of the message box when no displayable file is
     * found.
     */
    public int getPreferredEmptyMessageWidth() {
        return this.config.getInt(
                "preferred_empty_message_width"); //$NON-NLS-1$
    }

    /**
     * @return Preferred height of the message box when no displayable file is
     * found.
     */
    public int getPreferredEmptyMessageHeight() {
        return this.config.getInt(
                "preferred_empty_message_height"); //$NON-NLS-1$
    }

    /**
     * The filename in the confirmation message is split in several lines. This
     * value defines the maximum length of each line.
     * @return The maximum length of each line.
     */
    public int getMessageFilenameLineMaxLength() {
        return this.config.getInt(
                "message_filename_line_max_length"); //$NON-NLS-1$
    }

    /**
     * @return The height of the header row by multiplying this value with
     * the font size.
     */
    public double getFactorRowHeightHeader() {
        return this.config.getDouble("factor_row_height_header"); //$NON-NLS-1$
    }

    /**
     * @return The height of a table row by adding this value to the font size.
     */
    public double getSummandRowHeightBody() {
        return this.config.getDouble("summand_row_height_body"); //$NON-NLS-1$
    }

    /**
     * @return Keyboard shortcut to import files.
     */
    public String getKeyboardShortcutImport() {
        return this.config.getString("keyboard_shortcut_import"); //$NON-NLS-1$
    }

    /**
     * @return Keyboard shortcut to delete files.
     */
    public String getKeyboardShortcutDelete() {
        return this.config.getString("keyboard_shortcut_delete"); //$NON-NLS-1$
    }

    private static Image getImage(final String resource) {
        try {
            InputStream inputStream = Helper.getInputStreamFromResource(
                    resource);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
