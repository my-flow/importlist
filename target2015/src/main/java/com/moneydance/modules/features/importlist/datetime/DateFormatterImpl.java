package com.moneydance.modules.features.importlist.datetime;

import com.infinitekind.util.CustomDateFormat;

import java.time.Instant;
import java.util.Date;

/**
 * @author Florian J. Breunig
 */
public final class DateFormatterImpl implements DateFormatter {

    private final CustomDateFormat customDateFormatter;

    public DateFormatterImpl(final CustomDateFormat argShortDateFormatter) {
        this.customDateFormatter = argShortDateFormatter;
    }

    @Override
    public String format(final Instant instant) {
        return this.customDateFormatter.format(Date.from(instant));
    }
}
