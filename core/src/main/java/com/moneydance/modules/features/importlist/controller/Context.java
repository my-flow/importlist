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
}
