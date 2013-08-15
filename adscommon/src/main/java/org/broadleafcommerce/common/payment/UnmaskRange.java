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

package org.broadleafcommerce.common.payment;

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class UnmaskRange {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final int BEGINNINGTYPE = 0;

  /** DOCUMENT ME! */
  public static final int ENDTYPE = 1;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private int length;

  private int positionType;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new UnmaskRange object.
   *
   * @param  startPosition  DOCUMENT ME!
   * @param  length         DOCUMENT ME!
   */
  public UnmaskRange(int startPosition, int length) {
    this.positionType = startPosition;
    this.length       = length;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getLength() {
    return length;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getPositionType() {
    return positionType;
  }

} // end class UnmaskRange
