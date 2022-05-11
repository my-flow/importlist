package com.moneydance.modules.features.importlist.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;

import org.apache.commons.lang3.Validate;

/**
 * This helper class applies a given color scheme to a <code>Component</code>.
 * It is used by different <code>TableCellRenderer</code>s to guarantee a
 * consistent style in a table.
 *
 * @author Florian J. Breunig
 */
public final class EvenColorSchemeImpl implements ColorScheme {

    private static final long serialVersionUID = 1L;

    private Color foreground;
    private Color background;
    private Color backgroundAlt;

    public EvenColorSchemeImpl(
            final Color argForeground,
            final Color argBackground,
            final Color argBackgroundAlt) {
        this.setForeground(argForeground);
        this.setBackground(argBackground);
        this.setBackgroundAlt(argBackgroundAlt);
    }

    @Override
    public void applyColorScheme(final Component component, final int row) {
        component.setForeground(this.foreground);
        component.setBackground(this.background);
        if (row % 2 == 0) {
            if (component instanceof JComponent) {
                JComponent jComponent = (JComponent) component;
                jComponent.setOpaque(true);
            }
            component.setBackground(this.backgroundAlt);
        }
    }

    @Override
    public void setForeground(final Color argForeground) {
        Validate.notNull(argForeground, "argForeground must not be null");
        this.foreground = argForeground;
    }

    @Override
    public void setBackground(final Color argBackground) {
        Validate.notNull(argBackground, "argBackground must not be null");
        this.background = argBackground;
    }

    @Override
    public void setBackgroundAlt(final Color argBackgroundAlt) {
        Validate.notNull(argBackgroundAlt, "argBackgroundAlt must not be null");
        this.backgroundAlt = argBackgroundAlt;
    }
}
