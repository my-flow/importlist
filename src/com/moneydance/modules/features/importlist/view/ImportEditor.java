/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.moneydance.modules.features.importlist.io.FileAdmin;

final class ImportEditor extends DefaultCellEditor {

    private static final long serialVersionUID = 4286061728240721119L;
    private final transient FileAdmin       fileAdmin;
    private final transient ButtonRenderer  buttonRenderer;
    private                 String          label;

    ImportEditor(final FileAdmin argFileAdmin,
            final ButtonRenderer argButtonRenderer) {
        super(new JCheckBox());
        this.fileAdmin      = argFileAdmin;
        this.buttonRenderer = argButtonRenderer;
    }

    @Override
    public Object getCellEditorValue() {
        return this.label;
    }

    @Override
    public Component getTableCellEditorComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final int row,
            final int column) {
        if (value != null) {
            this.label = value.toString();
        }

        AbstractButton button = this.buttonRenderer.getTableCellRendererButton(
                table, value, isSelected, isSelected, row, column);
        ActionListener actionListener = this.getImportActionListener(
                table.convertRowIndexToModel(row));
        button.addActionListener(actionListener);

        return button;
    }

    private ActionListener getImportActionListener(final int rowNumber) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                ImportEditor.this.fileAdmin.importRow(rowNumber);
            }
        };
    }
}
