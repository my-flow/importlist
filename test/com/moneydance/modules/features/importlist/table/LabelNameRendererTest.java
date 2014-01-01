// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2014 Florian J. Breunig
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

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class LabelNameRendererTest {

    private TableCellRenderer labelNameRenderer;

    @Before
    public void setUp() {
        this.labelNameRenderer = new LabelNameRenderer(
                new OddColorSchemeHelper(
                        Color.white,
                        Color.white,
                        Color.white));
    }

    @Test
    public void testGetTableCellRendererComponent () {
        assertThat(
                this.labelNameRenderer.getTableCellRendererComponent(
                        new JTable(),
                        null, // null value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());

        assertThat(
                this.labelNameRenderer.getTableCellRendererComponent(
                        new JTable(),
                        "", // empty string value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());
    }
}
