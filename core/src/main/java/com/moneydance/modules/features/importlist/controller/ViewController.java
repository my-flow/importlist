package com.moneydance.modules.features.importlist.controller;

/**
 * @author Florian J. Breunig
 */
public interface ViewController {

    void chooseBaseDirectory();

    void setActive(boolean active);

    void cleanup();
}
