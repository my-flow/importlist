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

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import com.moneydance.modules.features.importlist.util.Localizable;
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

    private final Settings                  settings;
    private final Localizable               localizable;
    private final FeatureModuleContext      context;
    private final DirectoryChooser          directoryChooser;
    private final AbstractFileFilter        transactionFileFilter;
    private final AbstractFileFilter        textFileFilter;
    private final AbstractFileFilter        readableFileFilter;
    private FileAlterationObserver          observer;
    private final TransactionFileListener   listener;
    private final FileAlterationMonitor     monitor;
    private final List<File>                files;

    public FileAdmin(final String baseDirectory,
            final FeatureModuleContext argContext) {
        this.settings         = Helper.getSettings();
        this.localizable      = Helper.getLocalizable();
        this.context          = argContext;
        this.directoryChooser = new DirectoryChooser(baseDirectory);
        this.transactionFileFilter = new SuffixFileFilter(
                this.settings.getTransactionFileExtensions(),
                IOCase.INSENSITIVE);
        this.textFileFilter = new SuffixFileFilter(
                this.settings.getTextFileExtensions(),
                IOCase.INSENSITIVE);
        this.readableFileFilter = new AndFileFilter(
                CanReadFileFilter.CAN_READ,
                new OrFileFilter(
                        this.transactionFileFilter,
                        this.textFileFilter));

        this.listener = new TransactionFileListener();
        this.listener.addObserver(this);
        this.monitor  = new FileAlterationMonitor(
                this.settings.getMonitorInterval());

        this.files = Collections.synchronizedList(new ArrayList<File>());
    }

    public void resetBaseDirectory() {
        LOG.info("Resetting base directory.");
        this.directoryChooser.reset();
        this.monitor.removeObserver(this.observer);
        this.setFileMonitorToCurrentImportDir();
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
        LOG.debug("Starting the directory monitor.");
        this.setFileMonitorToCurrentImportDir();
        try {
            this.monitor.start();
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    /**
     * Stop monitoring the current import directory.
     */
    public void stopMonitor() {
        LOG.debug("Stopping the directory monitor.");
        try {
            this.monitor.stop(0);
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    public void importAllRows() {
        FileOperation fileImporter = new FileImporter(
                this.context,
                this.transactionFileFilter,
                this.textFileFilter);

        for (final File file : this.files) {
            try {
                fileImporter.perform(file);
            } catch (IOException e) {
                LOG.warn(e.getMessage(), e);
            }
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void importRow(final int rowNumber) {
        if (rowNumber >= this.files.size()) {
            return;
        }
        final File file = this.files.get(rowNumber);
        try {
            FileOperation fileImporter = new FileImporter(
                    this.context,
                    this.transactionFileFilter,
                    this.textFileFilter);
            fileImporter.perform(file);
        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void deleteAllRows() {
        if (this.files.isEmpty()) {
            return;
        }
        if (this.showWarningBeforeDeletingAllFiles(this.files.size())) {
            FileOperation fileDeleter = new FileDeleter();
            for (final File file : this.files) {
                try {
                    fileDeleter.perform(file);
                } catch (IOException e) {
                    LOG.warn(e.getMessage(), e);
                    final String errorMessage =
                     this.localizable.getErrorMessageDeleteFile(file.getName());
                    final Object errorLabel = new JLabel(errorMessage);
                    JOptionPane.showMessageDialog(
                            null, // no parent component
                            errorLabel,
                            null, // no title
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            LOG.info("Canceled deleting all files");
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void deleteRow(final int rowNumber) {
        if (rowNumber >= this.files.size()) {
            return;
        }
        final File file = this.files.get(rowNumber);
        if (this.showWarningBeforeDeletingOneFile(file)) {
            try {
                new FileDeleter().perform(file);
            } catch (IOException e) {
                LOG.warn("Could not delete file " + file.getAbsoluteFile());
                final String errorMessage =
                    this.localizable.getErrorMessageDeleteFile(file.getName());
                final Object errorLabel = new JLabel(errorMessage);
                JOptionPane.showMessageDialog(
                        null, // no parent component
                        errorLabel,
                        null, // no title
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            LOG.info("Canceled deleting file " + file.getAbsoluteFile());
        }
        this.setChanged();
        this.notifyObservers();
    }

    private boolean showWarningBeforeDeletingOneFile(final File file) {
        final String message =
           this.localizable.getConfirmationMessageDeleteOneFile(file.getName());
        final Object confirmationLabel = new JLabel(message);
        final Image image = Helper.getIconImage();
        Icon  icon  = null;
        if (image != null) {
            icon = new ImageIcon(image);
        }
        final Object[] options = {
                this.localizable.getOptionDeleteFile(),
                this.localizable.getOptionCancel()
        };

        final int choice = JOptionPane.showOptionDialog(
                null, // no parent component
                confirmationLabel,
                null, // no title
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                options,
                options[1]);

        return choice == 0;
    }

    private boolean showWarningBeforeDeletingAllFiles(final int size) {
        final String message =
            this.localizable.getConfirmationMessageDeleteAllFiles(size);
        final Object confirmationLabel = new JLabel(message);
        final Image image = Helper.getIconImage();
        Icon  icon  = null;
        if (image != null) {
            icon = new ImageIcon(image);
        }
        final Object[] options = {
                this.localizable.getOptionDeleteFile(),
                this.localizable.getOptionCancel()
        };

        final int choice = JOptionPane.showOptionDialog(
                null, // no parent component
                confirmationLabel,
                null, // no title
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                options,
                options[1]);

        return choice == 0;
    }

    private void setFileMonitorToCurrentImportDir() {
        this.observer  = new FileAlterationObserver(
                this.getBaseDirectory(),
                this.readableFileFilter,
                IOCase.SYSTEM);
        this.observer.addListener(this.listener);
        this.monitor.addObserver(this.observer);
    }
}
