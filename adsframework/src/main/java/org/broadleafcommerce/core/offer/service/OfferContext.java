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

package org.broadleafcommerce.core.offer.service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OfferContext {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final ThreadLocal<OfferContext> OFFERCONTEXT = new ThreadLocal<OfferContext>();

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Boolean executePromotionCalculation = true;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static OfferContext getOfferContext() {
    return OFFERCONTEXT.get();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerContext  DOCUMENT ME!
   */
  public static void setOfferContext(OfferContext offerContext) {
    OFFERCONTEXT.set(offerContext);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getExecutePromotionCalculation() {
    return executePromotionCalculation;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  executePromotionCalculation  DOCUMENT ME!
   */
  public void setExecutePromotionCalculation(Boolean executePromotionCalculation) {
    this.executePromotionCalculation = executePromotionCalculation;
  }
} // end class OfferContext
