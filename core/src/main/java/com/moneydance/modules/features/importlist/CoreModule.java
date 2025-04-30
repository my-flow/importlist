package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.di.ImportListModule;

import dagger.Module;

/**
 * @author Florian J. Breunig
 */
@Module(includes = {
    AllFactoryModules.class,
    ImportListModule.class
})
class CoreModule {
}
