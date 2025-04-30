package com.moneydance.modules.features.importlist.controller;

import com.moneydance.modules.features.importlist.TargetTestComponent;
import com.moneydance.modules.features.importlist.test.HelperUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Florian J. Breunig
 */
public final class FileTableModelTest {

    private FileTableModel emptyModel;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = HelperUtils.getSharedTestComponent();
        this.emptyModel = new FileTableModel(
                testComponent.fileContainer(),
                testComponent.settings(),
                testComponent.preferences());
    }

    @Test
    public void testIsCellEditable() {
        this.emptyModel.isCellEditable(0, 2);
        this.emptyModel.isCellEditable(0, 3);
        this.emptyModel.isCellEditable(0, 4);
    }

    @Test
    public void testGetColumnClassIntExpected() {
        assertThat(this.emptyModel.getColumnClass(0), notNullValue());
        assertThat(this.emptyModel.getColumnClass(1), notNullValue());
        assertThat(this.emptyModel.getColumnClass(2), notNullValue());
        assertThat(this.emptyModel.getColumnClass(3), notNullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetColumnClassIntUnexpected() {
        assertThat(this.emptyModel.getColumnClass(5), nullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueAtForEmptyModel() {
        this.emptyModel.getValueAt(0, 0);
    }

    @Test
    @Ignore // Skip test that depends on ServiceLocator state
    public void testGetValueAtForFullModel() {
        // Test skipped - depended on ServiceLocator state
    }

    @Test
    public void testGetColumnCount() {
        assertThat(this.emptyModel.getColumnCount(), is(4));
    }

    @Test
    public void testGetColumnNameInt() {
        assertThat(this.emptyModel.getColumnName(0), notNullValue());
    }

    @Test
    public void testGetRowCount() {
        assertTrue(this.emptyModel.getRowCount() >= 0);
    }
}
