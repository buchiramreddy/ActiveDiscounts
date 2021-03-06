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

package org.broadleafcommerce.common.logging;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;


/**
 * Log4J filter that will remove any SUPPORT level log messages from appearing.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class SupportLevelFilter extends Filter {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.apache.log4j.spi.Filter#decide(org.apache.log4j.spi.LoggingEvent)
   */
  @Override public int decide(LoggingEvent event) {
    if (SupportLevel.SUPPORT.equals(event.getLevel())) {
      return Filter.DENY;
    }

    return Filter.ACCEPT;
  }

}
