// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2014 Florian J. Breunig
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.util.Helper;

/**
 * @author Florian J. Breunig
 */
public final class DeleteAllOperationTest {

    private FileOperation fileOperation;
    private List<File> files;

    @Before
    public void setUp() {
        Helper.INSTANCE.getPreferences();
        new StubContextFactory();

        this.files = new ArrayList<File>();
        this.files.add(new File("."));

        this.fileOperation = new DeleteAllOperation();
    }

    @Test
    public void testShowWarningAndExecute() {
        this.fileOperation.showWarningAndExecute(this.files);
    }

    @Test
    public void testExecute() {
        this.fileOperation.execute(this.files);
    }
}
