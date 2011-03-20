package com.moneydance.modules.features.importlist;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class ListItemFilenameFilter implements FileFilter {

   public final boolean accept(final File pathname) {
      if (pathname.isDirectory() || pathname.isHidden()) {
         return false;
      }

      for (String fileExtension : Constants.FILE_EXTENSIONS) {
         if (pathname.getName().matches("(?i).*\\.(" + fileExtension + ")$")) {
            return true;
         }
      }
      return false;
   }
}
