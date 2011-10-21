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
import com.moneydance.modules.features.importlist.view.DefaultColorSchemeHelper;
import com.moneydance.modules.features.importlist.view.OpaqueColorSchemeHelper;
import com.moneydance.util.CustomDateFormat;

/**
 * This factory provides unified access to the required renderer classes.
 */
public final class ColumnFactory {

    private final ColorSchemeHelper     colorSchemeHelper;
    private final TableCellRenderer     headerRenderer;
    private final TableCellRenderer     labelNameRenderer;
    private final LabelModifiedRenderer labelModifiedRenderer;
    private final ButtonRenderer        buttonRenderer;
    private final TableCellEditor       importOneEditor;
    private final TableCellEditor       importAllEditor;
    private final TableCellEditor       deleteOneEditor;
    private final TableCellEditor       deleteAllEditor;

    public ColumnFactory(
            final FileAdmin fileAdmin,
            final TableCellRenderer defaultHeaderTableCellRenderer) {
        Validate.notNull(fileAdmin, "fileAdmin can't be null");
        final Preferences prefs = Helper.getPreferences();

        int opaqueVersion = prefs.getVersionWithOpaqueHomepageView();
        if (prefs.getVersion() >= opaqueVersion) {
            this.colorSchemeHelper  = new OpaqueColorSchemeHelper();
        } else {
            this.colorSchemeHelper  = new DefaultColorSchemeHelper();
        }
        this.headerRenderer         = new HeaderRenderer(
                this.colorSchemeHelper,
                defaultHeaderTableCellRenderer);
        this.labelNameRenderer      = new LabelNameRenderer(
                this.colorSchemeHelper);
        this.labelModifiedRenderer  = new LabelModifiedRenderer(
                this.colorSchemeHelper);
        this.buttonRenderer         = new ButtonRenderer(
                this.colorSchemeHelper);
        this.importOneEditor        = new ImportOneEditor(
                fileAdmin,
                this.buttonRenderer);
        this.importAllEditor        = new ImportAllEditor(
                fileAdmin,
                this.buttonRenderer);
        this.deleteOneEditor        = new DeleteOneEditor(
                fileAdmin,
                this.buttonRenderer);
        this.deleteAllEditor        = new DeleteAllEditor(
                fileAdmin,
                this.buttonRenderer);
    }

    public TableCellRenderer getHeaderRenderer() {
        return this.headerRenderer;
    }

    public TableCellRenderer getLabelNameRenderer() {
        return this.labelNameRenderer;
    }

    public TableCellRenderer getLabelModifiedRenderer() {
        return this.labelModifiedRenderer;
    }

    public TableCellRenderer getButtonRenderer() {
        return this.buttonRenderer;
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
        this.colorSchemeHelper.setForeground(foreground);
    }

    public void setBackground(final Color background) {
        this.colorSchemeHelper.setBackground(background);
    }

    public void setBackgroundAlt(final Color backgroundAlt) {
        this.colorSchemeHelper.setBackgroundAlt(backgroundAlt);
    }

    public void setDateFormatter(final CustomDateFormat argDateFormatter) {
        this.labelModifiedRenderer.setDateFormatter(argDateFormatter);
    }

    public void setTimeFormatter(final DateFormat argTimeFormatter) {
        this.labelModifiedRenderer.setTimeFormatter(argTimeFormatter);
    }
}
