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

import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

import org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType;

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
@DiscriminatorColumn(name = "TYPE")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FULFILLMENT_GROUP_ITEM")
public class FulfillmentGroupItemImpl implements FulfillmentGroupItem, Cloneable, CurrencyCodeIdentifiable {
  private static final Log  LOG              = LogFactory.getLog(FulfillmentGroupItemImpl.class);
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "FULFILLMENT_GROUP_ITEM_ID")
  @GeneratedValue(generator = "FulfillmentGroupItemId")
  @GenericGenerator(
    name       = "FulfillmentGroupItemId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FulfillmentGroupItemImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.FulfillmentGroupItemImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Index(
    name        = "FGITEM_FG_INDEX",
    columnNames = { "FULFILLMENT_GROUP_ID" }
  )
  @JoinColumn(name = "FULFILLMENT_GROUP_ID")
  @ManyToOne(
    targetEntity = FulfillmentGroupImpl.class,
    optional     = false
  )
  protected FulfillmentGroup fulfillmentGroup;

  // this needs to stay OrderItem in order to provide backwards compatibility for those implementations that place a BundleOrderItem
  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupItemImpl_Order_Item",
    prominent    = true,
    order        = 1000,
    gridOrder    = 1000
  )
  @AdminPresentationToOneLookup
  @JoinColumn(name = "ORDER_ITEM_ID")
  @ManyToOne(
    targetEntity = OrderItemImpl.class,
    optional     = false
  )
  protected OrderItem                               orderItem;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupItemImpl_Quantity",
    prominent    = true,
    order        = 2000,
    gridOrder    = 2000
  )
  @Column(
    name     = "QUANTITY",
    nullable = false
  )
  protected int quantity;

  @AdminPresentation(
    friendlyName = "FulfillmentGroupItemImpl_Status",
    prominent    = true,
    order        = 3000,
    gridOrder    = 3000
  )
  @Column(name = "STATUS")
  @Index(
    name        = "FGITEM_STATUS_INDEX",
    columnNames = { "STATUS" }
  )
  private String status;

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @JoinTable(
    name               = "BLC_FG_ITEM_TAX_XREF",
    joinColumns        = @JoinColumn(name = "FULFILLMENT_GROUP_ITEM_ID"),
    inverseJoinColumns = @JoinColumn(name = "TAX_DETAIL_ID")
  )
  @OneToMany(
    fetch         = FetchType.LAZY,
    targetEntity  = TaxDetailImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<TaxDetail> taxes = new ArrayList<TaxDetail>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupItemImpl_Total_Item_Tax",
    order        = 4000,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "TOTAL_ITEM_TAX",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal totalTax;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupItemImpl_Total_Item_Amount",
    order        = 5000,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "TOTAL_ITEM_AMOUNT",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal totalItemAmount;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupItemImpl_Total_Item_Taxable_Amount",
    order        = 6000,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "TOTAL_ITEM_TAXABLE_AMOUNT",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal totalItemTaxableAmount;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupItemImpl_Prorated_Adjustment",
    order        = 7000,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(name = "PRORATED_ORDER_ADJ")
  protected BigDecimal proratedOrderAdjustment;

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getFulfillmentGroup()
   */
  @Override public FulfillmentGroup getFulfillmentGroup() {
    return fulfillmentGroup;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup)
   */
  @Override public void setFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
    this.fulfillmentGroup = fulfillmentGroup;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getOrderItem()
   */
  @Override public OrderItem getOrderItem() {
    return orderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setOrderItem(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getQuantity()
   */
  @Override public int getQuantity() {
    return quantity;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setQuantity(int)
   */
  @Override public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getRetailPrice()
   */
  @Override public Money getRetailPrice() {
    return orderItem.getRetailPrice();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getSalePrice()
   */
  @Override public Money getSalePrice() {
    return orderItem.getSalePrice();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getPrice()
   */
  @Override public Money getPrice() {
    return orderItem.getAveragePrice();
  }

  /**
   * DOCUMENT ME!
   *
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Money convertToMoney(BigDecimal amount) {
    return (amount == null) ? null : BroadleafCurrencyUtils.getMoney(amount, orderItem.getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getTotalItemAmount()
   */
  @Override public Money getTotalItemAmount() {
    return convertToMoney(totalItemAmount);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setTotalItemAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalItemAmount(Money amount) {
    totalItemAmount = amount.getAmount();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getProratedOrderAdjustmentAmount()
   */
  @Override public Money getProratedOrderAdjustmentAmount() {
    return convertToMoney(proratedOrderAdjustment);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setProratedOrderAdjustmentAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setProratedOrderAdjustmentAmount(Money proratedOrderAdjustment) {
    this.proratedOrderAdjustment = proratedOrderAdjustment.getAmount();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getTotalItemTaxableAmount()
   */
  @Override public Money getTotalItemTaxableAmount() {
    return convertToMoney(totalItemTaxableAmount);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setTotalItemTaxableAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalItemTaxableAmount(Money taxableAmount) {
    totalItemTaxableAmount = taxableAmount.getAmount();
  }


  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getStatus()
   */
  @Override public FulfillmentGroupStatusType getStatus() {
    return FulfillmentGroupStatusType.getInstance(this.status);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setStatus(org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType)
   */
  @Override public void setStatus(FulfillmentGroupStatusType status) {
    this.status = status.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#removeAssociations()
   */
  @Override public void removeAssociations() {
    if (getFulfillmentGroup() != null) {
      getFulfillmentGroup().getFulfillmentGroupItems().remove(this);
    }

    setFulfillmentGroup(null);
    setOrderItem(null);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getTaxes()
   */
  @Override public List<TaxDetail> getTaxes() {
    return this.taxes;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setTaxes(java.util.List)
   */
  @Override public void setTaxes(List<TaxDetail> taxes) {
    this.taxes = taxes;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getTotalTax()
   */
  @Override public Money getTotalTax() {
    return (totalTax == null)
      ? null : BroadleafCurrencyUtils.getMoney(totalTax, getFulfillmentGroup().getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#setTotalTax(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalTax(Money totalTax) {
    this.totalTax = Money.toAmount(totalTax);
  }

  /**
   * @see  org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable#getCurrencyCode()
   */
  @Override public String getCurrencyCode() {
    return ((CurrencyCodeIdentifiable) fulfillmentGroup).getCurrencyCode();
  }

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroupItem  DOCUMENT ME!
   *
   * @throws  CloneNotSupportedException  DOCUMENT ME!
   * @throws  SecurityException           DOCUMENT ME!
   * @throws  NoSuchMethodException       DOCUMENT ME!
   */
  public void checkCloneable(FulfillmentGroupItem fulfillmentGroupItem) throws CloneNotSupportedException,
    SecurityException, NoSuchMethodException {
    Method cloneMethod = fulfillmentGroupItem.getClass().getMethod("clone", new Class[] {});

    if (cloneMethod.getDeclaringClass().getName().startsWith("org.broadleafcommerce")
          && !orderItem.getClass().getName().startsWith("org.broadleafcommerce")) {
      // subclass is not implementing the clone method
      throw new CloneNotSupportedException(
        "Custom extensions and implementations should implement clone in order to guarantee split and merge operations are performed accurately");
    }
  }

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public FulfillmentGroupItem clone() {
    // this is likely an extended class - instantiate from the fully qualified name via reflection
    FulfillmentGroupItem clonedFulfillmentGroupItem;

    try {
      clonedFulfillmentGroupItem = (FulfillmentGroupItem) Class.forName(this.getClass().getName()).newInstance();

      try {
        checkCloneable(clonedFulfillmentGroupItem);
      } catch (CloneNotSupportedException e) {
        LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: "
          + clonedFulfillmentGroupItem.getClass().getName(), e);
      }

      clonedFulfillmentGroupItem.setFulfillmentGroup(getFulfillmentGroup());
      clonedFulfillmentGroupItem.setOrderItem(getOrderItem());
      clonedFulfillmentGroupItem.setQuantity(getQuantity());
      clonedFulfillmentGroupItem.setTotalItemAmount(getTotalItemAmount());
      clonedFulfillmentGroupItem.setTotalItemTaxableAmount(getTotalItemTaxableAmount());

      if (getStatus() != null) {
        clonedFulfillmentGroupItem.setStatus(getStatus());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return clonedFulfillmentGroupItem;
  } // end method clone

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroupItem#getHasProratedOrderAdjustments()
   */
  @Override public boolean getHasProratedOrderAdjustments() {
    if (proratedOrderAdjustment != null) {
      return (proratedOrderAdjustment.compareTo(BigDecimal.ZERO) == 0);
    }

    return false;
  }

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

    FulfillmentGroupItemImpl other = (FulfillmentGroupItemImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (orderItem == null) {
      if (other.orderItem != null) {
        return false;
      }
    } else if (!orderItem.equals(other.orderItem)) {
      return false;
    }

    return true;
  } // end method equals

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((orderItem == null) ? 0 : orderItem.hashCode());

    return result;
  }
} // end class FulfillmentGroupItemImpl
