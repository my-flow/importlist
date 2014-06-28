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

package com.moneydance.modules.features.importlist.util;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.moneydance.apps.md.controller.StubContextFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class LocalizableTest {

    private Localizable localizable;
    private StubContextFactory factory;

    @Before
    public void setUp() {
        this.localizable = Helper.INSTANCE.getLocalizable();
    }

    @Test
    public void testGetDirectoryChooserTitle() {
        assertThat(this.localizable.getDirectoryChooserTitle(), notNullValue());
    }

    @Test
    public void testGetHeaderValueName() {
        assertThat(this.localizable.getHeaderValueName(), notNullValue());
    }

    @Test
    public void testGetHeaderValueModified() {
        assertThat(this.localizable.getHeaderValueModified(), notNullValue());
    }

    @Test
    public void testGetHeaderValueImport() {
        assertThat(this.localizable.getHeaderValueImport(), notNullValue());
    }

    @Test
    public void testGetHeaderValueDelete() {
        assertThat(this.localizable.getHeaderValueDelete(), notNullValue());
    }

    @Test
    public void testGetLabelImportOneButton() {
        assertThat(this.localizable.getLabelImportOneButton(), notNullValue());
    }

    @Test
    public void testGetLabelImportAllButton() {
        assertThat(this.localizable.getLabelImportAllButton(), notNullValue());
    }

    @Test
    public void testGetLabelDeleteOneButton() {
        assertThat(this.localizable.getLabelDeleteOneButton(), notNullValue());
    }

    @Test
    public void testGetLabelDeleteAllButton() {
        assertThat(this.localizable.getLabelDeleteAllButton(), notNullValue());
    }

    @Test
    public void testGetConfirmationMessageDeleteOneFile() {
        assertThat(
                this.localizable.getConfirmationMessageDeleteOneFile("file name"),
                notNullValue());
    }

    @Test
    public void testGetConfirmationMessageDeleteAllFiles() {
        assertThat(this.localizable.getConfirmationMessageDeleteAllFiles(2), notNullValue());
    }

    @Test
    public void testGetErrorMessageDeleteFile() {
        assertThat(this.localizable.getErrorMessageDeleteFile("file name"), notNullValue());
    }

    @Test
    public void testGetOptionDeleteFile() {
        assertThat(this.localizable.getOptionDeleteFile(), notNullValue());
    }

    @Test
    public void testGetOptionCancel() {
        assertThat(this.localizable.getOptionCancel(), notNullValue());
    }

    @Test
    public void testGetEmptyMessage() {
        assertThat(this.localizable.getEmptyMessage("base directory"), notNullValue());
    }

}
