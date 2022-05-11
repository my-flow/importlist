package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.io.FileAdmin;

import java.awt.event.ActionListener;

import javax.inject.Inject;

/**
 * @author Florian J. Breunig
 */
final class ImportAllEditor extends AbstractEditor {

    private static final long serialVersionUID = 1L;

    @Inject ImportAllEditor(
            final FileAdmin fileAdmin,
            final ButtonRenderer buttonRenderer,
            final String keyboardShortcut) {
        super(fileAdmin, buttonRenderer, keyboardShortcut);
    }

    @Override
    public ActionListener getActionListener(final int rowNumber) {
        return actionEvent -> this.getFileAdmin().importAllRows();
    }
}
