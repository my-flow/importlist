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
import java.text.DateFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Observable;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;

import org.apache.commons.lang.Validate;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.util.CustomDateFormat;
import com.moneydance.util.StreamTable;

/**
 * This preferences class contains the values the user can control in the
 * application. It serves as a facade abstracting Moneydance's
 * <code>UserPreferences</code> (received from the
 * <code>FeatureModuleContext</code>).
 *
 * @author Florian J. Breunig
 */
public final class Preferences extends Observable {

    private         UserPreferences userPreferences;
    private final   Settings        settings;
    private final   StreamTable     columnOrderDefault;
    private final   StreamTable     sortOrderDefault;
    private final   StreamTable     columnWidths;
    private         StreamTable     columnOrder;

    /**
     * The constructor must be called exactly once before using the only
     * instance of this class.
     */
    Preferences() {
        this.settings           = new Settings();
        this.columnWidths       = new StreamTable();
        this.columnOrderDefault = new StreamTable();
        this.columnOrderDefault.put("0", this.settings.getDescName());
        this.columnOrderDefault.put("1", this.settings.getDescModified());
        this.columnOrderDefault.put("2", this.settings.getDescImport());
        this.columnOrderDefault.put("3", this.settings.getDescDelete());
        this.sortOrderDefault   = new StreamTable();
        this.sortOrderDefault.put("0", SortOrder.ASCENDING.toString());
    }

    public void reload() {
        this.setChanged();
        this.notifyObservers(Boolean.TRUE);
    }

    public void setContext(final FeatureModuleContext context) {
        this.userPreferences = ((com.moneydance.apps.md.controller.Main)
                context).getPreferences();
    }

    private UserPreferences getUserPreferences() {
        if (this.userPreferences == null) {
            this.setChanged();
            this.notifyObservers(Boolean.FALSE);
            Validate.notNull(this.userPreferences,
                    "user preferences not initialized");
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
                "current_version", "0");
        return fullString;
    }

    public int getMajorVersion() {
        final String fullString = this.getFullVersion();
        final int endIndex = Math.min(
                this.settings.getLengthOfVersionDigits(),
                fullString.length());
        String substring = fullString.substring(0, endIndex);
        return Integer.parseInt(substring);
    }

    public Locale getLocale() {
        return this.getUserPreferences().getLocale();
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

    public boolean useProxy() {
        return this.getUserPreferences().getBoolSetting(
                UserPreferences.NET_USE_PROXY,
                false);
    }

    public String getProxyHost() {
        return this.getUserPreferences().getSetting(
                UserPreferences.NET_PROXY_HOST);
    }

    public int getProxyPort() {
        return this.getUserPreferences().getIntSetting(
                UserPreferences.NET_PROXY_PORT,
                0);
    }

    public boolean needProxyAuthentication() {
        return this.getUserPreferences().getBoolSetting(
                UserPreferences.NET_AUTH_PROXY,
                false);
    }

    public String getProxyUsername() {
        return this.getUserPreferences().getSetting(
                UserPreferences.NET_PROXY_USER);
    }

    public String getProxyPassword() {
        return this.getUserPreferences().getSetting(
                UserPreferences.NET_PROXY_PASS);
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
                this.settings.getColumnWidth());
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
                "importlist.sort_order", this.sortOrderDefault);
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
     * @return Preferred width of the file table.
     */
    public int getPreferredTableWidth() {
        return this.settings.getMinimumTableWidth();
    }

    /**
     * @param rowCount The number of rows to display inside the table.
     * @return Preferred height of the file table.
     */
    public int getPreferredTableHeight(final int rowCount) {
        int fit = this.getHeaderRowHeight()
                + rowCount * this.getBodyRowHeight();
        int min = Math.min(this.getMaximumTableHeight(), fit);
        return Math.max(this.settings.getMinimumTableHeight(), min);
    }

    /**
     * @return Maximum width of the file table.
     */
    public int getMaximumTableWidth() {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                / (2 + 1);
    }

    /**
     * @return Maximum height of the file table.
     */
    public int getMaximumTableHeight() {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                / (2 + 1);
    }

    /**
     * @return Default foreground color.
     */
    public Color getForeground() {
        int foregroundValue = this.getUserPreferences().getIntSetting(
                UserPreferences.GUI_HOMEPG_FG,
                this.settings.getColorValueFgDef());
        return new Color(foregroundValue);
    }

    /**
     * @return Default background color.
     */
    public Color getBackground() {
        int backgroundValue = this.getUserPreferences().getIntSetting(
                UserPreferences.GUI_HOMEPG_BG,
                this.settings.getColorValueBgDef());
        return new Color(backgroundValue);
    }

    /**
     * @return Default alternative background color.
     */
    public Color getBackgroundAlt() {
        int backgroundAltValue = this.getUserPreferences().getIntSetting(
                "gui.home_alt_bg",
                this.settings.getColorValueBgAltDef());
        return new Color(backgroundAltValue);
    }

    public Font getHeaderFont() {
        final Font baseFont = UIManager.getFont("Label.font");
        return baseFont.deriveFont(Font.BOLD, baseFont.getSize2D() + 2.0F);
    }

    public Font getBodyFont() {
        return UIManager.getFont("Label.font");
    }

    public int getHeaderRowHeight() {
        return Math.round(
                (float) this.settings.getFactorRowHeightHeader()
                * this.getBodyFont().getSize2D());
    }

    public int getBodyRowHeight() {
        return Math.round(
                (float) this.settings.getSummandRowHeightBody()
                + this.getBodyFont().getSize2D());
    }
}
