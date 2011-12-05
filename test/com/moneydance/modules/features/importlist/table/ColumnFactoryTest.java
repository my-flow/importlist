/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.table;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableCellRenderer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.util.CustomDateFormat;

/**
 * @author Florian J. Breunig
 */
public final class ColumnFactoryTest {

    private ColumnFactory columnFactory;

    @Before
    public void setUp() {
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
        Assert.assertNotNull("header renderer must not be null",
                this.columnFactory.getHeaderRenderer());
    }

    @Test
    public void testGetLabelNameOneRenderer() {
        Assert.assertNotNull("label name one renderer must not be null",
                this.columnFactory.getLabelNameOneRenderer());
    }

    @Test
    public void testGetLabelNameAllRenderer() {
        Assert.assertNotNull("label name all renderer must not be null",
                this.columnFactory.getLabelNameAllRenderer());
    }

    @Test
    public void testGetLabelModifiedOneRenderer() {
        Assert.assertNotNull("label modified one renderer must not be null",
                this.columnFactory.getLabelModifiedOneRenderer());
    }

    @Test
    public void testGetLabelModifiedAllRenderer() {
        Assert.assertNotNull(this.columnFactory.getLabelModifiedAllRenderer());
    }

    @Test
    public void testGetButtonOneRenderer() {
        Assert.assertNotNull("button one renderer must not be null",
                this.columnFactory.getButtonOneRenderer());
    }

    @Test
    public void testGetButtonAllRenderer() {
        Assert.assertNotNull("button all renderer must not be null",
                this.columnFactory.getButtonAllRenderer());
    }

    @Test
    public void testGetImportOneEditor() {
        Assert.assertNotNull("import one editor must not be null",
                this.columnFactory.getImportOneEditor());
    }

    @Test
    public void testGetImportAllEditor() {
        Assert.assertNotNull("import all editor must not be null",
                this.columnFactory.getImportAllEditor());
    }

    @Test
    public void testGetDeleteOneEditor() {
        Assert.assertNotNull("delete one editor must not be null",
                this.columnFactory.getDeleteOneEditor());
    }

    @Test
    public void testGetDeleteAllEditor() {
        Assert.assertNotNull("delete all editor must not be null",
                this.columnFactory.getDeleteAllEditor());
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
