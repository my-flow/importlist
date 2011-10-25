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
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;
import java.text.DateFormat;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

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
    private static final Logger LOG = Logger.getLogger(Preferences.class);

    /**
     * The resource in the JAR file to read the properties from.
     */
    private static final String PROPERTIES_RESOURCE
    = "com/moneydance/modules/features/importlist/resources/"
        + "importlist.properties";

    private         FeatureModule   featureModule;
    private final   StreamTable     columnOrderDefault;
    private final   StreamTable     sortOrderDefault;
    private final   StreamTable     columnWidths;
    private         StreamTable     columnOrder;
    private         UserPreferences userPreferences;
    private final   Configuration   config;
    private         ResourceBundle  localizable;

    /**
     * The constructor must be called exactly once before using the only
     * instance of this class.
     */
    Preferences() {
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
        this.config             = abstractFileConfiguration;

        this.columnWidths       = new StreamTable();
        this.columnOrderDefault = new StreamTable();
        this.columnOrderDefault.put("0", this.getDescName());
        this.columnOrderDefault.put("1", this.getDescModified());
        this.columnOrderDefault.put("2", this.getDescImport());
        this.columnOrderDefault.put("3", this.getDescDelete());
        this.sortOrderDefault   = new StreamTable();
        this.sortOrderDefault.put("0", SortOrder.ASCENDING.toString());
    }

    /**
     * @param argFeatureModule The feature module to receive the context from.
     */
    public void setFeatureModule(final FeatureModule argFeatureModule) {
        this.featureModule = argFeatureModule;
    }

    /**
     * Reload context from feature module.
     */
    public void reload() {
        Validate.notNull(this.featureModule, "You must set a feature module "
                + "first before you can reload the context");
        this.featureModule.invoke(this.getReloadContextSuffix());
        this.localizable = ResourceBundle.getBundle(
                this.getLocalizableResource(),
                this.getUserPreferences().getLocale());
    }

    public void setContext(final FeatureModuleContext context) {
        this.userPreferences = ((com.moneydance.apps.md.controller.Main)
                context).getPreferences();
    }

    private UserPreferences getUserPreferences() {
        if (this.userPreferences == null) {
            this.reload();
        }
        return this.userPreferences;
    }

    public void setAllWritablePreferencesToNull() {
        this.setBaseDirectory(null);
        this.getUserPreferences().setSetting(
                "importlist.column_widths",
                (StreamTable) null);
        this.setColumnNames(
                (Hashtable<String, String>) null);
        this.getUserPreferences().setSetting(
                "importlist.sort_order",
                (StreamTable) null);
    }

    public String getFullVersion() {
        final String fullString = this.getUserPreferences().getSetting(
        "current_version");
        if (fullString == null) {
            return "0";
        }
        return fullString;
    }

    public int getMajorVersion() {
        final String fullString = this.getFullVersion();
        final int endIndex = Math.min(
                this.config.getInt("length_of_version_digits"),
                fullString.length());
        String substring = fullString.substring(0, endIndex);
        return Integer.parseInt(substring);
    }

    /**
     * @return The version of Moneydance that uses an opaque background for its
     * homepage views.
     */
    public int getVersionWithOpaqueHomepageView() {
        return this.config.getInt("version_with_opaque_homepage_view");
    }

    public String getBaseDirectory() {
        return this.getUserPreferences().getSetting("importlist.import_dir");
    }

    public void setBaseDirectory(final String baseDirectory) {
        this.getUserPreferences().setSetting(
                "importlist.import_dir",
                baseDirectory);
    }

    public String getImportDirectory() {
        return this.getUserPreferences().getSetting(UserPreferences.IMPORT_DIR);
    }

    /**
     * @return The proxy server that the extension use to establish
     * a connection.
     */
    public Proxy getProxy() {
        boolean useProxy = this.getUserPreferences().getBoolSetting(
                UserPreferences.NET_USE_PROXY,
                false);

        if (!useProxy) {
            return Proxy.NO_PROXY;
        }

        final SocketAddress socketAddress = new InetSocketAddress(
                this.getUserPreferences().getSetting(
                        UserPreferences.NET_PROXY_HOST),
                        this.getUserPreferences().getIntSetting(
                                UserPreferences.NET_PROXY_PORT,
                                0));

        boolean authProxy = this.getUserPreferences().getBoolSetting(
                UserPreferences.NET_AUTH_PROXY,
                false);

        Proxy.Type proxyType = Proxy.Type.HTTP;

        if (authProxy) {
            proxyType = Proxy.Type.SOCKS;
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    String proxyUser =
                        Preferences.this.getUserPreferences().getSetting(
                                UserPreferences.NET_PROXY_USER, "");
                    String proxyPass =
                        Preferences.this.getUserPreferences().getSetting(
                                UserPreferences.NET_PROXY_PASS, "");
                    return new PasswordAuthentication(
                            proxyUser,
                            proxyPass.toCharArray());
                }
            });
        }
        return new Proxy(proxyType, socketAddress);
    }

    public void setColumnWidths(
            final int column,
            final int columnWidth) {
        this.columnWidths.put(Integer.toString(column), columnWidth);
        this.getUserPreferences().setSetting(
                "importlist.column_widths",
                this.columnWidths);
    }

    public int getColumnWidths(final int column) {
        StreamTable streamTable = this.getUserPreferences().getTableSetting(
                "importlist.column_widths",
                this.columnWidths);
        return streamTable.getInt(
                Integer.toString(column),
                this.config.getInt("column_width"));
    }

    public void setColumnNames(final Hashtable<String, String> hashtable) {
        StreamTable streamTable = null;
        if (hashtable != null) {
            streamTable = new StreamTable();
            streamTable.merge(hashtable);
        }
        this.getUserPreferences().setSetting(
                "importlist.column_order",
                streamTable);
    }

    public String getColumnName(final int column) {
        if (this.columnOrder == null) {
            this.columnOrder = this.getUserPreferences().getTableSetting(
                    "importlist.column_order",
                    this.columnOrderDefault);
        }
        return this.columnOrder.getStr(Integer.toString(column), null);
    }

    public void setSortKey(final RowSorter.SortKey sortKey) {
        StreamTable streamTable = new StreamTable();
        streamTable.put(sortKey.getColumn(), sortKey.getSortOrder().toString());
        this.getUserPreferences().setSetting(
                "importlist.sort_order",
                streamTable);
    }

    public RowSorter.SortKey getSortKey() {
        StreamTable streamTable = this.getUserPreferences().getTableSetting(
                "importlist.sort_order",
                null);
        if (streamTable == null || streamTable.keySet().isEmpty()) {
            streamTable = this.sortOrderDefault;
        }
        Object key        = streamTable.keys().nextElement();
        int column        = Integer.parseInt(key.toString());
        String sortOrder  = streamTable.getStr(key, null);
        return new RowSorter.SortKey(column, SortOrder.valueOf(sortOrder));
    }

    public int getColumnCount() {
        if (this.columnOrder == null) {
            this.columnOrder = this.getUserPreferences().getTableSetting(
                    "importlist.column_order",
                    this.columnOrderDefault);
        }
        return this.columnOrder.size();
    }

    public CustomDateFormat getDateFormatter() {
        return this.getUserPreferences().getShortDateFormatter();
    }

    public DateFormat getTimeFormatter() {
        return DateFormat.getTimeInstance();
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
     * @return The suffix of the application event that reloads the context in
     * which this extension is running.
     */
    public String getReloadContextSuffix() {
        return this.config.getString("reload_context_suffix");
    }

    /**
     * @return The prefix of the application event that imports a given file.
     */
    public String getTransactionFileImportUriPrefix() {
        return this.config.getString("transaction_file_import_uri_prefix");
    }

    /**
     * @return The prefix of the application event that imports a given CSV file
     * using the text file importer plugin.
     */
    public String getTextFileImportUriPrefix() {
        return this.config.getString("text_file_import_uri_prefix");
    }

    /**
     * @return The amount of milliseconds to wait between two runs of the file
     * scanner.
     */
    public int getMonitorIntervall() {
        return this.config.getInt("monitor_intervall");
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
     * @return Header of the "name" column.
     */
    public String getHeaderValueName() {
        final String headerValueName =
            this.localizable.getString("header_value_name");
        return this.getIndentationPrefix() + headerValueName;
    }

    /**
     * @return Header of the "modified" column.
     */
    public String getHeaderValueModified() {
        final String headerValueModified =
            this.localizable.getString("header_value_modified");
        return this.getIndentationPrefix() + headerValueModified;
    }

    /**
     * @return Header of the "import" column.
     */
    public String getHeaderValueImport() {
        final String headerValueImport =
            this.localizable.getString("header_value_import");
        return this.getIndentationPrefix() + headerValueImport;
    }

    /**
     * @return Header of the "delete" column.
     */
    public String getHeaderValueDelete() {
        final String headerValueDelete =
            this.localizable.getString("header_value_delete");
        return this.getIndentationPrefix() + headerValueDelete;
    }

    /**
     * @return Label of the "import" button.
     */
    public String getLabelImportOneButton() {
        return this.localizable.getString("label_import_one_button");
    }

    /**
     * @return Label of the "import all" button.
     */
    public String getLabelImportAllButton() {
        return this.localizable.getString("label_import_all_button");
    }

    /**
     * @return Label of the "delete" button.
     */
    public String getLabelDeleteOneButton() {
        return this.localizable.getString("label_delete_one_button");
    }

    /**
     * @return Label of the "delete all" button.
     */
    public String getLabelDeleteAllButton() {
        return this.localizable.getString("label_delete_all_button");
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
     * @param rowCount The number of rows to display inside the table.
     * @return Preferred width of the file table.
     */
    public int getPreferredTableWidth(final int rowCount) {
        return this.getMinimumTableWidth();
    }

    /**
     * @param rowCount The number of rows to display inside the table.
     * @return Preferred height of the file table.
     */
    public int getPreferredTableHeight(final int rowCount) {
        int fit = this.getHeaderRowHeight()
                + rowCount * this.getBodyRowHeight();
        int min = Math.min(this.getMaximumTableHeight(), fit);
        return Math.max(this.getMinimumTableHeight(), min);
    }

    /**
     * @return Maximum width of the file table.
     */
    public int getMaximumTableWidth() {
        return
        (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / (2 + 1);
    }

    /**
     * @return Maximum height of the file table.
     */
    public int getMaximumTableHeight() {
        return
        (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / (2 + 1);
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
     * @return Default foreground color.
     */
    public Color getForeground() {
        int foregroundValue = this.getUserPreferences().getIntSetting(
                UserPreferences.GUI_HOMEPG_FG,
                this.config.getInt("color_value_fg_def"));
        return new Color(foregroundValue);
    }

    /**
     * @return Default background color.
     */
    public Color getBackground() {
        int backgroundValue = this.getUserPreferences().getIntSetting(
                UserPreferences.GUI_HOMEPG_BG,
                this.config.getInt("color_value_bg_def"));
        return new Color(backgroundValue);
    }

    /**
     * @return Default alternative background color.
     */
    public Color getBackgroundAlt() {
        int backgroundAltValue = this.getUserPreferences().getIntSetting(
                "gui.home_alt_bg",
                this.config.getInt("color_value_bg_alt_def"));
        return new Color(backgroundAltValue);
    }

    public Font getHeaderFont() {
        return UIManager.getFont("OptionPane.font");
    }

    public Font getBodyFont() {
        return UIManager.getFont("Label.font");
    }

    public int getHeaderRowHeight() {
        return (int) (this.config.getDouble("factor_row_height")
                * this.getHeaderFont().getSize());
    }

    public int getBodyRowHeight() {
        return (int) (this.config.getDouble("factor_row_height")
                * this.getBodyFont().getSize());
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

    /**
     * @return The title of the dialog that is displayed to choose the base
     * directory.
     */
    public String getDirectoryChooserTitle() {
        return this.localizable.getString("directory_chooser_title");
    }

    /**
     * @param filename The name of the file that is to be deleted.
     * @return Confirmation message to be displayed before a file will be
     *  deleted.
     */
    public String getConfirmationMessageDeleteOneFile(final String filename) {
        String rawMessage = this.localizable.getString(
        "confirmation_message_delete_one_file");
        return rawMessage.replace("(0)", Helper.getMarkupFilename(filename));
    }

    /**
     * @param size The number of all files that are to be deleted.
     * @return Confirmation message to be displayed before all files will be
     *  deleted.
     */
    public String getConfirmationMessageDeleteAllFiles(final int size) {
        String rawMessage = this.localizable.getString(
        "confirmation_message_delete_all_files");
        return rawMessage.replace("(0)", String.valueOf(size));
    }

    /**
     * @param filename The name of the file that cannot be deleted.
     * @return Error message to be displayed if a file cannot be deleted.
     */
    public String getErrorMessageDeleteFile(final String filename) {
        String rawMessage = this.localizable.getString(
        "error_message_delete_file");
        return rawMessage.replace("(0)", Helper.getMarkupFilename(filename));
    }

    /**
     * @return Delete button in confirmation message.
     */
    public String getOptionDeleteFile() {
        return this.localizable.getString("option_delete_file");
    }

    /**
     * @return Cancel button in confirmation message.
     */
    public String getOptionCancel() {
        return this.localizable.getString("option_cancel");
    }

    /**
     * @param baseDirectory The base directory which does not contain any files
     * to display.
     * @return Message to display if the list of files is empty.
     */
    public String getEmptyMessage(final String baseDirectory) {
        String rawMessage = this.localizable.getString("empty_message");
        return rawMessage.replace("(0)", baseDirectory);
    }
}
