package com.moneydance.modules.features.importlist.io;

import java.io.File;
import java.util.List;


/**
 * A command that can be executed on the file system.
 *
 * @author Florian J. Breunig
 */
public interface FileOperation {

    void showWarningAndExecute(List<File> files);

    void execute(List<File> files);
}
