package com.moneydance.modules.features.importlist.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A mock implementation of AbstractEditor for testing purposes.
 *
 * @author Florian J. Breunig
 */
public final class MockAbstractEditor extends AbstractEditor {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a mock AbstractEditor.
     */
    public MockAbstractEditor() {
        super(null, new ButtonRenderer(new MockColorScheme()), "");
    }

    /**
     * Returns a mock action listener for the given row.
     *
     * @param rowNumber The row number for the action
     * @return A mock action listener
     */
    @Override
    public ActionListener getActionListener(final int rowNumber) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                // Mock implementation does nothing
                java.util.logging.Logger logger =
                        java.util.logging.Logger.getLogger(MockAbstractEditor.class.getName());
                if (logger.isLoggable(java.util.logging.Level.FINE)) {
                    logger.fine("MockAbstractEditor action performed for row: " + rowNumber);
                }
            }
        };
    }
}
