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

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupImpl;

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
@Table(name = "BLC_CANDIDATE_FG_OFFER")
public class CandidateFulfillmentGroupOfferImpl implements CandidateFulfillmentGroupOffer {
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
  @Index(
    name        = "CANDIDATE_FG_INDEX",
    columnNames = { "FULFILLMENT_GROUP_ID" }
  )
  @JoinColumn(name = "FULFILLMENT_GROUP_ID")
  @ManyToOne(targetEntity = FulfillmentGroupImpl.class)
  protected FulfillmentGroup fulfillmentGroup;

  /** DOCUMENT ME! */
  @Column(name = "CANDIDATE_FG_OFFER_ID")
  @GeneratedValue(generator = "CandidateFGOfferId")
  @GenericGenerator(
    name       = "CandidateFGOfferId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CandidateFulfillmentGroupOfferImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOfferImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Index(
    name        = "CANDIDATE_FGOFFER_INDEX",
    columnNames = { "OFFER_ID" }
  )
  @JoinColumn(name = "OFFER_ID")
  @ManyToOne(
    targetEntity = OfferImpl.class,
    optional     = false
  )
  protected Offer offer;

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

    CandidateFulfillmentGroupOfferImpl other = (CandidateFulfillmentGroupOfferImpl) obj;

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

    if (fulfillmentGroup == null) {
      if (other.fulfillmentGroup != null) {
        return false;
      }
    } else if (!fulfillmentGroup.equals(other.fulfillmentGroup)) {
      return false;
    }

    if (offer == null) {
      if (other.offer != null) {
        return false;
      }
    } else if (!offer.equals(other.offer)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer#getDiscountedPrice()
   */
  @Override public Money getDiscountedPrice() {
    return (discountedPrice == null)
      ? null : BroadleafCurrencyUtils.getMoney(discountedPrice, getFulfillmentGroup().getOrder().getCurrency());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer#getFulfillmentGroup()
   */
  @Override public FulfillmentGroup getFulfillmentGroup() {
    return fulfillmentGroup;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer#getOffer()
   */
  @Override public Offer getOffer() {
    return offer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer#getPriority()
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
    result = (prime * result) + ((fulfillmentGroup == null) ? 0 : fulfillmentGroup.hashCode());
    result = (prime * result) + ((offer == null) ? 0 : offer.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer#setDiscountedPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setDiscountedPrice(Money discountedPrice) {
    this.discountedPrice = discountedPrice.getAmount();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer#setFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup)
   */
  @Override public void setFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
    this.fulfillmentGroup = fulfillmentGroup;
    discountedPrice       = null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer#setOffer(org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public void setOffer(Offer offer) {
    this.offer      = offer;
    discountedPrice = null;
  }

} // end class CandidateFulfillmentGroupOfferImpl
