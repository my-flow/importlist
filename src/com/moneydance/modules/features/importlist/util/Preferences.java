/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Hashtable;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.util.CustomDateFormat;
import com.moneydance.util.StreamTable;

/**
 * This preferences class contains all settings that are required in the
 * extension. It serves as a facade abstracting Moneydance's
 * <code>UserPreferences</code> (received from the
 * <code>FeatureModuleContext</code>) and the configuration values that are read
 * from a configuration file.
 */
public final class Preferences {

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(Preferences.class);

    private static Preferences  instance;
    private final FeatureModule featureModule;
    private final StreamTable   columnOrderDefault;
    private final StreamTable   sortOrderDefault;
    private Image               image;
    private final StreamTable   columnWidths;
    private StreamTable         columnOrder;
    private UserPreferences     userPreferences;
    private final Configuration config;
    private boolean             initialized;

    /**
     * The constructor must be called exactly once before using the only
     * instance of this class.
     * @param argFeatureModule The feature module to receive the context from.
     */
    public Preferences(final FeatureModule argFeatureModule) {
        Validate.isTrue(instance == null,
        "You can instantiate Preferences only once");
        Validate.notNull(argFeatureModule, "argFeatureModule can't be null");

        AbstractFileConfiguration abstractFileConfiguration =
            new PropertiesConfiguration();

        try {
            InputStream inputStream =
                getInputStreamFromResource(Constants.PROPERTIES_RESOURCE);
            abstractFileConfiguration.load(inputStream);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage(), e);
        } catch (ConfigurationException e) {
            log.warn(e.getMessage(), e);
        }
        this.config = abstractFileConfiguration;

        this.featureModule      = argFeatureModule;
        this.columnWidths       = new StreamTable();
        this.columnOrderDefault = new StreamTable();
        this.columnOrderDefault.put("0", Constants.DESC_NAME);
        this.columnOrderDefault.put("1", Constants.DESC_MODIFIED);
        this.columnOrderDefault.put("2", Constants.DESC_IMPORT);
        this.columnOrderDefault.put("3", Constants.DESC_DELETE);
        this.sortOrderDefault   = new StreamTable();
        this.sortOrderDefault.put("0", SortOrder.ASCENDING.toString());

        instance = this;
    }

    public static Preferences getInstance() {
        Validate.notNull(instance, "You must instantiate Preferences "
                + "first before you can access it");
        if (!instance.initialized) {
            instance.reload();
        }
        return instance;
    }

    /**
     * Reload context from feature module.
     */
    public void reload() {
        this.featureModule.invoke(this.getReloadContextSuffix());
        instance.initialized = true;
    }

    public void setContext(final FeatureModuleContext context) {
        this.userPreferences = ((com.moneydance.apps.md.controller.Main)
                context).getPreferences();
    }

    public static void loadLoggerConfiguration() {
        boolean rootIsConfigured =
            Logger.getRootLogger().getAllAppenders().hasMoreElements();
        if (rootIsConfigured) {
            // do not overwrite any existing configurations
            return;
        }

        Properties properties = new Properties();
        try {
            InputStream inputStream = Preferences.getInputStreamFromResource(
                    Constants.LOG4J_PROPERTIES_RESOURCE);
            properties.load(inputStream);
        }  catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        PropertyConfigurator.configure(properties);
    }

    public void setAllWritablePreferencesToNull() {
        this.setBaseDirectory(null);
        this.userPreferences.setSetting(
                Constants.PREF_COLUMN_WIDTHS,
                (StreamTable) null);
        this.userPreferences.setSetting(
                Constants.PREF_COLUMN_ORDER,
                (StreamTable) null);
        this.userPreferences.setSetting(
                Constants.PREF_SORT_ORDER,
                (StreamTable) null);
    }

    public int getVersion() {
        String fullString = this.userPreferences.getSetting(
                Constants.PREF_CURRENT_VERSION,
                null);
        if (fullString == null) {
            return 0;
        }
        int endIndex = Math.min(
                Constants.LENGTH_OF_VERSION_DIGITS,
                fullString.length());
        String substring = fullString.substring(0, endIndex);
        return Integer.parseInt(substring);
    }

    /**
     * @return The version of Moneydance that uses an opaque background for its
     * homepage views.
     */
    public int getVersionWithOpaqueHomepageView() {
        return this.config.getInt(
                "version_with_opaque_homepage_view",
                Constants.VERSION_WITH_OPAQUE_HOMEPAGE_VIEW);
    }

    public String getBaseDirectory() {
        return this.userPreferences.getSetting(
                Constants.PREF_BASE_DIR,
                null);
    }

    public void setBaseDirectory(final String baseDirectory) {
        this.userPreferences.setSetting(
                Constants.PREF_BASE_DIR,
                baseDirectory);
    }

    public String getImportDirectory() {
        return this.userPreferences.getSetting(
                UserPreferences.IMPORT_DIR,
                null);
    }

    public Image getIconImage() {
        if (this.image != null) {
            return this.image;
        }
        try {
            log.debug("Loading icon " + this.getIconResource()
                    + " from resource.");
            InputStream inputStream =
                getInputStreamFromResource(this.getIconResource());
            this.image = ImageIO.read(inputStream);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage(), e);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        return this.image;
    }

    public void setColumnWidths(
            final int column,
            final int columnWidth) {
        this.columnWidths.put(column + "", columnWidth);
        this.userPreferences.setSetting(
                Constants.PREF_COLUMN_WIDTHS,
                this.columnWidths);
    }

    public int getColumnWidths(final int column) {
        StreamTable streamTable = this.userPreferences.getTableSetting(
                Constants.PREF_COLUMN_WIDTHS,
                this.columnWidths);
        return streamTable.getInt(column + "", Constants.BUTTON_WIDTH);
    }

    public void setColumnNames(final Hashtable<String, String> hashtable) {
        StreamTable streamTable = new StreamTable();
        streamTable.merge(hashtable);
        this.userPreferences.setSetting(
                Constants.PREF_COLUMN_ORDER,
                streamTable);
    }

    public String getColumnName(final int column) {
        StreamTable streamTable = this.userPreferences.getTableSetting(
                Constants.PREF_COLUMN_ORDER,
                this.columnOrderDefault);
        if (this.columnOrder == null) {
            this.columnOrder = streamTable;
        }
        return this.columnOrder.getStr(column + "", null);
    }

    public void setSortKey(final RowSorter.SortKey sortKey) {
        StreamTable streamTable = new StreamTable();
        streamTable.put(sortKey.getColumn(), sortKey.getSortOrder().toString());
        this.userPreferences.setSetting(
                Constants.PREF_SORT_ORDER,
                streamTable);
    }

    public RowSorter.SortKey getSortKey() {
        StreamTable streamTable = this.userPreferences.getTableSetting(
                Constants.PREF_SORT_ORDER,
                null);
        if (streamTable == null || streamTable.keySet().isEmpty()) {
            streamTable = this.sortOrderDefault;
        }
        Object key        = streamTable.keys().nextElement();
        int column        = Integer.parseInt(key + "");
        String sortOrder  = streamTable.getStr(key, null);
        return new RowSorter.SortKey(column, SortOrder.valueOf(sortOrder));
    }

    public int getColumnCount() {
        StreamTable streamTable = this.userPreferences.getTableSetting(
                Constants.PREF_COLUMN_ORDER,
                this.columnOrderDefault);
        if (this.columnOrder == null) {
            this.columnOrder = streamTable;
        }
        return this.columnOrder.size();
    }

    public CustomDateFormat getDateFormatter() {
        return this.userPreferences.getShortDateFormatter();
    }

    public DateFormat getTimeFormatter() {
        return DateFormat.getTimeInstance();
    }

    public Color getForeground() {
        int foregroundValue = this.userPreferences.getIntSetting(
                Constants.PREF_FG_COLOR,
                Constants.COLOR_VALUE_FG_DEF);
        return new Color(foregroundValue);
    }

    public Color getBackground() {
        int backgroundValue = this.userPreferences.getIntSetting(
                Constants.PREF_BG_COLOR,
                Constants.COLOR_VALUE_BG_DEF);
        return new Color(backgroundValue);
    }

    public Color getBackgroundAlt() {
        int backgroundAltValue = this.userPreferences.getIntSetting(
                Constants.PREF_BG_COLOR_ALT,
                Constants.COLOR_VALUE_BG_ALT_DEF);
        return new Color(backgroundAltValue);
    }

    public Font getHeaderFont() {
        return UIManager.getFont(Constants.HEADER_KEY_FONT);
    }

    public Font getBodyFont() {
        return UIManager.getFont(Constants.BODY_KEY_FONT);
    }

    public int getHeaderRowHeight() {
        return (int)
        (Constants.FACTOR_ROW_HEIGHT * this.getHeaderFont().getSize());
    }

    public int getBodyRowHeight() {
        return (int)
        (Constants.FACTOR_ROW_HEIGHT * this.getBodyFont().getSize());
    }

    /**
     * @return The descriptive name of this extension.
     */
    public String getExtensionName() {
        return this.config.getString(
                "extension_name",
                Constants.EXTENSION_NAME);
    }

    /**
     * @return The ID string for this extension.
     */
    public String getId() {
        return this.config.getString(
                "id",
                Constants.ID);
    }

    /**
     * @return The icon image that represents this extension.
     */
    public String getIconResource() {
        return this.config.getString(
                "icon_resource",
                Constants.ICON_RESOURCE);
    }

    /**
     * @return The suffix of the application event that lets the user change the
     * base directory.
     */
    public String getChooseBaseDirSuffix() {
        return this.config.getString(
                "choose_base_dir_suffix",
                Constants.CHOOSE_BASE_DIR_SUFFIX);
    }

    /**
     * @return The suffix of the application event that reloads the context in
     * which this extension is running.
     */
    public String getReloadContextSuffix() {
        return this.config.getString(
                "reload_context_suffix",
                Constants.RELOAD_CONTEXT_SUFFIX);
    }

    /**
     * @return The prefix of the application event that imports a given file.
     */
    public String getTransactionFileImportUriPrefix() {
        return this.config.getString(
                "transaction_file_import_uri_prefix",
                Constants.TRANSACTION_FILE_IMPORT_URI_PREFIX);
    }

    /**
     * @return The prefix of the application event that imports a given CSV file
     * using the text file importer plugin.
     */
    public String getTextFileImportUriPrefix() {
        return this.config.getString(
                "text_file_import_uri_prefix",
                Constants.TEXT_FILE_IMPORT_URI_PREFIX);
    }

    /**
     * @return The amount of milliseconds to wait between two runs of the file
     * scanner.
     */
    public int getMonitorIntervall() {
        return this.config.getInt(
                "monitor_intervall",
                Constants.MONITOR_INTERVAL);
    }

    /**
     * @return Valid extensions of transaction files that can be imported
     * (case-insensitive).
     */
    public String[] getTransactionFileExtensions() {
        String[] transactionFileExtensions =
            this.config.getStringArray("transaction_file_extensions");
        if (transactionFileExtensions == null
                || transactionFileExtensions.length == 0) {
            transactionFileExtensions = Constants.TRANSACTION_FILE_EXTENSIONS;
        }
        return transactionFileExtensions;
    }

    /**
     * @return Valid extensions of text files that can be imported
     * (case-insensitive).
     */
    public String[] getTextFileExtensions() {
        String[] textFileExtensions =
            this.config.getStringArray("text_file_extensions");
        if (textFileExtensions == null
                || textFileExtensions.length == 0) {
            textFileExtensions = Constants.TEXT_FILE_EXTENSIONS;
        }
        return textFileExtensions;
    }

    /**
     * @return Maximum length of a filename displayed in an error message.
     */
    public int getMaxFilenameLength() {
        return this.config.getInt(
                "max_filename_length",
                Constants.MAX_FILENAME_LENGTH);
    }

    /**
     * @return Unique descriptor of the "name" column.
     */
    public String getDescName() {
        return this.config.getString(
                "desc_name",
                Constants.DESC_NAME);
    }

    /**
     * @return Unique descriptor of the "modified" column.
     */
    public String getDescModified() {
        return this.config.getString(
                "desc_modified",
                Constants.DESC_MODIFIED);
    }

    /**
     * @return Unique descriptor of the "import" column.
     */
    public String getDescImport() {
        return this.config.getString(
                "desc_import",
                Constants.DESC_IMPORT);
    }

    /**
     * @return Unique descriptor of the "delete" column.
     */
    public String getDescDelete() {
        return this.config.getString(
                "desc_delete",
                Constants.DESC_DELETE);
    }

    /**
     * @return Indentation prefix for table header and values.
     */
    public String getIndentationPrefix() {
        return this.config.getString(
                "indentation_prefix",
                Constants.INDENTATION_PREFIX);
    }

    /**
     * @return Header of the "name" column.
     */
    public String getHeaderValueName() {
        final String headerValueName =
            this.config.getString(
                    "header_value_name",
                    Constants.HEADER_VALUE_NAME);
        return this.getIndentationPrefix() + headerValueName;
    }

    /**
     * @return Header of the "modified" column.
     */
    public String getHeaderValueModified() {
        final String headerValueModified =
            this.config.getString(
                    "header_value_modified",
                    Constants.HEADER_VALUE_MODIFIED);
        return this.getIndentationPrefix() + headerValueModified;
    }

    /**
     * @return Header of the "import" column.
     */
    public String getHeaderValueImport() {
        final String headerValueImport =
            this.config.getString(
                    "header_value_import",
                    Constants.HEADER_VALUE_IMPORT);
        return this.getIndentationPrefix() + headerValueImport;
    }

    /**
     * @return Header of the "delete" column.
     */
    public String getHeaderValueDelete() {
        final String headerValueDelete =
            this.config.getString(
                    "header_value_delete",
                    Constants.HEADER_VALUE_DELETE);
        return this.getIndentationPrefix() + headerValueDelete;
    }

    /**
     * @return Label of the "import" button.
     */
    public String getLabelImportButton() {
        return this.config.getString(
                "label_import_button",
                Constants.LABEL_IMPORT_BUTTON);
    }

    /**
     * @return Label of the "delete" button.
     */
    public String getLabelDeleteButton() {
        return this.config.getString(
                "label_delete_button",
                Constants.LABEL_DELETE_BUTTON);
    }

    /**
     * @return Determines if the button columns can have different widths.
     */
    public boolean getButtonResizable() {
        return this.config.getBoolean(
                "button_resizable",
                Constants.BUTTON_RESIZABLE);
    }

    /**
     * @return Minimum width of all columns.
     */
    public int getMinColumnWidth() {
        return this.config.getInt(
                "min_column_width",
                Constants.MIN_COLUMN_WIDTH);
    }

    /**
     * @return Determines if reordering of the columns is allowed.
     */
    public boolean getAllowReordering() {
        return this.config.getBoolean(
                "allow_reordering",
                Constants.ALLOW_REORDERING);
    }

    /**
     * @return Preferred width of the file table.
     */
    public int getPreferredTableWidth() {
        return this.config.getInt(
                "preferred_table_width",
                Constants.PREFERRED_TABLE_WIDTH);
    }

    /**
     * @return Preferred height of the file table.
     */
    public int getPreferredTableHeight() {
        return this.config.getInt(
                "preferred_table_height",
                Constants.PREFERRED_TABLE_HEIGHT);
    }

    /**
     * @return Preferred width of the message box when no displayable file is
     * found.
     */
    public int getPreferredEmptyMessageWidth() {
        return this.config.getInt(
                "preferred_empty_message_width",
                Constants.PREFERRED_EMPTY_MESSAGE_WIDTH);
    }

    /**
     * @return Preferred height of the message box when no displayable file is
     * found.
     */
    public int getPreferredEmptyMessageHeight() {
        return this.config.getInt(
                "preferred_empty_message_height",
                Constants.PREFERRED_EMPTY_MESSAGE_HEIGHT);
    }

    /**
     * @return The title of the dialog that is displayed to choose the base
     * directory.
     */
    public String getDirectoryChooserTitle() {
        return this.config.getString(
                "directory_chooser_title",
                Constants.DIRECTORY_CHOOSER_TITLE);
    }

    /**
     * The filename in the confirmation message is split in several lines. This
     * value defines the maximum length of each line.
     * @return The maximum length of each line.
     */
    public int getMessageFilenameLineMaxLength() {
        return this.config.getInt(
                "message_filename_line_max_length",
                Constants.MESSAGE_FILENAME_LINE_MAX_LENGTH);
    }

    /**
     * @param filename The name of the file that is to be deleted.
     * @return Confirmation message to be displayed before a file will be
     *  deleted.
     */
    public String getConfirmationMessageDeleteFile(final String filename) {
        String rawMessage = this.config.getString(
                "confirmation_message_delete_file",
                Constants.CONFIRMATION_MESSAGE_DELETE_FILE);
        return rawMessage.replace("(0)", this.getMarkupFilename(filename));
    }

    /**
     * @param filename The name of the file that cannot be deleted.
     * @return Error message to be displayed if a file cannot be deleted.
     */
    public String getErrorMessageDeleteFile(final String filename) {
        String rawMessage = this.config.getString(
                "error_message_delete_file",
                Constants.ERROR_MESSAGE_DELETE_FILE);
        return rawMessage.replace("(0)", this.getMarkupFilename(filename));
    }

    /**
     * @return Delete button in confirmation message.
     */
    public String getOptionDeleteFile() {
        return this.config.getString(
                "option_delete_file",
                Constants.OPTION_DELETE_FILE);
    }

    /**
     * @return Cancel button in confirmation message.
     */
    public String getOptionCancel() {
        return this.config.getString(
                "option_cancel",
                Constants.OPTION_CANCEL);
    };

    /**
     * @param baseDirectory The base directory which does not contain any files
     * to display.
     * @return Message to display if the list of files is empty.
     */
    public String getEmptyMessage(final String baseDirectory) {
        String rawMessage = this.config.getString(
                "empty_message",
                Constants.EMPTY_MESSAGE);
        return rawMessage.replace("(0)", baseDirectory);
    }

    private String getMarkupFilename(final String filename) {
        int length = this.getMessageFilenameLineMaxLength();

        int numberOfLines = filename.length() / length + 1;
        String[] substrings = new String[numberOfLines];

        for (int i = 0; i < numberOfLines; i++) {
            int start =  i      * length;
            int end   = (i + 1) * length;
            substrings[i] = StringUtils.substring(filename, start, end);
        }
        return StringUtils.join(substrings, "<br>");
    }

    private static InputStream getInputStreamFromResource(
            final String resource) {
        ClassLoader cl          = Preferences.class.getClassLoader();
        InputStream inputStream = cl.getResourceAsStream(resource);
        Validate.notNull(inputStream,
                "Resource " + resource + " was not found.");
        return inputStream;
    }
}
