package com.moneydance.modules.features.importlist.io;

import java.io.File;
import java.io.FileFilter;

import com.moneydance.modules.features.importlist.Constants;

/**
 * Filter out files that cannot be imported, i.e. accept only predefined
 * file extensions when checking the file name.
 *
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class ListItemFilenameFilter implements FileFilter {

   public final boolean accept(final File pathname) {
      if (pathname.isDirectory() || pathname.isHidden()) {
         return false;
      }

      for (String fileExtension : Constants.FILE_EXTENSIONS) {
         // case-insensitive regular expression
         String regularExpression = "(?i).*\\.(" + fileExtension + ")$";
         if (pathname.getName().matches(regularExpression)) {
            return true;
         }
      }
      return false;
   }
}
