package com.moneydance.modules.features.importlist.util;

import com.moneydance.apps.md.controller.StubContext;
import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.apps.md.controller.UserPreferences;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nullable;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class PreferencesImplTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    private UserPreferences userPreferences;
    @Nullable private String previousImportDirectory;
    private Preferences prefs;

    @Before
    public void setUp() {
        final StubContext context = Objects.requireNonNull(new StubContextFactory().getContext());
        this.userPreferences = context.getPreferences();
        this.previousImportDirectory = this.userPreferences.getSetting(UserPreferences.IMPORT_DIR, null);
        this.prefs = new PreferencesImpl(FactoryModule.createSettingsSafe(), context);
    }

    @After
    public void tearDown() {
        this.userPreferences.setSetting(UserPreferences.IMPORT_DIR, this.previousImportDirectory);
    }

    @Test
    public void testGetMoneydanceImportDirectoryWithoutSetting() {
        this.userPreferences.setSetting(UserPreferences.IMPORT_DIR, (String) null);
        assertThat(this.prefs.getMoneydanceImportDirectory(), is(Optional.empty()));
    }

    @Test
    public void testGetMoneydanceImportDirectoryWithDirectory() throws IOException {
        final File directory = this.temporaryFolder.newFolder("import");
        this.userPreferences.setSetting(UserPreferences.IMPORT_DIR, directory.getAbsolutePath());
        assertThat(this.prefs.getMoneydanceImportDirectory(), is(Optional.of(directory)));
    }

    @Test
    public void testGetMoneydanceImportDirectoryWithFile() throws IOException {
        final File file = this.temporaryFolder.newFile("transactions.qif");
        this.userPreferences.setSetting(UserPreferences.IMPORT_DIR, file.getAbsolutePath());
        assertThat(this.prefs.getMoneydanceImportDirectory(), is(Optional.of(file.getParentFile())));
    }

    @Test
    public void testGetMoneydanceImportDirectoryWithMissingDirectory() {
        final File file = new File(new File(this.temporaryFolder.getRoot(), "missing"), "transactions.qif");
        this.userPreferences.setSetting(UserPreferences.IMPORT_DIR, file.getAbsolutePath());
        assertThat(this.prefs.getMoneydanceImportDirectory(), is(Optional.empty()));
    }
}
