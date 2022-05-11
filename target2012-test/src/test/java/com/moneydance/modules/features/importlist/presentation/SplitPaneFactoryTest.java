package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;
import com.moneydance.modules.features.importlist.util.Preferences;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class SplitPaneFactoryTest {

    private SplitPaneFactory splitPaneFactory;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = DaggerTargetTestComponent.builder().build();
        final Preferences prefs = testComponent.preferences();

        this.splitPaneFactory = new SplitPaneFactory(
                new EmptyLabelFactory(
                        "stub text 1",
                        prefs.getHomePageBorder(),
                        prefs.getBackground(),
                        prefs.getBodyFont()),
                new EmptyLabelFactory(
                        "stub text 2",
                        prefs.getHomePageBorder(),
                        prefs.getBackground(),
                        prefs.getBodyFont()));
    }

    @Test
    public void testGetComponent() {
        assertThat(this.splitPaneFactory.getComponent(), notNullValue());
    }
}
