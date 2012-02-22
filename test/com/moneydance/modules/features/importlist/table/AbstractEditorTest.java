/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.moneydance.modules.features.importlist.table;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.view.OddColorSchemeHelper;

/**
 * @author Florian J. Breunig
 */
public final class AbstractEditorTest {

    private AbstractEditor nullStubEditor;
    private AbstractEditor fullStubEditor;

    @Before
    public void setUp() {
        StubContextFactory factory = new StubContextFactory();
        this.nullStubEditor = new NullStubEditor(
                new FileAdmin(null, factory.getContext()),
                new ButtonRenderer(new OddColorSchemeHelper(
                        Color.white,
                        Color.white,
                        Color.white)));
        this.fullStubEditor = new FullStubEditor(
                new FileAdmin(null, factory.getContext()),
                new ButtonRenderer(new OddColorSchemeHelper(
                        Color.white,
                        Color.white,
                        Color.white)));
    }

    @Test
    public void testGetCellEditorValue() {
        Assert.assertNull("cell editor value must be null",
                this.nullStubEditor.getCellEditorValue());
    }

    @Test
    public void testGetTableCellEditorComponentJTableObjectBooleanIntInt() {
        Assert.assertNotNull(
                "component must not be null",
                this.nullStubEditor.getTableCellEditorComponent(
                        new JTable(),
                        "",
                        false,
                        0,
                        0));

        Assert.assertNotNull(
                "component must not be null",
                this.fullStubEditor.getTableCellEditorComponent(
                        new JTable(),
                        null,
                        true,
                        1,
                        1));
    }

    @Test
    public void testRegisterKeyboardShortcut() {
        final JLabel label = new JLabel();

        this.nullStubEditor.registerKeyboardShortcut(label);

        this.fullStubEditor.registerKeyboardShortcut(label);
        final Object key = label.getActionMap().allKeys()[0];
        Assert.assertNotNull("key in action map must not be null", key);

        final Action action = label.getActionMap().get(key);
        Assert.assertNotNull("action value must not be null", action);

        action.actionPerformed(null);
    }

    private static class NullStubEditor extends AbstractEditor {

        private static final long serialVersionUID = 5291937749895264108L;

        NullStubEditor(
                final FileAdmin argFileAdmin,
                final ButtonRenderer argButtonRenderer) {
            super(argFileAdmin, argButtonRenderer);
        }

        @Override
        public KeyStroke getKeyStroke() {
            return null;
        }

        @Override
        public ActionListener getActionListener(final int rowNumber) {
            return null;
        }
    }


    private static class FullStubEditor extends AbstractEditor {

        private static final long serialVersionUID = 5291937749895264108L;

        FullStubEditor(
                final FileAdmin argFileAdmin,
                final ButtonRenderer argButtonRenderer) {
            super(argFileAdmin, argButtonRenderer);
        }

        @Override
        public KeyStroke getKeyStroke() {
            return KeyStroke.getKeyStroke('x');
        }

        @Override
        public ActionListener getActionListener(final int rowNumber) {
            return new StubActionListener();
        }

        private static class StubActionListener implements ActionListener {

            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                // do nothing
            }
        }
    }
}
