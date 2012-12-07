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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.util.Helper;

/**
 * @author Florian J. Breunig
 */
public final class ViewControllerTest {

    private ViewController viewController;

    @Before
    public void setUp() {
        this.viewController = new ViewController(
                null,
                new StubContextFactory().getContext(),
                Helper.INSTANCE.getTracker(0));
    }

    @Test
    public void testRefresh() {
        this.viewController.refresh();
    }

    @Test
    public void testUpdate() {
        this.viewController.update(null, null);
    }

    @Test
    public void testGetGUIView() {
        Assert.assertNotNull("GUI must not be null",
                this.viewController.getGUIView(null));
        Assert.assertNotNull("GUI must not be null", // second call
                this.viewController.getGUIView(null));
    }

    @Test
    public void testReset() {
        this.viewController.reset();
    }

    @Test
    public void testSetActive() {
        this.viewController.setActive(true);
        this.viewController.setActive(false);

        this.viewController.getGUIView(null);
        this.viewController.setActive(true);
        this.viewController.setActive(false);
    }

    @Test
    public void testGetID() {
        Assert.assertNotNull("id must not be null",
                this.viewController.getID());
    }

    @Test
    public void testToString() {
        Assert.assertNotNull("extension name must not be null",
                this.viewController.toString());
    }

    @Test
    public void testChooseBaseDirectory() {
        this.viewController.chooseBaseDirectory();
    }

    @Test
    public void testCleanup() {
        this.viewController.cleanup();

        this.viewController.getGUIView(null);
        this.viewController.cleanup();
    }

    @Test
    public void testSetDirty() {
        this.viewController.setDirty(true);
        this.viewController.setDirty(false);
    }

    @Test
    public void testIsDirty() {
        this.viewController.setDirty(true);
        Assert.assertTrue("view must be dirty",
                this.viewController.isDirty());

        this.viewController.setDirty(false);
        Assert.assertFalse("view must not be dirty",
                this.viewController.isDirty());
    }
}
