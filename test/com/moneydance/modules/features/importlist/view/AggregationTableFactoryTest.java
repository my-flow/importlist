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

package com.moneydance.modules.features.importlist.view;

import javax.swing.table.TableModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.controller.AggregationTableModel;
import com.moneydance.modules.features.importlist.io.FileAdmin;

/**
 * @author Florian J. Breunig
 */
public final class AggregationTableFactoryTest {

    private AggregationTableFactory tableFactory;

    @Before
    public void setUp() throws Exception {
        StubContextFactory factory = new StubContextFactory();
        FileAdmin fileAdmin     = new FileAdmin(null, factory.getContext());
        TableModel tableModel   = new AggregationTableModel();
        this.tableFactory = new AggregationTableFactory(tableModel, fileAdmin);
    }

    @Test
    public void testGetComponent() {
        Assert.assertNotNull("component must not be null",
                this.tableFactory.getComponent()); // init
        Assert.assertNotNull("component must not be null",
                this.tableFactory.getComponent());
    }

    @Test
    public void testGetColumnFactory() {
        Assert.assertNotNull("column factory must not be null",
                this.tableFactory.getColumnFactory()); // init
        Assert.assertNotNull("column factory must not be null",
                this.tableFactory.getColumnFactory());
    }
}
