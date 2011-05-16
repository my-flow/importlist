package com.moneydance.modules.features.importlist.view;

import javax.swing.JScrollPane;

/**
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
class JCustomScrollPane extends JScrollPane {

    private static final long serialVersionUID = -2741147624999364681L;

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
