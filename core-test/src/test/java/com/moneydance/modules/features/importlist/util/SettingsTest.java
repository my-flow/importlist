// Import List - https://www.my-flow.com/importlist/
// Copyright (C) 2011-2021 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.util;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class SettingsTest {

    private Settings settings;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();

        this.settings = testComponent.settings();
    }

    @Test
    public void testGetExtensionName() {
        assertThat(this.settings.getExtensionName(), notNullValue());
    }

    @Test
    public void testGetExtensionIdentifier() {
        assertThat(this.settings.getExtensionIdentifier(), notNullValue());
    }

    @Test
    public void testGetIconImage() {
        assertThat(this.settings.getIconImage(), notNullValue());
    }

    @Test
    public void testGetLoggingPropertiesResource() {
        assertThat(this.settings.getLoggingPropertiesResource(), notNullValue());
    }

    @Test
    public void testGetLocalizableResource() {
        assertThat(this.settings.getLocalizableResource(), notNullValue());
    }

    @Test
    public void testGetChooseBaseDirSuffix() {
        assertThat(this.settings.getChooseBaseDirSuffix(), notNullValue());
    }

    @Test
    public void testGetTransactionFileImportUriScheme() {
        assertThat(this.settings.getTransactionFileImportUriScheme(),
                notNullValue());
    }

    @Test
    public void testGetTextFileImportUriScheme() {
        assertThat(this.settings.getTextFileImportUriScheme(), notNullValue());
    }

    @Test
    public void testGetMonitorIntervall() {
        assertThat(this.settings.getMonitorInterval(), notNullValue());
    }

    @Test
    public void testGetTransactionFileExtensions() {
        assertThat(this.settings.getTransactionFileExtensions(),
                notNullValue());
        assertThat(this.settings.getTransactionFileExtensions().size(), greaterThan(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetTransactionFileExtensionsImmutability() {
        this.settings.getTransactionFileExtensions().clear();
    }

    @Test
    public void testGetMaxFilenameLength() {
        assertThat(this.settings.getMaxFilenameLength(), notNullValue());
    }

    @Test
    public void testGetDescName() {
        assertThat(this.settings.getDescName(), notNullValue());
    }

    @Test
    public void testGetDescModified() {
        assertThat(this.settings.getDescModified(), notNullValue());
    }

    @Test
    public void testGetDescImport() {
        assertThat(this.settings.getDescImport(), notNullValue());
    }

    @Test
    public void testGetDescDelete() {
        assertThat(this.settings.getDescDelete(), notNullValue());
    }

    @Test
    public void testGetIndentationPrefix() {
        assertThat(this.settings.getIndentationPrefix(), notNullValue());
    }

    @Test
    public void testIsButtonResizable() {
        assertThat(this.settings.isButtonResizable(), notNullValue());
    }

    @Test
    public void testGetMinColumnWidth() {
        assertThat(this.settings.getMinColumnWidth() >= 0, is(true));
    }

    @Test
    public void testIsReorderingAllowed() {
        assertThat(this.settings.isReorderingAllowed(), notNullValue());
    }

    @Test
    public void testGetMinimumTableWidth() {
        assertThat(this.settings.getMinimumTableWidth() >= 0, notNullValue());
    }

    @Test
    public void testGetMinimumTableHeight() {
        assertThat(this.settings.getMinimumTableHeight() >= 0, is(true));
    }

    @Test
    public void testGetTableHeightOffset() {
        assertThat(this.settings.getTableHeightOffset() >= 0, is(true));
    }

    @Test
    public void testGetColumnWidth() {
        assertThat(this.settings.getColumnWidth() >= 0, is(true));
    }

    @Test
    public void testGetPreferredEmptyMessageWidth() {
        assertThat(this.settings.getPreferredEmptyMessageWidth() >= 0, is(true));
    }

    @Test
    public void testGetPreferredEmptyMessageHeight() {
        assertThat(this.settings.getPreferredEmptyMessageHeight() >= 0, is(true));
    }

    @Test
    public void testGetMessageFilenameLineMaxLength() {
        assertThat(this.settings.getMessageFilenameLineMaxLength() >= 0, is(true));
    }

    @Test
    public void testGetFactorRowHeightHeader() {
        assertThat(this.settings.getFactorRowHeightHeader() >= 0, is(true));
    }

    @Test
    public void testGetSummandRowHeightBody() {
        assertThat(this.settings.getSummandRowHeightBody() >= 0, is(true));
    }

    @Test
    public void testGetKeyboardShortcutImport() {
        assertThat(this.settings.getKeyboardShortcutImport(), notNullValue());
    }

    @Test
    public void testGetKeyboardShortcutDelete() {
        assertThat(this.settings.getKeyboardShortcutDelete(), notNullValue());
    }
}
