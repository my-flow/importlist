package com.moneydance.modules.features.importlist;

import dagger.Module;

/**
 * @author Florian J. Breunig
 */
@Module(includes = {
    com.moneydance.modules.features.importlist.bootstrap.FactoryModule.class,
    com.moneydance.modules.features.importlist.controller.FactoryModule.class,
    com.moneydance.modules.features.importlist.io.FactoryModule.class,
    com.moneydance.modules.features.importlist.util.FactoryModule.class
})
class CoreModule {
}
