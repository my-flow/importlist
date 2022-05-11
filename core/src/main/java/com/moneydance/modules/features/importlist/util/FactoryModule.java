package com.moneydance.modules.features.importlist.util;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * @author Florian J. Breunig
 */
@Module
public final class FactoryModule {

    private static final String SETTINGS_RESOURCE = "settings.properties";

    @SuppressFBWarnings(
            value = "EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS",
            justification = "@Provides methods may only throw unchecked exceptions")
    @Provides
    Settings provideSettings() {
        try {
            return new Settings(SETTINGS_RESOURCE);
        } catch (ConfigurationException | IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Provides
    Localizable provideLocalizable(final Settings settings, final Preferences prefs) {
        return new Localizable(settings, prefs.getLocale());
    }
}
