// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2016 Florian J. Breunig
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

package com.moneydance.modules.features.importlist;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Florian J. Breunig
 */
@RunWith(Suite.class)
@SuiteClasses({
        MainTest.class,
        com.moneydance.modules.features.importlist.io.AllTests.class,
        com.moneydance.modules.features.importlist.table.AllTests.class,
        com.moneydance.modules.features.importlist.util.AllTests.class,
        com.moneydance.modules.features.importlist.presentation.AllTests.class,
        com.moneydance.modules.features.importlist.controller.AllTests.class
})
public final class AllTests {
    // no test cases
}
