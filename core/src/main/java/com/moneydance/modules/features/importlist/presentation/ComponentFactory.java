package com.moneydance.modules.features.importlist.presentation;

import javax.swing.JComponent;

/**
 * @author Florian J. Breunig
 */
@FunctionalInterface
public interface ComponentFactory {

    JComponent getComponent();
}
