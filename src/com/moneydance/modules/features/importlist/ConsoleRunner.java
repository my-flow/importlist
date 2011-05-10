package com.moneydance.modules.features.importlist;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.moneydance.apps.md.view.HomePageView;

/**
 * This class is used to run the extension as a stand-alone application from
 * the console or from an IDE. It allows for fast feedback during the
 * development process.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
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

        JFrame frame = new JFrame();
        frame.setTitle(Constants.EXTENSION_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent guiView = homePageView.getGUIView(null);
        frame.setContentPane(guiView);
        frame.setSize(guiView.getPreferredSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
