package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.bootstrap.MainHelper;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

/**
 * @author Florian J. Breunig
 */
public interface CoreComponent {

    Settings settings();

    Preferences preferences();

    Localizable localizable();

    Context context();

    MainHelper mainHelper();

    ViewController viewController();
}
