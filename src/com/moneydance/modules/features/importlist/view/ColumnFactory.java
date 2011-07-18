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

package com.moneydance.modules.features.importlist.view;

import java.awt.Color;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.lang.Validate;

import com.moneydance.modules.features.importlist.io.FileAdmin;

/**
 * This factory provides unified access to the required renderer classes.
 */
class ColumnFactory {

    private final HeaderRenderer    headerRenderer;
    private final LabelRenderer     labelRenderer;
    private final ButtonRenderer    buttonRenderer;
    private final ImportEditor      importEditor;
    private final DeleteEditor      deleteEditor;

    ColumnFactory(final FileAdmin argFileAdmin,
            final TableCellRenderer argDefaultHeaderTableCellRenderer) {
        Validate.notNull(argFileAdmin, "argFileAdmin can't be null");
        this.headerRenderer = new HeaderRenderer(
                argDefaultHeaderTableCellRenderer);
        this.labelRenderer  = new LabelRenderer();
        this.buttonRenderer = new ButtonRenderer();
        this.importEditor   = new ImportEditor(
                argFileAdmin,
                this.buttonRenderer);
        this.deleteEditor   = new DeleteEditor(
                argFileAdmin,
                this.buttonRenderer);
    }

    final TableCellRenderer getHeaderRenderer() {
        return this.headerRenderer;
    }

    final TableCellRenderer getLabelRenderer() {
        return this.labelRenderer;
    }

    final TableCellRenderer getButtonRenderer() {
        return this.buttonRenderer;
    }

    final TableCellEditor getImportEditor() {
        return this.importEditor;
    }

    final TableCellEditor getDeleteEditor() {
        return this.deleteEditor;
    }

    final void setForeground(final Color foreground) {
        ColorSchemeHelper.setForeground(foreground);
    }

    final void setBackground(final Color background) {
        ColorSchemeHelper.setBackground(background);
    }

    final void setBackgroundAlt(final Color backgroundAlt) {
        ColorSchemeHelper.setBackgroundAlt(backgroundAlt);
    }
}
