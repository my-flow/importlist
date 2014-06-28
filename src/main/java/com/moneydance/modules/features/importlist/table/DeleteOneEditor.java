// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2014 Florian J. Breunig
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
import com.moneydance.modules.features.importlist.util.Helper;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.KeyStroke;

/**
 * @author Florian J. Breunig
 */
final class DeleteOneEditor extends AbstractEditor {

    private static final long serialVersionUID = 1L;

    DeleteOneEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        super(fileAdmin, buttonRenderer);
    }

    @Override
    public ActionListener getActionListener(final int rowNumber) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        DeleteOneEditor.this.getFileAdmin()
                        .deleteRow(rowNumber);
                    }
                });
            }
        };
    }

    @Override
    public KeyStroke getKeyStroke() {
        return KeyStroke.getKeyStroke(
                Helper.INSTANCE.getSettings().getKeyboardShortcutDelete());
    }
}
