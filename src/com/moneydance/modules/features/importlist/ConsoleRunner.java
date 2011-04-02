package com.moneydance.modules.features.importlist;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * This class is used to run the extension as a stand-alone application from
 * the console or from an IDE. It allows for fast feedback during the
 * development process.
 *
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public final class ConsoleRunner {

   /**
    * Private constructor prevents this class from being instantiated.
    */
   private ConsoleRunner() {
   }


   /**
    * @param args command The first parameter specifies the base directory
    */
   public static void main(final String[] args) {
      String baseDirectory = null;
      if (args != null && args.length > 0) {
         baseDirectory = args[0];
      }

      Main main = new Main(baseDirectory);
      View view = main.getView();
      view.refresh();

      JFrame frame = new JFrame();
      frame.setTitle(Constants.EXTENSION_NAME);
      frame.setIconImage(main.getIconImage());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JComponent guiView = view.getGUIView(null);
      frame.setContentPane(guiView);
      frame.setSize(guiView.getPreferredSize());
      frame.setLocation(
         (frame.getToolkit().getScreenSize().width  - frame.getWidth())  / 2,
         (frame.getToolkit().getScreenSize().height - frame.getHeight()) / 2);

      frame.setVisible(true);
  }
}
