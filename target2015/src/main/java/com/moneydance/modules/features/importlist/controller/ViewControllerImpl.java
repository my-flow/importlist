package com.moneydance.modules.features.importlist.controller;

import com.infinitekind.moneydance.model.AccountBook;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;

/**
 * The user interface that is displayed on the homepage.
 *
 * @author Florian J. Breunig
 */
public final class ViewControllerImpl extends DefaultViewController implements HomePageView {

    public ViewControllerImpl(
            final FileAdmin fileAdmin,
            final AbstractTableModel baseTableModel,
            final AbstractTableModel aggrTableModel,
            final ColorScheme evenColorScheme,
            final ColorScheme oddColorScheme,
            final Settings settings,
            final Preferences prefs) {
        super(fileAdmin,
                baseTableModel,
                aggrTableModel,
                evenColorScheme,
                oddColorScheme,
                settings,
                prefs);
    }


    @Override
    @SuppressWarnings("nullness")
    public synchronized void refresh() {
        super.refresh();
    }


    @Override
    public JComponent getGUIView(final AccountBook accountBook) {
        return super.getGUIView();
    }


    @Override
    public void reset() {
        // ignore
    }
}
