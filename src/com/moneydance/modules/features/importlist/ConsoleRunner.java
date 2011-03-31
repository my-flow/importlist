package com.moneydance.modules.features.importlist;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    * @param args command line arguments are not used
    */
   public static void main(final String[] args) {

      JFrame jFrame = new JFrame();
      Main main = new Main();
      main.init();
      main.getView().refresh();

      jFrame.setContentPane(main.getView().getGUIView(null));
      jFrame.setSize(main.getView().getGUIView(null).getPreferredSize());

      jFrame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(final WindowEvent e) {
             System.exit(0);
         }
      });
      jFrame.setVisible(true);
  }
}
