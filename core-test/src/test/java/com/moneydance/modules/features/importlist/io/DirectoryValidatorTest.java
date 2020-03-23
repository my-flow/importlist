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

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class DirectoryValidatorTest {

    private DirectoryValidator directoryValidator;

    @Before
    public void setUp() {
        this.directoryValidator = new DirectoryValidator();
    }

    @Test
    public void testIsValidDirectory() {
        assertThat(this.directoryValidator.isValidDirectory(new File("")), is(false));
        assertThat(this.directoryValidator.isValidDirectory(null), is(false));
        assertThat(this.directoryValidator.isValidDirectory(new File(System.getProperty("user.home"))), is(true));
    }

    @Test
    public void testAcceptFile() {
        assertThat(this.directoryValidator.accept(new File("")), is(false));
    }

    @Test
    public void testAcceptFileString() {
        assertThat(
                this.directoryValidator.accept(
                        new File(""),
                        new File("").getName()),
                        is(true));
    }
}
