package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.TestComponentFactory;

import java.awt.event.ActionListener;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class ImportAllEditorTest {

    private AbstractEditor importAllEditor;

    @Before
    public void setUp() {
        final CoreTestComponent testComponent = TestComponentFactory.createTestComponent();

        this.importAllEditor = testComponent.importAllEditor();
    }

    @Test
    public void testGetActionListener() {
        ActionListener actionListener =
                this.importAllEditor.getActionListener(0);
        assertThat(actionListener, notNullValue());
        actionListener.actionPerformed(null);
    }
}
