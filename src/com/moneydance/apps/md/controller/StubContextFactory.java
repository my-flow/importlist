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

package com.moneydance.apps.md.controller;

import org.apache.log4j.Logger;

import com.moneydance.util.StreamTable;


public class StubContextFactory {

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(StubContextFactory.class);

    private final FeatureModule featureModule;
    private final FeatureModuleContext featureModuleContext;

    public StubContextFactory(final FeatureModule argFeatureModule,
            final FeatureModuleContext argFeatureModuleContext) {
        this.featureModule        = argFeatureModule;
        this.featureModuleContext = argFeatureModuleContext;
    }

    public final void setup() {
        if (this.featureModuleContext == null) {
            log.info("Setting up stub context");
            FeatureModuleContext newContext =
                new StubContext(this.featureModule);
            this.featureModule.setup(
                    newContext,
                    null,
                    new StreamTable(),
                    null,
                    null);
        }
    }
}
