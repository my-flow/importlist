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

package com.moneydance.apps.md.controller;

import java.awt.Image;
import java.io.File;

import org.apache.log4j.Logger;

import com.moneydance.apps.md.extensionapi.AccountEditor;
import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;

/**
 * This test stub simulates a context in stand-alone mode. It provides canned
 * answers to the standard calls. Also, it can be cast safely to
 * <code>com.moneydance.apps.md.controller.Main</code> in order to request the
 * user's preferences.
 */
public class StubContext extends Main implements FeatureModuleContext {

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(StubContext.class);

    private final FeatureModule featureModule;

    StubContext(final FeatureModule argFeatureModule) {
        this.featureModule = argFeatureModule;
    }

    @Override
    public final RootAccount getRootAccount() {
        return null;
    }

    @Override
    public final String getVersion() {
        return null;
    }

    @Override
    public final int getBuild() {
        return 0;
    }

    @Override
    public final void showURL(final String url) {
        String suffix = url;
        int theIdx = url.lastIndexOf(':');
        if (theIdx >= 0) {
            suffix = url.substring(theIdx + 1);
        }
        log.debug("Stub context forwards received URL " + suffix + " to module "
                + this.featureModule);
        this.featureModule.invoke(suffix);
    }

    @Override
    public final void registerFeature(
            final FeatureModule module,
            final String parameters,
            final Image buttonImage,
            final String buttonText) {
        log.debug("Stub context ignores registered feature " + buttonText
                + " of module " + module);
    }

    @Override
    public final void registerHomePageView(
            final FeatureModule module,
            final HomePageView view) {
        log.debug("Stub context ignores registered homepage view " + view
                + " of module " + module);
    }

    @Override
    public final void registerAccountEditor(
            final FeatureModule module,
            final int accountType,
            final AccountEditor editor) {
        log.debug("Stub context ignores registered account editor " + editor
                + " of module " + module);
    }

    @Override
    public final UserPreferences getPreferences() {
        File preferencesFile = Common.getPreferencesFile();
        log.debug("Stub context returns user preferences from file "
                + preferencesFile.getAbsolutePath());
        return new UserPreferences(preferencesFile);
    }
}
