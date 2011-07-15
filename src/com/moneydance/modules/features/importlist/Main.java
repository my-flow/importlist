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
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.io.FileAdministration;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.view.View;

/**
 * The main class of the extension, instantiated by Moneydance's class
 * loader.
 */
public class Main extends FeatureModule {

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(Main.class);

    private String             baseDirectory;
    private FileAdministration fileAdministration;
    private HomePageView       homePageView;
    private final Preferences  prefs;

    static {
        Preferences.loadLoggerConfiguration();
    }

    /**
     * Standard constructor must be available in the Moneydance context.
     */
    public Main() {
        super();
        log.info("Initializing extension in Moneydance's application context.");
        this.prefs = new Preferences(this);
    }

    public Main(final String argBaseDirectory) {
        super();
        log.info("Initializing extension in stand-alone mode.");
        this.baseDirectory = argBaseDirectory;
        this.prefs = new Preferences(this);
    }

    @Override
    public final void init() {
        this.fileAdministration = new FileAdministration(
                this,
                this.baseDirectory);
        this.homePageView       = new View(this.fileAdministration);
        this.fileAdministration.setHomePageView(this.homePageView);

        if (this.getContext() != null) {
            log.debug("Registering homepage view");
            // register this module's homepage view
            this.getContext().registerHomePageView(this, this.homePageView);

            log.debug("Registering toolbar feature");
            // register this module to be invoked via the application toolbar
            this.getContext().registerFeature(
                    this,
                    this.prefs.getChooseBaseDirSuffix(),
                    null,
                    this.getName());
        }
    }

    @Override
    public final String getName() {
        return this.prefs.getExtensionName();
    }

    @Override
    public final Image getIconImage() {
        return this.prefs.getIconImage();
    }

    @Override
    public final void invoke(final String uri) {
        if (this.prefs.getChooseBaseDirSuffix().equals(uri)) {
            this.fileAdministration.reset();
        }

        if (this.prefs.getReloadContextSuffix().equals(uri)) {
            log.info("Reloading context from underlying framework.");
            if (this.fileAdministration != null) {
                this.fileAdministration.setContext(this.getContext());
            }
            this.prefs.setContext(this.getContext());
        }
    }

    @Override
    public final void unload() {
        log.info("Unloading extension.");
        this.cleanup();
        this.homePageView.setActive(false);
        this.prefs.setAllWritablePreferencesToNull();
    }

    @Override
    public final void cleanup() {
        this.fileAdministration.stopMonitor();
    }

    public final HomePageView getHomePageView() {
        return this.homePageView;
    }
}
