package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

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
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();

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
