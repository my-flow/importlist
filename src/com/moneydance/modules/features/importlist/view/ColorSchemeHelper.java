package com.moneydance.modules.features.importlist.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;

/**
 * This helper class applies a given color scheme to a <code>Component</code>.
 * It is used by different <code>TableCellRenderer</code>s to guarantee a
 * consistent style in a table.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
final class ColorSchemeHelper {

    private static final long serialVersionUID = -2724848115953702911L;
    private static Color foreground;
    private static Color background;
    private static Color backgroundAlt;

    /**
     * Private constructor prevents this class from being instantiated.
     */
    private ColorSchemeHelper() {
    }

    static void applyColorScheme(
            final Component component,
            final int row) {
        component.setForeground(foreground);
        component.setBackground(background);
        if (row % 2 == 0) {
            if (component instanceof JComponent) {
                JComponent jComponent = (JComponent) component;
                jComponent.setOpaque(true);
            }
            component.setBackground(backgroundAlt);
        }
    }

    static void setForeground(final Color argForeground) {
        foreground = argForeground;
    }

    static void setBackground(final Color argBackground) {
        background = argBackground;
    }

    static void setBackgroundAlt(final Color argBackgroundAlt) {
        backgroundAlt = argBackgroundAlt;
    }
}
