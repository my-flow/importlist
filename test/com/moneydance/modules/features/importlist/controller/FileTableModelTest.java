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

package com.moneydance.modules.features.importlist.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;

/**
 * @author Florian J. Breunig
 */
public final class FileTableModelTest {

    private FileTableModel emptyModel;
    private FileTableModel fullModel;

    @Before
    public void setUp() {
        new StubContextFactory();
        this.emptyModel = new FileTableModel(new ArrayList<File>());
        List<File> list = new ArrayList<File>(1);
        list.add(new File("."));
        this.fullModel = new FileTableModel(list);
    }

    @Test
    public void testIsCellEditable() {
        this.emptyModel.isCellEditable(0, 2);
        this.emptyModel.isCellEditable(0, 3);
        this.emptyModel.isCellEditable(0, 4);
    }

    @Test
    public void testGetColumnClassInt() {
        Assert.assertNotNull("column class must not be null",
                this.emptyModel.getColumnClass(0));
        Assert.assertNotNull("column class must not be null",
                this.emptyModel.getColumnClass(1));
        Assert.assertNotNull("column class must not be null",
                this.emptyModel.getColumnClass(2));
        Assert.assertNotNull("column class must not be null",
                this.emptyModel.getColumnClass(3));
        Assert.assertNull("column class must be null",
                this.emptyModel.getColumnClass(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueAtForEmptyModel() {
        this.emptyModel.getValueAt(0,  0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueAtForFullModel() {
        Assert.assertNotNull("value for full model must not be null",
                this.fullModel.getValueAt(0,  0));
        Assert.assertNotNull("value for full model must not be null",
                this.fullModel.getValueAt(0,  1));
        Assert.assertNotNull("value for full model must not be null",
                this.fullModel.getValueAt(0,  2));
        Assert.assertNotNull("value for full model must not be null",
                this.fullModel.getValueAt(0,  3));
        this.fullModel.getValueAt(0,  4); // throw an expected exception
    }

    @Test
    public void testGetColumnCount() {
        Assert.assertEquals("column count must be equal to 4", 4,
                this.emptyModel.getColumnCount());
    }

    @Test
    public void testGetColumnNameInt() {
        Assert.assertNotNull("column must have a name",
                this.emptyModel.getColumnName(0));
    }

    @Test
    public void testGetRowCount() {
        Assert.assertTrue("row count must be equal to or greater than zero",
                this.emptyModel.getRowCount() >= 0);
    }
}
