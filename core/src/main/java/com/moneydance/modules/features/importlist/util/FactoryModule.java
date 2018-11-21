// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2018 Florian J. Breunig
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
