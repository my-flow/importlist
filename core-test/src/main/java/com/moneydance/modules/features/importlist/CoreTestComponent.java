package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.io.FileOperation;
import com.moneydance.modules.features.importlist.table.AbstractEditor;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.table.LabelModifiedRenderer;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Florian J. Breunig
 */
@Component(modules = {
    CoreModule.class,
    CoreTestModule.class
})
@Singleton
public interface CoreTestComponent extends CoreComponent {

    ColumnFactory columnFactory();

    FileAdmin fileAdmin();

    FileContainer fileContainer();

    LabelModifiedRenderer labelNameRenderer();

    LabelModifiedRenderer labelModifiedRenderer();

    @Named("import one")
    FileOperation importOneOperation();

    @Named("import all")
    FileOperation importAllOperation();

    @Named("import all")
    AbstractEditor importAllEditor();

    @Named("import one")
    AbstractEditor importOneEditor();

    @Named("delete all")
    AbstractEditor deleteAllEditor();

    @Named("delete one")
    AbstractEditor deleteOneEditor();
}
