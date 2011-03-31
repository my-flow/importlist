package com.moneydance.modules.features.importlist.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.modules.features.importlist.Preferences;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 */
public class PreferencesTest {

   private Preferences preferences;


   @Before
   public final void setUp() throws Exception {
      this.preferences = new Preferences(null);
   }


   @Test
   public final void testGetDateFormatter() {
      assertNotNull("date formatter must not be null",
            this.preferences.getDateFormatter());
   }


   @Test
   public final void testGetTimeFormatter() {
      assertNotNull("time formatter must not be null",
            this.preferences.getTimeFormatter());
   }


   @Test
   public final void testGetForegroundColor() {
      assertNotNull("foreground color must not be null",
            this.preferences.getForegroundColor());
   }


   @Test
   public final void testGetBackgroundColor() {
      assertNotNull("background color must not be null",
            this.preferences.getBackgroundColor());
   }


   @Test
   public final void testGetBackgroundColorAlt() {
      assertNotNull("alternating background color must not be null",
            this.preferences.getBackgroundColorAlt());
   }
}
