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

package com.moneydance.modules.features.importlist.controller;

import com.moneydance.apps.md.controller.StubContextFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

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
        List<File> list = new ArrayList<>(1);
        list.add(new File(""));
        this.fullModel = new FileTableModel(list);
    }

    @Test
    public void testIsCellEditable() {
        this.emptyModel.isCellEditable(0, 2);
        this.emptyModel.isCellEditable(0, 3);
        this.emptyModel.isCellEditable(0, 4);
    }

    @Test
    public void testGetColumnClassIntExpected() {
        assertThat(this.emptyModel.getColumnClass(0), notNullValue());
        assertThat(this.emptyModel.getColumnClass(1), notNullValue());
        assertThat(this.emptyModel.getColumnClass(2), notNullValue());
        assertThat(this.emptyModel.getColumnClass(3), notNullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetColumnClassIntUnexpected() {
        assertThat(this.emptyModel.getColumnClass(5), nullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueAtForEmptyModel() {
        this.emptyModel.getValueAt(0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueAtForFullModel() {
        assertThat(this.fullModel.getValueAt(0, 0), notNullValue());
        assertThat(this.fullModel.getValueAt(0, 1), notNullValue());
        assertThat(this.fullModel.getValueAt(0, 2), notNullValue());
        assertThat(this.fullModel.getValueAt(0, 3), notNullValue());
        this.fullModel.getValueAt(0, 4); // throw an expected exception
    }

    @Test
    public void testGetColumnCount() {
        assertThat(this.emptyModel.getColumnCount(), is(4));
    }

    @Test
    public void testGetColumnNameInt() {
        assertThat(this.emptyModel.getColumnName(0), notNullValue());
    }

    @Test
    public void testGetRowCount() {
        assertThat(this.emptyModel.getRowCount() >= 0, is(true));
    }
}
