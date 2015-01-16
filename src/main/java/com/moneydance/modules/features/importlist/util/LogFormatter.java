// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2015 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package com.moneydance.modules.features.importlist.util;
import java.io.IOException;
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

    @Override
    public String format(final LogRecord record) {
        final StringBuilder stringBuilder = new StringBuilder(7);

        stringBuilder.append(new Date(record.getMillis()))
        .append(' ')
        .append(record.getLevel().getLocalizedName());

        stringBuilder.append(": ")
        .append(this.formatMessage(record))
        .append(LINE_SEPARATOR);

        if (record.getThrown() != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            try {
                record.getThrown().printStackTrace(printWriter);
                stringBuilder.append(stringWriter.toString());
            } finally {
                printWriter.close();
                try {
                    stringWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuilder.toString();
    }
}
