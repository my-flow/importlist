package com.moneydance.modules.features.importlist.util;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class AlphanumComparatorTest {

    private Comparator<String> alphanum;

    @Before
    public void setUp() {
        this.alphanum = AlphanumComparator.ALPHANUM;
    }

    @Test
    public void testCompare() {
        String string1 = "start123-10";
        String string2 = "start123-2";
        int compare = this.alphanum.compare(string1, string2);
        assertThat(compare, is(1));

        string1 = "start134start";
        string2 = "start123start";
        compare = this.alphanum.compare(string1, string2);
        assertThat(compare, is(1));

        string1 = "startabc";
        string2 = "startabc";
        compare = this.alphanum.compare(string1, string2);
        assertThat(compare, is(0));
    }
}
