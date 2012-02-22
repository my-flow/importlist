/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class HelperTest {

    @Test
    public void testGetPreferences() {
        Assert.assertNotNull("preferences must not be null",
                Helper.getPreferences());
    }

    @Test
    public void testGetSettings() {
        Assert.assertNotNull("settings must not be null", Helper.getSettings());
    }

    @Test
    public void testGetLocalizable() {
        Assert.assertNotNull("localizable must not be null",
                Helper.getLocalizable());
    }

    @Test
    public void testLoadLoggerConfiguration() {
        Helper.loadLoggerConfiguration();
    }

    @Test
    public void testGetInputStreamFromResource() {
        Assert.assertNotNull("input stream from resource must not be null",
                Helper.getInputStreamFromResource(
                        Helper.getSettings().getLog4jPropertiesResource()));
    }

    @Test
    public void testGetIconImage() {
        Assert.assertNotNull("icon image must not be null",
                Helper.getIconImage());
    }

}
