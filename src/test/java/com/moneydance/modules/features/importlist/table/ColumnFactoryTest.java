// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2015 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.table;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableCellRenderer;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.util.CustomDateFormat;

/**
 * @author Florian J. Breunig
 */
public final class ColumnFactoryTest {

    private ColumnFactory columnFactory;

    @Before
    public void setUp() {
        Helper.INSTANCE.getPreferences();
        StubContextFactory factory = new StubContextFactory();
        this.columnFactory = new ColumnFactory(
                new FileAdmin(null, factory.getContext()),
                Color.white,
                Color.white,
                Color.white,
                new DefaultTableCellRenderer(),
                new CustomDateFormat(""),
                new SimpleDateFormat());
    }

    @Test
    public void testGetHeaderRenderer() {
        assertThat(this.columnFactory.getHeaderRenderer(), notNullValue());
    }

    @Test
    public void testGetLabelNameOneRenderer() {
        assertThat(this.columnFactory.getLabelNameOneRenderer(), notNullValue());
    }

    @Test
    public void testGetLabelNameAllRenderer() {
        assertThat(this.columnFactory.getLabelNameAllRenderer(), notNullValue());
    }

    @Test
    public void testGetLabelModifiedOneRenderer() {
        assertThat(this.columnFactory.getLabelModifiedOneRenderer(), notNullValue());
    }

    @Test
    public void testGetLabelModifiedAllRenderer() {
        assertThat(this.columnFactory.getLabelModifiedAllRenderer(), notNullValue());
    }

    @Test
    public void testGetButtonOneRenderer() {
        assertThat(this.columnFactory.getButtonOneRenderer(), notNullValue());
    }

    @Test
    public void testGetButtonAllRenderer() {
        assertThat(this.columnFactory.getButtonAllRenderer(), notNullValue());
    }

    @Test
    public void testGetImportOneEditor() {
        assertThat(this.columnFactory.getImportOneEditor(), notNullValue());
    }

    @Test
    public void testGetImportAllEditor() {
        assertThat(this.columnFactory.getImportAllEditor(), notNullValue());
    }

    @Test
    public void testGetDeleteOneEditor() {
        assertThat(this.columnFactory.getDeleteOneEditor(), notNullValue());
    }

    @Test
    public void testGetDeleteAllEditor() {
        assertThat(this.columnFactory.getDeleteAllEditor(), notNullValue());
    }

    @Test
    public void testSetForeground() {
        this.columnFactory.setForeground(Color.BLACK);
    }

    @Test
    public void testSetBackground() {
        this.columnFactory.setBackground(Color.BLACK);
    }

    @Test
    public void testSetBackgroundAlt() {
        this.columnFactory.setBackgroundAlt(Color.BLACK);
    }

    @Test
    public void testSetDateFormatter() {
        this.columnFactory.setDateFormatter(new CustomDateFormat(""));
    }

    @Test
    public void testSetTimeFormatter() {
        this.columnFactory.setTimeFormatter(DateFormat.getTimeInstance());
    }

}
