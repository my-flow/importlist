package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.io.FileContainer;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Florian J. Breunig
 */
@Component(modules = {
    CoreModule.class,
    TargetTestModule.class,
})
@Singleton
public interface TargetTestComponent extends TargetComponent {

    FileContainer fileContainer();
}
