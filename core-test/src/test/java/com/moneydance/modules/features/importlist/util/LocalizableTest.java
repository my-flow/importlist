package com.moneydance.modules.features.importlist.util;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class LocalizableTest {

    private Localizable localizable;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testFactory = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();
        this.localizable = new Localizable(testFactory.settings(), Locale.US);
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
    public void testGetErrorMessageBaseDirectory() {
        assertThat(
                this.localizable.getErrorMessageBaseDirectory("directory name"),
                notNullValue());
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
