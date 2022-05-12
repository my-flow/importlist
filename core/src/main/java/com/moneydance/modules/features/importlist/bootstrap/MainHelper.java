package com.moneydance.modules.features.importlist.bootstrap;

import com.moneydance.modules.features.importlist.CoreComponent;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.Image;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * The main class of the extension, instantiated by Moneydance's class loader.
 *
 * @author Florian J. Breunig
 */
@SuppressWarnings("nullness")
public final class MainHelper {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(MainHelper.class.getName());

    private final Settings settings;
    private Preferences prefs;
    private ViewController viewController;

    MainHelper(final Settings argSettings) {
        super();
        Helper.loadLoggerConfiguration(argSettings);
        LOG.info("Initializing extension in Moneydance's application context.");
        this.settings = argSettings;
    }

    public void init(final CoreComponent argCoreComponent, final Observer argObserver) {
        Helper.INSTANCE.init(argCoreComponent);
        Helper.INSTANCE.addObserver(argObserver);

        this.prefs = argCoreComponent.preferences();
        this.viewController = Helper.INSTANCE.getViewController();

        if (this.prefs.isFirstRun()) {
            this.prefs.setFirstRun(false);
            LOG.config("Install");
        }

        // register this module to be invoked via the application toolbar
        LOG.config("Registering toolbar feature");
        Helper.INSTANCE.getContext().registerFeature(this.settings.getChooseBaseDirSuffix(), this.getName());

    }

    public String getName() {
        return this.settings.getExtensionName();
    }

    public Image getIconImage() {
        return this.settings.getIconImage();
    }

    public void invoke(final String uri) {
        if (this.settings.getChooseBaseDirSuffix().equals(uri)) {
            this.viewController.chooseBaseDirectory();
        }
    }

    public void update(final CoreComponent coreComponent) {
        Helper.INSTANCE.init(coreComponent);
    }

    public void unload() {
        LOG.info("Unloading extension."); //$NON-NLS-1$
        this.viewController.setActive(false);
        this.cleanup();
        this.prefs.setAllWritablePreferencesToNull();
    }

    public void cleanup() {
        this.viewController.cleanup();
    }

    ViewController getViewController() {
        return this.viewController;
    }
}
