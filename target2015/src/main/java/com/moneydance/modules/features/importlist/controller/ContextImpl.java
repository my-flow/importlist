package com.moneydance.modules.features.importlist.controller;

import com.infinitekind.util.StreamTable;
import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.Main;
import com.moneydance.apps.md.controller.UserPreferences;
import com.moneydance.apps.md.view.gui.MoneydanceGUI;

import java.awt.Frame;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Florian J. Breunig
 */
public final class ContextImpl implements Context {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(ContextImpl.class.getName());

    private final FeatureModule featureModule;
    private final FeatureModuleContext context;

    public ContextImpl(final FeatureModule argFeatureModule, final FeatureModuleContext argContext) {
        this.featureModule = argFeatureModule;
        this.context = argContext;
    }

    @Override
    public void showURL(final String resolvedUri) {
        this.context.showURL(resolvedUri);
    }

    @Override
    public void registerFeature(final String parameters, final String buttonText) {
        this.context.registerFeature(this.featureModule, parameters, null, buttonText);
    }

    @Override
    public Optional<Frame> getMainFrame() {
        if (this.context instanceof Main main && main.getUI() instanceof MoneydanceGUI gui) {
            return Optional.ofNullable(gui.getFirstMainFrame());
        }
        return Optional.empty();
    }

    @Override
    public void invokeAfterModuleOperation(final Runnable task) {
        // Moneydance holds the lock on Main while it updates or uninstalls a
        // module, on whichever thread it does so
        final Thread thread = new Thread(() -> {
            synchronized (this.context) {
                task.run();
            }
        }, "importlist-after-module-operation");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public boolean isModuleInstalled() {
        if (this.context instanceof Main main) {
            final StreamTable modules = main.getPreferences().getTableSetting(
                    UserPreferences.GEN_FMODULE_LIST, new StreamTable());
            return modules.containsKey(this.featureModule.getIDStr());
        }
        return true;
    }

    @Override
    public void savePreferences() {
        if (this.context instanceof Main main) {
            try {
                main.savePreferences();
            // savePreferences() declares throws Exception (library method)
            } catch (Exception e) { // NOPMD - AvoidCatchingGenericException - library method declares Exception
                LOG.log(Level.WARNING, "Could not save Moneydance's preferences", e);
            }
        }
    }
}
