// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2018 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.table;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class ButtonRendererTest {

    private ButtonRenderer buttonRenderer;

    @Before
    public void setUp() {
        this.buttonRenderer = new ButtonRenderer();
    }

    @Test
    public void testGetTableCellRendererComponent() {
        assertThat(
                this.buttonRenderer.getTableCellRendererComponent(
                        new JTable(),
                        null,
                        false,
                        false,
                        0,
                        0),
                        notNullValue());
    }

    @Test
    public void testGetTableCellRendererButton() {
        AbstractButton button = this.buttonRenderer.getTableCellRendererButton(
                "",
                1);
        assertThat(button, notNullValue());

        button.setForeground(Color.white);
        button.setBackground(Color.white);
        MouseListener[] mouseListeners = button.getMouseListeners();
        assertThat(mouseListeners, notNullValue());
        MouseListener mouseListener = mouseListeners[1];
        MouseEvent mouseEvent = new MouseEvent(button, 0, 0, 0, 0, 0, 0, false);
        mouseListener.mousePressed(mouseEvent);
        mouseListener.mouseReleased(mouseEvent);

        button.setForeground(Color.white);
        button.setBackground(Color.black);
        mouseListener.mousePressed(mouseEvent);
        mouseListener.mouseReleased(mouseEvent);

        button.setForeground(Color.black);
        button.setBackground(Color.white);
        mouseListener.mousePressed(mouseEvent);
        mouseListener.mouseReleased(mouseEvent);

        button.setForeground(Color.black);
        button.setBackground(Color.black);
        mouseListener.mousePressed(mouseEvent);
        mouseListener.mouseReleased(mouseEvent);
    }
}
