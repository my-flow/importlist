/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.moneydance.modules.features.importlist.util;

import java.io.InputStream;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This configuration class accesses all values that are read
 * from a settings file in plain text. The settings file cannot be
 * modified at runtime, so all <code>Settings</code> objects are immutable.
 *
 * @author Florian J. Breunig
 */
public final class Settings {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(Settings.class);

    private final Configuration config;

    /**
     * The resource in the JAR file to read the settings from.
     */
    private static final String PROPERTIES_RESOURCE
    = "com/moneydance/modules/features/importlist/resources/"
            + "settings.properties";

    Settings() {
        final AbstractFileConfiguration abstractFileConfiguration =
                new PropertiesConfiguration();

        try {
            InputStream inputStream = Helper.getInputStreamFromResource(
                    PROPERTIES_RESOURCE);
            abstractFileConfiguration.load(inputStream);
        } catch (IllegalArgumentException e) {
            LOG.warn(e.getMessage(), e);
        } catch (ConfigurationException e) {
            LOG.warn(e.getMessage(), e);
        }
        this.config = abstractFileConfiguration;
    }

    /**
     * @return The descriptive name of this extension.
     */
    public String getExtensionName() {
        return this.config.getString("extension_name");
    }

    /**
     * @return The ID string for this extension.
     */
    public String getExtensionIdentifier() {
        return this.config.getString("extension_identifier");
    }

    /**
     * @return The icon image that represents this extension.
     */
    public String getIconResource() {
        return this.config.getString("icon_resource");
    }

    /**
     * @return The resource that contains the configuration of log4j.
     */
    public String getLog4jPropertiesResource() {
        return this.config.getString("log4j_properties_resource");
    }

    /**
     * @return The resource in the JAR file to read the language strings from.
     */
    public String getLocalizableResource() {
        return this.config.getString("localizable_resource");
    }

    /**
     * @return The suffix of the application event that lets the user change the
     * base directory.
     */
    public String getChooseBaseDirSuffix() {
        return this.config.getString("choose_base_dir_suffix");
    }

    /**
     * @return The scheme of the application event that imports a given file.
     */
    public String getTransactionFileImportUriScheme() {
        return this.config.getString("transaction_file_import_uri_scheme");
    }

    /**
     * @return The scheme of the application event that imports a given CSV file
     * using the text file importer plugin.
     */
    public String getTextFileImportUriScheme() {
        return this.config.getString("text_file_import_uri_scheme");
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
        String[] transactionFileExtensions =
                this.config.getStringArray("transaction_file_extensions");
        return transactionFileExtensions;
    }

    /**
     * @return Valid extensions of text files that can be imported
     * (case-insensitive).
     */
    public String[] getTextFileExtensions() {
        String[] textFileExtensions =
                this.config.getStringArray("text_file_extensions");
        return textFileExtensions;
    }

    /**
     * @return Maximum length of a filename displayed in an error message.
     */
    public int getMaxFilenameLength() {
        return this.config.getInt("max_filename_length");
    }

    /**
     * @return Unique descriptor of the "name" column.
     */
    public String getDescName() {
        return this.config.getString("desc_name");
    }

    /**
     * @return Unique descriptor of the "modified" column.
     */
    public String getDescModified() {
        return this.config.getString("desc_modified");
    }

    /**
     * @return Unique descriptor of the "import" column.
     */
    public String getDescImport() {
        return this.config.getString("desc_import");
    }

    /**
     * @return Unique descriptor of the "delete" column.
     */
    public String getDescDelete() {
        return this.config.getString("desc_delete");
    }

    /**
     * @return Indentation prefix for table header and values.
     */
    public String getIndentationPrefix() {
        return this.config.getString("indentation_prefix");
    }

    /**
     * @return Determines if the button columns can have different widths.
     */
    public boolean isButtonResizable() {
        return this.config.getBoolean("button_resizable");
    }

    /**
     * @return Minimum width of all columns.
     */
    public int getMinColumnWidth() {
        return this.config.getInt("min_column_width");
    }

    /**
     * @return Determines if reordering of the columns is allowed.
     */
    public boolean isReorderingAllowed() {
        return this.config.getBoolean("reordering_allowed");
    }

    /**
     * @return Minimum width of the file table.
     */
    public int getMinimumTableWidth() {
        return this.config.getInt("minimum_table_width");
    }

    /**
     * @return Minimum height of the file table.
     */
    public int getMinimumTableHeight() {
        return this.config.getInt("minimum_table_height");
    }

    /**
     * @return Constant offset to determine the preferred table height.
     */
    public int getTableHeightOffset() {
        return this.config.getInt("table_height_offset");
    }

    /**
     * @return The default width of the columns
     */
    public int getColumnWidth() {
        return this.config.getInt("column_width");
    }

    /**
     * @return Preferred width of the message box when no displayable file is
     * found.
     */
    public int getPreferredEmptyMessageWidth() {
        return this.config.getInt("preferred_empty_message_width");
    }

    /**
     * @return Preferred height of the message box when no displayable file is
     * found.
     */
    public int getPreferredEmptyMessageHeight() {
        return this.config.getInt("preferred_empty_message_height");
    }

    /**
     * The filename in the confirmation message is split in several lines. This
     * value defines the maximum length of each line.
     * @return The maximum length of each line.
     */
    public int getMessageFilenameLineMaxLength() {
        return this.config.getInt("message_filename_line_max_length");
    }

    /**
     * @return The length of the version substring extracted from the user's
     * settings, e.g. 2007r5.
     */
    public int getLengthOfVersionDigits() {
        return this.config.getInt("length_of_version_digits");
    }

    /**
     * @return The version of Moneydance that uses an opaque background for its
     * homepage views.
     */
    public int getVersionWithOpaqueHomepageView() {
        return this.config.getInt("version_with_opaque_homepage_view");
    }

    /**
     * @return The default foreground color.
     */
    public int getColorValueFgDef() {
        return this.config.getInt("color_value_fg_def");
    }

    /**
     * @return The default background color.
     */
    public int getColorValueBgDef() {
        return this.config.getInt("color_value_bg_def");
    }

    /**
     * @return Default alternative background color.
     */
    public int getColorValueBgAltDef() {
        return this.config.getInt("color_value_bg_alt_def");
    }

    /**
     * @return The height of the header row by multiplying this value with
     * the font size.
     */
    public double getFactorRowHeightHeader() {
        return this.config.getDouble("factor_row_height_header");
    }

    /**
     * @return The height of a table row by adding this value to the font size.
     */
    public double getSummandRowHeightBody() {
        return this.config.getDouble("summand_row_height_body");
    }

    /**
     * @return Keyboard shortcut to import files.
     */
    public String getKeyboardShortcutImport() {
        return this.config.getString("keyboard_shortcut_import");
    }

    /**
     * @return Keyboard shortcut to delete files.
     */
    public String getKeyboardShortcutDelete() {
        return this.config.getString("keyboard_shortcut_delete");
    }

    /**
     * @return Tracking code for Google Analytics (aka "utmac").
     */
    public String getTrackingCode() {
        return this.config.getString("tracking_code");
    }
}
