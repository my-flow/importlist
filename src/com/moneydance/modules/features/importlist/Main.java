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
import java.io.File;

import org.apache.log4j.Logger;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.view.View;

/**
 * The main class of the extension, instantiated by Moneydance's class
 * loader.
 */
public class Main extends FeatureModule {

    static {
        Preferences.loadLoggerConfiguration();
    }

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(Main.class);

    private String             baseDirectory;
    private FileAdmin          fileAdmin;
    private HomePageView       homePageView;
    private final Preferences  prefs;

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
        StubContextFactory stubContextFactory =
            new StubContextFactory(this, this.getContext());
        stubContextFactory.setup();

        this.fileAdmin = new FileAdmin(this, this.baseDirectory);
        this.homePageView = new View(this.fileAdmin);
        this.fileAdmin.setHomePageView(this.homePageView);

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
        String command = uri;
        String parameters = "";
        int theIdx = uri.indexOf('?');
        if (theIdx >= 0) {
            command = uri.substring(0, theIdx);
            parameters = uri.substring(theIdx + 1);
        } else {
            theIdx = uri.indexOf(':');
            if (theIdx >= 0) {
                command = uri.substring(0, theIdx);
            }
        }

        if (this.prefs.getChooseBaseDirSuffix().equals(command)) {
            this.fileAdmin.reset();
        }

        if (this.prefs.getReloadContextSuffix().equals(command)) {
            log.info("Reloading context from underlying framework.");
            if (this.fileAdmin != null) {
                this.fileAdmin.setContext(this.getContext());
            }
            this.prefs.setContext(this.getContext());
        }

        if (this.prefs.getDeleteFileSuffix().equals(command)) {
            File deleteFile = new File(parameters);
            this.fileAdmin.deleteFile(deleteFile);
        }
    }

    @Override
    public final void unload() {
        log.info("Unloading extension.");
        this.homePageView.setActive(false);
        this.cleanup();
        this.prefs.setAllWritablePreferencesToNull();
    }

    @Override
    public final void cleanup() {
        this.fileAdmin.stopMonitor();
    }

    public final HomePageView getHomePageView() {
        return this.homePageView;
    }
}
