// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2016 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.presentation;

import javax.swing.JViewport;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class JCustomScrollPaneTest {

    private JCustomScrollPane customScrollPane;

    @Before
    public void setUp() {
        this.customScrollPane =
                new JCustomScrollPane();
    }

    @Test
    public void testAddNotify() {
        this.customScrollPane.setColumnHeader(null);
        this.customScrollPane.setViewport(null);
        this.customScrollPane.addNotify();

        this.customScrollPane.setColumnHeader(new JViewport());
        this.customScrollPane.setViewport(null);
        this.customScrollPane.addNotify();
        Assert.assertSame("background color must match",
                this.customScrollPane.getBackground(),
                this.customScrollPane.getColumnHeader().getBackground());

        this.customScrollPane.setColumnHeader(null);
        this.customScrollPane.setViewport(new JViewport());
        this.customScrollPane.addNotify();

        this.customScrollPane.setColumnHeader(new JViewport());
        this.customScrollPane.setViewport(new JViewport());
        this.customScrollPane.addNotify();
        Assert.assertSame("background color must match",
                this.customScrollPane.getBackground(),
                this.customScrollPane.getColumnHeader().getBackground());
    }
}
