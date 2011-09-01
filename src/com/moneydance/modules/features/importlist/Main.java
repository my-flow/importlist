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

import org.apache.log4j.Logger;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * The main class of the extension, instantiated by Moneydance's class loader.
 */
public class Main extends FeatureModule {

    static {
        Helper.loadLoggerConfiguration();
    }

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(Main.class);

    private final           Preferences  prefs;
    private String          baseDirectory;
    private ViewController  viewController;

    /**
     * Standard constructor must be available in the Moneydance context.
     */
    public Main() {
        super();
        LOG.info("Initializing extension in Moneydance's application context.");
        Helper.setFeatureModule(this);
        this.prefs = Helper.getPreferences();
    }

    @Override
    public final void init() {
        final StubContextFactory factory =
            new StubContextFactory(this, this.getContext());
        factory.setup();

        this.viewController = new ViewController(
                this.baseDirectory,
                this.getContext());

        // register this module's homepage view
        LOG.debug("Registering homepage view");
        this.getContext().registerHomePageView(this, this.viewController);

        // register this module to be invoked via the application toolbar
        LOG.debug("Registering toolbar feature");
        this.getContext().registerFeature(
                this,
                this.prefs.getChooseBaseDirSuffix(),
                null, // buttonImage
                this.getName());
    }

    @Override
    public final String getName() {
        return this.prefs.getExtensionName();
    }

    @Override
    public final Image getIconImage() {
        return Helper.getIconImage();
    }

    @Override
    public final void invoke(final String uri) {
        if (this.prefs.getChooseBaseDirSuffix().equals(uri)) {
            this.viewController.resetBaseDirectory();
        }

        if (this.prefs.getReloadContextSuffix().equals(uri)) {
            LOG.info("Reloading context from underlying framework.");
            this.prefs.setContext(this.getContext());
        }
    }

    @Override
    public final void unload() {
        LOG.info("Unloading extension.");
        this.viewController.setActive(false);
        this.cleanup();
        this.prefs.setAllWritablePreferencesToNull();
    }

    @Override
    public final void cleanup() {
        this.viewController.cleanup();
    }

    final void setBaseDirectory(final String argBaseDirectory) {
        this.baseDirectory = argBaseDirectory;
    }

    final HomePageView getHomePageView() {
        return this.viewController;
    }
}
