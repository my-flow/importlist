package com.moneydance.modules.features.importlist.controller;

import java.awt.Frame;
import java.util.Optional;

/**
 * @author Florian J. Breunig
 */
public final class ContextMock implements Context {

    private boolean moduleInstalled = true;
    private boolean preferencesSaved;

    @Override
    public void showURL(final String resolvedUri) {
        // ignore
    }

    @Override
    public void registerFeature(final String parameters, final String buttonText) {
        // ignore
    }

    @Override
    public Optional<Frame> getMainFrame() {
        return Optional.empty();
    }

    /**
     * Runs the task immediately on the calling thread.
     */
    @Override
    public void invokeAfterModuleOperation(final Runnable task) {
        task.run();
    }

    @Override
    public boolean isModuleInstalled() {
        return this.moduleInstalled;
    }

    /**
     * @param argModuleInstalled Whether {@link #isModuleInstalled()} reports
     * the extension as installed.
     */
    public void setModuleInstalled(final boolean argModuleInstalled) {
        this.moduleInstalled = argModuleInstalled;
    }

    @Override
    public void savePreferences() {
        this.preferencesSaved = true;
    }

    /**
     * @return Whether {@link #savePreferences()} has been called.
     */
    public boolean isPreferencesSaved() {
        return this.preferencesSaved;
    }
}
