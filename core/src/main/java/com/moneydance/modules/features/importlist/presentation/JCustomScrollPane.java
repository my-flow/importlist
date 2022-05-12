package com.moneydance.modules.features.importlist.presentation;

import javax.swing.JScrollPane;

/**
 * @author Florian J. Breunig
 */
final class JCustomScrollPane extends JScrollPane {

    private static final long serialVersionUID = 1L;

    @Override
    public void addNotify() {
        super.addNotify();
        if (this.getColumnHeader() != null) {
            this.getColumnHeader().setOpaque(false);
            this.getColumnHeader().setBackground(this.getBackground());
        }

        if (this.getViewport() != null) {
            this.getViewport().setOpaque(false);
            this.getViewport().setBackground(this.getBackground());
        }
    }
}
