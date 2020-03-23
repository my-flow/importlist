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

package com.moneydance.modules.features.importlist;

import com.moneydance.apps.md.controller.FeatureModule;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.bootstrap.MainHelper;
import com.moneydance.modules.features.importlist.controller.ViewControllerImpl;

import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

/**
 * The main class of the extension, instantiated by Moneydance's class loader.
 *
 * @author Florian J. Breunig
 */
@SuppressWarnings("nullness")
public final class Main extends FeatureModule implements Observer {

    private final TargetModule targetModule;
    private final TargetComponent targetComponent;
    private final MainHelper mainHelper;
    private ViewControllerImpl viewController;

    /**
     * Public standard constructor must be available in the Moneydance context.
     */
    public Main() {
        super();
        this.targetModule = new TargetModule(this);
        this.targetComponent = DaggerTargetComponent.builder().targetModule(this.targetModule).build();
        this.mainHelper = this.targetComponent.mainHelper();
    }

    @Override
    public void init() {
        this.targetModule.setContext(this.getContext());

        this.mainHelper.init(this.targetComponent, this);
        this.viewController = (ViewControllerImpl) this.targetComponent.viewController();

        // register this module's homepage view
        this.getContext().registerHomePageView(this, this.viewController);
    }

    @Override
    public String getName() {
        return this.mainHelper.getName();
    }

    @Override
    public Image getIconImage() {
        return this.mainHelper.getIconImage();
    }

    @Override
    public void invoke(final String uri) {
        this.mainHelper.invoke(uri);
    }

    @Override
    public void update(final Observable observable, final Object updateAll) {
        this.targetModule.setContext(this.getContext());
        this.mainHelper.update(this.targetComponent);
    }

    @Override
    public void unload() {
        this.mainHelper.unload();
    }

    @Override
    public void cleanup() {
        this.mainHelper.cleanup();
    }

    HomePageView getHomePageView() {
        return this.viewController;
    }
}
