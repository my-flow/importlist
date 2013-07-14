// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2013 Florian J. Breunig
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class DirectoryValidatorTest {

    @Test
    public void testIsValidDirectory() {
        assertThat(DirectoryValidator.INSTANCE.isValidDirectory(new File(".")), is(true));
        assertThat(DirectoryValidator.INSTANCE.isValidDirectory(null), is(false));
    }

    @Test
    public void testAcceptFile() {
        assertThat(DirectoryValidator.INSTANCE.accept(new File(".")), is(true));
    }

    @Test
    public void testAcceptFileString() {
        assertThat(
                DirectoryValidator.INSTANCE.accept(
                        new File("."),
                        new File(".").getName()),
                is(true));
    }
}
