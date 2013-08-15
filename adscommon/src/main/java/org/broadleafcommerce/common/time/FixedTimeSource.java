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

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class FixedTimeSource implements TimeSource {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final long timeInMillis;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new FixedTimeSource object.
   *
   * @param  timeInMillis  DOCUMENT ME!
   */
  public FixedTimeSource(long timeInMillis) {
    this.timeInMillis = timeInMillis;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.time.TimeSource#timeInMillis()
   */
  @Override public long timeInMillis() {
    return timeInMillis;
  }
} // end class FixedTimeSource
