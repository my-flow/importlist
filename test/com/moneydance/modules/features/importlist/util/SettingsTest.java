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

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class SettingsTest {

    private Settings settings;

    @Before
    public void setUp() {
        this.settings = new Settings();
    }

    @Test
    public void testGetExtensionName() {
        Assert.assertNotNull("extension name must not be null",
                this.settings.getExtensionName());
    }

    @Test
    public void testGetExtensionIdentifier() {
        Assert.assertNotNull("extension identifier must not be null",
                this.settings.getExtensionIdentifier());
    }

    @Test
    public void testGetIconResource() {
        Assert.assertNotNull("icon resource must not be null",
                this.settings.getIconResource());
    }

    @Test
    public void testGetLog4jPropertiesResource() {
        Assert.assertNotNull("log4j properties resource must not be null",
                this.settings.getLog4jPropertiesResource());
    }

    @Test
    public void testGetLocalizableResource() {
        Assert.assertNotNull("localizable resource must not be null",
                this.settings.getLocalizableResource());
    }

    @Test
    public void testGetChooseBaseDirSuffix() {
        Assert.assertNotNull("choose basedir suffix must not be null",
                this.settings.getChooseBaseDirSuffix());
    }

    @Test
    public void testGetTransactionFileImportUriScheme() {
        Assert.assertNotNull("transaction file import uri scheme must not be "
                + "null", this.settings.getTransactionFileImportUriScheme());
    }

    @Test
    public void testGetTextFileImportUriScheme() {
        Assert.assertNotNull("text file import uri scheme must not be  null",
                this.settings.getTextFileImportUriScheme());
    }

    @Test
    public void testGetMonitorIntervall() {
        Assert.assertNotNull("monitor interval must not be  null",
                this.settings.getMonitorInterval());
    }

    @Test
    public void testGetTransactionFileExtensions() {
        Assert.assertNotNull("transaction file extension must not be  null",
                this.settings.getTransactionFileExtensions());
    }

    @Test
    public void testGetTextFileExtensions() {
        Assert.assertNotNull("text file extension must not be  null",
                this.settings.getTextFileExtensions());
    }

    @Test
    public void testGetMaxFilenameLength() {
        Assert.assertNotNull("max filename length must not be  null",
                this.settings.getMaxFilenameLength());
    }

    @Test
    public void testGetDescName() {
        Assert.assertNotNull("desc name must not be  null",
                this.settings.getDescName());
    }

    @Test
    public void testGetDescModified() {
        Assert.assertNotNull("desc modified must not be  null",
                this.settings.getDescModified());
    }

    @Test
    public void testGetDescImport() {
        Assert.assertNotNull("desc import must not be  null",
                this.settings.getDescImport());
    }

    @Test
    public void testGetDescDelete() {
        Assert.assertNotNull("desc delete must not be  null",
                this.settings.getDescDelete());
    }

    @Test
    public void testGetIndentationPrefix() {
        Assert.assertNotNull("indentation prefix must not be null",
                this.settings.getIndentationPrefix());
    }

    @Test
    public void testIsButtonResizable() {
        Assert.assertNotNull("button resizable must not be null",
                this.settings.isButtonResizable());
    }

    @Test
    public void testGetMinColumnWidth() {
        Assert.assertNotNull("min column width must not be null",
                this.settings.getMinColumnWidth());
    }

    @Test
    public void testIsReorderingAllowed() {
        Assert.assertNotNull("reordering allowed must not be null",
                this.settings.isReorderingAllowed());
    }

    @Test
    public void testGetMinimumTableWidth() {
        Assert.assertNotNull("minimum table width must not be null",
                this.settings.getMinimumTableWidth());
    }

    @Test
    public void testGetMinimumTableHeight() {
        Assert.assertNotNull("minimum table height must not be null",
                this.settings.getMinimumTableHeight());
    }

    @Test
    public void testGetTableHeightOffset() {
        Assert.assertNotNull("table height offset must not be null",
                this.settings.getTableHeightOffset());
    }

    @Test
    public void testGetColumnWidth() {
        Assert.assertNotNull("column width must not be null",
                this.settings.getColumnWidth());
    }

    @Test
    public void testGetPreferredEmptyMessageWidth() {
        Assert.assertNotNull("preferred empty message width must not be null",
                this.settings.getPreferredEmptyMessageWidth());
    }

    @Test
    public void testGetPreferredEmptyMessageHeight() {
        Assert.assertNotNull("preferred empty message height must not be null",
                this.settings.getPreferredEmptyMessageHeight());
    }

    @Test
    public void testGetMessageFilenameLineMaxLength() {
        Assert.assertNotNull("message filename line max length must not be "
                + "null", this.settings.getMessageFilenameLineMaxLength());
    }

    @Test
    public void testGetLengthOfVersionDigits() {
        Assert.assertNotNull("length of version digits must not be null",
                this.settings.getLengthOfVersionDigits());
    }

    @Test
    public void testGetVersionWithOpaqueHomepageView() {
        Assert.assertNotNull("version with opaque homepage view must not be "
                + "null", this.settings.getVersionWithOpaqueHomepageView());
    }

    @Test
    public void testGetColorValueFgDef() {
        Assert.assertNotNull("color value fg def must not be null",
                this.settings.getColorValueFgDef());
    }

    @Test
    public void testGetColorValueBgDef() {
        Assert.assertNotNull("color value bg def must not be null",
                this.settings.getColorValueBgDef());
    }

    @Test
    public void testGetColorValueBgAltDef() {
        Assert.assertNotNull("color value bg alt def must not be null",
                this.settings.getColorValueBgAltDef());
    }

    @Test
    public void testGetFactorRowHeightHeader() {
        Assert.assertNotNull("factor row height header must not be null",
                this.settings.getFactorRowHeightHeader());
    }

    @Test
    public void testGetSummandRowHeightBody() {
        Assert.assertNotNull("summand row height body must not be null",
                this.settings.getSummandRowHeightBody());
    }

    @Test
    public void testGetKeyboardShortcutImport() {
        Assert.assertNotNull("keyboard shortcut import must not be null",
                this.settings.getKeyboardShortcutImport());
    }

    @Test
    public void testGetKeyboardShortcutDelete() {
        Assert.assertNotNull("keyboard shortcut delete must not be null",
                this.settings.getKeyboardShortcutDelete());
    }

    @Test
    public void testGetTrackingCode() {
        Assert.assertNotNull("tracking code must not be null",
                this.settings.getTrackingCode());
    }
}
