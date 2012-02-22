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

package com.moneydance.modules.features.importlist;

import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

/**
 * The main class of the extension, instantiated by Moneydance's class loader.
 *
 * @author Florian J. Breunig
 */
public final class Main extends FeatureModule implements Observer {

    static {
        Helper.loadLoggerConfiguration();
    }

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private final           Preferences  prefs;
    private final           Settings     settings;
    private String          baseDirectory;
    private ViewController  viewController;

    /**
     * Standard constructor must be available in the Moneydance context.
     */
    public Main() {
        LOG.info("Initializing extension in Moneydance's application context.");
        this.prefs    = Helper.getPreferences();
        this.settings = Helper.getSettings();
    }

    @Override
    public void init() {
        this.prefs.addObserver(this);

        this.viewController = new ViewController(
                this.baseDirectory,
                this.getContext(),
                this.getBuild());

        // register this module's homepage view
        LOG.debug("Registering homepage view");
        this.getContext().registerHomePageView(this, this.viewController);

        // register this module to be invoked via the application toolbar
        LOG.debug("Registering toolbar feature");
        this.getContext().registerFeature(
                this,
                this.settings.getChooseBaseDirSuffix(),
                null, // buttonImage
                this.getName());
    }

    @Override
    public String getName() {
        return this.settings.getExtensionName();
    }

    @Override
    public Image getIconImage() {
        return Helper.getIconImage();
    }

    @Override
    public void invoke(final String uri) {
        if (this.settings.getChooseBaseDirSuffix().equals(uri)) {
            this.viewController.chooseBaseDirectory();
        }
    }

    @Override
    public void update(final Observable observable, final Object updateAll) {
        LOG.info("Reloading context from underlying framework.");
        this.prefs.setContext(this.getContext());
    }

    @Override
    public void unload() {
        LOG.info("Unloading extension.");
        this.viewController.setActive(false);
        this.cleanup();
        this.prefs.setAllWritablePreferencesToNull();
    }

    @Override
    public void cleanup() {
        this.viewController.cleanup();
    }

    void setBaseDirectory(final String argBaseDirectory) {
        this.baseDirectory = argBaseDirectory;
    }

    HomePageView getHomePageView() {
        return this.viewController;
    }
}
