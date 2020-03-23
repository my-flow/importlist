// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2020 Florian J. Breunig
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

import com.moneydance.modules.features.importlist.datetime.DateFormatter;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java8.util.Optional;

/**
 * @author Florian J. Breunig
 */
public final class PreferencesMock implements Preferences {

    @Override
    public void setAllWritablePreferencesToNull() {
        // empty
    }

    @Override
    public void setFirstRun(final boolean firstRun) {
        // empty
    }

    @Override
    public boolean isFirstRun() {
        return false;
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
            throw new IllegalStateException(e.getMessage(), e);
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
        return "column name";
    }

    @Override
    public void setSortKey(final RowSorter.SortKey sortKey) {
        // empty
    }

    @Override
    public RowSorter.SortKey getSortKey() {
        return null;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public DateFormatter getDateFormatter() {
        return null;
    }

    @Override
    public DateFormatter getTimeFormatter() {
        return null;
    }

    @Override
    public Border getHomePageBorder() {
        return new EmptyBorder(0, 0, 0, 0);
    }

    @Override
    public int getPreferredTableWidth() {
        return 0;
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
        return null;
    }

    @Override
    public int getHeaderRowHeight() {
        return 0;
    }

    @Override
    public int getBodyRowHeight() {
        return 0;
    }
}
