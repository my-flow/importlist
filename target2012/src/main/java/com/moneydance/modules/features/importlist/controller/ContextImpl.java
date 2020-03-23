// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2020 Florian J. Breunig
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
