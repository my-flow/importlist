// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2016 Florian J. Breunig
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

import com.moneydance.modules.features.importlist.io.FileAdmin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import org.apache.commons.lang3.Validate;

/**
 * @author Florian J. Breunig
 */
public abstract class AbstractEditor extends DefaultCellEditor {

    private static final long serialVersionUID = 1L;
    private final transient FileAdmin       fileAdmin;
    private final transient ButtonRenderer  buttonRenderer;
    private                 String          label;

    public abstract ActionListener getActionListener(final int rowNumber);

    public abstract KeyStroke getKeyStroke();

    protected AbstractEditor(
            final FileAdmin argFileAdmin,
            final ButtonRenderer argButtonRenderer) {
        super(new JCheckBox());
        Validate.notNull(
                argFileAdmin,
                "file admin must not be null"); //$NON-NLS-1$
        Validate.notNull(
                argButtonRenderer,
                "button renderer must not be null");
        this.fileAdmin      = argFileAdmin;
        this.buttonRenderer = argButtonRenderer;
    }

    protected final FileAdmin getFileAdmin() {
        return this.fileAdmin;
    }

    @Override
    public final Object getCellEditorValue() {
        return this.label;
    }

    @Override
    public final Component getTableCellEditorComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final int row,
            final int column) {
        if (value != null) {
            this.label = value.toString();
        }

        AbstractButton button = this.buttonRenderer.getTableCellRendererButton(
                value, row);
        ActionListener actionListener = this.getActionListener(
                table.convertRowIndexToModel(row));
        button.addActionListener(actionListener);

        return button;
    }


    public final void registerKeyboardShortcut(final JComponent jComponent) {
        Validate.notNull(jComponent, "jComponent must not be null");
        if (this.getKeyStroke() == null) {
            return;
        }

        final Action action = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                ActionListener actionListener =
                        AbstractEditor.this.getActionListener(0);
                actionListener.actionPerformed(actionEvent);
            }
        };

        final String actionMapKey = this.getClass().getName(); // unique
        jComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                this.getKeyStroke(),
                actionMapKey);
        jComponent.getActionMap().put(actionMapKey, action);
    }
}
