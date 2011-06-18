package com.moneydance.modules.features.importlist;

import java.awt.Image;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.io.FileAdministration;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.view.View;

/**
 * The main class of the extension, instantiated by Moneydance's class
 * loader.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
public class Main extends FeatureModule {

    private String             baseDirectory;
    private FileAdministration fileAdministration;
    private HomePageView       homePageView;
    private final Preferences  prefs;

    /**
     * Standard constructor must be available in the Moneydance context.
     */
    public Main() {
        super();
        this.prefs = new Preferences(this);
    }

    public Main(final String argBaseDirectory) {
        super();
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
            // register this module's home page view
            this.getContext().registerHomePageView(this, this.homePageView);

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
            if (this.fileAdministration != null) {
                this.fileAdministration.setContext(this.getContext());
            }
            this.prefs.setContext(this.getContext());
        }
    }

    @Override
    public final void unload() {
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
