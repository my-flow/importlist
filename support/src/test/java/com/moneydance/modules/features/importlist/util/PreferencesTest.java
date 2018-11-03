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

import java.util.Hashtable;

import javax.swing.RowSorter;
import javax.swing.SortOrder;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class PreferencesTest {

    private Preferences prefs;

    @Before
    public void setUp() {
        this.prefs = Helper.INSTANCE.getPreferences();
    }

    @Test
    public void testSetAllWritablePreferencesToNull() {
        this.prefs.setAllWritablePreferencesToNull();
    }

    @Test
    public void testSetFirstRun() {
        this.prefs.setFirstRun(true);
        assertThat(this.prefs.isFirstRun(), is(true));

        this.prefs.setFirstRun(false);
        assertThat(this.prefs.isFirstRun(), is(false));
    }

    @Test
    public void testIsFirstRun() {
        this.prefs.isFirstRun();
    }

    @Test
    public void testGetLocale() {
        assertThat(this.prefs.getLocale(), notNullValue());
    }

    @Test
    public void testGetBaseDirectory() {
        assertThat(this.prefs.getBaseDirectory(), notNullValue());
    }

    @Test
    public void testSetBaseDirectory() {
        this.prefs.setBaseDirectory(null);
    }

    @Test
    public void testSetColumnWidths() {
        this.prefs.setColumnWidths(0, 0);
    }

    @Test
    public void testGetColumnWidths() {
        this.prefs.setColumnWidths(0, 0);
        assertThat(this.prefs.getColumnWidths(0), is(0));
    }

    @Test
    public void testSetColumnNames() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        this.prefs.setColumnNames(hashtable);
    }

    @Test
    public void testGetColumnName() {
        assertThat(this.prefs.getColumnName(20), nullValue());
    }

    @Test
    public void testSetSortKey() {
        RowSorter.SortKey sortKey = new RowSorter.SortKey(0, SortOrder.UNSORTED);
        this.prefs.setSortKey(sortKey);
        assertEquals(sortKey, this.prefs.getSortKey());
    }

    @Test
    public void testGetSortKey() {
        assertThat(this.prefs.getSortKey(), notNullValue());
    }

    @Test
    public void testGetColumnCount() {
        assertThat(this.prefs.getColumnCount(), greaterThan(0));
    }

    @Test
    public void testGetDateFormatter() {
        assertThat(this.prefs.getDateFormatter(), is(notNullValue()));
    }

    @Test
    public void testGetTimeFormatter() {
        assertThat(Preferences.getTimeFormatter(), is(notNullValue()));
    }

    @Test
    public void testGetPreferredTableWidth() {
        assertThat(this.prefs.getPreferredTableWidth(), greaterThan(0));
    }

    @Test
    public void testGetPreferredTableHeight() {
        assertThat(this.prefs.getPreferredTableHeight(0), greaterThan(0));
    }

    @Test
    public void testGetMaximumTableWidth() {
        assertThat(Preferences.getMaximumTableWidth(), greaterThan(0));
    }

    @Test
    public void testGetMaximumTableHeight() {
        assertThat(Preferences.getMaximumTableHeight(), greaterThan(0));
    }

    @Test
    public void testGetHeaderFont() {
        assertThat(Preferences.getHeaderFont(), notNullValue());
    }

    @Test
    public void testGetHeaderForeground() {
        assertThat(Preferences.getHeaderForeground(), notNullValue());
    }

    @Test
    public void testGetHeaderRowHeight() {
        assertThat(this.prefs.getHeaderRowHeight(), notNullValue());
    }

    @Test
    public void testGetBodyRowHeight() {
        assertThat(this.prefs.getBodyRowHeight(), greaterThan(0));
    }
}
