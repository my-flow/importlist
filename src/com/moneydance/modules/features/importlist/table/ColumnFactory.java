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

package com.moneydance.modules.features.importlist.table;

import java.awt.Color;
import java.text.DateFormat;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.lang.Validate;

import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.view.ColorSchemeHelper;
import com.moneydance.modules.features.importlist.view.EvenColorSchemeHelper;
import com.moneydance.modules.features.importlist.view.OddColorSchemeHelper;
import com.moneydance.util.CustomDateFormat;

/**
 * This factory provides unified access to the required renderer classes.
 */
public final class ColumnFactory {

    private final ColorSchemeHelper     evenColorSchemeHelper;
    private final ColorSchemeHelper     oddColorSchemeHelper;
    private final TableCellRenderer     headerRenderer;
    private final TableCellRenderer     labelNameOneRenderer;
    private final TableCellRenderer     labelNameAllRenderer;
    private final LabelModifiedRenderer labelModifiedOneRenderer;
    private final LabelModifiedRenderer labelModifiedAllRenderer;
    private final ButtonRenderer        buttonOneRenderer;
    private final ButtonRenderer        buttonAllRenderer;
    private final TableCellEditor       importOneEditor;
    private final TableCellEditor       importAllEditor;
    private final TableCellEditor       deleteOneEditor;
    private final TableCellEditor       deleteAllEditor;

    public ColumnFactory(
            final FileAdmin fileAdmin,
            final TableCellRenderer defaultHeaderTableCellRenderer) {
        Validate.notNull(fileAdmin, "fileAdmin can't be null");
        final Preferences prefs = Helper.getPreferences();

        this.evenColorSchemeHelper = new EvenColorSchemeHelper();
        this.oddColorSchemeHelper  = new OddColorSchemeHelper();

        ColorSchemeHelper defaultColorSchemeHelper = this.evenColorSchemeHelper;
        ColorSchemeHelper customColorSchemeHelper  = this.oddColorSchemeHelper;
        int opaqueVersion = prefs.getVersionWithOpaqueHomepageView();
        if (prefs.getMajorVersion() >= opaqueVersion) {
            defaultColorSchemeHelper = this.oddColorSchemeHelper;
        }

        this.headerRenderer           = new HeaderRenderer(
                defaultColorSchemeHelper,
                defaultHeaderTableCellRenderer);
        this.labelNameOneRenderer     = new LabelNameRenderer(
                defaultColorSchemeHelper);
        this.labelNameAllRenderer     = new LabelNameRenderer(
                customColorSchemeHelper);
        this.labelModifiedOneRenderer = new LabelModifiedRenderer(
                defaultColorSchemeHelper);
        this.labelModifiedAllRenderer = new LabelModifiedRenderer(
                customColorSchemeHelper);
        this.buttonOneRenderer        = new ButtonRenderer(
                defaultColorSchemeHelper);
        this.buttonAllRenderer        = new ButtonRenderer(
                customColorSchemeHelper);
        this.importOneEditor          = new ImportOneEditor(
                fileAdmin,
                this.buttonOneRenderer);
        this.importAllEditor          = new ImportAllEditor(
                fileAdmin,
                this.buttonAllRenderer);
        this.deleteOneEditor          = new DeleteOneEditor(
                fileAdmin,
                this.buttonOneRenderer);
        this.deleteAllEditor          = new DeleteAllEditor(
                fileAdmin,
                this.buttonAllRenderer);
    }

    public TableCellRenderer getHeaderRenderer() {
        return this.headerRenderer;
    }

    public TableCellRenderer getLabelNameOneRenderer() {
        return this.labelNameOneRenderer;
    }

    public TableCellRenderer getLabelNameAllRenderer() {
        return this.labelNameAllRenderer;
    }

    public TableCellRenderer getLabelModifiedOneRenderer() {
        return this.labelModifiedOneRenderer;
    }

    public TableCellRenderer getLabelModifiedAllRenderer() {
        return this.labelModifiedAllRenderer;
    }

    public TableCellRenderer getButtonOneRenderer() {
        return this.buttonOneRenderer;
    }

    public TableCellRenderer getButtonAllRenderer() {
        return this.buttonAllRenderer;
    }

    public TableCellEditor getImportOneEditor() {
        return this.importOneEditor;
    }

    public TableCellEditor getImportAllEditor() {
        return this.importAllEditor;
    }

    public TableCellEditor getDeleteOneEditor() {
        return this.deleteOneEditor;
    }

    public TableCellEditor getDeleteAllEditor() {
        return this.deleteAllEditor;
    }

    public void setForeground(final Color foreground) {
        this.evenColorSchemeHelper.setForeground(foreground);
        this.oddColorSchemeHelper.setForeground(foreground);
    }

    public void setBackground(final Color background) {
        this.evenColorSchemeHelper.setBackground(background);
        this.oddColorSchemeHelper.setBackground(background);
    }

    public void setBackgroundAlt(final Color backgroundAlt) {
        this.evenColorSchemeHelper.setBackgroundAlt(backgroundAlt);
        this.oddColorSchemeHelper.setBackgroundAlt(backgroundAlt);
    }

    public void setDateFormatter(final CustomDateFormat argDateFormatter) {
        this.labelModifiedOneRenderer.setDateFormatter(argDateFormatter);
    }

    public void setTimeFormatter(final DateFormat argTimeFormatter) {
        this.labelModifiedOneRenderer.setTimeFormatter(argTimeFormatter);
    }
}
