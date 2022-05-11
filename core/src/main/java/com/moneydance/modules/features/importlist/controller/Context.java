package com.moneydance.modules.features.importlist.controller;

/**
 * @author Florian J. Breunig
 */
public interface Context {

    void showURL(String resolvedUri);

    void registerFeature(String parameters, String buttonText);
}
