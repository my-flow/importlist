package com.moneydance.modules.features.importlist.io;

import java.io.File;
import java.util.List;

/**
 * A mock implementation of FileOperation for testing purposes.
 *
 * @author Florian J. Breunig
 */
public final class MockFileOperation implements FileOperation {

    private final String name;

    /**
     * Constructs a mock file operation with the given name.
     *
     * @param argName The name to identify this operation
     */
    public MockFileOperation(final String argName) {
        this.name = argName;
    }

    /**
     * Shows a mock warning and executes the operation.
     *
     * @param files Files to operate on
     */
    @Override
    public void showWarningAndExecute(final List<File> files) {
        // Mock implementation does nothing
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MockFileOperation.class.getName());
        if (logger.isLoggable(java.util.logging.Level.FINE)) {
            logger.fine(String.format("MockFileOperation %s.showWarningAndExecute called with %d files",
                this.name, files.size()));
        }
    }

    /**
     * Executes the operation directly without warning.
     *
     * @param files Files to operate on
     */
    @Override
    public void execute(final List<File> files) {
        // Mock implementation does nothing
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MockFileOperation.class.getName());
        if (logger.isLoggable(java.util.logging.Level.FINE)) {
            logger.fine("MockFileOperation " + this.name + ".execute called with " + files.size() + " files");
        }
    }

    @Override
    public String toString() {
        return "MockFileOperation[" + this.name + "]";
    }
}
