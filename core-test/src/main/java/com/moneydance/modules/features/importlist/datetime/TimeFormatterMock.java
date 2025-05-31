package com.moneydance.modules.features.importlist.datetime;

import java.time.Instant;

/**
 * @author Florian J. Breunig
 */
public final class TimeFormatterMock implements DateFormatter {

    @Override
    public String format(final Instant instant) {
        return "";
    }
}
