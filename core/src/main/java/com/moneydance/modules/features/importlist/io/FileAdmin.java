// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2018 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package com.moneydance.modules.features.importlist.io;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.modules.features.importlist.util.Helper;

import java.io.File;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * This core class coordinates and delegates operations on the file system.
 *
 * @author Florian J. Breunig
 */
public final class FileAdmin extends Observable implements Observer {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(FileAdmin.class.getName());

    private final FeatureModuleContext context;
    private final AbstractDirectoryChooser directoryChooser;
    private final DirectoryValidator directoryValidator;
    private final IOFileFilter transactionFileFilter;
    private final IOFileFilter readableFileFilter;
    private final TransactionFileListener listener;
    private final FileAlterationMonitor monitor;
    private final FileContainer fileContainer;
    @Nullable private FileAlterationObserver observer;
    private boolean isMonitorRunning;

    public FileAdmin(final File baseDirectory, final FeatureModuleContext argContext) {
        super();
        this.context = argContext;
        this.directoryChooser = new DefaultDirectoryChooser(baseDirectory);
        this.directoryValidator = DirectoryValidator.INSTANCE;
        this.transactionFileFilter = new SuffixFileFilter(
                Helper.INSTANCE.getSettings().getTransactionFileExtensions(),
                IOCase.INSENSITIVE);
        this.readableFileFilter = FileFilterUtils.and(
                CanReadFileFilter.CAN_READ,
                this.transactionFileFilter);

        this.listener = new TransactionFileListener();
        this.listener.addObserver(this);
        this.monitor = new FileAlterationMonitor(Helper.INSTANCE.getSettings().getMonitorInterval());

        this.fileContainer = new FileContainer(this.readableFileFilter);
    }

    public void chooseBaseDirectory() {
        LOG.info("Choosing new base directory.");
        this.directoryChooser.chooseBaseDirectory();
        if (this.observer != null) {
            this.monitor.removeObserver(this.observer);
        }
        this.setFileMonitorToCurrentImportDir();
        this.startMonitor();
        this.setChanged();
        this.notifyObservers();
    }

    public void checkValidBaseDirectory() {
        this.getBaseDirectory().ifPresent(baseDirectory -> {
            if (this.directoryValidator.isValidDirectory(baseDirectory)) {
                return;
            }
            LOG.warning(String.format(
                    "Could not open directory %s",
                    baseDirectory.getAbsolutePath()));
            final String errorMessage = Helper.INSTANCE.getLocalizable().
                    getErrorMessageBaseDirectory(baseDirectory.getName());
            final JLabel errorLabel = new JLabel(errorMessage);
            errorLabel.setLabelFor(null);
            JOptionPane.showMessageDialog(
                    null, // no parent component
                    errorLabel,
                    null, // no title
                    JOptionPane.ERROR_MESSAGE);

            this.directoryChooser.reset();
        });
    }

    public void reloadFiles() {
        synchronized (FileAdmin.class) {
            try {
                final File baseDirectory = this.getBaseDirectory().orElseThrow(AssertionError::new);
                this.fileContainer.reloadFiles(baseDirectory);
            } catch (IllegalArgumentException e) {
                final String message = e.getMessage();
                if (message != null) {
                    LOG.log(Level.WARNING, message, e);
                }
            }
        }
    }

    public Optional<File> getBaseDirectory() {
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
        if (!this.getBaseDirectory().isPresent()) {
            return;
        }
        LOG.config("Starting the directory monitor.");
        this.setFileMonitorToCurrentImportDir();
        try {
            this.monitor.start();
            this.isMonitorRunning = true;
        } catch (Exception e) {
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.WARNING, message, e);
            }
        }
    }

    /**
     * Stop monitoring the current import directory.
     */
    public void stopMonitor() {
        if (!this.isMonitorRunning) {
            return;
        }
        LOG.config("Stopping the directory monitor.");
        // ESCA-JAVA0166:
        try {
            this.monitor.stop(0);
            this.isMonitorRunning = false;
        } catch (Exception e) {
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.WARNING, message, e);
            }
        }
    }

    public void importAllRows() {
        FileOperation importAllOperation = new ImportAllOperation(
                this.context,
                this.transactionFileFilter);
        importAllOperation.showWarningAndExecute(this.fileContainer);
        this.setChanged();
        this.notifyObservers();
    }

    public void importRow(final int rowNumber) {
        if (rowNumber >= this.fileContainer.size()) {
            return;
        }
        FileOperation importOneOperation = new ImportOneOperation(
                this.context,
                this.transactionFileFilter);
        importOneOperation.showWarningAndExecute(
                Collections.singletonList(this.fileContainer.get(rowNumber)));
        this.setChanged();
        this.notifyObservers();
    }

    public void deleteAllRows() {
        if (this.fileContainer.isEmpty()) {
            return;
        }
        FileOperation deleteAllOperation = new DeleteAllOperation();
        deleteAllOperation.showWarningAndExecute(this.fileContainer);
        this.setChanged();
        this.notifyObservers();
    }

    public void deleteRow(final int rowNumber) {
        if (rowNumber >= this.fileContainer.size()) {
            return;
        }
        FileOperation deleteOneOperation = new DeleteOneOperation();
        deleteOneOperation.showWarningAndExecute(
                Collections.singletonList(this.fileContainer.get(rowNumber)));
        this.setChanged();
        this.notifyObservers();
    }

    private void setFileMonitorToCurrentImportDir() {
        if (!this.getBaseDirectory().isPresent()) {
            return;
        }
        this.observer = new FileAlterationObserver(
                this.getBaseDirectory().get(),
                this.readableFileFilter,
                IOCase.SYSTEM);
        this.observer.addListener(this.listener);
        this.monitor.addObserver(this.observer);
    }

    public FileContainer getFileContainer() {
        return fileContainer;
    }
}
