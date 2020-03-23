// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2020 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

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
