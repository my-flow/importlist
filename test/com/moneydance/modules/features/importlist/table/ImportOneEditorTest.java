/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2013 Florian J. Breunig
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
import java.awt.event.ActionListener;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.view.OddColorSchemeHelper;

/**
 * @author Florian J. Breunig
 */
public final class ImportOneEditorTest {

    private AbstractEditor importOneEditor;

    @Before
    public void setUp() {
        StubContextFactory factory = new StubContextFactory();
        this.importOneEditor = new ImportOneEditor(
                new FileAdmin(null, factory.getContext()),
                new ButtonRenderer(new OddColorSchemeHelper(
                        Color.white,
                        Color.white,
                        Color.white)));
    }

    @Test
    public void testGetActionListener() {
        ActionListener actionListener =
                this.importOneEditor.getActionListener(0);
        Assert.assertNotNull("action listener must not be null",
                actionListener);
        actionListener.actionPerformed(null);
    }

    @Test
    public void testGetKeyStroke() {
        Assert.assertNotNull("key stroke must not be null",
                this.importOneEditor.getKeyStroke());
    }
}
