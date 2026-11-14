package com.moneydance.modules.features.importlist.controller;

import java.awt.Frame;
import java.util.Optional;

/**
 * @author Florian J. Breunig
 */
public interface Context {

    void showURL(String resolvedUri);

    void registerFeature(String parameters, String buttonText);

    /**
     * @return Moneydance's main window, if available.
     */
    Optional<Frame> getMainFrame();

    /**
     * Runs a task on a separate thread once Moneydance has finished the
     * module operation in progress, such as an update or an uninstall.
     *
     * @param task The task to run.
     */
    void invokeAfterModuleOperation(Runnable task);

    /**
     * @return Whether Moneydance lists this extension as installed.
     */
    boolean isModuleInstalled();

    /**
     * Writes Moneydance's preferences to disk.
     */
    void savePreferences();
}
