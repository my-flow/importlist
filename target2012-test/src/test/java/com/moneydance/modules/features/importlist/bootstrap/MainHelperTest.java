package com.moneydance.modules.features.importlist.bootstrap;

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;

import java.util.Observable;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class MainHelperTest {

    private MainHelper mainHelper;
    private TargetTestComponent testComponent;

    @Before
    public void setUp() {
        this.testComponent = DaggerTargetTestComponent.builder().build();
        this.mainHelper = this.testComponent.mainHelper();
        this.mainHelper.init(this.testComponent, (final Observable o, final Object arg) -> {
            // ignore
        });
    }

    @Test
    public void testUnload() {
        this.mainHelper.unload();
    }

    @Test
    public void testCleanup() {
        this.mainHelper.cleanup();
    }

    @Test
    public void testGetName() {
        assertThat(this.mainHelper.getName(), notNullValue());
    }

    @Test
    public void testGetIconImage() {
        assertThat(this.mainHelper.getIconImage(), notNullValue());
    }

    @Test
    public void testInvoke() {
        this.mainHelper.invoke(null);
    }

    @Test
    public void testUpdate() {
        this.mainHelper.update(null);
    }

    @Test
    public void testConstructor() {
        this.mainHelper = new MainHelper(this.testComponent.settings());
    }

    @Test
    public void testGetHomePageView() {
        assertThat(this.mainHelper.getViewController(), notNullValue());
    }
}
