package com.moneydance.modules.features.importlist.io;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DirectoryValidatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCheckValidDirectory() {
        Assert.assertTrue("directory must be valid",
                DirectoryValidator.INSTANCE.checkValidDirectory(new File(".")));

        Assert.assertFalse("directory must not be valid",
                DirectoryValidator.INSTANCE.checkValidDirectory(null));
}

    @Test
    public void testAcceptFile() {
        Assert.assertTrue("directory must be accepted",
                DirectoryValidator.INSTANCE.accept(new File(".")));
    }

    @Test
    public void testAcceptFileString() {
        Assert.assertTrue("directory must be accepted",
                DirectoryValidator.INSTANCE.accept(
                        new File("."),
                        new File(".").getName()));
    }

}
