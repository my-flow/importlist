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

package com.moneydance.modules.features.importlist;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moneydance.modules.features.importlist.util.Helper;


/**
 * This class is used to run the extension as a stand-alone application from
 * the console or from an IDE. It allows for fast feedback during the
 * development process.
 */
final class ConsoleRunner {

    static {
        Helper.loadLoggerConfiguration();
    }

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(ConsoleRunner.class);

    /**
     * Restrictive constructor.
     */
    private ConsoleRunner() {
        // Prevents this class from being instantiated from the outside.
    }

    /**
     * This method is called directly from the console.
     * The first parameter may specify the base directory, but may as well
     * be omitted.
     * @param args First element specifies the base directory
     */
    public static void main(final String[] args) {
        String baseDirectory = null;

        for (String arg : args) {
            if ("-d".equals(arg)) {
                LOG.warn("debugging...");
                com.moneydance.apps.md.controller.Main.DEBUG = true;
                continue;
            }
            if (arg.startsWith("-basedirectory=")) {
                baseDirectory = arg.substring(
                        "-basedirectory=".length());
                continue;
            }
            LOG.warn("ignoring argument: " + arg);
        }

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        final String argBaseDirectory = baseDirectory;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI(argBaseDirectory);
            }
        });
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     * @param baseDirectory The base directory where the transaction files
     * reside.
     */
    private static void createAndShowGUI(final String baseDirectory) {
        final Main main = new Main();
        main.setBaseDirectory(baseDirectory);
        main.init();

        final JFrame frame = new JFrame(main.getDisplayName());
        final JComponent guiView = main.getHomePageView().getGUIView(null);
        frame.setContentPane(guiView);
        frame.setMinimumSize(guiView.getMinimumSize());
        frame.setPreferredSize(guiView.getPreferredSize());
        frame.setMaximumSize(guiView.getMaximumSize());
        frame.setSize(frame.getPreferredSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
