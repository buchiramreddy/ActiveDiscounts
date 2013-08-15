/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.time;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class SystemTime {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final TimeSource                         defaultTimeSource = new DefaultTimeSource();
  private static TimeSource                               globalTimeSource  = null;
  private static final InheritableThreadLocal<TimeSource> localTimeSource   = new InheritableThreadLocal<TimeSource>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Calendar asCalendar() {
    return asCalendar(true);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   includeTime  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Calendar asCalendar(boolean includeTime) {
    return asCalendar(Locale.getDefault(), TimeZone.getDefault(), includeTime);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   locale  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Calendar asCalendar(Locale locale) {
    return asCalendar(locale, TimeZone.getDefault(), true);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   timeZone  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Calendar asCalendar(TimeZone timeZone) {
    return asCalendar(Locale.getDefault(), timeZone, true);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   locale       DOCUMENT ME!
   * @param   timeZone     DOCUMENT ME!
   * @param   includeTime  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Calendar asCalendar(Locale locale, TimeZone timeZone, boolean includeTime) {
    Calendar calendar = Calendar.getInstance(timeZone, locale);
    calendar.setTimeInMillis(asMillis());

    if (!includeTime) {
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
    }

    return calendar;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Date asDate() {
    return asDate(true);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   includeTime  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Date asDate(boolean includeTime) {
    if (includeTime) {
      return new Date(asMillis());
    }

    return asCalendar(includeTime).getTime();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static long asMillis() {
    return asMillis(true);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   includeTime  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static long asMillis(boolean includeTime) {
    if (includeTime) {
      return getTimeSource().timeInMillis();
    }

    return asCalendar(includeTime).getTimeInMillis();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public static void reset() {
    resetGlobalTimeSource();
    resetLocalTimeSource();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public static void resetGlobalTimeSource() {
    setGlobalTimeSource(null);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public static void resetLocalTimeSource() {
    SystemTime.localTimeSource.remove();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  globalTS  DOCUMENT ME!
   */
  public static void setGlobalTimeSource(final TimeSource globalTS) {
    SystemTime.globalTimeSource = globalTS;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  localTS  DOCUMENT ME!
   */
  public static void setLocalTimeSource(final TimeSource localTS) {
    SystemTime.localTimeSource.set(localTS);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private static TimeSource getTimeSource() {
    TimeSource applicableTimeSource;
    TimeSource localTS = localTimeSource.get();

    if (localTS != null) {
      applicableTimeSource = localTS;
    } else if (globalTimeSource != null) {
      applicableTimeSource = globalTimeSource;
    } else {
      applicableTimeSource = defaultTimeSource;
    }

    return applicableTimeSource;
  }
} // end class SystemTime
