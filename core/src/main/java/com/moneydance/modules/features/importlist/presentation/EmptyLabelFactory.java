package com.moneydance.modules.features.importlist.presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author Florian J. Breunig
 */
public final class EmptyLabelFactory implements ComponentFactory {

    private final JPanel emptyPanel;

    public EmptyLabelFactory(
            final String text,
            final Border border,
            final Color backgroundColor,
            final Font font) {
        this.emptyPanel = new JPanel(new GridBagLayout());

        final JLabel emptyLabel = new JLabel(text);
        emptyLabel.setLabelFor(this.emptyPanel);

        this.emptyPanel.add(emptyLabel);
        this.emptyPanel.setBorder(border);
        this.emptyPanel.setBackground(backgroundColor);
        this.emptyPanel.setFont(font);
    }

    @Override
    public JPanel getComponent() {
        return this.emptyPanel;
    }
}
