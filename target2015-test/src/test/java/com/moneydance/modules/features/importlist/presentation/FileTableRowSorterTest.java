package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.DaggerTargetTestComponent;
import com.moneydance.modules.features.importlist.TargetTestComponent;
import com.moneydance.modules.features.importlist.controller.FileTableModel;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class FileTableRowSorterTest {

    private FileTableRowSorter fileTableRowSorter;

    @Before
    public void setUp() {
        final TargetTestComponent testComponent = DaggerTargetTestComponent.builder().build();
        this.fileTableRowSorter = new FileTableRowSorter(
                new FileTableModel(
                        testComponent.fileContainer(),
                        testComponent.settings(),
                        testComponent.preferences()),
                testComponent.settings().getDescName(),
                testComponent.settings().getDescModified());
    }

    @Test
    public void testIsSortable() {
        this.fileTableRowSorter.isSortable(0);
        this.fileTableRowSorter.isSortable(1);
        this.fileTableRowSorter.isSortable(2);
    }

    @Test
    public void testGetComparatorIntExpected() {
        this.fileTableRowSorter.getComparator(0);
        this.fileTableRowSorter.getComparator(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComparatorIntUnexpected() {
        this.fileTableRowSorter.getComparator(2);
    }
}
