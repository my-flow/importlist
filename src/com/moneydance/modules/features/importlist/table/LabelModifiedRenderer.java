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

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang3.Validate;

import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Settings;
import com.moneydance.modules.features.importlist.view.ColorSchemeHelper;
import com.moneydance.util.CustomDateFormat;

/**
 * @author Florian J. Breunig
 */
final class LabelModifiedRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 7553393304980261323L;
    private final transient ColorSchemeHelper colorSchemeHelper;
    private final transient Settings          settings;
    private       transient CustomDateFormat  dateFormatter;
    private                 DateFormat        timeFormatter;

    LabelModifiedRenderer(final ColorSchemeHelper argColorSchemeHelper) {
        this.colorSchemeHelper = argColorSchemeHelper;
        this.settings          = Helper.getSettings();
    }

    // ESCA-JAVA0138: abstract method from interface TableCellRenderer
    @Override
    public Component getTableCellRendererComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {
        this.setOpaque(false);
        this.colorSchemeHelper.applyColorScheme(this, row);
        String label = null;
        if (value != null) {
            final Date fileDate = (Date) value;
            label = this.settings.getIndentationPrefix()
            + this.dateFormatter.format(fileDate)
            + " "
            + this.timeFormatter.format(fileDate);
        }

        super.getTableCellRendererComponent(
                table,
                label,
                isSelected,
                hasFocus,
                row,
                column);
        this.setBorder(noFocusBorder);
        return this;
    }

    void setDateFormatter(final CustomDateFormat argDateFormatter) {
        Validate.notNull(argDateFormatter, "argDateFormatter can't be null");
        this.dateFormatter = argDateFormatter;
    }

    void setTimeFormatter(final DateFormat argTimeFormatter) {
        Validate.notNull(argTimeFormatter, "argTimeFormatter can't be null");
        this.timeFormatter = argTimeFormatter;
    }
}
