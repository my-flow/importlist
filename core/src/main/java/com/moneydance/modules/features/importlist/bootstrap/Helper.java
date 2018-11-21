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

package com.moneydance.modules.features.importlist.bootstrap;

import com.moneydance.modules.features.importlist.CoreComponent;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.LogManager;

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

    private final HelperObservable observable;
    private CoreComponent coreComponent;

    Helper() {
        this.observable = new HelperObservable();
    }

    void init(final CoreComponent argCoreComponent) {
        synchronized (Helper.class) {
            this.coreComponent = argCoreComponent;
        }
    }

    public Preferences getPreferences() {
        return this.coreComponent.preferences();
    }

    public Localizable getLocalizable() {
        return this.coreComponent.localizable();
    }

    public Context getContext() {
        return this.coreComponent.context();
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

    public ViewController getViewController() {
        return this.coreComponent.viewController();
    }

    public static void loadLoggerConfiguration(final Settings settings) {
        try {
            InputStream inputStream = settings.getLoggingPropertiesResource();
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
