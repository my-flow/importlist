// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2019 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.bootstrap;

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;

import java.util.Observable;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class MainHelperTest {

    private MainHelper mainHelper;
    private TargetTestComponent testComponent;

    @Before
    public void setUp() {
        this.testComponent = DaggerTargetTestComponent.builder().build();
        this.mainHelper = this.testComponent.mainHelper();
        this.mainHelper.init(this.testComponent, (final Observable o, final Object arg) -> {
            // ignore
        });
    }

    @Test
    public void testUnload() {
        this.mainHelper.unload();
    }

    @Test
    public void testCleanup() {
        this.mainHelper.cleanup();
    }

    @Test
    public void testGetName() {
        assertThat(this.mainHelper.getName(), notNullValue());
    }

    @Test
    public void testGetIconImage() {
        assertThat(this.mainHelper.getIconImage(), notNullValue());
    }

    @Test
    public void testInvoke() {
        this.mainHelper.invoke(null);
    }

    @Test
    public void testUpdate() {
        this.mainHelper.update(null);
    }

    @Test
    public void testConstructor() {
        this.mainHelper = new MainHelper(this.testComponent.settings());
    }

    @Test
    public void testGetHomePageView() {
        assertThat(this.mainHelper.getViewController(), notNullValue());
    }
}
