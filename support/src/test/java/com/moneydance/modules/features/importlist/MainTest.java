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

package com.moneydance.modules.features.importlist;

import com.moneydance.apps.md.controller.StubContextFactory;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class MainTest {

    private Main main;

    @Before
    public void setUp() {
        this.main = new Main();
        final StubContextFactory factory = new StubContextFactory(this.main);
        factory.init();
        this.main.init();
    }

    @Test
    public void testInit() {
        this.main.init();
    }

    @Test
    public void testUnload() {
        this.main.unload();
    }

    @Test
    public void testCleanup() {
        this.main.cleanup();
    }

    @Test
    public void testGetName() {
        assertThat(this.main.getName(), notNullValue());
    }

    @Test
    public void testGetIconImage() {
        assertThat(this.main.getIconImage(), notNullValue());
    }

    @Test
    public void testUpdate() {
        this.main.update(null, null);
    }

    @Test
    public void testSetBaseDirectory() {
        this.main.setBaseDirectory(null);
    }

    @Test
    public void testGetHomePageView() {
        assertThat(this.main.getHomePageView(), notNullValue());
    }
}
