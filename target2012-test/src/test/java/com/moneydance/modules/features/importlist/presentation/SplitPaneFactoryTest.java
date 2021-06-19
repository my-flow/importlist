// Import List - https://www.my-flow.com/importlist/
// Copyright (C) 2011-2021 Florian J. Breunig
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

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;
import com.moneydance.modules.features.importlist.util.Preferences;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class SplitPaneFactoryTest {

    private SplitPaneFactory splitPaneFactory;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = DaggerTargetTestComponent.builder().build();
        final Preferences prefs = testComponent.preferences();

        this.splitPaneFactory = new SplitPaneFactory(
                new EmptyLabelFactory(
                        "stub text 1",
                        prefs.getHomePageBorder(),
                        prefs.getBackground(),
                        prefs.getBodyFont()),
                new EmptyLabelFactory(
                        "stub text 2",
                        prefs.getHomePageBorder(),
                        prefs.getBackground(),
                        prefs.getBodyFont()));
    }

    @Test
    public void testGetComponent() {
        assertThat(this.splitPaneFactory.getComponent(), notNullValue()); // init
        assertThat(this.splitPaneFactory.getComponent(), notNullValue());
    }
}
