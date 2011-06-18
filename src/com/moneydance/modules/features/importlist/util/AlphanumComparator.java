/*
 * The Alphanum Algorithm is an improved sorting algorithm for strings
 * containing numbers.  Instead of sorting numbers in ASCII order like
 * a standard sort, this algorithm sorts numbers in numeric order.
 *
 * The Alphanum Algorithm is discussed at http://www.DaveKoelle.com
 *
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301 USA
 *
 */

package com.moneydance.modules.features.importlist.util;
import java.io.Serializable;
import java.util.Comparator;

/**
 * This is an updated version of the
 * <a href="http://www.davekoelle.com/alphanum.html">Alphanum Algorithm</a> with
 * enhancements made by Daniel Migowski, Andre Bogus, and David Koelle.
 */
public class AlphanumComparator implements Comparator<String>, Serializable {

    private static final long serialVersionUID = 6905436500827769051L;
    public  static final int  ASCII_DIGIT_LOWER_BOUND = 48;
    public  static final int  ASCII_DIGIT_UPPER_BOUND = 57;

    private boolean isDigit(final char ch) {
        return ch >= ASCII_DIGIT_LOWER_BOUND && ch <= ASCII_DIGIT_UPPER_BOUND;
    }

    // The Length of string is passed in for improved efficiency
    // (only need to calculate it once).
    private String getChunk(final String s, final int slength, int marker) {
        StringBuilder chunk = new StringBuilder();
        char c = s.charAt(marker);
        chunk.append(c);
        marker++;
        if (this.isDigit(c)) {
            while (marker < slength) {
                c = s.charAt(marker);
                if (!this.isDigit(c)) {
                    break;
                }
                chunk.append(c);
                marker++;
            }
        } else {
            while (marker < slength) {
                c = s.charAt(marker);
                if (this.isDigit(c)) {
                    break;
                }
                chunk.append(c);
                marker++;
            }
        }
        return chunk.toString();
    }

    @Override
    public final int compare(final String s1, final String s2) {

        int thisMarker = 0;
        int thatMarker = 0;
        int s1Length = s1.length();
        int s2Length = s2.length();

        while (thisMarker < s1Length && thatMarker < s2Length) {
            String thisChunk = this.getChunk(s1, s1Length, thisMarker);
            thisMarker += thisChunk.length();

            String thatChunk = this.getChunk(s2, s2Length, thatMarker);
            thatMarker += thatChunk.length();

            // If both chunks contain numeric characters, sort them numerically
            int result = 0;
            if (this.isDigit(thisChunk.charAt(0))
                    && this.isDigit(thatChunk.charAt(0))) {
                // Simple chunk comparison by length.
                int thisChunkLength = thisChunk.length();
                result = thisChunkLength - thatChunk.length();
                // If equal, the first different number counts
                if (result == 0) {
                    for (int i = 0; i < thisChunkLength; i++) {
                        result = thisChunk.charAt(i) - thatChunk.charAt(i);
                        if (result != 0) {
                            return result;
                        }
                    }
                }
            } else {
                result = thisChunk.compareTo(thatChunk);
            }

            if (result != 0) {
                return result;
            }
        }

        return s1Length - s2Length;
    }
}
