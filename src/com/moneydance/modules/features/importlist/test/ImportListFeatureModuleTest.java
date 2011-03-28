package com.moneydance.modules.features.importlist.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.modules.features.importlist.Main;

public class ImportListFeatureModuleTest {

   private Main featureModule;

   @Before
   public final void setUp() throws Exception {
      this.featureModule = new Main();
      this.featureModule.init();
   }


   @Test
   public final void testGetName() {
      assertNotNull("extension name must not be null",
            this.featureModule.getName());
   }


   @Test
   public final void testInvokeString() {
      this.featureModule.invoke(null);
      this.featureModule.invoke("");
      this.featureModule.invoke(":");
      this.featureModule.invoke("?");
   }


   @Test
   public final void testImportFile() {
      this.featureModule.importFile(0);
   }


   @Test
   public final void testDeleteFile() {
      this.featureModule.deleteFile(0);
   }

}
