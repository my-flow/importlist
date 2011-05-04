package com.moneydance.modules.features.importlist;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.DateFormat;
import java.util.Hashtable;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;

import org.apache.commons.lang.Validate;

import com.moneydance.apps.md.controller.Common;
import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.util.CustomDateFormat;
import com.moneydance.util.StreamTable;

/**
 * This class contains all preferences that the user might change at runtime.
 * It serves as a facade abstracting Moneydance's
 * <code>UserPreferences</code> (received from the
 * <code>FeatureModuleContext</code>) and the hard-wired
 * default values used in stand-alone mode.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
public final class Preferences {

    private static Preferences  instance;
    private final FeatureModule featureModule;
    private final StreamTable   columnOrderDefault;
    private final StreamTable   sortOrderDefault;
    private final StreamTable   columnWidths;
    private StreamTable         columnOrder;
    private UserPreferences     userPreferences;

    /**
     * The constructor must be called exactly once before using the only
     * instance of this class.
     * @param argFeatureModule The feature module to receive the context from.
     */
    Preferences(final FeatureModule argFeatureModule) {
        Validate.isTrue(instance == null, "You can instantiate Preferences "
                + "only once");
        Validate.notNull(argFeatureModule, "argFeatureModule can't be null");
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
        // Reload context from feature module
        instance.featureModule.invoke(Constants.RELOAD_CONTEXT_SUFFIX);
        return instance;
    }

    public void setContext(final FeatureModuleContext context) {
        if (context != null) {
            this.userPreferences = ((com.moneydance.apps.md.controller.Main)
                    context).getPreferences();
        }

        if (this.userPreferences == null) {
            File file = Common.getPreferencesFile();
            this.userPreferences = new UserPreferences(file);
        }
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

    public Color getForegroundColor() {
        int foregroundColorValue = this.userPreferences.getIntSetting(
                Constants.PREF_FG_COLOR,
                Constants.COLOR_VALUE_FG_DEF);
        return new Color(foregroundColorValue);
    }

    public Color getBackgroundColor() {
        int backgroundColorValue = this.userPreferences.getIntSetting(
                Constants.PREF_BG_COLOR,
                Constants.COLOR_VALUE_BG_DEF);
        return new Color(backgroundColorValue);
    }

    public Color getBackgroundColorAlt() {
        int backgroundColorAltValue = this.userPreferences.getIntSetting(
                Constants.PREF_BG_COLOR_ALT,
                Constants.COLOR_VALUE_BG_ALT_DEF);
        return new Color(backgroundColorAltValue);
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
}
