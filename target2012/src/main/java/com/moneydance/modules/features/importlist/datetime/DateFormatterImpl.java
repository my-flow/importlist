package com.moneydance.modules.features.importlist.datetime;

import com.moneydance.util.CustomDateFormat;

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
    public String format(final Date date) {
        return this.customDateFormatter.format(date);
    }
}
