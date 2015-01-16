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

package com.moneydance.modules.features.importlist.table;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class OddColorSchemeHelperTest {

    private OddColorSchemeHelper colorSchemeHelper;

    @Before
    public void setUp() {
        this.colorSchemeHelper = new OddColorSchemeHelper(
                Color.black,
                Color.white,
                Color.white);
    }

    @Test
    public void testApplyColorScheme() {
        Component component = new Button("button");
        this.colorSchemeHelper.applyColorScheme(component, 0);
        this.colorSchemeHelper.applyColorScheme(component, 1);

        JComponent jComponent = new JButton("button");
        this.colorSchemeHelper.applyColorScheme(jComponent, 0);
        this.colorSchemeHelper.applyColorScheme(jComponent, 1);
    }

    @Test
    public void testSetForeground() {
        this.colorSchemeHelper.setForeground(Color.black);
    }

    @Test
    public void testSetBackground() {
        this.colorSchemeHelper.setBackground(Color.black);
    }

    @Test
    public void testSetBackgroundAlt() {
        this.colorSchemeHelper.setBackgroundAlt(Color.black);
    }
}
