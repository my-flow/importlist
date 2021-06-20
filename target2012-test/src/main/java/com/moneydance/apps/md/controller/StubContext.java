// Import List - https://www.my-flow.com/importlist/
// Copyright (C) 2011-2021 Florian J. Breunig
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

package com.moneydance.apps.md.controller;

import com.moneydance.apps.md.extensionapi.AccountEditor;
import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;

import java.awt.Image;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(StubContext.class.getName());

    private RootAccount rootAccount;
    private UserPreferences userPreferences;

    StubContext() {
        super();
        try {
            this.initializeApp();
        } catch (Error e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
        }
    }

    void setRootAccount(final RootAccount argRootAccount) {
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
        // ignore
    }

    @Override
    public void registerFeature(
            final FeatureModule module,
            final String parameters,
            final Image buttonImage,
            final String buttonText) {
        LOG.config(String.format(
                "Stub context ignores registered feature %s of module %s",
                buttonText,
                module));
    }

    @Override
    public void registerHomePageView(
            final FeatureModule module,
            final HomePageView view) {
        LOG.config(String.format(
                "Stub context ignores registered homepage view %s of module %s",
                view,
                module));
    }

    @Override
    public void registerAccountEditor(
            final FeatureModule module,
            final int accountType,
            final AccountEditor editor) {
        LOG.config(String.format(
                "Stub context ignores registered account editor %s of module %s",
                editor,
                module));
    }

    @Override
    public UserPreferences getPreferences() {
        if (this.userPreferences == null) {
            final File preferencesFile = Common.getPreferencesFile();
            this.userPreferences = new UserPreferences(preferencesFile);
            LOG.config(String.format(
                    "Stub context returns user preferences from file %s",
                    preferencesFile.getAbsolutePath()));
        }
        return this.userPreferences;
    }
}
