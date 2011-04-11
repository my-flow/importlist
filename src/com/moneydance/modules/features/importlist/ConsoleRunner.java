package com.moneydance.modules.features.importlist;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.moneydance.apps.md.view.HomePageView;

/**
 * This class is used to run the extension as a stand-alone application from
 * the console or from an IDE. It allows for fast feedback during the
 * development process.
 *
 * @author Florian J. Breunig, http://www.my-flow.com
 */
public final class ConsoleRunner {

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
      HomePageView homePageView = main.getHomePageView();
      homePageView.refresh();

      JFrame frame = new JFrame();
      frame.setTitle(Constants.EXTENSION_NAME);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JComponent guiView = homePageView.getGUIView(null);
      frame.setContentPane(guiView);
      frame.setSize(guiView.getPreferredSize());
      frame.setLocation(
         (frame.getToolkit().getScreenSize().width  - frame.getWidth())  / 2,
         (frame.getToolkit().getScreenSize().height - frame.getHeight()) / 2);
      frame.setVisible(true);
  }
}
