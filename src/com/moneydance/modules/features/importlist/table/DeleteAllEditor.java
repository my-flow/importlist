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

package com.moneydance.modules.features.importlist.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.moneydance.modules.features.importlist.io.FileAdmin;

final class DeleteAllEditor extends AbstractEditor {

    private static final long serialVersionUID = 4632443623178927295L;

    DeleteAllEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer) {
        super(fileAdmin, buttonRenderer);
    }

    @Override
    ActionListener getActionListener(final int rowNumber) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                DeleteAllEditor.this.getFileAdmin().deleteAllRows();
            }
        };
    }
}
