// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2013 Florian J. Breunig
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.RowSorter;
import javax.swing.SortOrder;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;

/**
 * @author Florian J. Breunig
 */
public final class PreferencesTest {

    private Preferences prefs;
    private StubContextFactory factory;

    @Before
    public void setUp() {
        this.prefs = new Preferences();
        this.factory = new StubContextFactory();
        this.prefs.setContext(this.factory.getContext());
    }

    @Test
    public void testGetUserPreferences() {
        this.prefs = new Preferences();
        Helper.INSTANCE.addObserver(new Observer() {
            @Override
            public void update(final Observable observable,
                    final Object updateAll) {
                PreferencesTest.this.prefs.setContext(PreferencesTest.this.factory.getContext());
            }
        });
        this.prefs.getLocale();

        this.prefs = new Preferences();
        this.prefs.getLocale();
    }

    @Test
    public void testSetAllWritablePreferencesToNull() {
        this.prefs.setAllWritablePreferencesToNull();
    }

    @Test
    public void testSetFirstRun() {
        final boolean firstRun = false;
        this.prefs.setFirstRun(firstRun);
        assertThat(this.prefs.isFirstRun(), is(firstRun));
    }

    @Test
    public void testIsFirstRun() {
        this.prefs.isFirstRun();
    }

    @Test
    public void testGetFullVersion() {
        assertThat(this.prefs.getFullVersion(), notNullValue());
    }

    @Test
    public void testGetMajorVersion() {
        assertThat(this.prefs.getMajorVersion() > 0, is(true));
    }

    @Test
    public void testGetLocale() {
        assertThat(this.prefs.getLocale(), notNullValue());
    }

    @Test
    public void testGetBaseDirectory() {
        this.prefs.getBaseDirectory();
    }

    @Test
    public void testSetBaseDirectory() {
        String userHome = System.getProperty("user.home");
        this.prefs.setBaseDirectory(userHome);
        assertThat(this.prefs.getBaseDirectory(), is(userHome));
    }

    @Test
    public void testHasProxy() {
        this.prefs.hasProxy();
    }

    @Test
    public void testGetProxyHost() {
        assertThat(this.prefs.getProxyHost(), notNullValue());
    }

    @Test
    public void testGetProxyPort() {
        this.prefs.getProxyPort();
    }

    @Test
    public void testHasProxyAuthentication() {
        this.prefs.hasProxyAuthentication();
    }

    @Test
    public void testGetProxyUsername() {
        assertThat(this.prefs.getProxyUsername(), notNullValue());
    }

    @Test
    public void testGetProxyPassword() {
        assertThat(this.prefs.getProxyPassword(), notNullValue());
    }

    @Test
    public void testSetColumnWidths() {
        int columnWidth = 1;
        this.prefs.setColumnWidths(0,  columnWidth);
        assertThat(this.prefs.getColumnWidths(0), is(columnWidth));
    }

    @Test
    public void testGetColumnWidths() {
        assertThat(this.prefs.getColumnWidths(0) > 0, is(true));
    }

    @Test
    public void testSetColumnNames() {
        this.prefs.setColumnNames(null);
        this.prefs.setColumnNames(new Hashtable<String, String>(0));
    }

    @Test
    public void testGetColumnName() {
        assertThat(this.prefs.getColumnName(0), notNullValue());
        assertThat(this.prefs.getColumnName(1), notNullValue());
    }

    @Test
    public void testSetSortKey() {
        RowSorter.SortKey sortKey =
                new RowSorter.SortKey(0, SortOrder.ASCENDING);
        this.prefs.setSortKey(sortKey);
        assertThat(this.prefs.getSortKey(), is(sortKey));
    }

    @Test
    public void testGetSortKey() {
        assertThat(this.prefs.getSortKey(), notNullValue());
    }

    @Test
    public void testGetColumnCount() {
        assertThat(this.prefs.getColumnCount() > 0, is(true));
        assertThat(this.prefs.getColumnCount() > 0, is(true));
    }

    @Test
    public void testGetDateFormatter() {
        assertThat(this.prefs.getDateFormatter(), notNullValue());
    }

    @Test
    public void testGetTimeFormatter() {
        assertThat(this.prefs.getTimeFormatter(), notNullValue());
    }

    @Test
    public void testGetPreferredTableWidth() {
        assertThat(this.prefs.getPreferredTableWidth() > 0, is(true));
    }

    @Test
    public void testGetPreferredTableHeight() {
        assertThat(this.prefs.getPreferredTableHeight(0) > 0, is(true));
    }

    @Test
    public void testGetMaximumTableWidth() {
        assertThat(this.prefs.getMaximumTableWidth() > 0, is(true));
    }

    @Test
    public void testGetMaximumTableHeight() {
        assertThat(this.prefs.getMaximumTableHeight() > 0, is(true));
    }

    @Test
    public void testGetForeground() {
        assertThat(this.prefs.getForeground(), notNullValue());
    }

    @Test
    public void testGetBackground() {
        assertThat(this.prefs.getBackground(), notNullValue());
    }

    @Test
    public void testGetBackgroundAlt() {
        assertThat(this.prefs.getBackgroundAlt(), notNullValue());
    }

    @Test
    public void testGetHeaderFont() {
        assertThat(this.prefs.getHeaderFont(), notNullValue());
    }

    @Test
    public void testGetBodyFont() {
        assertThat(this.prefs.getBodyFont(), notNullValue());
    }

    @Test
    public void testGetHeaderRowHeight() {
        assertThat(this.prefs.getHeaderRowHeight() > 0, is(true));
    }

    @Test
    public void testGetBodyRowHeight() {
        assertThat(this.prefs.getBodyRowHeight() > 0, is(true));
    }

}
