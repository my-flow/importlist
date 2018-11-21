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

package com.moneydance.modules.features.importlist.io;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.util.Helper;

/**
 * @author Florian J. Breunig
 */
public final class FileAdminTest {

    private FileAdmin fileAdmin;

    @Before
    public void setUp() {
        Helper.INSTANCE.getPreferences();
        new StubContextFactory();
        final String basedir = String.format("%s%s%s%s%s",
                "src", File.separator,
                "test", File.separator,
                "resources");
        StubContextFactory factory = new StubContextFactory();
        this.fileAdmin = new FileAdmin(basedir, factory.getContext());
    }

    @Test
    public void testGetFiles() {
        assertThat(this.fileAdmin.getFiles(), notNullValue());
    }

    @Test
    public void testReloadFiles() {
        this.fileAdmin.reloadFiles();
        assertThat(this.fileAdmin.getFiles(), notNullValue());
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
        this.fileAdmin.stopMonitor();
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
