package com.moneydance.modules.features.importlist.io;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;

import com.moneydance.modules.features.importlist.util.Preferences;


/**
 * This class provides the functionality to choose, access and reset the
 * extension's base directory. The base directory is the directory in the file
 * system to be monitored. Choosing/resetting the base directory is reflected
 * in the user's preferences (if there are any).
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
class DirectoryChooser {

    private static final long serialVersionUID = -8581693236906919725L;
    private final Preferences prefs;
    private String baseDirectory;

    /**
     * @param argBaseDirectory set the base directory when executed as a stand-
     * alone application
     */
    DirectoryChooser(final String argBaseDirectory) {
        this.prefs          = Preferences.getInstance();
        this.baseDirectory  = argBaseDirectory;
        if (argBaseDirectory != null) {
            try {
                File baseDirectoryFile = new File(argBaseDirectory);
                this.baseDirectory     = baseDirectoryFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    final void reset() {
        this.displayFileChooser();
        this.saveDirectoryInPreferences();
    }

    final String getDirectory() {
        if (this.baseDirectory == null) {
            this.baseDirectory = this.prefs.getBaseDirectory();
        }

        if (this.baseDirectory == null) {
            this.displayFileChooser();
        }

        this.saveDirectoryInPreferences();

        return this.baseDirectory;
    }

    private void displayFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Please choose a base directory to monitor");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);

        chooser.setCurrentDirectory(FileUtils.getUserDirectory());
        if (this.baseDirectory != null) {
            File parentDirectory = new File(this.baseDirectory).getParentFile();
            chooser.setCurrentDirectory(parentDirectory);
        }

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            this.baseDirectory = chooser.getSelectedFile().getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            this.baseDirectory = chooser.getSelectedFile().getAbsolutePath();
        }
    }

    private void saveDirectoryInPreferences() {
        if (this.baseDirectory == null) {
            this.baseDirectory = this.prefs.getImportDirectory();
        }

        if (this.baseDirectory == null) {
            this.baseDirectory = FileUtils.getUserDirectoryPath();
        }

        this.prefs.setBaseDirectory(this.baseDirectory);
    }
}
