package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.io.FileAdmin;

import java.awt.event.ActionListener;

/**
 * @author Florian J. Breunig
 */
final class ImportOneEditor extends AbstractEditor {

    private static final long serialVersionUID = 1L;

    ImportOneEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer,
            final String keyboardShortcut) {
        super(fileAdmin, buttonRenderer, keyboardShortcut);
    }

    @Override
    public ActionListener getActionListener(final int rowNumber) {
        return actionEvent -> this.getFileAdmin().importRow(rowNumber);
    }
}
