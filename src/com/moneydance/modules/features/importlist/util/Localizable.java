/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.moneydance.modules.features.importlist.util;

import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/**
 * This i18n class provides language-dependent strings such as labels and
 * messages.
 *
 * @author Florian J. Breunig
 */
public final class Localizable implements Observer {

    private final Preferences     prefs;
    private final Settings        settings;
    private       ResourceBundle  resourceBundle;

    Localizable() {
        this.prefs    = Helper.getPreferences();
        this.prefs.addObserver(this);
        this.settings = new Settings();
    }

    @Override
    public void update(final Observable observable, final Object updateAll) {
        if (!Boolean.TRUE.equals(updateAll)) {
            return;
        }
        this.resourceBundle = ResourceBundle.getBundle(
                this.settings.getLocalizableResource(),
                this.prefs.getLocale());
    }

    private ResourceBundle getResourceBundle() {
        if (this.resourceBundle == null) {
            this.update(this.prefs, null);
        }
        return this.resourceBundle;
    }

    /**
     * @return The title of the dialog that is displayed to choose the base
     * directory.
     */
    public String getDirectoryChooserTitle() {
        return this.getResourceBundle().getString("directory_chooser_title");
    }

    /**
     * @return Header of the "name" column.
     */
    public String getHeaderValueName() {
        final String headerValueName =
            this.getResourceBundle().getString("header_value_name");
        return this.settings.getIndentationPrefix() + headerValueName;
    }

    /**
     * @return Header of the "modified" column.
     */
    public String getHeaderValueModified() {
        final String headerValueModified =
            this.getResourceBundle().getString("header_value_modified");
        return this.settings.getIndentationPrefix() + headerValueModified;
    }

    /**
     * @return Header of the "import" column.
     */
    public String getHeaderValueImport() {
        final String headerValueImport =
            this.getResourceBundle().getString("header_value_import");
        return this.settings.getIndentationPrefix() + headerValueImport;
    }

    /**
     * @return Header of the "delete" column.
     */
    public String getHeaderValueDelete() {
        final String headerValueDelete =
            this.getResourceBundle().getString("header_value_delete");
        return this.settings.getIndentationPrefix() + headerValueDelete;
    }

    /**
     * @return Label of the "import" button.
     */
    public String getLabelImportOneButton() {
        return this.getResourceBundle().getString("label_import_one_button");
    }

    /**
     * @return Label of the "import all" button.
     */
    public String getLabelImportAllButton() {
        return this.getResourceBundle().getString("label_import_all_button");
    }

    /**
     * @return Label of the "delete" button.
     */
    public String getLabelDeleteOneButton() {
        return this.getResourceBundle().getString("label_delete_one_button");
    }

    /**
     * @return Label of the "delete all" button.
     */
    public String getLabelDeleteAllButton() {
        return this.getResourceBundle().getString("label_delete_all_button");
    }

    /**
     * @param filename The name of the file that is to be deleted.
     * @return Confirmation message to be displayed before a file will be
     *  deleted.
     */
    public String getConfirmationMessageDeleteOneFile(final String filename) {
        String rawMessage = this.getResourceBundle().getString(
        "confirmation_message_delete_one_file");
        return rawMessage.replace("(0)", this.getMarkupFilename(filename));
    }

    /**
     * @param size The number of all files that are to be deleted.
     * @return Confirmation message to be displayed before all files will be
     *  deleted.
     */
    public String getConfirmationMessageDeleteAllFiles(final int size) {
        String rawMessage = this.getResourceBundle().getString(
        "confirmation_message_delete_all_files");
        return rawMessage.replace("(0)", String.valueOf(size));
    }

    /**
     * @param filename The name of the file that cannot be deleted.
     * @return Error message to be displayed if a file cannot be deleted.
     */
    public String getErrorMessageDeleteFile(final String filename) {
        String rawMessage = this.getResourceBundle().getString(
        "error_message_delete_file");
        return rawMessage.replace("(0)", this.getMarkupFilename(filename));
    }

    /**
     * @return Delete button in confirmation message.
     */
    public String getOptionDeleteFile() {
        return this.getResourceBundle().getString("option_delete_file");
    }

    /**
     * @return Cancel button in confirmation message.
     */
    public String getOptionCancel() {
        return this.getResourceBundle().getString("option_cancel");
    }

    /**
     * @param baseDirectory The base directory which does not contain any files
     * to display.
     * @return Message to display if the list of files is empty.
     */
    public String getEmptyMessage(final String baseDirectory) {
        String rawMessage = this.getResourceBundle().getString("empty_message");
        return rawMessage.replace("(0)", baseDirectory);
    }

    private String getMarkupFilename(final String filename) {
        int length = this.settings.getMessageFilenameLineMaxLength();

        int numberOfLines = filename.length() / length + 1;
        String[] substrings = new String[numberOfLines];

        for (int i = 0; i < numberOfLines; i++) {
            int start =  i      * length;
            int end   = (i + 1) * length;
            substrings[i] = StringUtils.substring(filename, start, end);
        }
        return StringUtils.join(substrings, "<br>");
    }
}
