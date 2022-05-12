package com.moneydance.modules.features.importlist.controller;

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;
import com.moneydance.modules.features.importlist.io.FileContainer;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
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
    private FileTableModel fullModel;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = DaggerTargetTestComponent.builder().build();
        this.emptyModel = new FileTableModel(
                testComponent.fileContainer(),
                testComponent.settings(),
                testComponent.preferences());

        List<File> files = Collections.singletonList(new File(""));
        List<Long> lastModificationTimes = Collections.singletonList(0L);

        this.fullModel = new FileTableModel(
                new FileContainer(files, lastModificationTimes),
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

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueAtForFullModel() {
        assertThat(this.fullModel.getValueAt(0, 0), notNullValue());
        assertThat(this.fullModel.getValueAt(0, 1), notNullValue());
        assertThat(this.fullModel.getValueAt(0, 2), notNullValue());
        assertThat(this.fullModel.getValueAt(0, 3), notNullValue());
        this.fullModel.getValueAt(0, 4); // throw an expected exception
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
