package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.io.FileOperation;
import com.moneydance.modules.features.importlist.table.AbstractEditor;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.table.LabelModifiedRenderer;

/**
 * Test component interface that extends CoreComponent with additional
 * methods required for testing.
 *
 * @author Florian J. Breunig
 */
public interface CoreTestComponent extends CoreComponent {

    ColumnFactory columnFactory();

    @Override
    FileAdmin fileAdmin();

    @Override
    FileContainer fileContainer();

    // This returns LabelModifiedRenderer for API compatibility
    LabelModifiedRenderer labelNameRenderer();

    LabelModifiedRenderer labelModifiedRenderer();

    FileOperation importOneOperation();

    FileOperation importAllOperation();

    AbstractEditor importAllEditor();

    AbstractEditor importOneEditor();

    AbstractEditor deleteAllEditor();

    AbstractEditor deleteOneEditor();
}
