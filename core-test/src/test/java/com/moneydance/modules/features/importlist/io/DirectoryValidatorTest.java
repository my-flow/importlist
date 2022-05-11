package com.moneydance.modules.features.importlist.io;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class DirectoryValidatorTest {

    private DirectoryValidator directoryValidator;

    @Before
    public void setUp() {
        this.directoryValidator = new DirectoryValidator();
    }

    @Test
    public void testIsValidDirectory() {
        assertThat(this.directoryValidator.isValidDirectory(new File("")), is(false));
        assertThat(this.directoryValidator.isValidDirectory(null), is(false));
        assertThat(this.directoryValidator.isValidDirectory(new File(System.getProperty("user.home"))), is(true));
    }

    @Test
    public void testAcceptFile() {
        assertThat(this.directoryValidator.accept(new File("")), is(false));
    }

    @Test
    public void testAcceptFileString() {
        assertThat(
                this.directoryValidator.accept(
                        new File(""),
                        new File("").getName()),
                        is(true));
    }
}
