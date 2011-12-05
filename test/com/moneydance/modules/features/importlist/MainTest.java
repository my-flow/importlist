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

package com.moneydance.modules.features.importlist;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.util.Helper;

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
        Assert.assertNotNull("name must not be null", this.main.getName());
    }

    @Test
    public void testGetIconImage() {
        Assert.assertNotNull("icon image must not be null",
                this.main.getIconImage());
    }

    @Test
    public void testInvokeString() {
        this.main.invoke("");
        this.main.invoke(Helper.getSettings().getChooseBaseDirSuffix());
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
        Assert.assertNotNull("homepage view must not be null",
                this.main.getHomePageView());
    }
}
