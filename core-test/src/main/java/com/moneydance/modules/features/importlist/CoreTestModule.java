package com.moneydance.modules.features.importlist;

import dagger.Module;

/**
 * @author Florian J. Breunig
 */
@Module(includes = {
    com.moneydance.modules.features.importlist.controller.TestModule.class,
    com.moneydance.modules.features.importlist.datetime.TestModule.class,
    com.moneydance.modules.features.importlist.table.TestModule.class,
    com.moneydance.modules.features.importlist.util.TestModule.class
})
public final class CoreTestModule {
}
