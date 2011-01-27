package com.moneydance.modules.features.importlist;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class ListItemFilenameFilter implements FileFilter {

   public final boolean accept(final File pathname) {
      return pathname.isFile() && !pathname.isHidden();
   }
}
