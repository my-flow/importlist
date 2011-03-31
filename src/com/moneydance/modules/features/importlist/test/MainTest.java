package com.moneydance.modules.features.importlist.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.modules.features.importlist.Main;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class MainTest {

   private Main main;


   @Before
   public final void setUp() throws Exception {
      this.main = new Main();
      this.main.init();
   }


   @Test
   public final void testGetName() {
      assertNotNull("extension name must not be null",
            this.main.getName());
   }


   @Test
   public final void testInvokeString() {
      this.main.invoke(null);
      this.main.invoke("");
      this.main.invoke(":");
      this.main.invoke("?");
   }


   @Test
   public final void testImportFile() {
      this.main.getImportActionListener(0);
   }


   @Test
   public final void getDeleteActionListener() {
      this.main.getDeleteActionListener(0);
   }

}
