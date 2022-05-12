package com.moneydance.modules.features.importlist;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ContextImpl;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.controller.ViewControllerImpl;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.ColorSchemeImpl;
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
        final com.moneydance.apps.md.controller.Main main =
                (com.moneydance.apps.md.controller.Main) argContext;
        return new PreferencesImpl(argSettings, main);
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
    ColorScheme provideEvenColorSchemeImpl(final Preferences prefs) {
        return new ColorSchemeImpl(prefs.getForeground());
    }

    @Provides
    @Singleton
    @Named("odd color scheme")
    ColorScheme provideOddColorSchemeImpl(final Preferences prefs) {
        return new ColorSchemeImpl(prefs.getForeground());
    }
}
