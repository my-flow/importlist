package com.moneydance.modules.features.importlist.io;

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
public final class FileAdminTest {

    private FileAdmin fileAdmin;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();
        this.fileAdmin = testComponent.fileAdmin();
    }

    @Test
    public void testCheckValidBaseDirectory() {
        this.fileAdmin.checkValidBaseDirectory();
    }

    @Test
    public void testGetFileContainer() {
        assertThat(this.fileAdmin.getFileContainer(), notNullValue());
    }

    @Test
    public void testGetBaseDirectory() {
        assertThat(this.fileAdmin.getBaseDirectory(), notNullValue());
    }

    @Test
    public void testUpdate() {
        this.fileAdmin.update(null, null);
    }

    @Test
    public void testStartMonitor() {
        this.fileAdmin.startMonitor();
        this.fileAdmin.startMonitor(); // fail the second time
    }

    @Test
    public void testStopMonitor() {
        this.fileAdmin.stopMonitor(); // monitor not started yet

        this.fileAdmin.startMonitor();
        this.fileAdmin.stopMonitor(); // shut down started monitor
    }

    @Test
    public void testImportAllRows() {
        this.fileAdmin.importAllRows();

        this.fileAdmin.reloadFiles();
        this.fileAdmin.importAllRows();
    }

    @Test
    public void testImportRow() {
        this.fileAdmin.importRow(0);

        this.fileAdmin.reloadFiles();
        this.fileAdmin.importRow(0);
        this.fileAdmin.importRow(Integer.MAX_VALUE);
    }
}
