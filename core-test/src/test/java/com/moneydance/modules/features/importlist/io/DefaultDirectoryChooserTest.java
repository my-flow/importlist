package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

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
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();
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
