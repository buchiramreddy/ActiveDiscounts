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

import java.io.Serializable;

import java.util.Set;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferItemCriteria;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer;


/**
 * Records the usage of this item as qualifier or target of the promotion. The discount amount will be 0 if this item
 * was only used as a qualifier.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class PromotionDiscount implements Serializable {
  private static final long serialVersionUID = 1L;

  private PromotableCandidateItemOffer candidateItemOffer;
  private Offer                        promotion;
  private Set<OfferItemCriteria>       itemCriteria;
  private int                          quantity;
  private int                          finalizedQuantity;


  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Offer getPromotion() {
    return promotion;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  promotion  DOCUMENT ME!
   */
  public void setPromotion(Offer promotion) {
    this.promotion = promotion;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Set<OfferItemCriteria> getItemCriteria() {
    return itemCriteria;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  itemCriteria  DOCUMENT ME!
   */
  public void setItemCriteria(Set<OfferItemCriteria> itemCriteria) {
    this.itemCriteria = itemCriteria;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  quantity  DOCUMENT ME!
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getFinalizedQuantity() {
    return finalizedQuantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  finalizedQuantity  DOCUMENT ME!
   */
  public void setFinalizedQuantity(int finalizedQuantity) {
    this.finalizedQuantity = finalizedQuantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  quantity  DOCUMENT ME!
   */
  public void incrementQuantity(int quantity) {
    this.quantity = this.quantity + quantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PromotableCandidateItemOffer getCandidateItemOffer() {
    return candidateItemOffer;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  candidateItemOffer  DOCUMENT ME!
   */
  public void setCandidateItemOffer(PromotableCandidateItemOffer candidateItemOffer) {
    this.candidateItemOffer = candidateItemOffer;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   splitQty  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PromotionDiscount split(int splitQty) {
    PromotionDiscount returnDiscount = copy();
    int               originalQty    = finalizedQuantity;

    setFinalizedQuantity(splitQty);
    setQuantity(splitQty);

    int newDiscountQty = originalQty - splitQty;

    if (newDiscountQty == 0) {
      return null;
    } else {
      returnDiscount.setQuantity(newDiscountQty);
      returnDiscount.setFinalizedQuantity(newDiscountQty);
    }

    return returnDiscount;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PromotionDiscount copy() {
    PromotionDiscount pd = new PromotionDiscount();
    pd.setItemCriteria(itemCriteria);
    pd.setPromotion(promotion);
    pd.setQuantity(quantity);
    pd.setFinalizedQuantity(finalizedQuantity);
    pd.setCandidateItemOffer(candidateItemOffer);

    return pd;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  qty  DOCUMENT ME!
   */
  public void resetQty(int qty) {
    quantity          = qty;
    finalizedQuantity = qty;
  }

} // end class PromotionDiscount
