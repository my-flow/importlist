/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2013 Florian J. Breunig
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.moneydance.modules.features.importlist.io;

import java.io.File;
import java.util.Observable;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This <code>FileAlterationListener</code> implementation is notified about
 * relevant modifications in the file system. It propagates these modifications
 * to its <code>Observer</code>s.
 *
 * @author Florian J. Breunig
 */
final class TransactionFileListener
extends Observable implements FileAlterationListener {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG
        = LoggerFactory.getLogger(TransactionFileListener.class);

    @Override
    public void onStart(final FileAlterationObserver observer) {
        LOG.debug("Checking the base directory for changes.");
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
            LOG.info("Base directory has changes.");
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
