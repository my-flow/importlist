package com.moneydance.modules.features.importlist.util;

import dagger.Module;
import dagger.Provides;

/**
 * @author Florian J. Breunig
 */
@Module
public final class MockModule {

    @Provides
    Preferences providePreferences() {
        return new PreferencesMock();
    }
}
