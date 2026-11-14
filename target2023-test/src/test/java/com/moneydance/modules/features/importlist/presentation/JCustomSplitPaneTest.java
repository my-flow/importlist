package com.moneydance.modules.features.importlist.presentation;

import javax.swing.JLabel;
import javax.swing.JSplitPane;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class JCustomSplitPaneTest {

    private JCustomSplitPane customSplitPane;

    @Before
    public void setUp() {
        this.customSplitPane =
                new JCustomSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    }

    @Test
    public void testAddNotify() {
        this.customSplitPane.setTopComponent(null);
        this.customSplitPane.setBottomComponent(null);
        this.customSplitPane.addNotify();

        this.customSplitPane.setTopComponent(new JLabel());
        this.customSplitPane.setBottomComponent(null);
        this.customSplitPane.addNotify();

        this.customSplitPane.setTopComponent(null);
        this.customSplitPane.setBottomComponent(new JLabel());
        this.customSplitPane.addNotify();

        this.customSplitPane.setTopComponent(new JLabel());
        this.customSplitPane.setBottomComponent(new JLabel());
        this.customSplitPane.addNotify();
    }
}
