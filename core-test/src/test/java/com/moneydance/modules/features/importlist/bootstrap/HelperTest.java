package com.moneydance.modules.features.importlist.bootstrap;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.TestComponentFactory;

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
        // Mock the test component to use for initialization
        final CoreTestComponent testComponent = TestComponentFactory.createTestComponent();
        try {
            // First reset to clear any existing component
            Helper.INSTANCE.reset();
            // Then initialize with a new component
            Helper.INSTANCE.init(testComponent);
        } catch (IllegalStateException e) {
            // If already initialized in another test, we'll use the existing component which should be fine
            System.out.println("Helper already initialized, using existing component");
        }
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
