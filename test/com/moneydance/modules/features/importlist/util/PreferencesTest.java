/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
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

import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.RowSorter;
import javax.swing.SortOrder;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;

/**
 * @author Florian J. Breunig
 */
public final class PreferencesTest {

    private Preferences prefs;

    @Before
    public void setUp() {
        this.prefs = new Preferences();
        StubContextFactory factory = new StubContextFactory();
        this.prefs.setContext(factory.getContext());
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserPreferences() {
        this.prefs = new Preferences();
        this.prefs.addObserver(new PreferencesObserver());
        this.prefs.getLocale();

        this.prefs = new Preferences();
        this.prefs.getLocale();
    }

    private final class PreferencesObserver implements Observer {
        @Override
        public void update(final Observable observable,
                final Object updateAll) {
            StubContextFactory factory = new StubContextFactory();
            PreferencesTest.this.prefs.setContext(factory.getContext());
        }
    }

    @Test
    public void testReload() {
        this.prefs.reload();
    }

    @Test
    public void testSetAllWritablePreferencesToNull() {
        this.prefs.setAllWritablePreferencesToNull();
    }

    @Test
    public void testGetFullVersion() {
        Assert.assertNotNull("full version must not be null",
                this.prefs.getFullVersion());
    }

    @Test
    public void testGetMajorVersion() {
        Assert.assertTrue("major version must be greater than zero",
                this.prefs.getMajorVersion() > 0);
    }

    @Test
    public void testGetLocale() {
        Assert.assertNotNull("locale must not be null",
                this.prefs.getLocale());
    }

    @Test
    public void testGetBaseDirectory() {
        Assert.assertNotNull("base directory must not be null",
                this.prefs.getBaseDirectory());
    }

    @Test
    public void testSetBaseDirectory() {
        String userHome = System.getProperty("user.home");
        this.prefs.setBaseDirectory(userHome);
        Assert.assertEquals("base directory must be equal to set value",
                userHome, this.prefs.getBaseDirectory());
    }

    @Test
    public void testUseProxy() {
        this.prefs.useProxy();
    }

    @Test
    public void testGetProxyHost() {
        Assert.assertNotNull("proxy host must not be null",
                this.prefs.getProxyHost());
    }

    @Test
    public void testGetProxyPort() {
        this.prefs.getProxyPort();
    }

    @Test
    public void testNeedProxyAuthentication() {
        this.prefs.needProxyAuthentication();
    }

    @Test
    public void testGetProxyUsername() {
        Assert.assertNotNull("proxy username host must not be null",
                this.prefs.getProxyUsername());
    }

    @Test
    public void testGetProxyPassword() {
        Assert.assertNotNull("proxy password host must not be null",
                this.prefs.getProxyPassword());
    }

    @Test
    public void testSetColumnWidths() {
        int columnWidth = 1;
        this.prefs.setColumnWidths(0,  columnWidth);
        Assert.assertEquals("column width must be equal to set value",
                columnWidth,
                this.prefs.getColumnWidths(0));
    }

    @Test
    public void testGetColumnWidths() {
        Assert.assertTrue("column width must be greater than zero",
                this.prefs.getColumnWidths(0) > 0);
    }

    @Test
    public void testSetColumnNames() {
        this.prefs.setColumnNames(null);
        this.prefs.setColumnNames(new Hashtable<String, String>());
    }

    @Test
    public void testGetColumnName() {
        Assert.assertNotNull("column name must not be null",
                this.prefs.getColumnName(0));
        Assert.assertNotNull("column name must not be null",
                this.prefs.getColumnName(1));
    }

    @Test
    public void testSetSortKey() {
        RowSorter.SortKey sortKey =
                new RowSorter.SortKey(0, SortOrder.ASCENDING);
        this.prefs.setSortKey(sortKey);
        Assert.assertEquals("sortkey must be equal to set value",
                sortKey, this.prefs.getSortKey());
    }

    @Test
    public void testGetSortKey() {
        Assert.assertNotNull("sort key must not be null",
                this.prefs.getSortKey());
    }

    @Test
    public void testGetColumnCount() {
        Assert.assertTrue("column count must be greater than zero",
                this.prefs.getColumnCount() > 0);
        Assert.assertTrue("column count must be greater than zero",
                this.prefs.getColumnCount() > 0);
    }

    @Test
    public void testGetDateFormatter() {
        Assert.assertNotNull("date formatter must not be null",
                this.prefs.getDateFormatter());
    }

    @Test
    public void testGetTimeFormatter() {
        Assert.assertNotNull("time formatter must not be null",
                this.prefs.getTimeFormatter());
    }

    @Test
    public void testGetPreferredTableWidth() {
        Assert.assertTrue("preferred table width must be greater than zero",
                this.prefs.getPreferredTableWidth() > 0);
    }

    @Test
    public void testGetPreferredTableHeight() {
        Assert.assertTrue("preferred table height must be greater than zero",
                this.prefs.getPreferredTableHeight(0) > 0);
    }

    @Test
    public void testGetMaximumTableWidth() {
        Assert.assertTrue("maximum table width must be greater than zero",
                this.prefs.getMaximumTableWidth() > 0);
    }

    @Test
    public void testGetMaximumTableHeight() {
        Assert.assertTrue("maximum table height must be greater than zero",
                this.prefs.getMaximumTableHeight() > 0);
    }

    @Test
    public void testGetForeground() {
        Assert.assertNotNull("foreground color must not be null",
                this.prefs.getForeground());
    }

    @Test
    public void testGetBackground() {
        Assert.assertNotNull("background color must not be null",
                this.prefs.getBackground());
    }

    @Test
    public void testGetBackgroundAlt() {
        Assert.assertNotNull("alternative background color must not be null",
                this.prefs.getBackgroundAlt());
    }

    @Test
    public void testGetHeaderFont() {
        Assert.assertNotNull("header font must not be null",
                this.prefs.getHeaderFont());
    }

    @Test
    public void testGetBodyFont() {
        Assert.assertNotNull("body font must not be null",
                this.prefs.getBodyFont());
    }

    @Test
    public void testGetHeaderRowHeight() {
        Assert.assertTrue("header row height must be greater than zero",
                this.prefs.getHeaderRowHeight() > 0);
    }

    @Test
    public void testGetBodyRowHeight() {
        Assert.assertTrue("body row height must be greater than zero",
                this.prefs.getBodyRowHeight() > 0);
    }

}
