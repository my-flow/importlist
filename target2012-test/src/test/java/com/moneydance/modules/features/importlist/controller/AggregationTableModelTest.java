package com.moneydance.modules.features.importlist.controller;

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class AggregationTableModelTest {

    private AggregationTableModel aggregationTableModel;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = DaggerTargetTestComponent.builder().build();
        this.aggregationTableModel = new AggregationTableModel(
                testComponent.settings(),
                testComponent.preferences());
    }

    @Test
    public void testIsCellEditable() {
        this.aggregationTableModel.isCellEditable(0, 2);
        this.aggregationTableModel.isCellEditable(0, 3);
        this.aggregationTableModel.isCellEditable(0, 4);
    }

    @Test
    public void testGetColumnClassInt() {
        assertThat(this.aggregationTableModel.getColumnClass(0), notNullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueAt() {
        assertThat(this.aggregationTableModel.getValueAt(0, 0), nullValue());
        assertThat(this.aggregationTableModel.getValueAt(0, 1), nullValue());
        assertThat(this.aggregationTableModel.getValueAt(0, 2), notNullValue());
        assertThat(this.aggregationTableModel.getValueAt(0, 3), notNullValue());
        this.aggregationTableModel.getValueAt(0, 4);
        // throw an expected exception
    }

    @Test
    public void testGetColumnCount() {
        assertThat(this.aggregationTableModel.getColumnCount(), is(4));
    }

    @Test
    public void testGetColumnNameInt() {
        assertThat(this.aggregationTableModel.getColumnName(0), notNullValue());
    }

    @Test
    public void testGetRowCount() {
        assertThat(this.aggregationTableModel.getRowCount(), is(1));
    }
}
