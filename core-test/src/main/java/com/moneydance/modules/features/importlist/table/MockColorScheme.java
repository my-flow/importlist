package com.moneydance.modules.features.importlist.table;

import java.awt.Color;
import java.awt.Component;

/**
 * A mock implementation of ColorScheme for testing purposes.
 *
 * @author Florian J. Breunig
 */
public final class MockColorScheme implements ColorScheme {

    private static final long serialVersionUID = 1L;
    private Color foreground = Color.BLACK;
    private Color background = Color.WHITE;
    private Color backgroundAlt = Color.LIGHT_GRAY;

    /**
     * Applies color scheme to the given component based on row number.
     *
     * @param component The component to apply colors to
     * @param row The row number to determine which colors to apply
     */
    @Override
    public void applyColorScheme(final Component component, final int row) {
        if (component == null) {
            return;
        }
        if (row % 2 == 0) {
            component.setBackground(this.background);
        } else {
            component.setBackground(this.backgroundAlt);
        }
        component.setForeground(this.foreground);
    }

    /**
     * Sets the foreground color.
     *
     * @param argForeground The foreground color to set
     */
    @Override
    public void setForeground(final Color argForeground) {
        this.foreground = argForeground;
    }

    /**
     * Sets the background color.
     *
     * @param argBackground The background color to set
     */
    @Override
    public void setBackground(final Color argBackground) {
        this.background = argBackground;
    }

    /**
     * Sets the alternating background color.
     *
     * @param argBackgroundAlt The alternating background color to set
     */
    @Override
    public void setBackgroundAlt(final Color argBackgroundAlt) {
        this.backgroundAlt = argBackgroundAlt;
    }
}
