/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
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

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.util.StreamTable;

/**
 * @author Florian J. Breunig
 */
public final class StubContextFactory {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(StubContextFactory.class);

    private final StubContext   context;
    private       FeatureModule featureModule = null;

    public StubContextFactory() {
        this.context       = new StubContext(this.featureModule);
        Helper.INSTANCE.getPreferences().setContext(this.getContext());
    }

    public StubContextFactory(final FeatureModule argFeatureModule) {
        Validate.notNull(argFeatureModule, "featureModule must not be null");
        this.featureModule  = argFeatureModule;
        this.context        = new StubContext(this.featureModule);
    }

    public void init() {
        LOG.info("Setting up stub context");
        this.featureModule.setup(
                this.context,
                null,
                new StreamTable(),
                null,
                null);
    }

    public StubContext getContext() {
        return this.context;
    }
}
