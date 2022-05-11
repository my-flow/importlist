package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.event.ActionListener;

/**
 * @author Florian J. Breunig
 */
final class NullEditorStub extends AbstractEditor {

    private static final long serialVersionUID = 1;

    NullEditorStub(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer,
            final Settings settings) {
        super(fileAdmin, buttonRenderer, settings.getKeyboardShortcutImport());
    }

    @Override
    public ActionListener getActionListener(final int rowNumber) {
        return null;
    }
}
