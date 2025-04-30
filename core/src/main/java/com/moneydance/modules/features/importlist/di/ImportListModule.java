package com.moneydance.modules.features.importlist.di;

import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Consolidated module that combines the core providers from various factory
 * modules. This reduces the fragmentation of DI configuration.
 *
 * @author Florian J. Breunig
 */
@Module
public final class ImportListModule {

    @Provides
    @Singleton
    Localizable provideLocalizable(final Settings settings, final Preferences prefs) {
        return new Localizable(settings, prefs.getLocale());
    }
}
