package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;


/**
 * @author Florian J. Breunig
 */
public class FileContainerTest {

    private FileContainer fileContainer;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();
        this.fileContainer = testComponent.fileContainer();
        final File basedir = new File(String.format("%s%s%s%s%s",
                "src", File.separator,
                "test", File.separator,
                "resources"));
        this.fileContainer.reloadFiles(basedir);
    }

    @Test
    public void testGet() {
        assertThat(this.fileContainer.get(0), notNullValue());
    }

    @Test
    public void testSize() {
        assertThat(this.fileContainer.size(), greaterThan(0));
    }

    @Test
    public void testIsEmpty() {
        assertThat(this.fileContainer.isEmpty(), is(false));
    }

    @Test
    public void testGetFileName() {
        assertThat(this.fileContainer.getFileName(0), notNullValue());
    }

    @Test
    public void testGetLastModifiedTime() {
        assertThat(this.fileContainer.getLastModifiedTime(0), notNullValue());
    }
}
