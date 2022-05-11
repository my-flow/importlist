package com.moneydance.modules.features.importlist.controller;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.controller.FeatureModuleContext;

import javax.inject.Inject;

/**
 * @author Florian J. Breunig
 */
public final class ContextImpl implements Context {

    private final FeatureModule featureModule;
    private final FeatureModuleContext context;

    @Inject public ContextImpl(final FeatureModule argFeatureModule, final FeatureModuleContext argContext) {
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
}
