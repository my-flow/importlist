package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.datetime.DateFormatter;

import java.awt.Component;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Florian J. Breunig
 */
public final class LabelModifiedRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private final ColorScheme colorScheme;
    private final String indentationPrefix;
    private DateFormatter dateFormatter;
    private DateFormatter timeFormatter;

    @Inject
    LabelModifiedRenderer(
            @Named("odd color scheme") final ColorScheme argColorScheme,
            @Named("date") final DateFormatter argDateFormatter,
            @Named("time") final DateFormatter argTimeFormatter,
            final String argIndentationPrefix) {
        super();
        this.colorScheme = argColorScheme;
        this.dateFormatter = argDateFormatter;
        this.timeFormatter = argTimeFormatter;
        this.indentationPrefix = argIndentationPrefix;
    }

    @Override
    @SuppressWarnings("nullness")
    public Component getTableCellRendererComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {

        this.setOpaque(false);
        this.colorScheme.applyColorScheme(this, row);
        String label = null;
        if (value instanceof Long) {
            final Date fileDate = new Date((Long) value);
            label = String.format("%s%s %s",
                    this.indentationPrefix,
                    this.dateFormatter.format(fileDate),
                    this.timeFormatter.format(fileDate));
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

    void setDateFormatter(final DateFormatter argDateFormatter) {
        this.dateFormatter = argDateFormatter;
    }

    void setTimeFormatter(final DateFormatter argTimeFormatter) {
        this.timeFormatter = argTimeFormatter;
    }
}
