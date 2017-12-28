// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2017 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package com.moneydance.modules.features.importlist.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.infinitekind.util.StringUtils;

/**
 * This i18n class provides language-dependent strings such as labels and
 * messages.
 *
 * @author Florian J. Breunig
 */
public final class Localizable {

    private final ResourceBundle resourceBundle;

    Localizable(final String localizableResource, final Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(
                localizableResource,
                locale);
    }

    /**
     * @return The title of the dialog that is displayed to choose the base
     * directory.
     */
    public String getDirectoryChooserTitle() {
        return this.resourceBundle.getString("directory_chooser_title");
    }

    /**
     * @return Header of the "name" column.
     */
    public String getHeaderValueName() {
        final String headerValueName =
                this.resourceBundle.getString("header_value_name");
        return Helper.INSTANCE.getSettings().getIndentationPrefix()
                + headerValueName;
    }

    /**
     * @return Header of the "modified" column.
     */
    public String getHeaderValueModified() {
        final String headerValueModified =
                this.resourceBundle.getString("header_value_modified");
        return String.format("%s%s",
                Helper.INSTANCE.getSettings().getIndentationPrefix(),
                headerValueModified);
    }

    /**
     * @return Header of the "import" column.
     */
    public String getHeaderValueImport() {
        final String headerValueImport =
                this.resourceBundle.getString("header_value_import");
        return String.format("%s%s",
                Helper.INSTANCE.getSettings().getIndentationPrefix(),
                headerValueImport);
    }

    /**
     * @return Header of the "delete" column.
     */
    public String getHeaderValueDelete() {
        final String headerValueDelete =
                this.resourceBundle.getString("header_value_delete");
        return String.format("%s%s",
                Helper.INSTANCE.getSettings().getIndentationPrefix(),
                headerValueDelete);
    }

    /**
     * @return Label of the "import" button.
     */
    public String getLabelImportOneButton() {
        return this.resourceBundle.getString("label_import_one_button");
    }

    /**
     * @return Label of the "import all" button.
     */
    public String getLabelImportAllButton() {
        return this.resourceBundle.getString("label_import_all_button");
    }

    /**
     * @return Label of the "delete" button.
     */
    public String getLabelDeleteOneButton() {
        return this.resourceBundle.getString("label_delete_one_button");
    }

    /**
     * @return Label of the "delete all" button.
     */
    public String getLabelDeleteAllButton() {
        return this.resourceBundle.getString("label_delete_all_button");
    }

    /**
     * @param baseDirectory The base directory which could not be opened.
     * @return Error message to be displayed if the base directory could not be
     * opened.
     */
    public String getErrorMessageBaseDirectory(final String baseDirectory) {
        final String templateString = this.resourceBundle.getString(
                "error_message_base_directory");
      
        return StringUtils.replaceAll(templateString, "${import.dir}", getMarkupFilename(baseDirectory));
    }

    /**
     * @param filename The name of the file that is to be deleted.
     * @return Confirmation message to be displayed before a file will be
     *  deleted.
     */
    public String getConfirmationMessageDeleteOneFile(final String filename) {
        final String templateString = this.resourceBundle.getString(
                "confirmation_message_delete_one_file");

        return StringUtils.replaceAll(templateString, "${filename}", getMarkupFilename(filename));
    }

    /**
     * @param size The number of all files that are to be deleted.
     * @return Confirmation message to be displayed before all files will be
     *  deleted.
     */
    public String getConfirmationMessageDeleteAllFiles(final int size) {
        final String templateString = this.resourceBundle.getString(
                "confirmation_message_delete_all_files");
      return StringUtils.replaceAll(templateString, "${no.files}", String.valueOf(size));
    }

    /**
     * @param filename The name of the file that cannot be deleted.
     * @return Error message to be displayed if a file cannot be deleted.
     */
    public String getErrorMessageDeleteFile(final String filename) {
        final String templateString = this.resourceBundle.getString(
                "error_message_delete_file");
      return StringUtils.replaceAll(templateString, "${filename}", getMarkupFilename(filename));
    }

    /**
     * @return Delete button in confirmation message.
     */
    public String getOptionDeleteFile() {
        return this.resourceBundle.getString("option_delete_file");
    }

    /**
     * @return Cancel button in confirmation message.
     */
    public String getOptionCancel() {
        return this.resourceBundle.getString("option_cancel");
    }

    /**
     * @param baseDirectory The base directory which does not contain any files
     * to display.
     * @return Message to display if the list of files is empty.
     */
    public String getEmptyMessage(final String baseDirectory) {
        final String templateString = this.resourceBundle.getString(
                "empty_message");
      return StringUtils.replaceAll(templateString, "${import.dir}", baseDirectory);
    }

    private static String getMarkupFilename(final String filename) {
      return wrapLongWords(filename, Helper.INSTANCE.getSettings().getMessageFilenameLineMaxLength(), "<br>", true);
    }
    
    // mostly copied from apache commons-text implementation of WordUtils.wrap() implementation
    private static String wrapLongWords(String stringToWrap, int maxLineLength, String newLineStr, boolean wrapLongWords) {
      StringBuilder str = new StringBuilder();
      Pattern wrapOnPattern = Pattern.compile(" ");
      int origLength = stringToWrap.length();
      final StringBuilder wrappedLine = new StringBuilder(stringToWrap.length() + 32);
      int offset = 0;

      while (offset < origLength) {
        int spaceToWrapAt = -1;
        Matcher matcher = wrapOnPattern.matcher(str.substring(offset, Math.min(offset + maxLineLength + 1, origLength)));
        if (matcher.find()) {
          if (matcher.start() == 0) {
            offset += matcher.end();
            continue;
          }
          spaceToWrapAt = matcher.start() + offset;
        }

        // only last line without leading spaces is left
        if(origLength - offset <= maxLineLength) {
          break;
        }

        while(matcher.find()){
          spaceToWrapAt = matcher.start() + offset;
        }

        if (spaceToWrapAt >= offset) {
          // normal case
          wrappedLine.append(str, offset, spaceToWrapAt);
          wrappedLine.append(newLineStr);
          offset = spaceToWrapAt + 1;

        } else {
          // really long word or URL
          if (wrapLongWords) {
            // wrap really long word one line at a time
            wrappedLine.append(str, offset, maxLineLength + offset);
            wrappedLine.append(newLineStr);
            offset += maxLineLength;
          } else {
            // do not wrap really long word, just extend beyond limit
            matcher = wrapOnPattern.matcher(str.substring(offset + maxLineLength));
            if (matcher.find()) {
              spaceToWrapAt = matcher.start() + offset + maxLineLength;
            }

            if (spaceToWrapAt >= 0) {
              wrappedLine.append(str, offset, spaceToWrapAt);
              wrappedLine.append(newLineStr);
              offset = spaceToWrapAt + 1;
            } else {
              wrappedLine.append(str, offset, str.length());
              offset = origLength
              ;
            }
          }
        }
      }

      // Whatever is left in line is short enough to just pass through
      return wrappedLine.append(str, offset, str.length()).toString();
    }
    
}
