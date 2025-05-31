package com.moneydance.modules.features.importlist.datetime;

import java.time.Instant;

/**
 * @author Florian J. Breunig
 */
@FunctionalInterface
public interface DateFormatter {

    String format(Instant instant);

}
