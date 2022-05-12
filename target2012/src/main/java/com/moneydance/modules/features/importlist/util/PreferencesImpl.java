package com.moneydance.modules.features.importlist.util;

import com.moneydance.apps.md.controller.Main;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.modules.features.importlist.datetime.DateFormatter;
import com.moneydance.modules.features.importlist.datetime.DateFormatterImpl;
import com.moneydance.modules.features.importlist.datetime.TimeFormatterImpl;
import com.moneydance.util.StreamTable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.text.DateFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.border.Border;

import java8.util.Optional;

/**
 * This preferences class contains the values the user can control in the
 * application. It serves as a facade abstracting Moneydance's
 * <code>UserPreferences</code> (received from the
 * <code>FeatureModuleContext</code>).
 *
 * @author Florian J. Breunig
 */
public final class PreferencesImpl implements Preferences {

    private final Settings settings;
    private final UserPreferences userPreferences;
    private final StreamTable columnOrderDefault;
    private final StreamTable sortOrderDefault;
    private final StreamTable columnWidths;
    private final DateFormatter dateFormatter;
    private final DateFormatter timeFormatter;
    @Nullable private StreamTable columnOrder;


    @Inject
    public PreferencesImpl(final Settings argSettings, final Main main) {
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
        this.dateFormatter = new DateFormatterImpl(this.userPreferences.getShortDateFormatter());
        this.timeFormatter = new TimeFormatterImpl(DateFormat.getTimeInstance());
    }

    @Override
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

    @Override
    public void setFirstRun(final boolean firstRun) {
        this.userPreferences.setSetting(
                "importlist.first_run",
                firstRun);
    }

    @Override
    public boolean isFirstRun() {
        return this.userPreferences.getBoolSetting(
                "importlist.first_run",
                true);
    }

    @Override
    public Locale getLocale() {
        return this.userPreferences.getLocale();
    }

    @Override
    public Optional<File> getBaseDirectory() {
        final String value = this.userPreferences.getSetting("importlist.import_dir");
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(new File(value));
    }

    @Override
    public void setBaseDirectory(@Nullable final File baseDirectory) {
        String value = null;
        if (baseDirectory != null) {
            value = baseDirectory.getAbsolutePath();
        }
        this.userPreferences.setSetting("importlist.import_dir", value);
    }

    @Override
    public void setColumnWidths(
            final int column,
            final int columnWidth) {
        this.columnWidths.put(Integer.toString(column), columnWidth);
        this.userPreferences.setSetting(
                "importlist.column_widths",
                this.columnWidths);
    }

    @Override
    public int getColumnWidths(final int column) {
        StreamTable streamTable = this.userPreferences.getTableSetting(
                "importlist.column_widths",
                this.columnWidths);
        return streamTable.getInt(
                Integer.toString(column),
                this.settings.getColumnWidth());
    }

    @Override
    public void setColumnNames(final Map<String, String> map) {
        StreamTable streamTable = null;
        if (map != null) {
            streamTable = new StreamTable();
            streamTable.merge(new Hashtable<>(map));
        }
        this.userPreferences.setSetting(
                "importlist.column_order",
                streamTable);
    }

    @Override
    public String getColumnName(final int column) {
        if (this.columnOrder == null) {
            this.columnOrder = this.userPreferences.getTableSetting(
                    "importlist.column_order",
                    this.columnOrderDefault);
        }
        return this.columnOrder.getStr(Integer.toString(column), null);
    }

    @Override
    public void setSortKey(final RowSorter.SortKey sortKey) {
        StreamTable streamTable = new StreamTable();
        streamTable.put(sortKey.getColumn(), sortKey.getSortOrder().toString());
        this.userPreferences.setSetting(
                "importlist.sort_order",
                streamTable);
    }

    @Override
    public RowSorter.SortKey getSortKey() {
        StreamTable streamTable = this.userPreferences.getTableSetting(
                "importlist.sort_order", this.sortOrderDefault);
        Object key = streamTable.keys().nextElement();
        int column = Integer.parseInt(key.toString());
        @SuppressWarnings("nullness")
        String sortOrder = streamTable.getStr(key, null);
        return new RowSorter.SortKey(column, SortOrder.valueOf(sortOrder));
    }

    @Override
    public int getColumnCount() {
        if (this.columnOrder == null) {
            this.columnOrder = this.userPreferences.getTableSetting(
                    "importlist.column_order",
                    this.columnOrderDefault);
        }
        return this.columnOrder.size();
    }

    @Override
    public DateFormatter getDateFormatter() {
        return this.dateFormatter;
    }

    @Override
    public DateFormatter getTimeFormatter() {
        return this.timeFormatter;
    }

    @Override
    public Border getHomePageBorder() {
        return MoneydanceLAF.homePageBorder;
    }

    @Override
    public int getPreferredTableWidth() {
        return this.settings.getMinimumTableWidth();
    }

    @Override
    public int getPreferredTableHeight(final int rowCount) {
        int fit = this.getHeaderRowHeight()
                + rowCount * this.getBodyRowHeight();
        int min = Math.min(this.getMaximumTableHeight(), fit);
        return Math.max(this.settings.getMinimumTableHeight(), min);
    }

    @Override
    public int getMaximumTableWidth() {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                / (2 + 1);
    }

    @Override
    public int getMaximumTableHeight() {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                / (2 + 1);
    }

    @Override
    public Color getForeground() {
        int foregroundValue = this.userPreferences.getIntSetting(
                UserPreferences.GUI_HOMEPG_FG,
                this.settings.getColorValueFgDef());
        return new Color(foregroundValue);
    }

    @Override
    public Color getHeaderForeground() {
        return UIManager.getColor("Label.disabledForeground");
    }

    @Override
    public Color getBackground() {
        int backgroundValue = this.userPreferences.getIntSetting(
                UserPreferences.GUI_HOMEPG_BG,
                this.settings.getColorValueBgDef());
        return new Color(backgroundValue);
    }

    @Override
    public Color getBackgroundAlt() {
        int backgroundAltValue = this.userPreferences.getIntSetting(
                UserPreferences.GUI_HOMEPGALT_BG,
                this.settings.getColorValueBgAltDef());
        return new Color(backgroundAltValue);
    }

    @Override
    public Font getHeaderFont() {
        final Font baseFont = UIManager.getFont("Label.font");
        return baseFont.deriveFont(Font.BOLD, baseFont.getSize2D() + 2.0F);
    }

    @Override
    public Font getBodyFont() {
        return UIManager.getFont("Label.font");
    }

    @Override
    public int getHeaderRowHeight() {
        return Math.round(
                (float) this.settings.getFactorRowHeightHeader()
                * this.getBodyFont().getSize2D());
    }

    @Override
    public int getBodyRowHeight() {
        return Math.round(
                (float) this.settings.getSummandRowHeightBody()
                + this.getBodyFont().getSize2D());
    }
}
