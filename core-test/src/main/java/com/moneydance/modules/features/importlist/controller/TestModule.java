package com.moneydance.modules.features.importlist.controller;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Florian J. Breunig
 */
@Module
public final class TestModule {

    @Provides
    Context provideContext() {
        return new ContextMock();
    }

    @Provides
    @Singleton
    ViewController provideViewController() {
        throw new IllegalStateException("Cannot provide ViewController instance in core-test project");
    }
}
