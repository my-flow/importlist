package com.moneydance.modules.features.importlist.util;

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        final TargetTestComponent testComponent = DaggerTargetTestComponent.builder().build();
        this.prefs = testComponent.preferences();
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
        Map<String, String> map = new ConcurrentHashMap<>();
        this.prefs.setColumnNames(map);
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
        assertThat(this.prefs.getDateFormatter(), notNullValue());
    }

    @Test
    public void testGetTimeFormatter() {
        assertThat(this.prefs.getTimeFormatter(), notNullValue());
    }

    @Test
    public void testGetHomePageBorder() {
        assertThat(this.prefs.getHomePageBorder(), notNullValue());
    }

    @Test
    public void testGetPreferredTableWidth() {
        assertThat(this.prefs.getPreferredTableWidth(), greaterThan(0));
    }

    @Test
    public void testGetForeground() {
        assertThat(this.prefs.getForeground(), notNullValue());
    }

    @Test
    public void testGetHeaderForeground() {
        assertThat(this.prefs.getHeaderForeground(), notNullValue());
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
        assertThat(this.prefs.getHeaderRowHeight(), notNullValue());
    }

    @Test
    public void testGetBodyRowHeight() {
        assertThat(this.prefs.getBodyRowHeight(), greaterThan(0));
    }
}
