package com.moneydance.modules.features.importlist.controller;

import com.infinitekind.util.StreamTable;
import com.moneydance.apps.md.controller.StubContext;
import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.modules.features.importlist.Main;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class ContextImplTest {

    private static final String MODULE_ID = "importlist-test";

    private StubContext stubContext;
    private UserPreferences userPreferences;
    @Nullable private StreamTable previousModules;
    private Context context;

    @Before
    public void setUp() {
        final Main featureModule = new Main();
        final StubContextFactory factory = new StubContextFactory(featureModule);
        factory.init(MODULE_ID);
        this.stubContext = Objects.requireNonNull(factory.getContext());
        this.userPreferences = this.stubContext.getPreferences();
        final StreamTable modules = this.userPreferences.getTableSetting(UserPreferences.GEN_FMODULE_LIST, null);
        if (modules != null) {
            this.previousModules = new StreamTable();
            this.previousModules.putAll(modules);
        }
        this.context = new ContextImpl(featureModule, this.stubContext);
    }

    @After
    public void tearDown() {
        this.userPreferences.setSetting(UserPreferences.GEN_FMODULE_LIST, this.previousModules);
    }

    @Test
    public void testIsModuleInstalled() {
        this.stubContext.addModule(MODULE_ID);
        assertThat(this.context.isModuleInstalled(), is(true));

        this.stubContext.removeModule(MODULE_ID);
        assertThat(this.context.isModuleInstalled(), is(false));
    }

    @Test
    public void testInvokeAfterModuleOperation() throws InterruptedException {
        final AtomicBoolean installed = new AtomicBoolean(true);
        final CountDownLatch latch = new CountDownLatch(1);
        this.stubContext.addModule(MODULE_ID);
        // simulate Moneydance's uninstallModule(), which holds the lock on Main
        synchronized (this.stubContext) {
            this.context.invokeAfterModuleOperation(() -> {
                installed.set(this.context.isModuleInstalled());
                latch.countDown();
            });
            assertThat(latch.await(200, TimeUnit.MILLISECONDS), is(false));
            this.stubContext.removeModule(MODULE_ID);
        }
        assertThat(latch.await(10, TimeUnit.SECONDS), is(true));
        assertThat(installed.get(), is(false));
    }
}
