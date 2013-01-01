/*
 * Import List - http://my-flow.github.io/importlist/
 * Copyright (C) 2011-2013 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.presentation;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import com.moneydance.apps.md.view.gui.MoneydanceLAF;

/**
 * @author Florian J. Breunig
 */
public final class SplitPaneFactory implements ComponentFactory {

    private       JSplitPane splitPane;
    private final ComponentFactory topComponentFactory;
    private final ComponentFactory bottomComponentFactory;

    public SplitPaneFactory(
            final ComponentFactory argTopComponentFactory,
            final ComponentFactory argBottomComponentFactory) {

        this.topComponentFactory    = argTopComponentFactory;
        this.bottomComponentFactory = argBottomComponentFactory;
    }

    private void init() {
        this.splitPane = new JCustomSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.splitPane.setResizeWeight(1.0);
        this.splitPane.setDividerSize(0);
        this.splitPane.setContinuousLayout(true);
        this.splitPane.setOpaque(false);
        this.splitPane.setBorder(MoneydanceLAF.homePageBorder);
    }

    @Override
    public JSplitPane getComponent() {
        if (this.splitPane == null) {
            this.init();
        }

        JComponent topComponent = this.topComponentFactory.getComponent();
        topComponent.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.splitPane.setTopComponent(topComponent);

        JComponent bottomComponent = this.bottomComponentFactory.getComponent();
        this.splitPane.setBottomComponent(bottomComponent);

        return this.splitPane;
    }
}
