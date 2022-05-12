package com.moneydance.modules.features.importlist.table;

import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;

/**
 * This template class defines the abstract methods required to apply a color
 * scheme to the components of the GUI framework.
 *
 * @author Florian J. Breunig
 */
public interface ColorScheme extends Serializable {

    void applyColorScheme(Component component, int row);

    void setForeground(Color argForeground);

    void setBackground(Color argBackground);

    void setBackgroundAlt(Color argBackgroundAlt);
}
