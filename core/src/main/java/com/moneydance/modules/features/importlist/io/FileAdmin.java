package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.bootstrap.Helper;

import java.io.File;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * This core class coordinates and delegates operations on the file system.
 *
 * @author Florian J. Breunig
 */
@Singleton
public final class FileAdmin extends Observable implements Observer {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(FileAdmin.class.getName());

    @Inject AbstractDirectoryChooser directoryChooser;
    @Inject DirectoryValidator directoryValidator;
    @Inject FileAlterationMonitor monitor;
    @Inject FileContainer fileContainer;
    @Inject @Named("import all") FileOperation importAllOperation;
    @Inject @Named("import one") FileOperation importOneOperation;
    @Inject @Named("delete all") FileOperation deleteAllOperation;
    @Inject @Named("delete one") FileOperation deleteOneOperation;
    @Inject @Named("readable files") IOFileFilter readableFileFilter;

    private final TransactionFileListener listener;
    @Nullable private FileAlterationObserver observer;
    private boolean isMonitorRunning;

    @Inject
    public FileAdmin() {
        super();
        this.listener = new TransactionFileListener();
        this.listener.addObserver(this);
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
            LOG.warning(() -> String.format("Could not open directory %s", baseDirectory.getAbsolutePath()));
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

    public FileContainer getFileContainer() {
        return this.fileContainer;
    }

    public void importAllRows() {
        this.importAllOperation.showWarningAndExecute(this.fileContainer);
        this.setChanged();
        this.notifyObservers();
    }

    public void importRow(final int rowNumber) {
        if (rowNumber >= this.fileContainer.size()) {
            return;
        }
        this.importOneOperation.showWarningAndExecute(
                Collections.singletonList(this.fileContainer.get(rowNumber)));
        this.setChanged();
        this.notifyObservers();
    }

    public void deleteAllRows() {
        if (this.fileContainer.isEmpty()) {
            return;
        }
        this.deleteAllOperation.showWarningAndExecute(this.fileContainer);
        this.setChanged();
        this.notifyObservers();
    }

    public void deleteRow(final int rowNumber) {
        if (rowNumber >= this.fileContainer.size()) {
            return;
        }
        this.deleteOneOperation.showWarningAndExecute(
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
}
