package com.moneydance.modules.features.importlist;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.io.FileAdministration;
import com.moneydance.modules.features.importlist.view.View;

/**
 * The main class of the extension, instantiated by Moneydance's class
 * loader.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
class Main extends FeatureModule {

    private String             baseDirectory;
    private FileAdministration fileAdministration;
    private HomePageView       homePageView;
    private Preferences        preferences;

    /**
     * Standard constructor must be available in the Moneydance context.
     */
    public Main() {
        super();
    }

    public Main(final String argBaseDirectory) {
        super();
        this.baseDirectory = argBaseDirectory;
        this.init();
    }

    @Override
    public final void init() {
        this.preferences        = new Preferences(this);
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
                    Constants.CHOOSE_BASE_DIR_SUFFIX,
                    null,
                    this.getName());
        }
    }

    @Override
    public final String getName() {
        return Constants.EXTENSION_NAME;
    }

    @Override
    public final Image getIconImage() {
        Image image             = null;
        ClassLoader cl          = this.getClass().getClassLoader();
        InputStream inputStream = cl.getResourceAsStream(Constants.ICON);
        File imageFile          = new File("src" + Constants.ICON);
        try {
            if (inputStream != null) {
                image = ImageIO.read(inputStream);
            } else {
                image = ImageIO.read(imageFile);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
        }
        return image;
    }

    @Override
    public final void invoke(final String uri) {
        if (Constants.CHOOSE_BASE_DIR_SUFFIX.equals(uri)) {
            this.fileAdministration.reset();
        }

        if (Constants.RELOAD_CONTEXT_SUFFIX.equals(uri)) {
            if (this.fileAdministration != null) {
                this.fileAdministration.setContext(this.getContext());
            }
            if (this.preferences != null) {
                this.preferences.setContext(this.getContext());
            }
        }
    }

    @Override
    public final void unload() {
        this.cleanup();
        this.homePageView.setActive(false);
        Preferences.getInstance().setAllWritablePreferencesToNull();
    }

    @Override
    public final void cleanup() {
        this.fileAdministration.stopMonitor();
    }

    public final HomePageView getHomePageView() {
        return this.homePageView;
    }
}
