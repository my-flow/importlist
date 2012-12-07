/*
 * Import List - http://my-flow.github.com/importlist/
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

package com.moneydance.modules.features.importlist.controller;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Florian J. Breunig
 */
public final class FileTableRowSorterTest {

    private FileTableRowSorter fileTableRowSorter;

    @Before
    public void setUp() {
        this.fileTableRowSorter = new FileTableRowSorter(
                new FileTableModel(new ArrayList<File>()));
    }

    @Test
    public void testIsSortable() {
        this.fileTableRowSorter.isSortable(0);
        this.fileTableRowSorter.isSortable(1);
        this.fileTableRowSorter.isSortable(2);
    }

    @Test
    public void testGetComparatorInt() {
        this.fileTableRowSorter.getComparator(0);
        this.fileTableRowSorter.getComparator(1);
        this.fileTableRowSorter.getComparator(2);
    }

}
