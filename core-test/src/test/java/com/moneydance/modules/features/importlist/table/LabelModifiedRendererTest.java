package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import java.util.Date;

import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class LabelModifiedRendererTest {

    private LabelModifiedRenderer labelModifiedRenderer;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();

        this.labelModifiedRenderer = testComponent.labelModifiedRenderer();
    }

    @Test
    public void testGetTableCellRendererComponent() {
        assertThat(
                this.labelModifiedRenderer.getTableCellRendererComponent(
                        new JTable(),
                        null, // null value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());

        assertThat(
                this.labelModifiedRenderer.getTableCellRendererComponent(
                        new JTable(),
                        new Date(), // date value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());

        assertThat(
                this.labelModifiedRenderer.getTableCellRendererComponent(
                        new JTable(),
                        "", // string value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());
    }

    @Test
    public void testSetDateFormatter() {
        this.labelModifiedRenderer.setDateFormatter(null);
    }

    @Test
    public void testSetTimeFormatter() {
        this.labelModifiedRenderer.setTimeFormatter(null);
    }

}
