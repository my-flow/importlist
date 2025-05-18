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
public final class DeleteAllEditorTest {

    private AbstractEditor deleteAllEditor;

    @Before
    public void setUp() {
        final CoreTestComponent testComponent = TestComponentFactory.createTestComponent();

        this.deleteAllEditor = testComponent.deleteAllEditor();
    }

    @Test
    public void testGetActionListener() {
        ActionListener actionListener =
                this.deleteAllEditor.getActionListener(0);
        assertThat(actionListener, notNullValue());
        actionListener.actionPerformed(null);
    }
}
