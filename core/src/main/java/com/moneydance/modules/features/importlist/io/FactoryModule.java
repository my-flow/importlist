package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;

/**
 * @author Florian J. Breunig
 */
@Module
public final class FactoryModule {

    @Provides
    AbstractDirectoryChooser provideAbstractDirectoryChooser(final Preferences prefs) {
        return new DefaultDirectoryChooser(prefs);
    }

    @Provides
    FileAlterationMonitor provideMonitor(final Settings settings) {
        return new FileAlterationMonitor(settings.getMonitorInterval());
    }

    @Provides
    @Named("transaction files")
    IOFileFilter provideTransactionFileFilter(final Settings settings) {
        return new SuffixFileFilter(settings.getTransactionFileExtensions(), IOCase.INSENSITIVE);
    }

    @Provides
    @Named("readable files")
    IOFileFilter provideReadableFileFilter(@Named("transaction files") final IOFileFilter transactionFileFilter) {
        return FileFilterUtils.and(CanReadFileFilter.CAN_READ, transactionFileFilter);
    }

    @Provides
    @Named("import all")
    FileOperation provideImportAllOperation(@Named("import one") final FileOperation importOneOperation) {
        return new ImportAllOperation(importOneOperation);
    }

    @Provides
    @Named("import one")
    FileOperation provideImportOneOperation(
            final Context context,
            @Named("transaction files") final IOFileFilter transactionFileFilter,
            final Settings settings) {
        return new ImportOneOperation(context, transactionFileFilter, settings);
    }

    @Provides
    @Named("delete all")
    FileOperation provideDeleteAllOperation(
            @Named("delete one") final FileOperation deleteOneOperation,
            final Settings settings,
            final Localizable localizable) {
        return new DeleteAllOperation(deleteOneOperation, settings, localizable);
    }

    @Provides
    @Named("delete one")
    FileOperation provideDeleteOneOperation(
            final Settings settings,
            final Localizable localizable) {
        return new DeleteOneOperation(settings, localizable);
    }
}
