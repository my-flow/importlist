package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Florian J. Breunig
 */
final class FullEditorStub extends AbstractEditor {

    private static final long serialVersionUID = 1;

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
