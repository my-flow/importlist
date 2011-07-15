/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
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

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * This <code>FileAlterationListener</code> implementation is notified about
 * relevant modifications in the file system. It propagates these modifications
 * to the <code>FileAdministration</code>.
 */
class TransactionFileListener extends FileAlterationListenerAdaptor {

    /**
     * Static initialization of class-dependent logger.
     */
    private static Logger log = Logger.getLogger(TransactionFileListener.class);

    private final FileAdministration fileAdministration;
    private boolean dirty;

    TransactionFileListener(final FileAdministration argFileAdministration) {
        Validate.notNull(argFileAdministration,
                "argFileAdministration can't be null");
        this.fileAdministration = argFileAdministration;
    }

    @Override
    public final void onStart(final FileAlterationObserver observer) {
        log.debug("Checking the base directory for changes");
        this.dirty = false;
    }

    @Override
    public final void onFileCreate(final File file) {
        this.dirty = true;
    }

    @Override
    public final void onFileChange(final File file) {
        this.dirty = true;
    }

    @Override
    public final void onFileDelete(final File file) {
        this.dirty = true;
    }

    @Override
    public final void onStop(final FileAlterationObserver observer) {
        if (this.dirty) {
            log.info("Base directory contains changes.");
            this.fileAdministration.setDirty(true);
        }
    }
}
