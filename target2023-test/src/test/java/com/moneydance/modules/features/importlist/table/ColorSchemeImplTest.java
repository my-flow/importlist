package com.moneydance.modules.features.importlist.table;

import java.awt.Color;

import javax.swing.JButton;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class ColorSchemeImplTest {

    @Test
    public void testApplyColorSchemeLeavesBackgroundUntouched() {
        final JButton button = new JButton();
        final Color background = button.getBackground();

        new ColorSchemeImpl(Color.WHITE).applyColorScheme(button, 0);

        assertThat(button.getForeground(), is(Color.WHITE));
        assertThat(button.getBackground(), sameInstance(background));
    }
}
