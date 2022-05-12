package com.moneydance.modules.features.importlist.io;

import java.io.File;
import java.util.Observable;
import java.util.logging.Logger;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * This <code>FileAlterationListener</code> implementation is notified about
 * relevant modifications in the file system. It propagates these modifications
 * to its <code>Observer</code>s.
 *
 * @author Florian J. Breunig
 */
final class TransactionFileListener extends Observable implements FileAlterationListener {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(TransactionFileListener.class.getName());

    @Override
    public void onStart(final FileAlterationObserver observer) {
        LOG.config("Checking the base directory for changes."); //$NON-NLS-1$
        this.clearChanged();
    }

    @Override
    public void onFileCreate(final File file) {
        this.setChanged();
    }

    @Override
    public void onFileChange(final File file) {
        this.setChanged();
    }

    @Override
    public void onFileDelete(final File file) {
        this.setChanged();
    }

    @Override
    public void onStop(final FileAlterationObserver observer) {
        if (this.hasChanged()) {
            LOG.info("Base directory has changes."); //$NON-NLS-1$
            this.notifyObservers();
        }
    }

    @Override
    public void onDirectoryChange(final File directory) {
        // ignore changed directories
    }

    @Override
    public void onDirectoryCreate(final File directory) {
        // ignore created directories
    }

    @Override
    public void onDirectoryDelete(final File directory) {
        // ignore deleted directories
    }
}
