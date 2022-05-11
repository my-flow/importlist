package com.moneydance.modules.features.importlist.table;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class ButtonRendererTest {

    private ButtonRenderer buttonRenderer;

    @Before
    public void setUp() {
        this.buttonRenderer = new ButtonRenderer(new ColorSchemeMock());
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
        assertNotEquals(mouseListeners.length, 0);

        MouseListener mouseListener = mouseListeners[0];
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
