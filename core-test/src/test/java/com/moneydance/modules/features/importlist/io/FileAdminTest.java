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

package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class FileAdminTest {

    private FileAdmin fileAdmin;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();
        this.fileAdmin = testComponent.fileAdmin();
    }

    @Test
    public void testCheckValidBaseDirectory() {
        this.fileAdmin.checkValidBaseDirectory();
    }

    @Test
    public void testGetFileContainer() {
        assertThat(this.fileAdmin.getFileContainer(), notNullValue());
    }

    @Test
    public void testGetBaseDirectory() {
        assertThat(this.fileAdmin.getBaseDirectory(), notNullValue());
    }

    @Test
    public void testUpdate() {
        this.fileAdmin.update(null, null);
    }

    @Test
    public void testStartMonitor() {
        this.fileAdmin.startMonitor();
        this.fileAdmin.startMonitor(); // fail the second time
    }

    @Test
    public void testStopMonitor() {
        this.fileAdmin.stopMonitor(); // monitor not started yet

        this.fileAdmin.startMonitor();
        this.fileAdmin.stopMonitor(); // shut down started monitor
    }

    @Test
    public void testImportAllRows() {
        this.fileAdmin.importAllRows();

        this.fileAdmin.reloadFiles();
        this.fileAdmin.importAllRows();
    }

    @Test
    public void testImportRow() {
        this.fileAdmin.importRow(0);

        this.fileAdmin.reloadFiles();
        this.fileAdmin.importRow(0);
        this.fileAdmin.importRow(Integer.MAX_VALUE);
    }
}
