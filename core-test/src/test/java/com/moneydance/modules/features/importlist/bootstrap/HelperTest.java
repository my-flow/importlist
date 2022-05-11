package com.moneydance.modules.features.importlist.bootstrap;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * @author Florian J. Breunig
 */
public final class HelperTest {

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();
        Helper.INSTANCE.init(testComponent);
    }

    @Test
    public void testGetPreferences() {
        assertThat(Helper.INSTANCE.getPreferences(), notNullValue());
    }

    @Test
    public void testGetLocalizable() {
        assertThat(Helper.INSTANCE.getLocalizable(), notNullValue());
    }
}
