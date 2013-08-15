/*
 * Copyright 2008-2013 Broadleaf Commerce, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
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
import javax.persistence.Transient;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.broadleafcommerce.core.order.domain.OrderItemPriceDetail;
import org.broadleafcommerce.core.order.domain.OrderItemPriceDetailImpl;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationMergeOverrides(
  {
    @AdminPresentationMergeOverride(
      name = "",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.READONLY,
          booleanOverrideValue = true
        )
    )
  }
)
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_ITEM_DTL_ADJ")
public class OrderItemPriceDetailAdjustmentImpl implements OrderItemPriceDetailAdjustment, CurrencyCodeIdentifiable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemPriceDetailAdjustmentImpl_appliedToSalePrice",
    order        = 3,
    group        = "OrderItemPriceDetailAdjustmentImpl_Description"
  )
  @Column(name = "APPLIED_TO_SALE_PRICE")
  protected boolean appliedToSalePrice;

  /** DOCUMENT ME! */
  @Column(name = "ORDER_ITEM_DTL_ADJ_ID")
  @GeneratedValue(generator = "OrderItemPriceDetailAdjustmentId")
  @GenericGenerator(
    name       = "OrderItemPriceDetailAdjustmentId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OrderItemPriceDetailAdjustmentImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustmentImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemPriceDetailAdjustmentImpl_Offer",
    order        = 1000,
    prominent    = true,
    gridOrder    = 1000
  )
  @AdminPresentationToOneLookup
  @JoinColumn(name = "OFFER_ID")
  @ManyToOne(
    targetEntity = OfferImpl.class,
    optional     = false
  )
  protected Offer                               offer;

  /** DOCUMENT ME! */
  @Column(name = "OFFER_NAME")
  protected String offerName;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "ORDER_ITEM_PRICE_DTL_ID")
  @ManyToOne(targetEntity = OrderItemPriceDetailImpl.class)
  protected OrderItemPriceDetail orderItemPriceDetail;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemPriceDetailAdjustmentImpl_reason",
    order        = 1,
    group        = "OrderItemPriceDetailAdjustmentImpl_Description"
  )
  @Column(
    name     = "ADJUSTMENT_REASON",
    nullable = false
  )
  protected String reason;

  /** DOCUMENT ME! */
  @Transient protected Money retailValue;

  /** DOCUMENT ME! */
  @Transient protected Money salesValue;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemPriceDetailAdjustmentImpl_value",
    order        = 2,
    group        = "OrderItemPriceDetailAdjustmentImpl_Description",
    fieldType    = SupportedFieldType.MONEY,
    prominent    = true
  )
  @Column(
    name      = "ADJUSTMENT_VALUE",
    nullable  = false,
    precision = 19,
    scale     = 5
  )
  protected BigDecimal value = Money.ZERO.getAmount();

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

    OrderItemPriceDetailAdjustmentImpl other = (OrderItemPriceDetailAdjustmentImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (offer == null) {
      if (other.offer != null) {
        return false;
      }
    } else if (!offer.equals(other.offer)) {
      return false;
    }

    if (orderItemPriceDetail == null) {
      if (other.orderItemPriceDetail != null) {
        return false;
      }
    } else if (!orderItemPriceDetail.equals(other.orderItemPriceDetail)) {
      return false;
    }

    if (reason == null) {
      if (other.reason != null) {
        return false;
      }
    } else if (!reason.equals(other.reason)) {
      return false;
    }

    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable#getCurrencyCode()
   */
  @Override public String getCurrencyCode() {
    if (getCurrency() != null) {
      return getCurrency().getCurrencyCode();
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Adjustment#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Adjustment#getOffer()
   */
  @Override public Offer getOffer() {
    return offer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#getOfferName()
   */
  @Override public String getOfferName() {
    return offerName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#getOrderItemPriceDetail()
   */
  @Override public OrderItemPriceDetail getOrderItemPriceDetail() {
    return orderItemPriceDetail;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Adjustment#getReason()
   */
  @Override public String getReason() {
    return reason;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#getRetailPriceValue()
   */
  @Override public Money getRetailPriceValue() {
    if (retailValue == null) {
      return BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());
    }

    return this.retailValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#getSalesPriceValue()
   */
  @Override public Money getSalesPriceValue() {
    if (salesValue == null) {
      return BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());
    }

    return salesValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Adjustment#getValue()
   */
  @Override public Money getValue() {
    return (value == null) ? null : BroadleafCurrencyUtils.getMoney(value, getCurrency());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((offer == null) ? 0 : offer.hashCode());
    result = (prime * result) + ((orderItemPriceDetail == null) ? 0 : orderItemPriceDetail.hashCode());
    result = (prime * result) + ((reason == null) ? 0 : reason.hashCode());
    result = (prime * result) + ((value == null) ? 0 : value.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#init(org.broadleafcommerce.core.order.domain.OrderItemPriceDetail,
   *       org.broadleafcommerce.core.offer.domain.Offer, java.lang.String)
   */
  @Override public void init(OrderItemPriceDetail orderItemPriceDetail, Offer offer, String reason) {
    this.orderItemPriceDetail = orderItemPriceDetail;
    setOffer(offer);

    if (reason == null) {
      this.reason = reason;
      this.reason = offer.getName();
    }

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#isAppliedToSalePrice()
   */
  @Override public boolean isAppliedToSalePrice() {
    return appliedToSalePrice;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#setAppliedToSalePrice(boolean)
   */
  @Override public void setAppliedToSalePrice(boolean appliedToSalePrice) {
    this.appliedToSalePrice = appliedToSalePrice;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Adjustment#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offer  DOCUMENT ME!
   */
  public void setOffer(Offer offer) {
    this.offer = offer;

    if (offer != null) {
      this.offerName = (offer.getMarketingMessage() != null) ? offer.getMarketingMessage() : offer.getName();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#setOfferName(java.lang.String)
   */
  @Override public void setOfferName(String offerName) {
    this.offerName = offer.getName();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#setOrderItemPriceDetail(org.broadleafcommerce.core.order.domain.OrderItemPriceDetail)
   */
  @Override public void setOrderItemPriceDetail(OrderItemPriceDetail orderItemPriceDetail) {
    this.orderItemPriceDetail = orderItemPriceDetail;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Adjustment#setReason(java.lang.String)
   */
  @Override public void setReason(String reason) {
    this.reason = reason;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#setRetailPriceValue(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setRetailPriceValue(Money retailPriceValue) {
    this.retailValue = retailPriceValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment#setSalesPriceValue(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setSalesPriceValue(Money salesPriceValue) {
    this.salesValue = salesPriceValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Adjustment#setValue(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setValue(Money value) {
    this.value = value.getAmount();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected BroadleafCurrency getCurrency() {
    return getOrderItemPriceDetail().getOrderItem().getOrder().getCurrency();
  }

} // end class OrderItemPriceDetailAdjustmentImpl
