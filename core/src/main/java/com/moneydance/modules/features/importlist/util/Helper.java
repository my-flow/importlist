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

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.LogManager;

import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * This singleton provides public convenience methods.
 *
 * @author Florian J. Breunig
 */
@SuppressWarnings("nullness")
public enum Helper {

    /**
     * Helper instance.
     */
    INSTANCE;

    /**
     * The resource in the JAR file to read the settings from.
     */
    private static final String SETTINGS_RESOURCE = "settings.properties";

    private final HelperObservable observable;
    private final Settings settings;
    private Preferences prefs;
    private Localizable localizable;

    Helper() {
        this.observable = new HelperObservable();
        try {
            this.settings = new Settings(SETTINGS_RESOURCE);
        } catch (ConfigurationException | IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void setContext(final FeatureModuleContext context) {
        synchronized (Helper.class) {
            final Main main = (Main) context;
            this.prefs = new Preferences(main, this.settings);
            this.localizable = new Localizable(this.settings, this.prefs.getLocale());
        }
    }

    public Settings getSettings() {
        return this.settings;
    }

    public Preferences getPreferences() {
        return this.prefs;
    }

    public Localizable getLocalizable() {
        return this.localizable;
    }

    public void addObserver(final Observer observer) {
        this.observable.addObserver(observer);
    }

    public void setChanged() {
        this.observable.setChanged();
    }

    public void notifyObservers(final Object arg) {
        this.observable.notifyObservers(arg);
    }

    public static void loadLoggerConfiguration() {
        try {
            InputStream inputStream = Helper.INSTANCE.getSettings().getLoggingPropertiesResource();
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (SecurityException | IOException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * @author Florian J. Breunig
     */
    private static final class HelperObservable extends Observable {
        @Override
        public void setChanged() { // increase visiblity
            synchronized (this) {
                super.setChanged();
            }
        }
    }
}
