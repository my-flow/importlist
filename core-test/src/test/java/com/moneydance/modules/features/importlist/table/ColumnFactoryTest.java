package com.moneydance.modules.features.importlist.table;

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
public final class ColumnFactoryTest {

    private ColumnFactory columnFactory;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();

        this.columnFactory = testComponent.columnFactory();
    }

    @Test
    public void testGetHeaderRenderer() {
        assertThat(this.columnFactory.getHeaderRenderer(), notNullValue());
    }

    @Test
    public void testGetLabelNameOneRenderer() {
        assertThat(this.columnFactory.getLabelNameOneRenderer(), notNullValue());
    }

    @Test
    public void testGetLabelNameAllRenderer() {
        assertThat(this.columnFactory.getLabelNameAllRenderer(), notNullValue());
    }

    @Test
    public void testGetLabelModifiedOneRenderer() {
        assertThat(this.columnFactory.getLabelModifiedOneRenderer(), notNullValue());
    }

    @Test
    public void testGetLabelModifiedAllRenderer() {
        assertThat(this.columnFactory.getLabelModifiedAllRenderer(), notNullValue());
    }

    @Test
    public void testGetButtonOneRenderer() {
        assertThat(this.columnFactory.getButtonOneRenderer(), notNullValue());
    }

    @Test
    public void testGetButtonAllRenderer() {
        assertThat(this.columnFactory.getButtonAllRenderer(), notNullValue());
    }

    @Test
    public void testGetImportOneEditor() {
        assertThat(this.columnFactory.getImportOneEditor(), notNullValue());
    }

    @Test
    public void testGetImportAllEditor() {
        assertThat(this.columnFactory.getImportAllEditor(), notNullValue());
    }

    @Test
    public void testGetDeleteOneEditor() {
        assertThat(this.columnFactory.getDeleteOneEditor(), notNullValue());
    }

    @Test
    public void testGetDeleteAllEditor() {
        assertThat(this.columnFactory.getDeleteAllEditor(), notNullValue());
    }

    @Test
    public void testSetDateFormatter() {
        this.columnFactory.setDateFormatter(null);
    }

    @Test
    public void testSetTimeFormatter() {
        this.columnFactory.setTimeFormatter(null);
    }

}
