package com.moneydance.modules.features.importlist.io;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;



public class TransactionFileListener extends FileAlterationListenerAdaptor {

   private final FileAdministration fileAdministration;


   public TransactionFileListener(
         final FileAdministration argFileAdministation) {
      this.fileAdministration = argFileAdministation;
   }


   @Override
   public final void onFileCreate(final File file) {
      this.fileAdministration.setDirty(true);
   }


   @Override
   public final void onFileChange(final File file) {
      this.fileAdministration.setDirty(true);
   }


   @Override
   public final void onFileDelete(final File file) {
      this.fileAdministration.setDirty(true);
   }
}
