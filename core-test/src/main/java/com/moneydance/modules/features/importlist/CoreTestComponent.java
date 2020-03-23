// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2020 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

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
