package com.moneydance.modules.features.importlist.util;

import java.util.Comparator;

/**
 * This is an updated version of the
 * <a href="http://www.davekoelle.com/alphanum.html">Alphanum Algorithm</a>.
 *
 * @author Daniel Migowski, Andre Bogus, David Koelle, and Florian J. Breunig.
 */
public enum AlphanumComparator implements Comparator<String> {

    /**
     * Alphanum comparator instance.
     */
    ALPHANUM;

    /**
     * Serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The lowest ASCII symbol representing a digit.
     */
    private static final int DIGIT_LOWER_BOUND = 48;

    /**
     * The highest ASCII symbol representing a digit.
     */
    private static final int DIGIT_UPPER_BOUND = 57;

    /**
     * Restrictive constructor.
     */
    AlphanumComparator() {
        // Prevents this class from being instantiated from the outside.
    }

    /**
     * @param character The character
     * @return <code>true</code> if the given char represents a digit;
     * <code>false</code> otherwise.
     */
    private static boolean isDigit(final char character) {
        return character >= DIGIT_LOWER_BOUND
                && character <= DIGIT_UPPER_BOUND;
    }

    /**
     * The length of the string is passed in for improved efficiency
     * (only need to calculate it once).
     * @param string The string to be analyzed.
     * @param slength The length of the string to be analyzed.
     * @param marker The index of the string to start analyzing from.
     * @return A substring containing only digits or only non-digits.
     */
    private static String getChunk(
            final String string,
            final int slength,
            final int marker) {
        int index = marker;
        final StringBuilder chunk = new StringBuilder();
        char character = string.charAt(index);
        chunk.append(character);
        index++;
        if (isDigit(character)) {
            while (index < slength) {
                character = string.charAt(index);
                if (!isDigit(character)) {
                    break;
                }
                chunk.append(character);
                index++;
            }
        } else {
            while (index < slength) {
                character = string.charAt(index);
                if (isDigit(character)) {
                    break;
                }
                chunk.append(character);
                index++;
            }
        }
        return chunk.toString();
    }

    /**
     * @param string1 The first string to be compared.
     * @param string2 The second string to be compared.
     * @return A negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second.
     */
    @Override
    @SuppressWarnings("PMD.CognitiveComplexity")
    public int compare(final String string1, final String string2) {
        int thisMarker = 0;
        int thatMarker = 0;
        final int s1Length = string1.length();
        final int s2Length = string2.length();

        while (thisMarker < s1Length && thatMarker < s2Length) {
            final String thisChunk = getChunk(string1, s1Length, thisMarker);
            thisMarker += thisChunk.length();

            final String thatChunk = getChunk(string2, s2Length, thatMarker);
            thatMarker += thatChunk.length();

            // If both chunks contain numeric characters, sort them numerically
            int result;
            if (isDigit(thisChunk.charAt(0))
                    && isDigit(thatChunk.charAt(0))) {
                // Simple chunk comparison by length.
                final int thisChunkLength = thisChunk.length();
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
