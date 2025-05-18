package com.moneydance.modules.features.importlist.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Mock implementation of the LabelModifiedRenderer for testing.
 *
 * @author Florian J. Breunig
 */
public final class MockLabelModifiedRenderer extends JLabel implements TableCellRenderer {

    private static final long serialVersionUID = 1L;


    /** Gets the component for rendering a table cell. */
    @Override
    public Component getTableCellRendererComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {
        if (value == null) {
            setText("");
        } else {
            setText(value.toString());
        }
        return this;
    }

    /**
     * Sets the date formatter (mock implementation).
     *
     * @param dateFormatter The date formatter to set
     */
    public void setDateFormatter(final Object dateFormatter) {
        // Mock implementation, does nothing
    }

    /**
     * Sets the time formatter (mock implementation).
     *
     * @param timeFormatter The time formatter to set
     */
    public void setTimeFormatter(final Object timeFormatter) {
        // Mock implementation, does nothing
    }
}
