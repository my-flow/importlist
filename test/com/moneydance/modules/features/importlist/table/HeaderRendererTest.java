/*
 * Import List - http://my-flow.github.io/importlist/
 * Copyright (C) 2011-2013 Florian J. Breunig
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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class HeaderRendererTest {

    @Test
    public void testGetTableCellRendererComponent() {
        TableCellRenderer headerRenderer = new HeaderRenderer(
                new OddColorSchemeHelper(
                        Color.white,
                        Color.white,
                        Color.white),
                new DefaultTableCellRenderer());

        assertThat(
                headerRenderer.getTableCellRendererComponent(
                        new JTable(),
                        null,
                        false,
                        false,
                        0,
                        0),
                notNullValue());

        headerRenderer = new HeaderRenderer(
                new OddColorSchemeHelper(
                        Color.white,
                        Color.white,
                        Color.white),
                new ComponentTableCellRenderer());

        assertThat(
                headerRenderer.getTableCellRendererComponent(
                        new JTable(),
                        null,
                        false,
                        false,
                        0,
                        0),
                notNullValue());

        headerRenderer = new HeaderRenderer(
                new OddColorSchemeHelper(
                        Color.white,
                        Color.white,
                        Color.white),
                new JTableCellRenderer());

        assertThat(
                headerRenderer.getTableCellRendererComponent(
                        new JTable(),
                        null,
                        false,
                        false,
                        0,
                        0),
                notNullValue());
    }

    /**
     * @author Florian J. Breunig
     */
    private static final class ComponentTableCellRenderer
        implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                final JTable table,
                final Object value,
                final boolean isSelected,
                final boolean hasFocus,
                final int row,
                final int column) {
            return new Button();
        }
    }

    /**
     * @author Florian J. Breunig
     */
    private static final class JTableCellRenderer
        implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                final JTable table,
                final Object value,
                final boolean isSelected,
                final boolean hasFocus,
                final int row,
                final int column) {
            return new JTable();
        }
    }
}
