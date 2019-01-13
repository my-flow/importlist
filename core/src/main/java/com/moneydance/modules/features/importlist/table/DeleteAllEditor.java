// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2019 Florian J. Breunig
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

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.inject.Inject;

/**
 * @author Florian J. Breunig
 */
final class DeleteAllEditor extends AbstractEditor {

    private static final long serialVersionUID = 1L;

    @Inject DeleteAllEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer,
            final String keyboardShortcut) {
        super(fileAdmin, buttonRenderer, keyboardShortcut);
    }

    @Override
    public ActionListener getActionListener(final int rowNumber) {
        return actionEvent -> EventQueue.invokeLater(() -> this.getFileAdmin().deleteAllRows());
    }
}
