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

package com.moneydance.modules.features.importlist.presentation;

import javax.swing.JLabel;
import javax.swing.JSplitPane;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class JCustomSplitPaneTest {

    private JCustomSplitPane customSplitPane;

    @Before
    public void setUp() {
        this.customSplitPane =
                new JCustomSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    }

    @Test
    public void testAddNotify() {
        this.customSplitPane.setTopComponent(null);
        this.customSplitPane.setBottomComponent(null);
        this.customSplitPane.addNotify();

        this.customSplitPane.setTopComponent(new JLabel());
        this.customSplitPane.setBottomComponent(null);
        this.customSplitPane.addNotify();

        this.customSplitPane.setTopComponent(null);
        this.customSplitPane.setBottomComponent(new JLabel());
        this.customSplitPane.addNotify();

        this.customSplitPane.setTopComponent(new JLabel());
        this.customSplitPane.setBottomComponent(new JLabel());
        this.customSplitPane.addNotify();
    }
}
