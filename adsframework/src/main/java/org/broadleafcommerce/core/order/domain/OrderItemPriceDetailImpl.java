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

package org.broadleafcommerce.core.order.domain;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment;
import org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustmentImpl;

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
@Table(name = "BLC_ORDER_ITEM_PRICE_DTL")
public class OrderItemPriceDetailImpl implements OrderItemPriceDetail, CurrencyCodeIdentifiable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemPriceDetailImpl_Id",
    group        = "OrderItemPriceDetailImpl_Primary_Key",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ORDER_ITEM_PRICE_DTL_ID")
  @GeneratedValue(generator = "OrderItemPriceDetailId")
  @GenericGenerator(
    name       = "OrderItemPriceDetailId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OrderItemPriceDetailImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.OrderItemPriceDetailImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "ORDER_ITEM_ID")
  @ManyToOne(targetEntity = OrderItemImpl.class)
  protected OrderItem orderItem;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    addType      = AddMethodType.PERSIST,
    friendlyName = "OrderItemPriceDetailImpl_orderItemPriceDetailAdjustments"
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "orderItemPriceDetail",
    targetEntity  = OrderItemPriceDetailAdjustmentImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<OrderItemPriceDetailAdjustment> orderItemPriceDetailAdjustments =
    new ArrayList<OrderItemPriceDetailAdjustment>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemPriceDetailImpl_quantity",
    order        = 5,
    group        = "OrderItemPriceDetailImpl_Pricing",
    prominent    = true
  )
  @Column(
    name     = "QUANTITY",
    nullable = false
  )
  protected int quantity;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemPriceDetailImpl_useSalePrice",
    order        = 5,
    group        = "OrderItemPriceDetailImpl_Pricing",
    prominent    = true
  )
  @Column(name = "USE_SALE_PRICE")
  protected Boolean useSalePrice;

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#getOrderItem()
   */
  @Override public OrderItem getOrderItem() {
    return orderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#setOrderItem(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#getOrderItemPriceDetailAdjustments()
   */
  @Override public List<OrderItemPriceDetailAdjustment> getOrderItemPriceDetailAdjustments() {
    return orderItemPriceDetailAdjustments;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#setOrderItemAdjustments(java.util.List)
   */
  @Override public void setOrderItemAdjustments(List<OrderItemPriceDetailAdjustment> orderItemPriceDetailAdjustments) {
    this.orderItemPriceDetailAdjustments = orderItemPriceDetailAdjustments;

  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#getQuantity()
   */
  @Override public int getQuantity() {
    return quantity;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#setQuantity(int)
   */
  @Override public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected BroadleafCurrency getCurrency() {
    return getOrderItem().getOrder().getCurrency();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#getAdjustmentValue()
   */
  @Override public Money getAdjustmentValue() {
    Money adjustmentValue = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());

    for (OrderItemPriceDetailAdjustment adjustment : orderItemPriceDetailAdjustments) {
      adjustmentValue = adjustmentValue.add(adjustment.getValue());
    }

    return adjustmentValue;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#getTotalAdjustmentValue()
   */
  @Override public Money getTotalAdjustmentValue() {
    return getAdjustmentValue().multiply(quantity);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#getTotalAdjustedPrice()
   */
  @Override public Money getTotalAdjustedPrice() {
    Money basePrice = orderItem.getPriceBeforeAdjustments(getUseSalePrice());

    return basePrice.multiply(quantity).subtract(getTotalAdjustmentValue());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#getUseSalePrice()
   */
  @Override public boolean getUseSalePrice() {
    if (useSalePrice == null) {
      return false;
    } else {
      return useSalePrice;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemPriceDetail#setUseSalePrice(boolean)
   */
  @Override public void setUseSalePrice(boolean useSalePrice) {
    this.useSalePrice = Boolean.valueOf(useSalePrice);
  }

  /**
   * @see  org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable#getCurrencyCode()
   */
  @Override public String getCurrencyCode() {
    if (getCurrency() != null) {
      return getCurrency().getCurrencyCode();
    }

    return null;
  }
} // end class OrderItemPriceDetailImpl
