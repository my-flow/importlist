package com.moneydance.modules.features.importlist.view;

import java.awt.Color;
import java.awt.Component;

/**
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
public final class ColorSchemeHelper {

    private static final long serialVersionUID = -2724848115953702911L;
    private static Color foregroundColor;
    private static Color backgroundColor;
    private static Color backgroundColorAlt;

    /**
     * Private constructor prevents this class from being instantiated.
     */
    private ColorSchemeHelper() {
    }

    static void applyColorScheme(
            final Component component,
            final int row) {
        component.setForeground(foregroundColor);
        component.setBackground(backgroundColorAlt);
        if (row % 2 != 0) {
            component.setBackground(backgroundColor);
        }
    }

    static void setForegroundColor(
            final Color argForegroundColor) {
        foregroundColor = argForegroundColor;
    }

    static void setBackgroundColor(
            final Color argBackgroundColor) {
        backgroundColor = argBackgroundColor;
    }

    static void setBackgroundColorAlt(
            final Color argBackgroundColorAlt) {
        backgroundColorAlt = argBackgroundColorAlt;
    }
}
