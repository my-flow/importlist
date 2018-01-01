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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.util.Helper;

/**
 * @author Florian J. Breunig
 */
public final class AggregationTableModelTest {

    private AggregationTableModel aggregationTableModel;

    @Before
    public void setUp() {
        Helper.INSTANCE.getPreferences();
        new StubContextFactory();
        this.aggregationTableModel = new AggregationTableModel();
    }

    @Test
    public void testIsCellEditable() {
        this.aggregationTableModel.isCellEditable(0, 2);
        this.aggregationTableModel.isCellEditable(0, 3);
        this.aggregationTableModel.isCellEditable(0, 4);
    }

    @Test
    public void testGetColumnClassInt() {
        assertThat(this.aggregationTableModel.getColumnClass(0), notNullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueAt() {
        assertThat(this.aggregationTableModel.getValueAt(0,  0), nullValue());
        assertThat(this.aggregationTableModel.getValueAt(0,  1), nullValue());
        assertThat(this.aggregationTableModel.getValueAt(0,  2), notNullValue());
        assertThat(this.aggregationTableModel.getValueAt(0,  3), notNullValue());
        this.aggregationTableModel.getValueAt(0,  4);
        // throw an expected exception
    }

    @Test
    public void testGetColumnCount() {
        assertThat(this.aggregationTableModel.getColumnCount(), is(4));
    }

    @Test
    public void testGetColumnNameInt() {
        assertThat(this.aggregationTableModel.getColumnName(0), notNullValue());
    }

    @Test
    public void testGetRowCount() {
        assertThat(this.aggregationTableModel.getRowCount(), is(1));
    }
}
