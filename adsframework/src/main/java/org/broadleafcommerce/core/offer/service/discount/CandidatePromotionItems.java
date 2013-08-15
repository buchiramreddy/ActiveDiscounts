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

package org.broadleafcommerce.core.offer.service.discount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.broadleafcommerce.core.offer.domain.OfferItemCriteria;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class CandidatePromotionItems {
  /** DOCUMENT ME! */
  protected HashMap<OfferItemCriteria, List<PromotableOrderItem>> candidateQualifiersMap =
    new HashMap<OfferItemCriteria, List<PromotableOrderItem>>();

  /** DOCUMENT ME! */
  protected boolean                                               isMatchedQualifier = false;

  /** DOCUMENT ME! */
  protected List<PromotableOrderItem>                             candidateTargets =
    new ArrayList<PromotableOrderItem>();

  /** DOCUMENT ME! */
  protected boolean                                               isMatchedTarget = false;

  /**
   * DOCUMENT ME!
   *
   * @param  criteria  DOCUMENT ME!
   * @param  item      DOCUMENT ME!
   */
  public void addQualifier(OfferItemCriteria criteria, PromotableOrderItem item) {
    List<PromotableOrderItem> itemList = candidateQualifiersMap.get(criteria);

    if (itemList == null) {
      itemList = new ArrayList<PromotableOrderItem>();
      candidateQualifiersMap.put(criteria, itemList);
    }

    itemList.add(item);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isMatchedQualifier() {
    return isMatchedQualifier;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  isMatchedCandidate  DOCUMENT ME!
   */
  public void setMatchedQualifier(boolean isMatchedCandidate) {
    this.isMatchedQualifier = isMatchedCandidate;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public HashMap<OfferItemCriteria, List<PromotableOrderItem>> getCandidateQualifiersMap() {
    return candidateQualifiersMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  item  DOCUMENT ME!
   */
  public void addTarget(PromotableOrderItem item) {
    candidateTargets.add(item);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isMatchedTarget() {
    return isMatchedTarget;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  isMatchedCandidate  DOCUMENT ME!
   */
  public void setMatchedTarget(boolean isMatchedCandidate) {
    this.isMatchedTarget = isMatchedCandidate;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<PromotableOrderItem> getCandidateTargets() {
    return candidateTargets;
  }

} // end class CandidatePromotionItems
