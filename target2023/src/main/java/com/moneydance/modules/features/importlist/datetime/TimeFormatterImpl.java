package com.moneydance.modules.features.importlist.datetime;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @author Florian J. Breunig
 */
public final class TimeFormatterImpl implements DateFormatter {

    private final DateFormat timeFormatter;

    public TimeFormatterImpl(final DateFormat argTimeFormatter) {
        this.timeFormatter = argTimeFormatter;
    }

    @Override
    public String format(final Instant instant) {
        return this.timeFormatter.format(Date.from(instant));
    }
}
