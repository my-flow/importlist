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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DirectoryValidatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCheckValidDirectory() {
        Assert.assertTrue("directory must be valid",
                DirectoryValidator.INSTANCE.checkValidDirectory(new File(".")));

        Assert.assertFalse("directory must not be valid",
                DirectoryValidator.INSTANCE.checkValidDirectory(null));
}

    @Test
    public void testAcceptFile() {
        Assert.assertTrue("directory must be accepted",
                DirectoryValidator.INSTANCE.accept(new File(".")));
    }

    @Test
    public void testAcceptFileString() {
        Assert.assertTrue("directory must be accepted",
                DirectoryValidator.INSTANCE.accept(
                        new File("."),
                        new File(".").getName()));
    }

}
