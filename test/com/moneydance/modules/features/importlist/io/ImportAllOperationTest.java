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

package com.moneydance.modules.features.importlist.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContext;
import com.moneydance.apps.md.controller.StubContextFactory;

/**
 * @author Florian J. Breunig
 */
public final class ImportAllOperationTest {

    private List<File> files;
    private FileOperation fileOperation;

    @Before
    public void setUp() {
        final StubContextFactory factory = new StubContextFactory();
        StubContext context = factory.getContext();

        this.files = new ArrayList<File>();
        this.files.add(new File("."));

        this.fileOperation = new ImportAllOperation(
                context,
                TrueFileFilter.TRUE,
                FalseFileFilter.FALSE);
    }

    @Test
    public void testShowWarningAndPerform() {
        this.fileOperation.showWarningAndPerform(this.files);
    }

    @Test
    public void testPerform() {
        this.fileOperation.perform(this.files);
    }
}
