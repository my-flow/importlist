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

import com.moneydance.apps.md.controller.StubContextFactory;

/**
 * @author Florian J. Breunig
 */
public final class LocalizableTest {

    private Localizable localizable;

    @Before
    public void setUp() {
        new StubContextFactory();
        this.localizable = Localizable.INSTANCE;
        this.localizable.update(null, Boolean.TRUE);
    }

    @Test
    public void testUpdate() {
        this.localizable.update(null, Boolean.TRUE);
        this.localizable.update(null, Boolean.FALSE);
    }

    @Test
    public void testGetDirectoryChooserTitle() {
        Assert.assertNotNull("directory chooser title must not be null",
                this.localizable.getDirectoryChooserTitle());
    }

    @Test
    public void testGetHeaderValueName() {
        Assert.assertNotNull("header value name must not be null",
                this.localizable.getHeaderValueName());
    }

    @Test
    public void testGetHeaderValueModified() {
        Assert.assertNotNull("header value modified must not be null",
                this.localizable.getHeaderValueModified());
    }

    @Test
    public void testGetHeaderValueImport() {
        Assert.assertNotNull("header value import must not be null",
                this.localizable.getHeaderValueImport());
    }

    @Test
    public void testGetHeaderValueDelete() {
        Assert.assertNotNull("header value delete must not be null",
                this.localizable.getHeaderValueDelete());
    }

    @Test
    public void testGetLabelImportOneButton() {
        Assert.assertNotNull("label import one button must not be null",
                this.localizable.getLabelImportOneButton());
    }

    @Test
    public void testGetLabelImportAllButton() {
        Assert.assertNotNull("label import all button must not be null",
                this.localizable.getLabelImportAllButton());
    }

    @Test
    public void testGetLabelDeleteOneButton() {
        Assert.assertNotNull("label delete one button must not be null",
                this.localizable.getLabelDeleteOneButton());
    }

    @Test
    public void testGetLabelDeleteAllButton() {
        Assert.assertNotNull("label delete all button must not be null",
                this.localizable.getLabelDeleteAllButton());
    }

    @Test
    public void testGetConfirmationMessageDeleteOneFile() {
        Assert.assertNotNull(
                "confirmation message delete one file must not be null",
                this.localizable.getConfirmationMessageDeleteOneFile(
                        "file name"));
    }

    @Test
    public void testGetConfirmationMessageDeleteAllFiles() {
        Assert.assertNotNull(
                "confirmation message delete all files must not be null",
                this.localizable.getConfirmationMessageDeleteAllFiles(2));
    }

    @Test
    public void testGetErrorMessageDeleteFile() {
        Assert.assertNotNull("error message delete file must not be null",
                this.localizable.getErrorMessageDeleteFile("file name"));
    }

    @Test
    public void testGetOptionDeleteFile() {
        Assert.assertNotNull("option delete file must not be null",
                this.localizable.getOptionDeleteFile());
    }

    @Test
    public void testGetOptionCancel() {
        Assert.assertNotNull("option cancel must not be null",
                this.localizable.getOptionCancel());
    }

    @Test
    public void testGetEmptyMessage() {
        Assert.assertNotNull("empty message must not be null",
                this.localizable.getEmptyMessage("base directory"));
    }

}
