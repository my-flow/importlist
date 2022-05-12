package com.moneydance.modules.features.importlist.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author Florian J. Breunig
 */
public final class DirectoryChooserButtonFactory implements ComponentFactory {

    private final JPanel panel;

    public DirectoryChooserButtonFactory(
            final String text,
            final ActionListener argActionListener,
            final Border border,
            final Color backgroundColor,
            final Font font) {
        JButton chooserButton = new JButton(text);
        chooserButton.setBorderPainted(false);
        chooserButton.addActionListener(argActionListener);

        this.panel = new JPanel(new BorderLayout());
        this.panel.add(chooserButton);
        this.panel.setBorder(border);
        this.panel.setBackground(backgroundColor);
        this.panel.setFont(font);
    }

    @Override
    public JPanel getComponent() {
        return this.panel;
    }
}
