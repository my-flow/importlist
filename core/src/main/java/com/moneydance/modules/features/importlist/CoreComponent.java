package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.bootstrap.MainHelper;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.util.ISettings;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;

import javax.swing.table.AbstractTableModel;

/**
 * @author Florian J. Breunig
 */
public interface CoreComponent {

    ISettings settings();

    Preferences preferences();

    Localizable localizable();

    Context context();

    MainHelper mainHelper();

    ViewController viewController();

    // Additional getters for services
    FileAdmin fileAdmin();

    FileContainer fileContainer();

    AbstractTableModel baseTableModel();

    AbstractTableModel aggregationTableModel();

    ColorScheme evenColorScheme();

    ColorScheme oddColorScheme();
}
