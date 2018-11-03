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

import com.infinitekind.util.CustomDateFormat;
import com.infinitekind.util.StreamTable;
import com.moneydance.apps.md.controller.Main;
import com.moneydance.apps.md.controller.UserPreferences;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.text.DateFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;

/**
 * This preferences class contains the values the user can control in the
 * application. It serves as a facade abstracting Moneydance's
 * <code>UserPreferences</code> (received from the
 * <code>FeatureModuleContext</code>).
 *
 * @author Florian J. Breunig
 */
public final class Preferences {

    private final Settings settings;
    private final UserPreferences userPreferences;
    private final StreamTable columnOrderDefault;
    private final StreamTable sortOrderDefault;
    private final StreamTable columnWidths;
    @Nullable private StreamTable columnOrder;

    Preferences(final Main main, final Settings argSettings) {
        this.settings = argSettings;
        this.userPreferences = main.getPreferences();
        this.columnWidths = new StreamTable();
        this.columnOrderDefault = new StreamTable();
        this.columnOrderDefault.put("0", this.settings.getDescName());
        this.columnOrderDefault.put("1", this.settings.getDescModified());
        this.columnOrderDefault.put("2", this.settings.getDescImport());
        this.columnOrderDefault.put("3", this.settings.getDescDelete());
        this.sortOrderDefault = new StreamTable();
        this.sortOrderDefault.put("0", SortOrder.ASCENDING.toString());
    }

    public void setAllWritablePreferencesToNull() {
        this.userPreferences.setSetting(
                "importlist.first_run",
                (String) null);
        this.setBaseDirectory(null);
        this.userPreferences.setSetting(
                "importlist.column_widths",
                (StreamTable) null);
        this.setColumnNames(
                null);
        this.userPreferences.setSetting(
                "importlist.sort_order",
                (StreamTable) null);
    }

    public void setFirstRun(final boolean firstRun) {
        this.userPreferences.setSetting(
                "importlist.first_run",
                firstRun);
    }

    public boolean isFirstRun() {
        return this.userPreferences.getBoolSetting(
                "importlist.first_run",
                true);
    }

    public Locale getLocale() {
        return this.userPreferences.getLocale();
    }

    public Optional<File> getBaseDirectory() {
        final String value = this.userPreferences.getSetting("importlist.import_dir");
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(new File(value));
    }

    public void setBaseDirectory(@Nullable final File baseDirectory) {
        String value = null;
        if (baseDirectory != null) {
            value = baseDirectory.getAbsolutePath();
        }
        this.userPreferences.setSetting("importlist.import_dir", value);
    }

    public void setColumnWidths(
            final int column,
            final int columnWidth) {
        this.columnWidths.put(Integer.toString(column), columnWidth);
        this.userPreferences.setSetting(
                "importlist.column_widths",
                this.columnWidths);
    }

    public int getColumnWidths(final int column) {
        StreamTable streamTable = this.userPreferences.getTableSetting(
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
        this.userPreferences.setSetting(
                "importlist.column_order",
                streamTable);
    }

    public String getColumnName(final int column) {
        if (this.columnOrder == null) {
            this.columnOrder = this.userPreferences.getTableSetting(
                    "importlist.column_order",
                    this.columnOrderDefault);
        }
        return this.columnOrder.getStr(Integer.toString(column), null);
    }

    public void setSortKey(final RowSorter.SortKey sortKey) {
        StreamTable streamTable = new StreamTable();
        streamTable.put(sortKey.getColumn(), sortKey.getSortOrder().toString());
        this.userPreferences.setSetting(
                "importlist.sort_order",
                streamTable);
    }

    public RowSorter.SortKey getSortKey() {
        StreamTable streamTable = this.userPreferences.getTableSetting(
                "importlist.sort_order", this.sortOrderDefault);
        Object key = streamTable.keys().nextElement();
        int column = Integer.parseInt(key.toString());
        @SuppressWarnings("nullness")
        String sortOrder = streamTable.getStr(key, null);
        return new RowSorter.SortKey(column, SortOrder.valueOf(sortOrder));
    }

    public int getColumnCount() {
        if (this.columnOrder == null) {
            this.columnOrder = this.userPreferences.getTableSetting(
                    "importlist.column_order",
                    this.columnOrderDefault);
        }
        return this.columnOrder.size();
    }

    public CustomDateFormat getDateFormatter() {
        return this.userPreferences.getShortDateFormatter();
    }

    public static DateFormat getTimeFormatter() {
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
        int min = Math.min(Preferences.getMaximumTableHeight(), fit);
        return Math.max(this.settings.getMinimumTableHeight(), min);
    }

    /**
     * @return Maximum width of the file table.
     */
    public static int getMaximumTableWidth() {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                / (2 + 1);
    }

    /**
     * @return Maximum height of the file table.
     */
    public static int getMaximumTableHeight() {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                / (2 + 1);
    }

    public static Font getHeaderFont() {
        final Font baseFont = UIManager.getFont("Label.font");
        return baseFont.deriveFont(Font.PLAIN, baseFont.getSize2D() + 2.0F);
    }

    public static Color getHeaderForeground() {
        return UIManager.getColor("Label.disabledForeground");
    }

    public int getHeaderRowHeight() {
        return Math.round(
                (float) this.settings.getFactorRowHeightHeader()
                * Preferences.getBodyFont().getSize2D());
    }

    public int getBodyRowHeight() {
        return Math.round(
                (float) this.settings.getSummandRowHeightBody()
                + Preferences.getBodyFont().getSize2D());
    }

    private static Font getBodyFont() {
        return UIManager.getFont("Label.font");
    }
}
