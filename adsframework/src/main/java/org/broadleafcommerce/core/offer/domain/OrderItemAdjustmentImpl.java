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
import javax.persistence.Transient;

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemImpl;

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
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "OrderItemAdjustmentImpl_baseOrderItemAdjustment"
)
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
@Table(name = "BLC_ORDER_ITEM_ADJUSTMENT")
public class OrderItemAdjustmentImpl implements OrderItemAdjustment, CurrencyCodeIdentifiable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemAdjustmentImpl_Apply_To_Sale_Price",
    order        = 4000
  )
  @Column(name = "APPLIED_TO_SALE_PRICE")
  protected boolean appliedToSalePrice;

  /** DOCUMENT ME! */
  @Column(name = "ORDER_ITEM_ADJUSTMENT_ID")
  @GeneratedValue(generator = "OrderItemAdjustmentId")
  @GenericGenerator(
    name       = "OrderItemAdjustmentId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OrderItemAdjustmentImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.offer.domain.OrderItemAdjustmentImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemAdjustmentImpl_Offer",
    order        = 1000,
    group        = "OrderItemAdjustmentImpl_Description",
    prominent    = true,
    gridOrder    = 1000
  )
  @AdminPresentationToOneLookup
  @Index(
    name        = "OIADJUST_OFFER_INDEX",
    columnNames = { "OFFER_ID" }
  )
  @JoinColumn(name = "OFFER_ID")
  @ManyToOne(
    targetEntity = OfferImpl.class,
    optional     = false
  )
  protected Offer                               offer;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Index(
    name        = "OIADJUST_ITEM_INDEX",
    columnNames = { "ORDER_ITEM_ID" }
  )
  @JoinColumn(name = "ORDER_ITEM_ID")
  @ManyToOne(targetEntity = OrderItemImpl.class)
  protected OrderItem orderItem;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemAdjustmentImpl_Item_Adjustment_Reason",
    order        = 2000
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
    friendlyName = "OrderItemAdjustmentImpl_Item_Adjustment_Value",
    order        = 3000,
    fieldType    = SupportedFieldType.MONEY,
    prominent    = true,
    gridOrder    = 2000
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

    OrderItemAdjustmentImpl other = (OrderItemAdjustmentImpl) obj;

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

    if (orderItem == null) {
      if (other.orderItem != null) {
        return false;
      }
    } else if (!orderItem.equals(other.orderItem)) {
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
    return ((CurrencyCodeIdentifiable) orderItem).getCurrencyCode();
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
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemAdjustment#getOrderItem()
   */
  @Override public OrderItem getOrderItem() {
    return orderItem;
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
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemAdjustment#getRetailPriceValue()
   */
  @Override public Money getRetailPriceValue() {
    if (retailValue == null) {
      return BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getOrderItem().getOrder().getCurrency());
    }

    return this.retailValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemAdjustment#getSalesPriceValue()
   */
  @Override public Money getSalesPriceValue() {
    if (salesValue == null) {
      return BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getOrderItem().getOrder().getCurrency());
    }

    return salesValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Adjustment#getValue()
   */
  @Override public Money getValue() {
    return (value == null) ? null : BroadleafCurrencyUtils.getMoney(value, getOrderItem().getOrder().getCurrency());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((offer == null) ? 0 : offer.hashCode());
    result = (prime * result) + ((orderItem == null) ? 0 : orderItem.hashCode());
    result = (prime * result) + ((reason == null) ? 0 : reason.hashCode());
    result = (prime * result) + ((value == null) ? 0 : value.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemAdjustment#init(org.broadleafcommerce.core.order.domain.OrderItem,
   *       org.broadleafcommerce.core.offer.domain.Offer, java.lang.String)
   */
  @Override public void init(OrderItem orderItem, Offer offer, String reason) {
    this.orderItem = orderItem;
    this.offer     = offer;
    this.reason    = reason;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemAdjustment#isAppliedToSalePrice()
   */
  @Override public boolean isAppliedToSalePrice() {
    return appliedToSalePrice;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemAdjustment#setAppliedToSalePrice(boolean)
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
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemAdjustment#setOrderItem(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
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
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemAdjustment#setRetailPriceValue(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setRetailPriceValue(Money retailPriceValue) {
    this.retailValue = retailPriceValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OrderItemAdjustment#setSalesPriceValue(org.broadleafcommerce.common.money.Money)
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

} // end class OrderItemAdjustmentImpl
