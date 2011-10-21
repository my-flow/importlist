/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.view.resources.Resources;

public final class Helper {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(Resources.class);

    private static  Preferences     prefs;

    /**
     * Restrictive constructor.
     */
    private Helper() {
        // Prevents this class from being instantiated from the outside.
    }

    public static void setFeatureModule(final FeatureModule argFeatureModule) {
        prefs.setFeatureModule(argFeatureModule);
    }

    public static synchronized Preferences getPreferences() {
        if (prefs == null) {
            prefs = new Preferences();
        }
        return prefs;
    }

    public static Image getIconImage() {
        Image image = null;
        try {
            LOG.debug("Loading icon " + getPreferences().getIconResource()
                    + " from resource.");
            InputStream inputStream = Helper.getInputStreamFromResource(
                    getPreferences().getIconResource());
            image = ImageIO.read(inputStream);
        } catch (IllegalArgumentException e) {
            LOG.warn(e.getMessage(), e);
        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
        }
        return image;
    }


    public static void loadLoggerConfiguration() {
        boolean rootIsConfigured =
            Logger.getRootLogger().getAllAppenders().hasMoreElements();
        if (rootIsConfigured) {
            // do not overwrite any existing configurations
            return;
        }

        final Properties properties = new Properties();
        try {
            InputStream inputStream = getInputStreamFromResource(
                    getPreferences().getLog4jPropertiesResource());
            properties.load(inputStream);
        }  catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        PropertyConfigurator.configure(properties);
    }

    public static InputStream getInputStreamFromResource(
            final String resource) {
        ClassLoader cloader     = Helper.class.getClassLoader();
        InputStream inputStream = cloader.getResourceAsStream(resource);
        Validate.notNull(inputStream, "Resource " + resource
                + " was not found.");
        return inputStream;
    }


    public static String getMarkupFilename(final String filename) {
        int length = getPreferences().getMessageFilenameLineMaxLength();

        int numberOfLines = filename.length() / length + 1;
        String[] substrings = new String[numberOfLines];

        for (int i = 0; i < numberOfLines; i++) {
            int start =  i      * length;
            int end   = (i + 1) * length;
            substrings[i] = StringUtils.substring(filename, start, end);
        }
        return StringUtils.join(substrings, "<br>");
    }
}
