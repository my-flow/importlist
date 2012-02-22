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

package com.moneydance.modules.features.importlist.io;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;

/**
 * @author Florian J. Breunig
 */
public final class FileAdminTest {

    private FileAdmin fileAdmin;

    @Before
    public void setUp() {
        String basedir = new File(".").getAbsolutePath()
                + File.separator + "test"
                + File.separator + "testfiles";
        StubContextFactory factory = new StubContextFactory();
        this.fileAdmin = new FileAdmin(basedir, factory.getContext());
    }

    @Test
    public void testChooseBaseDirectory() {
        this.fileAdmin.chooseBaseDirectory();
    }

    @Test
    public void testGetFiles() {
        Assert.assertNotNull("file list must not be null",
                this.fileAdmin.getFiles());
    }

    @Test
    public void testReloadFiles() {
        this.fileAdmin.reloadFiles();
        Assert.assertNotNull("file list must not be null",
                this.fileAdmin.getFiles());
    }

    @Test
    public void testGetBaseDirectory() {
        Assert.assertNotNull(this.fileAdmin.getBaseDirectory());
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

    @Test
    public void testDeleteAllRows() {
        this.fileAdmin.deleteAllRows();

        this.fileAdmin.reloadFiles();
        this.fileAdmin.deleteAllRows();
    }

    @Test
    public void testDeleteRow() {
        this.fileAdmin.deleteRow(0);

        this.fileAdmin.reloadFiles();
        this.fileAdmin.deleteRow(0);
        this.fileAdmin.deleteRow(Integer.MAX_VALUE);
    }
}
