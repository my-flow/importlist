// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2020 Florian J. Breunig
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
public final class DirectoryChooserButtonFactoryTest {

    private DirectoryChooserButtonFactory directoryChooserFactory;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = DaggerTargetTestComponent.builder().build();
        final Preferences prefs = testComponent.preferences();

        this.directoryChooserFactory = new DirectoryChooserButtonFactory(
                "stub text",
                null,
                prefs.getHomePageBorder(),
                prefs.getBackground(),
                prefs.getBodyFont());
    }

    @Test
    public void testGetComponent() {
        assertThat(this.directoryChooserFactory.getComponent(), notNullValue()); // init
        assertThat(this.directoryChooserFactory.getComponent(), notNullValue());
    }
}
