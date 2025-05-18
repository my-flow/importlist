package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.TargetTestComponent;
import com.moneydance.modules.features.importlist.test.HelperUtils;
import com.moneydance.modules.features.importlist.util.Preferences;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class DirectoryChooserButtonFactoryTest {

    private DirectoryChooserButtonFactory directoryChooserFactory;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = HelperUtils.createTestComponent();
        final Preferences prefs = testComponent.preferences();

        this.directoryChooserFactory = new DirectoryChooserButtonFactory(
                "stub text",
                null,
                prefs.getHomePageBorder(),
                prefs.getBackground(),
                prefs.getBodyFont());
    }

    @Test
    public void testGetComponent() {
        assertThat(this.directoryChooserFactory.getComponent(), notNullValue()); // init
        assertThat(this.directoryChooserFactory.getComponent(), notNullValue());
    }
}
