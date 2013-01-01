/*
 * Import List - http://my-flow.github.io/importlist/
 * Copyright (C) 2011-2013 Florian J. Breunig
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.moneydance.modules.features.importlist.util;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.Validate;

import com.moneydance.apps.md.controller.FeatureModuleContext;

/**
 * This helper class provides public convenience methods.
 *
 * @author Florian J. Breunig
 */
public enum Helper {

    /**
     * Helper instance.
     */
    INSTANCE;

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(Helper.class.getName());

    private final HelperObservable observable;
    private       Preferences prefs;
    private       Tracker tracker;

    private Helper() {
        this.observable = new HelperObservable();
    }

    public Preferences getPreferences() {
       synchronized (Helper.class) {
           if (this.prefs == null) {
               this.prefs = new Preferences();
           }
       }
       return this.prefs;
   }

    public Settings getSettings() {
        return Settings.INSTANCE;
    }

    public Localizable getLocalizable() {
        return Localizable.INSTANCE;
    }

    public Tracker getTracker(final int build) {
        synchronized (Helper.class) {
            if (this.tracker == null) {
                this.tracker = new Tracker(build);
            }
        }
        return this.tracker;
    }

    public void addObserver(final Observer o) {
        this.observable.addObserver(o);
    }

    public void setChanged() {
        this.observable.setChanged();
    }

    public void notifyObservers(final Object arg) {
        this.observable.notifyObservers(arg);
    }

    public void setContext(final FeatureModuleContext context) {
        this.getPreferences().setContext(context);
        this.getLocalizable().setContext(context);
    }

    public void loadLoggerConfiguration() {
        try {
            InputStream inputStream = getInputStreamFromResource(
                    getSettings().getLoggingPropertiesResource());
            LogManager.getLogManager().readConfiguration(inputStream);

        } catch (SecurityException e) {
            e.printStackTrace(System.err);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public InputStream getInputStreamFromResource(
            final String resource) {
        ClassLoader cloader     = Helper.class.getClassLoader();
        InputStream inputStream = cloader.getResourceAsStream(resource);
        Validate.notNull(inputStream, "Resource %s was not found.", resource);
        return inputStream;
    }

    public Image getIconImage() {
        Image image = null;
        try {
            LOG.config(String.format(
                    "Loading icon %s from resource.",
                    getSettings().getIconResource()));
            InputStream inputStream =
                    Helper.INSTANCE.getInputStreamFromResource(
                            getSettings().getIconResource());
            image = ImageIO.read(inputStream);
        } catch (IllegalArgumentException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
        } catch (IOException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
        }
        return image;
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
