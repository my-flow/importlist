package com.moneydance.modules.features.importlist;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public final class Start {

   /**
    * Private constructor prevents this class from being instantiated.
    */
   private Start() {
   }


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
