/*
 * Import List - http://my-flow.github.com/importlist/
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
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOG = LoggerFactory.getLogger(Helper.class);

    private Preferences prefs;

    private Tracker tracker;

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

    public void loadLoggerConfiguration() {
        boolean rootIsConfigured = org.apache.log4j.Logger
                .getRootLogger().getAllAppenders().hasMoreElements();
        if (rootIsConfigured) {
            // do not overwrite any existing configurations
            return;
        }

        final Properties properties = new Properties();
        try {
            InputStream inputStream = this.getInputStreamFromResource(
                    this.getSettings().getLog4jPropertiesResource());
            properties.load(inputStream);
        }  catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        PropertyConfigurator.configure(properties);
    }

    public InputStream getInputStreamFromResource(
            final String resource) {
        ClassLoader cloader     = Helper.class.getClassLoader();
        InputStream inputStream = cloader.getResourceAsStream(resource);
        Validate.notNull(inputStream, "Resource " + resource
                + " was not found.");
        return inputStream;
    }

    public Image getIconImage() {
        Image image = null;
        try {
            LOG.debug("Loading icon " + this.getSettings().getIconResource()
                    + " from resource.");
            InputStream inputStream =
                    Helper.INSTANCE.getInputStreamFromResource(
                            this.getSettings().getIconResource());
            image = ImageIO.read(inputStream);
        } catch (IllegalArgumentException e) {
            LOG.warn(e.getMessage(), e);
        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
        }
        return image;
    }
}
