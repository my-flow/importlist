package com.moneydance.modules.features.importlist.presentation;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

/**
 * @author Florian J. Breunig
 */
final class JCustomSplitPane extends JSplitPane {

    private static final long serialVersionUID = 1L;

    JCustomSplitPane(final int newOrientation) {
        super(newOrientation);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (this.getTopComponent() != null) {
            ((JComponent) this.getTopComponent()).setOpaque(false);
            this.getTopComponent().setBackground(this.getBackground());
        }

        if (this.getBottomComponent() != null) {
            ((JComponent) this.getBottomComponent()).setOpaque(false);
            this.getBottomComponent().setBackground(this.getBackground());
        }
    }
}
