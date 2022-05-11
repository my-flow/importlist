package com.moneydance.modules.features.importlist.table;

import java.awt.Color;
import java.awt.Component;

import org.apache.commons.lang3.Validate;

/**
 * This helper class applies a given color scheme to a <code>Component</code>.
 * It is used by different <code>TableCellRenderer</code>s to guarantee a
 * consistent style in a table.
 *
 * @author Florian J. Breunig
 */
public final class ColorSchemeImpl implements ColorScheme {

    private static final long serialVersionUID = 1L;

    private Color color;

    public ColorSchemeImpl(final Color argColor) {
        this.setForeground(argColor);
        this.setBackground(argColor);
    }

    @Override
    public void applyColorScheme(final Component component, final int row) {
        component.setForeground(this.color);
        component.setBackground(this.color);
    }

    @Override
    public void setForeground(final Color argForeground) {
        Validate.notNull(argForeground, "argForeground must not be null");
        this.color = argForeground;
    }

    @Override
    public void setBackground(final Color argBackground) {
        // no manual setting required
    }

    @Override
    public void setBackgroundAlt(final Color argBackgroundAlt) {
        // no manual setting required
    }
}
