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

import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * This class is used to run the extension as a stand-alone application from
 * the console or from an IDE. It allows for fast feedback during the
 * development process.
 */
final class ConsoleRunner {

    /**
     * Private constructor prevents this class from being instantiated.
     */
    private ConsoleRunner() {
    }

    /**
     * This method is called directly from the console.
     * The first parameter may specify the base directory, but may as well
     * be omitted.
     * @param args First element specifies the base directory
     */
    public static void main(final String[] args) {
        String baseDirectory = null;
        if (args != null && args.length > 0) {
            baseDirectory = args[0];
        }

        Main main = new Main(baseDirectory);
        main.init();
        HomePageView homePageView = main.getHomePageView();
        homePageView.refresh();

        Preferences prefs = Preferences.getInstance();
        JFrame frame = new JFrame();
        frame.setTitle(prefs.getExtensionName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent guiView = homePageView.getGUIView(null);
        frame.setContentPane(guiView);
        frame.setSize(guiView.getPreferredSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
