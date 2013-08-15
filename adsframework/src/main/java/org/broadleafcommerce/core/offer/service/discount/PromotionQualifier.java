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

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferItemCriteria;


/**
 * Records the usage of this item as qualifier or target of the promotion. The discount amount will be 0 if this item
 * was only used as a qualifier.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class PromotionQualifier implements Serializable {
  private static final long serialVersionUID = 1L;

  private Offer             promotion;
  private OfferItemCriteria itemCriteria;
  private int               quantity;
  private int               finalizedQuantity;

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
  public OfferItemCriteria getItemCriteria() {
    return itemCriteria;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  itemCriteria  DOCUMENT ME!
   */
  public void setItemCriteria(OfferItemCriteria itemCriteria) {
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
  public PromotionQualifier copy() {
    PromotionQualifier pq = new PromotionQualifier();
    pq.setItemCriteria(itemCriteria);
    pq.setPromotion(promotion);
    pq.setQuantity(quantity);
    pq.setFinalizedQuantity(finalizedQuantity);

    return pq;
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

  /**
   * DOCUMENT ME!
   *
   * @param   splitItemQty  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  public PromotionQualifier split(int splitItemQty) {
    PromotionQualifier returnQualifier = copy();
    int                newQty          = finalizedQuantity - splitItemQty;

    if (newQty <= 0) {
      throw new IllegalArgumentException("Splitting PromotionQualifier resulted in a negative quantity");
    }

    setFinalizedQuantity(newQty);
    setQuantity(newQty);

    returnQualifier.setQuantity(splitItemQty);
    returnQualifier.setFinalizedQuantity(splitItemQty);

    return returnQualifier;
  }

} // end class PromotionQualifier
