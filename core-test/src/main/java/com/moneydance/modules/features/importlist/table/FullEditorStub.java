// Import List - https://www.my-flow.com/importlist/
// Copyright (C) 2011-2021 Florian J. Breunig
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
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Florian J. Breunig
 */
final class FullEditorStub extends AbstractEditor {

    private static final long serialVersionUID = 5291937749895264108L;

    FullEditorStub(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer,
            final Settings settings) {
        super(fileAdmin, buttonRenderer, settings.getKeyboardShortcutImport());
    }

    @Override
    public ActionListener getActionListener(final int rowNumber) {
        return new StubActionListener();
    }

    /**
     * @author Florian J. Breunig
     */
    private static final class StubActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            // do nothing
        }
    }
}
