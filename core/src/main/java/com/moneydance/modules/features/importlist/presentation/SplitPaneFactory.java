package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.bootstrap.Helper;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

/**
 * @author Florian J. Breunig
 */
public final class SplitPaneFactory implements ComponentFactory {

    private final JSplitPane splitPane;
    private final ComponentFactory topComponentFactory;
    private final ComponentFactory bottomComponentFactory;

    public SplitPaneFactory(
            final ComponentFactory argTopComponentFactory,
            final ComponentFactory argBottomComponentFactory) {

        this.topComponentFactory = argTopComponentFactory;
        this.bottomComponentFactory = argBottomComponentFactory;

        this.splitPane = new JCustomSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.splitPane.setResizeWeight(1.0);
        this.splitPane.setDividerSize(0);
        this.splitPane.setContinuousLayout(true);
        this.splitPane.setOpaque(false);
        this.splitPane.setBorder(Helper.INSTANCE.getPreferences().getHomePageBorder());
    }

    @Override
    public JSplitPane getComponent() {
        JComponent topComponent = this.topComponentFactory.getComponent();
        topComponent.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.splitPane.setTopComponent(topComponent);

        JComponent bottomComponent = this.bottomComponentFactory.getComponent();
        this.splitPane.setBottomComponent(bottomComponent);

        return this.splitPane;
    }
}
