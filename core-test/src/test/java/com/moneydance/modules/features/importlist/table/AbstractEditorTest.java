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

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class AbstractEditorTest {

    private AbstractEditor nullStubEditor;
    private AbstractEditor fullStubEditor;

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();

        this.nullStubEditor = new NullEditorStub(
                testComponent.fileAdmin(),
                new ButtonRenderer(),
                testComponent.settings());
        this.fullStubEditor = new FullEditorStub(
                testComponent.fileAdmin(),
                new ButtonRenderer(),
                testComponent.settings());
    }

    @Test
    public void testGetCellEditorValue() {
        assertThat(this.nullStubEditor.getCellEditorValue(), nullValue());
    }

    @Test
    public void testGetTableCellEditorComponent() {
        assertThat(
                this.nullStubEditor.getTableCellEditorComponent(
                        new JTable(),
                        "",
                        false,
                        0,
                        0),
                        notNullValue());

        assertThat(
                this.fullStubEditor.getTableCellEditorComponent(
                        new JTable(),
                        null,
                        true,
                        1,
                        1),
                        notNullValue());
    }

    @Test
    public void testRegisterKeyboardShortcutNull() {
        final JLabel label = new JLabel();
        this.nullStubEditor.registerKeyboardShortcut(label);

        final Object key = label.getActionMap().allKeys()[0];
        assertThat(key, notNullValue());

        final Action action = label.getActionMap().get(key);
        assertThat(action, notNullValue());
    }

    @Test
    public void testRegisterKeyboardShortcutFull() {
        final JLabel label = new JLabel();
        this.fullStubEditor.registerKeyboardShortcut(label);

        final Object key = label.getActionMap().allKeys()[0];
        assertThat(key, notNullValue());

        final Action action = label.getActionMap().get(key);
        assertThat(action, notNullValue());

        action.actionPerformed(null);
    }
}
