package com.moneydance.modules.features.importlist;

import dagger.Module;

/**
 * @author Florian J. Breunig
 */
@Module(includes = {
    com.moneydance.modules.features.importlist.controller.MockModule.class,
    com.moneydance.modules.features.importlist.datetime.MockModule.class,
    com.moneydance.modules.features.importlist.table.MockModule.class,
    com.moneydance.modules.features.importlist.util.MockModule.class
})
public final class CoreTestModule {
}
