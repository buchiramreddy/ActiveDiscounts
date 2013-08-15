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

package org.broadleafcommerce.core.offer.service.discount.domain;

import java.util.HashMap;
import java.util.List;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferItemCriteria;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PromotableCandidateFulfillmentGroupOffer {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  HashMap<OfferItemCriteria, List<PromotableOrderItem>> getCandidateQualifiersMap();

  /**
   * DOCUMENT ME!
   *
   * @param  candidateItemsMap  DOCUMENT ME!
   */
  void setCandidateQualifiersMap(HashMap<OfferItemCriteria, List<PromotableOrderItem>> candidateItemsMap);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money computeDiscountedAmount();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getDiscountedPrice();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Offer getOffer();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableFulfillmentGroup getFulfillmentGroup();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getDiscountedAmount();
} // end interface PromotableCandidateFulfillmentGroupOffer
