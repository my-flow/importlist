package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.util.ISettings;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;

/**
 * Utility class for creating file-related instances.
 *
 * @author Florian J. Breunig
 */
public final class FactoryModule {

    private FactoryModule() {
        // Private constructor to prevent instantiation
    }

    public static AbstractDirectoryChooser createDirectoryChooser(final Preferences prefs) {
        return new DefaultDirectoryChooser(prefs);
    }

    public static FileAlterationMonitor createMonitor(final ISettings settings) {
        return new FileAlterationMonitor(settings.getMonitorInterval());
    }

    public static IOFileFilter createTransactionFileFilter(final ISettings settings) {
        return new SuffixFileFilter(settings.getTransactionFileExtensions(), IOCase.INSENSITIVE);
    }

    public static IOFileFilter createReadableFileFilter(final IOFileFilter transactionFileFilter) {
        return FileFilterUtils.and(CanReadFileFilter.CAN_READ, transactionFileFilter);
    }

    public static FileOperation createImportAllOperation(final FileOperation importOneOperation) {
        return new ImportAllOperation(importOneOperation);
    }

    public static FileOperation createImportOneOperation(
            final Context context,
            final IOFileFilter transactionFileFilter,
            final ISettings settings) {
        return new ImportOneOperation(context, transactionFileFilter, settings);
    }

    public static FileOperation createDeleteAllOperation(
            final FileOperation deleteOneOperation,
            final ISettings settings,
            final Localizable localizable) {
        return new DeleteAllOperation(deleteOneOperation, settings, localizable);
    }

    public static FileOperation createDeleteOneOperation(
            final ISettings settings,
            final Localizable localizable) {
        return new DeleteOneOperation(settings, localizable);
    }

    public static FileAdmin createFileAdmin(
            final IOFileFilter readableFilter,
            final Context context,
            final ISettings settings,
            final Preferences prefs) {
        return new FileAdmin(readableFilter, context, settings, prefs);
    }
}
