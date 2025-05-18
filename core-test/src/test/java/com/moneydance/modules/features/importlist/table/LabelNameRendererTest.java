package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.TestComponentFactory;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class LabelNameRendererTest {

    private TableCellRenderer labelNameRenderer;

    @Before
    public void setUp() {
        final CoreTestComponent testComponent = TestComponentFactory.createTestComponent();

        this.labelNameRenderer = testComponent.labelNameRenderer();
    }

    @Test
    public void testGetTableCellRendererComponent() {
        assertThat(
                this.labelNameRenderer.getTableCellRendererComponent(
                        new JTable(),
                        null, // null value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());

        assertThat(
                this.labelNameRenderer.getTableCellRendererComponent(
                        new JTable(),
                        "", // empty string value
                        false,
                        false,
                        0,
                        0),
                        notNullValue());
    }
}
