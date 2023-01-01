package com.moneydance.modules.features.importlist.util;

import com.moneydance.modules.features.importlist.datetime.DateFormatter;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.swing.RowSorter;
import javax.swing.border.Border;

/**
 * This preferences class contains the values the user can control in the
 * application. It serves as a facade abstracting Moneydance's
 * <code>UserPreferences</code> (received from the
 * <code>FeatureModuleContext</code>).
 *
 * @author Florian J. Breunig
 */
public interface Preferences {

    void setAllWritablePreferencesToNull();

    void setFirstRun(boolean firstRun);

    boolean isFirstRun();

    Locale getLocale();

    Optional<File> getBaseDirectory();

    void setBaseDirectory(@Nullable File baseDirectory);

    void setColumnWidths(int column, int columnWidth);

    int getColumnWidths(int column);

    void setColumnNames(Map<String, String> map);

    String getColumnName(int column);

    void setSortKey(RowSorter.SortKey sortKey);

    RowSorter.SortKey getSortKey();

    int getColumnCount();

    DateFormatter getDateFormatter();

    DateFormatter getTimeFormatter();

    Border getHomePageBorder();

    /**
     * @return Preferred width of the file table.
     */
    int getPreferredTableWidth();

    /**
     * @param rowCount The number of rows to display inside the table.
     * @return Preferred height of the file table.
     */
    int getPreferredTableHeight(int rowCount);

    /**
     * @return Maximum width of the file table.
     */
    int getMaximumTableWidth();

    /**
     * @return Maximum height of the file table.
     */
    int getMaximumTableHeight();

    /**
     * @return Default foreground color.
     */
    Color getForeground();

    Color getHeaderForeground();

    /**
     * @return Default background color.
     */
    Color getBackground();

    /**
     * @return Default alternative background color.
     */
    Color getBackgroundAlt();

    Font getHeaderFont();

    Font getBodyFont();

    int getHeaderRowHeight();

    int getBodyRowHeight();
}
