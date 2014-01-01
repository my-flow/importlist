// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2014 Florian J. Breunig
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

package com.moneydance.modules.features.importlist;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;
import com.moneydance.modules.features.importlist.util.Tracker;

import java.awt.Image;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * The main class of the extension, instantiated by Moneydance's class loader.
 *
 * @author Florian J. Breunig
 */
public final class Main extends FeatureModule implements Observer {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private final Preferences     prefs;
    private final Settings       settings;
    private       Tracker         tracker;
    private       String          baseDirectory;
    private       ViewController  viewController;

    static {
        Helper.loadLoggerConfiguration();
    }

    /**
     * Public standard constructor must be available in the Moneydance context.
     */
    public Main() {
        LOG.info("Initializing extension in Moneydance's application context.");
        this.prefs = Helper.INSTANCE.getPreferences();
        this.settings = Helper.INSTANCE.getSettings();
    }

    @Override
    public void init() {
        Helper.INSTANCE.addObserver(this);
        this.tracker  = Helper.INSTANCE.getTracker(this.getBuild());

        if (this.prefs.isFirstRun()) {
            this.prefs.setFirstRun(false);
            LOG.config("Install");
            this.tracker.track(Tracker.EventName.INSTALL);
        }

        this.viewController = new ViewController(
                this.baseDirectory,
                this.getContext(),
                this.tracker);

        // register this module's homepage view
        LOG.config("Registering homepage view");
        this.getContext().registerHomePageView(this, this.viewController);

        // register this module to be invoked via the application toolbar
        LOG.config("Registering toolbar feature");
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
        return this.settings.getIconImage();
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
        Helper.INSTANCE.setContext(this.getContext());
    }

    @Override
    public void unload() {
        LOG.info("Unloading extension."); //$NON-NLS-1$
        this.tracker.track(Tracker.EventName.UNINSTALL);
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
