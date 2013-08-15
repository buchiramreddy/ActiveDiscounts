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

import java.io.Serializable;

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
public interface PromotableCandidateItemOffer extends Serializable {
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
  List<PromotableOrderItem> getCandidateTargets();

  /**
   * DOCUMENT ME!
   *
   * @param  candidateTargets  DOCUMENT ME!
   */
  void setCandidateTargets(List<PromotableOrderItem> candidateTargets);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getPotentialSavings();

  /**
   * DOCUMENT ME!
   *
   * @param  savings  DOCUMENT ME!
   */
  void setPotentialSavings(Money savings);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean hasQualifyingItemCriteria();

  /**
   * Public only for unit testing - not intended to be called.
   *
   * @param   orderItem            DOCUMENT ME!
   * @param   qtyToReceiveSavings  DOCUMENT ME!
   *
   * @return  public only for unit testing - not intended to be called.
   */
  Money calculateSavingsForOrderItem(PromotableOrderItem orderItem, int qtyToReceiveSavings);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  int calculateMaximumNumberOfUses();

  /**
   * Returns the number of item quantities that qualified as targets for this promotion.
   *
   * @return  the number of item quantities that qualified as targets for this promotion.
   */
  int calculateTargetQuantityForTieredOffer();

  /**
   * Determines the max number of times this itemCriteria might apply. This calculation does not take into account other
   * promotions. It is useful only to assist in prioritizing the order to process the promotions.
   *
   * @param   itemCriteria  DOCUMENT ME!
   * @param   promotion     DOCUMENT ME!
   *
   * @return  determines the max number of times this itemCriteria might apply.
   */
  int calculateMaxUsesForItemCriteria(OfferItemCriteria itemCriteria, Offer promotion);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  int getPriority();

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
  int getUses();

  /**
   * DOCUMENT ME!
   */
  void addUse();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isLegacyOffer();
} // end interface PromotableCandidateItemOffer
