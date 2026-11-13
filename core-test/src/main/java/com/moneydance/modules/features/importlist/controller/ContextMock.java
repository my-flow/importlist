package com.moneydance.modules.features.importlist.controller;

import java.awt.Frame;
import java.util.Optional;

/**
 * @author Florian J. Breunig
 */
public final class ContextMock implements Context {

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
}
