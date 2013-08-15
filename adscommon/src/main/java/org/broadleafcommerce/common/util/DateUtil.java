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

package org.broadleafcommerce.common.util;

import java.util.Date;

import org.broadleafcommerce.common.time.SystemTime;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class DateUtil {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   startDate    DOCUMENT ME!
   * @param   endDate      DOCUMENT ME!
   * @param   includeTime  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static boolean isActive(Date startDate, Date endDate, boolean includeTime) {
    Long date = SystemTime.asMillis(includeTime);

    return !((startDate == null) || (startDate.getTime() > date) || ((endDate != null) && (endDate.getTime() < date)));
  }
}
