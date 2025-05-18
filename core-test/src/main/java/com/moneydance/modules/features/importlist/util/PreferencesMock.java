package com.moneydance.modules.features.importlist.util;

import com.moneydance.modules.features.importlist.datetime.DateFormatter;
import com.moneydance.modules.features.importlist.datetime.DateFormatterMock;
import com.moneydance.modules.features.importlist.datetime.TimeFormatterMock;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * @author Florian J. Breunig
 */
public final class PreferencesMock implements Preferences {

    private boolean firstRun;
    private final Map<Integer, String> columnNames;
    private RowSorter.SortKey sortKey;
    private final DateFormatter dateFormatter;
    private final DateFormatter timeFormatter;

    public PreferencesMock() {
        this.columnNames = new HashMap<>();
        this.columnNames.put(0, "Name");
        this.columnNames.put(1, "Date Modified");
        this.columnNames.put(2, "Import");
        this.columnNames.put(3, "Delete");
        this.sortKey = new RowSorter.SortKey(0, SortOrder.ASCENDING);
        this.dateFormatter = new DateFormatterMock();
        this.timeFormatter = new TimeFormatterMock();
    }

    @Override
    public void setAllWritablePreferencesToNull() {
        // empty
    }

    @Override
    public void setFirstRun(final boolean argFirstRun) {
        this.firstRun = argFirstRun;
    }

    @Override
    public boolean isFirstRun() {
        return this.firstRun;
    }

    @Override
    public Locale getLocale() {
        return Locale.US;
    }

    @Override
    public Optional<File> getBaseDirectory() {
        try {
            final File file = File.createTempFile("prefix", null);
            file.deleteOnExit();
            return Optional.of(file.getParentFile());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void setBaseDirectory(@Nullable final File baseDirectory) {
        // empty
    }

    @Override
    public void setColumnWidths(
            final int column,
            final int columnWidth) {
        // empty
    }

    @Override
    public int getColumnWidths(final int column) {
        return 0;
    }

    @Override
    public void setColumnNames(final Map<String, String> map) {
        // empty
    }

    @Override
    public String getColumnName(final int column) {
        return this.columnNames.getOrDefault(column, null);
    }

    @Override
    public void setSortKey(final RowSorter.SortKey argSortKey) {
        this.sortKey = argSortKey;
    }

    @Override
    public RowSorter.SortKey getSortKey() {
        return this.sortKey;
    }

    @Override
    public int getColumnCount() {
        return 4;
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
        return new EmptyBorder(0, 0, 0, 0);
    }

    @Override
    public int getPreferredTableWidth() {
        return 100;
    }

    @Override
    public int getPreferredTableHeight(final int rowCount) {
        return 0;
    }

    @Override
    public int getMaximumTableWidth() {
        return 0;
    }

    @Override
    public int getMaximumTableHeight() {
        return 0;
    }

    @Override
    public Color getForeground() {
        return Color.BLACK;
    }

    @Override
    public Color getHeaderForeground() {
        return Color.BLACK;
    }

    @Override
    public Color getBackground() {
        return Color.BLACK;
    }

    @Override
    public Color getBackgroundAlt() {
        return Color.BLACK;
    }

    @Override
    public Font getHeaderFont() {
        return UIManager.getFont("Label.font");
    }

    @Override
    public Font getBodyFont() {
        return UIManager.getFont("Label.font");
    }

    @Override
    public int getHeaderRowHeight() {
        return 20;
    }

    @Override
    public int getBodyRowHeight() {
        return 20;
    }
}
