package com.moneydance.modules.features.importlist.presentation;

import javax.swing.JViewport;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class JCustomScrollPaneTest {

    private JCustomScrollPane customScrollPane;

    @Before
    public void setUp() {
        this.customScrollPane =
                new JCustomScrollPane();
    }

    @Test
    public void testAddNotify() {
        this.customScrollPane.setColumnHeader(null);
        this.customScrollPane.setViewport(null);
        this.customScrollPane.addNotify();

        this.customScrollPane.setColumnHeader(new JViewport());
        this.customScrollPane.setViewport(null);
        this.customScrollPane.addNotify();
        Assert.assertSame("background color must match",
                this.customScrollPane.getBackground(),
                this.customScrollPane.getColumnHeader().getBackground());

        this.customScrollPane.setColumnHeader(null);
        this.customScrollPane.setViewport(new JViewport());
        this.customScrollPane.addNotify();

        this.customScrollPane.setColumnHeader(new JViewport());
        this.customScrollPane.setViewport(new JViewport());
        this.customScrollPane.addNotify();
        Assert.assertSame("background color must match",
                this.customScrollPane.getBackground(),
                this.customScrollPane.getColumnHeader().getBackground());
    }
}
