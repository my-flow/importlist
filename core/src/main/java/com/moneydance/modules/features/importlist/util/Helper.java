// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2016 Florian J. Breunig
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.LogManager;

import org.apache.commons.lang3.Validate;

/**
 * This singleton provides public convenience methods.
 *
 * @author Florian J. Breunig
 */
public enum Helper {

    /**
     * Helper instance.
     */
    INSTANCE;

    private final HelperObservable observable;
    private final Settings settings;
    private       Preferences prefs;
    private       Localizable localizable;
    private       Tracker tracker;

    private Helper() {
        this.observable = new HelperObservable();
        this.settings   = new Settings();
    }

    public Settings getSettings() {
        return this.settings;
    }

    public Preferences getPreferences() {
        synchronized (Helper.class) {
            if (this.prefs == null) {
                this.prefs = new Preferences();
            }
        }
        return this.prefs;
    }

    public Localizable getLocalizable() {
        synchronized (Helper.class) {
            if (this.localizable == null) {
                this.localizable = new Localizable(
                        this.settings.getLocalizableResource(),
                        this.prefs.getLocale());
            }
        }
        return this.localizable;
    }

    public Tracker getTracker(final int build) {
        synchronized (Helper.class) {
            if (this.tracker == null) {
                this.tracker = new Tracker(
                        build,
                        this.settings.getExtensionName(),
                        this.prefs.getFullVersion(),
                        this.settings.getTrackingCode());
            }
        }
        return this.tracker;
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

    public void setContext(final FeatureModuleContext context) {
        this.prefs.setContext(context);
    }

    public static void loadLoggerConfiguration() {
        try {
            InputStream inputStream = getInputStreamFromResource(
                    Helper.INSTANCE.getSettings()
                    .getLoggingPropertiesResource());
            LogManager.getLogManager().readConfiguration(inputStream);

        } catch (SecurityException e) {
            e.printStackTrace(System.err);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public static InputStream getInputStreamFromResource(
            final String resource) {
        ClassLoader cloader     = Helper.class.getClassLoader();
        InputStream inputStream = cloader.getResourceAsStream(resource);
        Validate.notNull(inputStream, "Resource %s was not found.", resource);
        return inputStream;
    }

    /**
     * @author Florian J. Breunig
     */
    private final class HelperObservable extends Observable {
        @Override
        public synchronized void setChanged() { // increase visiblity
            super.setChanged();
        }
    }
}
