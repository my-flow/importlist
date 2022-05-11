package com.moneydance.modules.features.importlist.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


/**
 * @author Florian J. Breunig
 */
public final class LogFormatter extends Formatter {

    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator");

    /**
     * Construct a new formatter.
     */
    LogFormatter() {
        super();
    }

    @Override
    public String format(final LogRecord record) {
        final StringBuilder stringBuilder = new StringBuilder(7);

        stringBuilder.append(new Date(record.getMillis())).
                append(' ').
                append(record.getLevel().getLocalizedName()).
                append(": ").
                append(this.formatMessage(record)).
                append(LINE_SEPARATOR);

        if (record.getThrown() != null) {
            StringWriter stringWriter = new StringWriter();
            try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
                printWriter.println(record.getThrown().getMessage());
            }
            stringBuilder.append(stringWriter);
        }

        return stringBuilder.toString();
    }
}
