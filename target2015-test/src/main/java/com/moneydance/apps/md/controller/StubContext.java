// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2019 Florian J. Breunig
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

import com.infinitekind.moneydance.model.AccountBook;
import com.moneydance.apps.md.extensionapi.AccountEditor;
import com.moneydance.apps.md.view.HomePageView;

import java.awt.Image;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

/**
 * This test stub simulates a context in stand-alone mode. It provides canned
 * answers to the standard calls. Also, it can be cast safely to
 * <code>com.moneydance.apps.md.controller.MainHelper</code> in order to
 * request the user's preferences.
 *
 * @author Florian J. Breunig
 */
public final class StubContext extends Main {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(StubContext.class.getName());

    @Nullable private FeatureModule featureModule;
    @Nullable private AccountBook accountBook;
    @Nullable private UserPreferences userPreferences;

    @SuppressWarnings("nullness")
    StubContext() {
        super();
        this.init();
    }

    StubContext(final FeatureModule argFeatureModule) {
        super();
        Validate.notNull(argFeatureModule, "featureModule must not be null");
        this.featureModule = argFeatureModule;
        this.init();
    }

    private void init() {
        try {
            if (!this.isInitialized()) {
                this.initializeApp();
            }
        } catch (Error | Exception e) {
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.SEVERE, message, e);
            }
        }
    }

    public void setAccountBook(final AccountBook argAccountBook) {
        this.accountBook = argAccountBook;
    }

    @Override
    public AccountBook getCurrentAccountBook() {
        assert this.accountBook != null : "@AssumeAssertion(nullness)";
        return this.accountBook;
    }

    @Override
    public String getVersion() {
        return "0";
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
            LOG.config(String.format(
                    "Stub context forwards received URL %s to module %s",
                    suffix,
                    this.featureModule));
            this.featureModule.invoke(suffix);
        }
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
        assert this.userPreferences != null : "@AssumeAssertion(nullness)";
        return this.userPreferences;
    }
}
