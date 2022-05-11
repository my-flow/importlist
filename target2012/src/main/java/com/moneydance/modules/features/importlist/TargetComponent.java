package com.moneydance.modules.features.importlist;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Florian J. Breunig
 */
@Component(modules = {
    CoreModule.class,
    TargetModule.class
})
@Singleton
interface TargetComponent extends CoreComponent {
}
