package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.util.Settings;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.text.StringSubstitutor;

/**
 * @author Florian J. Breunig
 */
final class ImportOneOperation implements FileOperation {

    private final Context context;
    private final FileFilter transactionFileFilter;
    private final Settings settings;

    ImportOneOperation(
            final Context argContext,
            final FileFilter argTransactionFileFilter,
            final Settings argSettings) {
        this.context = argContext;
        this.transactionFileFilter = argTransactionFileFilter;
        this.settings = argSettings;
    }

    @Override
    public void showWarningAndExecute(final List<File> files) {
        this.execute(files);
    }

    @Override
    public void execute(final List<File> files) {
        final File file = files.get(0);
        Map<String, String> valuesMap = new ConcurrentHashMap<>(1);
        valuesMap.put("filename", file.getAbsolutePath());

        String uriScheme = "";
        if (this.transactionFileFilter.accept(file)) {
            uriScheme = this.settings.getTransactionFileImportUriScheme();
        }

        final StringSubstitutor sub = new StringSubstitutor(valuesMap);
        final String resolvedUri = sub.replace(uriScheme);

        // Import the file by calling the URI
        this.context.showURL(resolvedUri);
    }
}
