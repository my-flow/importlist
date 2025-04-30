package com.moneydance.modules.features.importlist;

import dagger.Module;

/**
 * Consolidated module that includes all factory modules.
 *
 * This approach reduces the fragmentation in CoreModule while maintaining
 * the package-level access restrictions of the original implementation.
 *
 * @author Florian J. Breunig
 */
@Module(includes = {
    com.moneydance.modules.features.importlist.bootstrap.FactoryModule.class,
    com.moneydance.modules.features.importlist.controller.FactoryModule.class,
    com.moneydance.modules.features.importlist.io.FactoryModule.class,
    com.moneydance.modules.features.importlist.util.FactoryModule.class
})
public final class AllFactoryModules {
    // This module doesn't provide any dependencies directly
    // It only aggregates other modules
}
