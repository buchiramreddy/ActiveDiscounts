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

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderImpl;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CANDIDATE_ORDER_OFFER")
public class CandidateOrderOfferImpl implements CandidateOrderOffer {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(
    name      = "DISCOUNTED_PRICE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal discountedPrice;

  /** DOCUMENT ME! */
  @Column(name = "CANDIDATE_ORDER_OFFER_ID")
  @GeneratedValue(generator = "CandidateOrderOfferId")
  @GenericGenerator(
    name       = "CandidateOrderOfferId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CandidateOrderOfferImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.offer.domain.CandidateOrderOfferImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Index(
    name        = "CANDIDATE_ORDEROFFER_INDEX",
    columnNames = { "OFFER_ID" }
  )
  @JoinColumn(name = "OFFER_ID")
  @ManyToOne(
    targetEntity = OfferImpl.class,
    optional     = false
  )
  protected Offer offer;

  /** DOCUMENT ME! */
  @Index(
    name        = "CANDIDATE_ORDER_INDEX",
    columnNames = { "ORDER_ID" }
  )
  @JoinColumn(name = "ORDER_ID")
  @ManyToOne(targetEntity = OrderImpl.class)
  protected Order order;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    CandidateOrderOfferImpl other = (CandidateOrderOfferImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (discountedPrice == null) {
      if (other.discountedPrice != null) {
        return false;
      }
    } else if (!discountedPrice.equals(other.discountedPrice)) {
      return false;
    }

    if (offer == null) {
      if (other.offer != null) {
        return false;
      }
    } else if (!offer.equals(other.offer)) {
      return false;
    }

    if (order == null) {
      if (other.order != null) {
        return false;
      }
    } else if (!order.equals(other.order)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateOrderOffer#getDiscountedPrice()
   */
  @Override public Money getDiscountedPrice() {
    return (discountedPrice == null) ? null
                                     : BroadleafCurrencyUtils.getMoney(discountedPrice, getOrder().getCurrency());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateOrderOffer#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateOrderOffer#getOffer()
   */
  @Override public Offer getOffer() {
    return offer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateOrderOffer#getOrder()
   */
  @Override public Order getOrder() {
    return order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateOrderOffer#getPriority()
   */
  @Override public int getPriority() {
    return offer.getPriority();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((discountedPrice == null) ? 0 : discountedPrice.hashCode());
    result = (prime * result) + ((offer == null) ? 0 : offer.hashCode());
    result = (prime * result) + ((order == null) ? 0 : order.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateOrderOffer#setDiscountedPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setDiscountedPrice(Money discountedPrice) {
    this.discountedPrice = discountedPrice.getAmount();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateOrderOffer#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateOrderOffer#setOffer(org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public void setOffer(Offer offer) {
    this.offer      = offer;
    discountedPrice = null; // price needs to be recalculated
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateOrderOffer#setOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public void setOrder(Order order) {
    this.order      = order;
    discountedPrice = null; // price needs to be recalculated
  }

} // end class CandidateOrderOfferImpl
