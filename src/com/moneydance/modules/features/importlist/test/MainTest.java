package com.moneydance.modules.features.importlist.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.modules.features.importlist.Main;

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
      this.main.importFile(0);
   }


   @Test
   public final void testDeleteFile() {
      this.main.deleteFile(0);
   }

}
