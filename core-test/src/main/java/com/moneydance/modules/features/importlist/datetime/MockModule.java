package com.moneydance.modules.features.importlist.datetime;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Florian J. Breunig
 */
@Module
public final class MockModule {

    @Provides
    @Named("date")
    DateFormatter provideDateFormatter() {
        return new DateFormatterMock();
    }

    @Provides
    @Named("time")
    DateFormatter provideTimeFormatter() {
        return new TimeFormatterMock();
    }
}
