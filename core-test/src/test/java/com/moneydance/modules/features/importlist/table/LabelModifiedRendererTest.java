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

package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import java.util.Date;

import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class LabelModifiedRendererTest {

    private LabelModifiedRenderer labelModifiedRenderer;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();

        this.labelModifiedRenderer = testComponent.labelModifiedRenderer();
    }

    @Test
    public void testGetTableCellRendererComponent() {
        assertThat(
                this.labelModifiedRenderer.getTableCellRendererComponent(
                        new JTable(),
                        null, // null value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());

        assertThat(
                this.labelModifiedRenderer.getTableCellRendererComponent(
                        new JTable(),
                        new Date(), // date value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());

        assertThat(
                this.labelModifiedRenderer.getTableCellRendererComponent(
                        new JTable(),
                        "", // string value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());
    }

    @Test
    public void testSetDateFormatter() {
        this.labelModifiedRenderer.setDateFormatter(null);
    }

    @Test
    public void testSetTimeFormatter() {
        this.labelModifiedRenderer.setTimeFormatter(null);
    }

}
