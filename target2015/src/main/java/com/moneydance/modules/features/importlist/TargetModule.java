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

package com.moneydance.modules.features.importlist;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ContextImpl;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.controller.ViewControllerImpl;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.EvenColorSchemeImpl;
import com.moneydance.modules.features.importlist.table.OddColorSchemeImpl;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.PreferencesImpl;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.table.AbstractTableModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author Florian J. Breunig
 */
@Module
final class TargetModule {

    private final FeatureModule featureModule;
    private FeatureModuleContext context;

    TargetModule(final FeatureModule argFeatureModule) {
        super();
        this.featureModule = argFeatureModule;
    }

    void setContext(final FeatureModuleContext argContext) {
        this.context = argContext;
    }

    @Provides
    FeatureModule provideFeatureModule() {
        return this.featureModule;
    }

    @Provides
    FeatureModuleContext provideFeatureModuleContext() {
        return this.context;
    }

    @Provides
    Context provideContext(
            final FeatureModule argFeatureModule,
            final FeatureModuleContext argFeatureModuleContext) {
        return new ContextImpl(argFeatureModule, argFeatureModuleContext);
    }

    @Provides
    Preferences providePreferences(final Settings argSettings, final FeatureModuleContext argContext) {
        return new PreferencesImpl(argSettings, argContext);
    }

    @Provides
    @Singleton
    ViewController provideViewController(
            @Named("base") final AbstractTableModel baseTableModel,
            @Named("aggregation") final AbstractTableModel aggrTableModel,
            final FileAdmin fileAdmin,
            @Named("even color scheme") final ColorScheme evenColorScheme,
            @Named("odd color scheme") final ColorScheme oddColorScheme,
            final Settings settings,
            final Preferences prefs) {
        return new ViewControllerImpl(
                fileAdmin,
                baseTableModel,
                aggrTableModel,
                evenColorScheme,
                oddColorScheme,
                settings,
                prefs);
    }

    @Provides
    @Singleton
    @Named("even color scheme")
    ColorScheme provideEvenColorSchemeImpl() {
        return new EvenColorSchemeImpl();
    }

    @Provides
    @Singleton
    @Named("odd color scheme")
    ColorScheme provideOddColorSchemeImpl() {
        return new OddColorSchemeImpl();
    }
}
