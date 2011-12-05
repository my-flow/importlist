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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moneydance.apps.md.extensionapi.AccountEditor;
import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;

/**
 * This test stub simulates a context in stand-alone mode. It provides canned
 * answers to the standard calls. Also, it can be cast safely to
 * <code>com.moneydance.apps.md.controller.Main</code> in order to request the
 * user's preferences.
 *
 * @author Florian J. Breunig
 */
public final class StubContext extends Main {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(StubContext.class);

    private final FeatureModule featureModule;
    private       RootAccount rootAccount;

    StubContext(final FeatureModule argFeatureModule) {
        this.featureModule = argFeatureModule;
    }

    public void setRootAccount(final RootAccount argRootAccount) {
        this.rootAccount = argRootAccount;
    }

    @Override
    public RootAccount getRootAccount() {
        return this.rootAccount;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public int getBuild() {
        return 0;
    }

    @Override
    public void showURL(final String url) {
        if (this.featureModule != null) {
            String suffix = url;
            final int theIdx = url.lastIndexOf(':');
            if (theIdx >= 0) {
                suffix = url.substring(theIdx + 1);
            }
            LOG.debug("Stub context forwards received URL " + suffix
                    + " to module " + this.featureModule);
            this.featureModule.invoke(suffix);
        }
    }

    @Override
    public void registerFeature(
            final FeatureModule module,
            final String parameters,
            final Image buttonImage,
            final String buttonText) {
        LOG.debug("Stub context ignores registered feature " + buttonText
                + " of module " + module);
    }

    @Override
    public void registerHomePageView(
            final FeatureModule module,
            final HomePageView view) {
        LOG.debug("Stub context ignores registered homepage view " + view
                + " of module " + module);
    }

    @Override
    public void registerAccountEditor(
            final FeatureModule module,
            final int accountType,
            final AccountEditor editor) {
        LOG.debug("Stub context ignores registered account editor " + editor
                + " of module " + module);
    }

    @Override
    public UserPreferences getPreferences() {
        final File preferencesFile = Common.getPreferencesFile();
        LOG.debug("Stub context returns user preferences from file "
                + preferencesFile.getAbsolutePath());
        return new UserPreferences(preferencesFile);
    }
}
