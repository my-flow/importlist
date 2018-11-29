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

package com.moneydance.modules.features.importlist.controller;

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class ViewControllerTest {

    private ViewControllerImpl viewController;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = DaggerTargetTestComponent.builder().build();
        this.viewController = (ViewControllerImpl) testComponent.viewController();
    }

    @Test
    public void testRefresh() {
        this.viewController.refresh();
    }

    @Test
    public void testGetGUIView() {
        assertThat(this.viewController.getGUIView(null), notNullValue());
        assertThat(this.viewController.getGUIView(null), notNullValue()); // second call
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
        assertThat(this.viewController.getID(), notNullValue());
    }

    @Test
    public void testToString() {
        assertThat(this.viewController.toString(), notNullValue());
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
        assertThat(this.viewController.isDirty(), is(true));

        this.viewController.setDirty(false);
        assertThat(this.viewController.isDirty(), is(false));
    }
}
