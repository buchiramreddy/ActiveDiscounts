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

package org.broadleafcommerce.core.offer.domain;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface FulfillmentGroupAdjustment extends Adjustment {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup getFulfillmentGroup();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroup  DOCUMENT ME!
   * @param  offer             DOCUMENT ME!
   * @param  reason            DOCUMENT ME!
   */
  void init(FulfillmentGroup fulfillmentGroup, Offer offer, String reason);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroup  DOCUMENT ME!
   */
  void setFulfillmentGroup(FulfillmentGroup fulfillmentGroup);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Adjustment#setValue(org.broadleafcommerce.common.money.Money)
   */
  @Override void setValue(Money value);

} // end interface FulfillmentGroupAdjustment
