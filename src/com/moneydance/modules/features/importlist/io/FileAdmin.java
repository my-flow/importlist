/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Settings;

/**
 * This core class coordinates and delegates operations on the file system.
 *
 * @author Florian J. Breunig
 */
public final class FileAdmin extends Observable implements Observer {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(FileAdmin.class);

    private final FeatureModuleContext      context;
    private final DirectoryChooser          directoryChooser;
    private final AbstractFileFilter        transactionFileFilter;
    private final AbstractFileFilter        textFileFilter;
    private final AbstractFileFilter        readableFileFilter;
    private final TransactionFileListener   listener;
    private final FileAlterationMonitor     monitor;
    private final List<File>                files;
    private       FileAlterationObserver    observer;
    private       boolean                   isMonitorRunning;

    public FileAdmin(final String baseDirectory,
            final FeatureModuleContext argContext) {
        this.context          = argContext;
        this.directoryChooser = new DirectoryChooser(baseDirectory);
        Settings settings = Helper.getSettings();
        this.transactionFileFilter = new SuffixFileFilter(
                settings.getTransactionFileExtensions(),
                IOCase.INSENSITIVE);
        this.textFileFilter = new SuffixFileFilter(
                settings.getTextFileExtensions(),
                IOCase.INSENSITIVE);
        this.readableFileFilter = new AndFileFilter(
                CanReadFileFilter.CAN_READ,
                new OrFileFilter(
                        this.transactionFileFilter,
                        this.textFileFilter));

        this.listener = new TransactionFileListener();
        this.listener.addObserver(this);
        this.monitor  = new FileAlterationMonitor(
                settings.getMonitorInterval());

        this.files = Collections.synchronizedList(new ArrayList<File>());
    }

    public void chooseBaseDirectory() {
        LOG.info("Choosing new base directory.");
        this.directoryChooser.chooseBaseDirectory();
        this.monitor.removeObserver(this.observer);
        this.setFileMonitorToCurrentImportDir();
        this.startMonitor();
        this.setChanged();
        this.notifyObservers();
    }

    public List<File> getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    public synchronized void reloadFiles() {
        this.files.clear();
        try {
            final Collection<File> collection = FileUtils.listFiles(
                    this.getBaseDirectory(),
                    this.readableFileFilter,
                    null); // ignore subdirectories
            this.files.addAll(collection);
        } catch (IllegalArgumentException e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    public File getBaseDirectory() {
        return this.directoryChooser.getBaseDirectory();
    }

    @Override
    public void update(final Observable observable, final Object object) {
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Start monitoring the current import directory.
     */
    public void startMonitor() {
        if (this.isMonitorRunning) {
            return;
        }
        if (this.getBaseDirectory() == null) {
            return;
        }
        LOG.debug("Starting the directory monitor.");
        this.setFileMonitorToCurrentImportDir();
        try {
            this.monitor.start();
            this.isMonitorRunning = true;
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    /**
     * Stop monitoring the current import directory.
     */
    public void stopMonitor() {
        if (!this.isMonitorRunning) {
            return;
        }
        LOG.debug("Stopping the directory monitor.");
        try {
            this.monitor.stop(0);
            this.isMonitorRunning = false;
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    public void importAllRows() {
        FileOperation importAllOperation = new ImportAllOperation(
                this.context,
                this.transactionFileFilter,
                this.textFileFilter);
        importAllOperation.showWarningAndPerform(this.files);
        this.setChanged();
        this.notifyObservers();
    }

    public void importRow(final int rowNumber) {
        if (rowNumber >= this.files.size()) {
            return;
        }
        FileOperation importOneOperation = new ImportOneOperation(
                this.context,
                this.transactionFileFilter,
                this.textFileFilter);
        importOneOperation.showWarningAndPerform(
                Collections.singletonList(this.files.get(rowNumber)));
        this.setChanged();
        this.notifyObservers();
    }

    public void deleteAllRows() {
        if (this.files.isEmpty()) {
            return;
        }
        FileOperation deleteAllOperation = new DeleteAllOperation();
        deleteAllOperation.showWarningAndPerform(this.files);
        this.setChanged();
        this.notifyObservers();
    }

    public void deleteRow(final int rowNumber) {
        if (rowNumber >= this.files.size()) {
            return;
        }
        FileOperation deleteOneOperation = new DeleteOneOperation();
        deleteOneOperation.showWarningAndPerform(
                Collections.singletonList(this.files.get(rowNumber)));
        this.setChanged();
        this.notifyObservers();
    }

    private void setFileMonitorToCurrentImportDir() {
        if (this.getBaseDirectory() == null) {
            return;
        }
        this.observer  = new FileAlterationObserver(
                this.getBaseDirectory(),
                this.readableFileFilter,
                IOCase.SYSTEM);
        this.observer.addListener(this.listener);
        this.monitor.addObserver(this.observer);
    }
}
