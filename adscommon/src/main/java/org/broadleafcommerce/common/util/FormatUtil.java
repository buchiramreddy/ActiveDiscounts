/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.util;

import java.text.SimpleDateFormat;

import org.broadleafcommerce.common.web.BroadleafRequestContext;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class FormatUtil {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final String DATE_FORMAT               = "yyyy.MM.dd HH:mm:ss";

  /** DOCUMENT ME! */
  public static final String DATE_FORMAT_WITH_TIMEZONE = "yyyy.MM.dd HH:mm:ss Z";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static SimpleDateFormat getDateFormat() {
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    formatter.setTimeZone(BroadleafRequestContext.getBroadleafRequestContext().getTimeZone());

    return formatter;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Used with dates in rules since they are not stored as a Timestamp type (and thus not converted to a specific
   * database timezone on a save). In order to provide accurate information, the timezone must also be preserved in the
   * MVEL rule expression
   *
   * @return  used with dates in rules since they are not stored as a Timestamp type (and thus not converted to a
   *          specific database timezone on a save).
   */
  public static SimpleDateFormat getTimeZoneFormat() {
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_WITH_TIMEZONE);
    formatter.setTimeZone(BroadleafRequestContext.getBroadleafRequestContext().getTimeZone());

    return formatter;
  }
} // end class FormatUtil
