/*
 * Import List - http://my-flow.github.io/importlist/
 * Copyright (C) 2011-2013 Florian J. Breunig
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

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.lang3.text.WordUtils;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.Main;
import com.moneydance.apps.md.view.gui.MoneydanceGUI;

/**
 * This i18n class provides language-dependent strings such as labels and
 * messages.
 *
 * @author Florian J. Breunig
 */
public enum Localizable {

    INSTANCE;

    private final Preferences       prefs;
    private final Settings          settings;
    private       ResourceBundle    resourceBundle;
    private       MoneydanceGUI     mdGUI;

    /**
     * The constructor must be called exactly once before using the only
     * instance of this class.
     */
    Localizable() {
        this.prefs = Helper.INSTANCE.getPreferences();
        this.settings = Helper.INSTANCE.getSettings();
        this.resourceBundle = ResourceBundle.getBundle(
              this.settings.getLocalizableResource(),
              this.prefs.getLocale());
    }

    public void setContext(final FeatureModuleContext context) {
        this.resourceBundle = ResourceBundle.getBundle(
                this.settings.getLocalizableResource(),
                this.prefs.getLocale());
        // Using undocumented feature.
        Main main = (Main) context;
        if (main != null) {
            this.mdGUI = (MoneydanceGUI) main.getUI();
        }
    }

    private MoneydanceGUI getMoneydanceGUI() {
        if (this.mdGUI == null) {
            Helper.INSTANCE.setChanged();
            Helper.INSTANCE.notifyObservers(Boolean.FALSE);
            Validate.notNull(this.mdGUI, "Moneydance GUI not initialized");
        }
        return this.mdGUI;
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
        final String headerValueName = this.getMoneydanceGUI().getStr("name");
        return String.format("%s%s",
                this.settings.getIndentationPrefix(),
                headerValueName);
    }

    /**
     * @return Header of the "modified" column.
     */
    public String getHeaderValueModified() {
        final String headerValueModified =
            this.resourceBundle.getString("header_value_modified");
        return String.format("%s%s",
                this.settings.getIndentationPrefix(),
                headerValueModified);
    }

    /**
     * @return Header of the "import" column.
     */
    public String getHeaderValueImport() {
        final String headerValueImport =
            this.resourceBundle.getString("header_value_import");
        return String.format("%s%s",
                this.settings.getIndentationPrefix(),
                headerValueImport);
    }

    /**
     * @return Header of the "delete" column.
     */
    public String getHeaderValueDelete() {
        final String headerValueDelete =
            this.resourceBundle.getString("header_value_delete");
        return String.format("%s%s",
                this.settings.getIndentationPrefix(),
                headerValueDelete);
    }

    /**
     * @return Label of the "import" button.
     */
    public String getLabelImportOneButton() {
        return this.getMoneydanceGUI().getStr("import");
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
        return this.getMoneydanceGUI().getStr("delete");
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

        Map<String, String> valuesMap = new HashMap<String, String>(1);
        valuesMap.put("import.dir",  this.getMarkupFilename(baseDirectory));
        StrSubstitutor sub = new StrSubstitutor(valuesMap);

        return sub.replace(templateString);
    }

    /**
     * @param filename The name of the file that is to be deleted.
     * @return Confirmation message to be displayed before a file will be
     *  deleted.
     */
    public String getConfirmationMessageDeleteOneFile(final String filename) {
        final String templateString = this.resourceBundle.getString(
        "confirmation_message_delete_one_file");

        Map<String, String> valuesMap = new HashMap<String, String>(1);
        valuesMap.put("filename",  this.getMarkupFilename(filename));
        StrSubstitutor sub = new StrSubstitutor(valuesMap);

        return sub.replace(templateString);
    }

    /**
     * @param size The number of all files that are to be deleted.
     * @return Confirmation message to be displayed before all files will be
     *  deleted.
     */
    public String getConfirmationMessageDeleteAllFiles(final int size) {
        final String templateString = this.resourceBundle.getString(
        "confirmation_message_delete_all_files");

        Map<String, String> valuesMap = new HashMap<String, String>(1);
        valuesMap.put("no.files",  String.valueOf(size));
        StrSubstitutor sub = new StrSubstitutor(valuesMap);

        return sub.replace(templateString);
    }

    /**
     * @param filename The name of the file that cannot be deleted.
     * @return Error message to be displayed if a file cannot be deleted.
     */
    public String getErrorMessageDeleteFile(final String filename) {
        final String templateString = this.resourceBundle.getString(
        "error_message_delete_file");

        Map<String, String> valuesMap = new HashMap<String, String>(1);
        valuesMap.put("filename",  this.getMarkupFilename(filename));
        StrSubstitutor sub = new StrSubstitutor(valuesMap);

        return sub.replace(templateString);
    }

    /**
     * @return Delete button in confirmation message.
     */
    public String getOptionDeleteFile() {
        return this.getMoneydanceGUI().getStr("delete");
    }

    /**
     * @return Cancel button in confirmation message.
     */
    public String getOptionCancel() {
        return this.getMoneydanceGUI().getStr("cancel");
    }

    /**
     * @param baseDirectory The base directory which does not contain any files
     * to display.
     * @return Message to display if the list of files is empty.
     */
    public String getEmptyMessage(final String baseDirectory) {
        final String templateString = this.resourceBundle.getString(
        "empty_message");

        Map<String, String> valuesMap = new HashMap<String, String>(1);
        valuesMap.put("import.dir", baseDirectory);
        StrSubstitutor sub = new StrSubstitutor(valuesMap);

        return sub.replace(templateString);
    }

    private String getMarkupFilename(final String filename) {
        int length = this.settings.getMessageFilenameLineMaxLength();
        return WordUtils.wrap(filename, length, "<br>", /*wrapLongWords*/ true);
    }
}
