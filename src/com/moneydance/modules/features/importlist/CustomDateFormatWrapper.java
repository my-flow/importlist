package com.moneydance.modules.features.importlist;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

import com.moneydance.util.CustomDateFormat;

class CustomDateFormatWrapper extends DateFormat {

   private static final           long serialVersionUID = -4747520955099837684L;
   private        final transient CustomDateFormat customDateFormat;


   public CustomDateFormatWrapper(final CustomDateFormat argCustomDateFormat) {
      this.customDateFormat = argCustomDateFormat;
   }


   @Override
   public StringBuffer format(final Date date, final StringBuffer stringbuffer,
         final FieldPosition fieldposition) {
      return new StringBuffer(this.customDateFormat.format(date));
   }


   @Override
   public Date parse(final String s, final ParsePosition parseposition) {
      return this.customDateFormat.parse(s);
   }
}
