package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.TestComponentFactory;
import com.moneydance.modules.features.importlist.controller.ContextMock;
import com.moneydance.modules.features.importlist.util.PreferencesMock;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class DefaultDirectoryChooserTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    private DefaultDirectoryChooser directoryChooser;

    @Before
    public void setUp() {
        final CoreTestComponent testComponent = TestComponentFactory.createTestComponent();
        this.directoryChooser = new DefaultDirectoryChooser(testComponent.preferences(), new ContextMock());
    }

    @Test
    public void testGetBaseDirectory() {
        assertThat(this.directoryChooser.getBaseDirectory().isPresent(), is(true));
    }

    @Test
    public void testReset() {
        this.directoryChooser.reset();
        assertThat(this.directoryChooser.getBaseDirectory().isPresent(), is(false));
    }

    @Test
    public void testGetPrefs() {
        assertThat(this.directoryChooser.getPrefs(), is(notNullValue()));
    }

    @Test
    public void testGetStartDirectoryWithBaseDirectory() throws IOException {
        final File baseDirectory = this.temporaryFolder.newFolder("base");
        final PreferencesMock prefs = new PreferencesMock();
        prefs.setBaseDirectory(baseDirectory);
        prefs.setMoneydanceImportDirectory(this.temporaryFolder.newFolder("import"));

        final DefaultDirectoryChooser chooser = new DefaultDirectoryChooser(prefs, new ContextMock());
        assertThat(chooser.getStartDirectory(), is(Optional.of(baseDirectory.getParentFile())));
    }

    @Test
    public void testGetStartDirectoryWithMoneydanceImportDirectory() throws IOException {
        final File importDirectory = this.temporaryFolder.newFolder("import");
        final PreferencesMock prefs = new PreferencesMock();
        prefs.setBaseDirectory(null);
        prefs.setMoneydanceImportDirectory(importDirectory);

        final DefaultDirectoryChooser chooser = new DefaultDirectoryChooser(prefs, new ContextMock());
        assertThat(chooser.getStartDirectory(), is(Optional.of(importDirectory)));
    }

    @Test
    public void testGetStartDirectoryWithRootAsBaseDirectory() throws IOException {
        final File importDirectory = this.temporaryFolder.newFolder("import");
        final PreferencesMock prefs = new PreferencesMock();
        prefs.setBaseDirectory(File.listRoots()[0]);
        prefs.setMoneydanceImportDirectory(importDirectory);

        final DefaultDirectoryChooser chooser = new DefaultDirectoryChooser(prefs, new ContextMock());
        assertThat(chooser.getStartDirectory(), is(Optional.of(importDirectory)));
    }

    @Test
    public void testGetStartDirectoryWithUserDirectory() {
        final PreferencesMock prefs = new PreferencesMock();
        prefs.setBaseDirectory(null);

        final DefaultDirectoryChooser chooser = new DefaultDirectoryChooser(prefs, new ContextMock());
        assertThat(chooser.getStartDirectory(), is(Optional.of(FileUtils.getUserDirectory())));
    }
}
