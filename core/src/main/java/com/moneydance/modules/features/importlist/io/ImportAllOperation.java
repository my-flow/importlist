package com.moneydance.modules.features.importlist.io;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author Florian J. Breunig
 */
final class ImportAllOperation implements FileOperation {

    private final FileOperation importOneOperation;

    ImportAllOperation(final FileOperation argImportOneOperation) {
        super();
        this.importOneOperation = argImportOneOperation;
    }

    @Override
    public void showWarningAndExecute(final List<File> files) {
        this.execute(files);
    }

    @Override
    public void execute(final List<File> files) {
        files.forEach(file -> this.importOneOperation.execute(Collections.singletonList(file)));
    }
}
