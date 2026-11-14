package com.moneydance.modules.features.importlist.bootstrap;

import com.moneydance.modules.features.importlist.TargetTestComponent;
import com.moneydance.modules.features.importlist.controller.ContextMock;
import com.moneydance.modules.features.importlist.test.HelperUtils;
import com.moneydance.modules.features.importlist.util.PreferencesMock;

import java.io.File;
import java.util.Observable;
import java.util.Optional;

import javax.swing.RowSorter;
import javax.swing.SortOrder;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class MainHelperTest {

    private static final File BASE_DIRECTORY = new File("base");
    private static final RowSorter.SortKey SORT_KEY = new RowSorter.SortKey(1, SortOrder.DESCENDING);

    private MainHelper mainHelper;
    private TargetTestComponent testComponent;

    @Before
    public void setUp() {
        this.testComponent = HelperUtils.createTestComponent();
        this.mainHelper = this.testComponent.mainHelper();
        this.mainHelper.init(this.testComponent, (final Observable o, final Object arg) -> {
            // ignore
        });
    }

    @Test
    public void testUnloadOnUpdateKeepsSettings() {
        final PreferencesMock prefs = this.changeSettings();
        final ContextMock context = (ContextMock) this.testComponent.context();
        context.setModuleInstalled(true);
        this.mainHelper.unload();
        assertThat(prefs.isFirstRun(), is(false));
        assertThat(prefs.getBaseDirectory(), is(Optional.of(BASE_DIRECTORY)));
        assertThat(prefs.getSortKey(), is(SORT_KEY));
        assertThat(context.isPreferencesSaved(), is(false));
    }

    @Test
    public void testUnloadOnUninstallClearsSettings() {
        final PreferencesMock prefs = this.changeSettings();
        final ContextMock context = (ContextMock) this.testComponent.context();
        context.setModuleInstalled(false);
        this.mainHelper.unload();
        assertThat(prefs.isFirstRun(), is(true));
        assertThat(prefs.getBaseDirectory(), is(Optional.empty()));
        assertThat(prefs.getSortKey(), is(not(SORT_KEY)));
        assertThat(context.isPreferencesSaved(), is(true));
    }

    private PreferencesMock changeSettings() {
        final PreferencesMock prefs = (PreferencesMock) this.testComponent.preferences();
        assertThat(prefs.isFirstRun(), is(false));
        prefs.setBaseDirectory(BASE_DIRECTORY);
        prefs.setSortKey(SORT_KEY);
        return prefs;
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
        this.mainHelper.update(this.testComponent);
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
