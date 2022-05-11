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
