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

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;


/**
 * @author Florian J. Breunig
 */
public class FileContainerTest {

    private FileContainer fileContainer;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();
        this.fileContainer = testComponent.fileContainer();
        final File basedir = new File(String.format("%s%s%s%s%s",
                "src", File.separator,
                "test", File.separator,
                "resources"));
        this.fileContainer.reloadFiles(basedir);
    }

    @Test
    public void testGet() {
        assertThat(this.fileContainer.get(0), notNullValue());
    }

    @Test
    public void testSize() {
        assertThat(this.fileContainer.size(), greaterThan(0));
    }

    @Test
    public void testIsEmpty() {
        assertThat(this.fileContainer.isEmpty(), is(false));
    }

    @Test
    public void testGetFileName() {
        assertThat(this.fileContainer.getFileName(0), notNullValue());
    }

    @Test
    public void testGetLastModifiedTime() {
        assertThat(this.fileContainer.getLastModifiedTime(0), notNullValue());
    }
}
