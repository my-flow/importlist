package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.TestComponentFactory;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class DefaultDirectoryChooserTest {

    private DefaultDirectoryChooser directoryChooser;

    @Before
    public void setUp() {
        final CoreTestComponent testComponent = TestComponentFactory.createTestComponent();
        this.directoryChooser = new DefaultDirectoryChooser(testComponent.preferences());
    }

    @Test
    public void testGetBaseDirectory() {
        assertThat(this.directoryChooser.getBaseDirectory().isPresent(), is(true));
    }

    @Test
    public void testReset() {
        this.directoryChooser.reset();
    }

    @Test
    public void testGetPrefs() {
        assertThat(this.directoryChooser.getPrefs(), is(notNullValue()));
    }
}
