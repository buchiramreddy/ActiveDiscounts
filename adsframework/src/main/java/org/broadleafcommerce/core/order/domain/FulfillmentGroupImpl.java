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
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer;
import org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOfferImpl;
import org.broadleafcommerce.core.offer.domain.FulfillmentGroupAdjustment;
import org.broadleafcommerce.core.offer.domain.FulfillmentGroupAdjustmentImpl;
import org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;

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
  friendlyName        = "FulfillmentGroupImpl_baseFulfillmentGroup"
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
    ),
    @AdminPresentationMergeOverride(
      name = "currency",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.PROMINENT,
          booleanOverrideValue = false
        )
    ),
    @AdminPresentationMergeOverride(
      name = "personalMessage",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.TAB,
          overrideValue = FulfillmentGroupImpl.Presentation.Tab.Name.Advanced
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.TABORDER,
          intOverrideValue = FulfillmentGroupImpl.Presentation.Tab.Order.Advanced
        )
      }
    ),
    @AdminPresentationMergeOverride(
      name = "address",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.TAB,
          overrideValue = FulfillmentGroupImpl.Presentation.Tab.Name.Address
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.TABORDER,
          intOverrideValue = FulfillmentGroupImpl.Presentation.Tab.Order.Address
        )
      }
    ),
    @AdminPresentationMergeOverride(
      name = "address.isDefault",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = true
        )
      }
    ),
    @AdminPresentationMergeOverride(
      name = "address.isActive",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = true
        )
      }
    ),
    @AdminPresentationMergeOverride(
      name = "address.isBusiness",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = true
        )
      }
    ),
    @AdminPresentationMergeOverride(
      name = "phone",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = true
        )
      }
    ),
    @AdminPresentationMergeOverride(
      name = "phone.phoneNumber",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = false
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.ORDER,
          intOverrideValue = FulfillmentGroupImpl.Presentation.FieldOrder.PHONE
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.GROUP,
          overrideValue = "General"
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.REQUIREDOVERRIDE,
          overrideValue = "NOT_REQUIRED"
        )
      }
    )
  }
)
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FULFILLMENT_GROUP")
public class FulfillmentGroupImpl implements FulfillmentGroup, CurrencyCodeIdentifiable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "FULFILLMENT_GROUP_ID")
  @GeneratedValue(generator = "FulfillmentGroupId")
  @GenericGenerator(
    name       = "FulfillmentGroupId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FulfillmentGroupImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.FulfillmentGroupImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_FG_Reference_Number",
    order        = Presentation.FieldOrder.REFNUMBER,
    groupOrder   = Presentation.Group.Order.General
  )
  @Column(name = "REFERENCE_NUMBER")
  @Index(
    name        = "FG_REFERENCE_INDEX",
    columnNames = { "REFERENCE_NUMBER" }
  )
  protected String referenceNumber;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "METHOD")
  @Deprecated
  @Index(
    name        = "FG_METHOD_INDEX",
    columnNames = { "METHOD" }
  )
  protected String             method;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "SERVICE")
  @Deprecated
  @Index(
    name        = "FG_SERVICE_INDEX",
    columnNames = { "SERVICE" }
  )
  protected String             service;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_Retail_Shipping_Price",
    order        = Presentation.FieldOrder.RETAIL,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "RETAIL_PRICE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal retailFulfillmentPrice;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_Sale_Shipping_Price",
    order        = Presentation.FieldOrder.SALE,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "SALE_PRICE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal saleFulfillmentPrice;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_Shipping_Price",
    order        = Presentation.FieldOrder.PRICE,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "PRICE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal fulfillmentPrice;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "FulfillmentGroupImpl_FG_Type",
    order                = Presentation.FieldOrder.TYPE,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.order.service.type.FulfillmentType",
    prominent            = true,
    gridOrder            = 3000
  )
  @Column(name = "TYPE")
  protected String type;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_FG_Total_Tax",
    order        = Presentation.FieldOrder.TOTALTAX,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "TOTAL_TAX",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal totalTax;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_FG_Total_Item_Tax",
    order        = Presentation.FieldOrder.ITEMTAX,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "TOTAL_ITEM_TAX",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal totalItemTax;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_FG_Total_Fee_Tax",
    order        = Presentation.FieldOrder.FEETAX,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "TOTAL_FEE_TAX",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal totalFeeTax;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_FG_Total_FG_Tax",
    order        = Presentation.FieldOrder.FGTAX,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "TOTAL_FG_TAX",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal totalFulfillmentGroupTax;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_FG_Delivery_Instruction",
    order        = Presentation.FieldOrder.DELIVERINSTRUCTION
  )
  @Column(name = "DELIVERY_INSTRUCTION")
  protected String deliveryInstruction;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_Primary_FG",
    order        = Presentation.FieldOrder.PRIMARY
  )
  @Column(name = "IS_PRIMARY")
  @Index(
    name        = "FG_PRIMARY_INDEX",
    columnNames = { "IS_PRIMARY" }
  )
  protected boolean primary = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_FG_Merchandise_Total",
    order        = Presentation.FieldOrder.MERCHANDISETOTAL,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "MERCHANDISE_TOTAL",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal merchandiseTotal;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_FG_Total",
    order        = Presentation.FieldOrder.TOTAL,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing,
    fieldType    = SupportedFieldType.MONEY,
    prominent    = true,
    gridOrder    = 2000
  )
  @Column(
    name      = "TOTAL",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal total;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "FulfillmentGroupImpl_FG_Status",
    order                = Presentation.FieldOrder.STATUS,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType",
    prominent            = true,
    gridOrder            = 4000
  )
  @Column(name = "STATUS")
  @Index(
    name        = "FG_STATUS_INDEX",
    columnNames = { "STATUS" }
  )
  protected String status;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentGroupImpl_Shipping_Price_Taxable",
    order        = Presentation.FieldOrder.TAXABLE,
    group        = Presentation.Group.Name.Pricing,
    groupOrder   = Presentation.Group.Order.Pricing,
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing
  )
  @Column(name = "SHIPPING_PRICE_TAXABLE")
  protected Boolean isShippingPriceTaxable = Boolean.FALSE;

  /** DOCUMENT ME! */
  @JoinColumn(name = "FULFILLMENT_OPTION_ID")
  @ManyToOne(
    targetEntity = FulfillmentOptionImpl.class,
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  protected FulfillmentOption fulfillmentOption;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Index(
    name        = "FG_ORDER_INDEX",
    columnNames = { "ORDER_ID" }
  )
  @JoinColumn(name = "ORDER_ID")
  @ManyToOne(
    targetEntity = OrderImpl.class,
    optional     = false
  )
  protected Order order;

  /** DOCUMENT ME! */
  @Column(name = "FULFILLMENT_GROUP_SEQUNCE")
  protected Integer sequence;

  /** DOCUMENT ME! */
  @Index(
    name        = "FG_ADDRESS_INDEX",
    columnNames = { "ADDRESS_ID" }
  )
  @JoinColumn(name = "ADDRESS_ID")
  @ManyToOne(
    targetEntity = AddressImpl.class,
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  protected Address address;

  /** DOCUMENT ME! */
  @Index(
    name        = "FG_PHONE_INDEX",
    columnNames = { "PHONE_ID" }
  )
  @JoinColumn(name = "PHONE_ID")
  @ManyToOne(
    targetEntity = PhoneImpl.class,
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  protected Phone phone;

  /** DOCUMENT ME! */
  @Index(
    name        = "FG_MESSAGE_INDEX",
    columnNames = { "PERSONAL_MESSAGE_ID" }
  )
  @JoinColumn(name = "PERSONAL_MESSAGE_ID")
  @ManyToOne(
    targetEntity = PersonalMessageImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected PersonalMessage personalMessage;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "FulfillmentGroupImpl_Items",
    tab          = Presentation.Tab.Name.Items,
    tabOrder     = Presentation.Tab.Order.Items
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "fulfillmentGroup",
    targetEntity  = FulfillmentGroupItemImpl.class,
    cascade       = CascadeType.ALL,
    orphanRemoval = true
  )
  protected List<FulfillmentGroupItem> fulfillmentGroupItems = new ArrayList<FulfillmentGroupItem>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "FulfillmentGroupImpl_Fees",
    tab          = Presentation.Tab.Name.Pricing,
    tabOrder     = Presentation.Tab.Order.Pricing
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "fulfillmentGroup",
    targetEntity  = FulfillmentGroupFeeImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<FulfillmentGroupFee> fulfillmentGroupFees = new ArrayList<FulfillmentGroupFee>();

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "fulfillmentGroup",
    targetEntity  = CandidateFulfillmentGroupOfferImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<CandidateFulfillmentGroupOffer> candidateOffers = new ArrayList<CandidateFulfillmentGroupOffer>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "FulfillmentGroupImpl_Adjustments",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "fulfillmentGroup",
    targetEntity  = FulfillmentGroupAdjustmentImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<FulfillmentGroupAdjustment> fulfillmentGroupAdjustments = new ArrayList<FulfillmentGroupAdjustment>();

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @JoinTable(
    name               = "BLC_FG_FG_TAX_XREF",
    joinColumns        = @JoinColumn(name = "FULFILLMENT_GROUP_ID"),
    inverseJoinColumns = @JoinColumn(name = "TAX_DETAIL_ID")
  )
  @OneToMany(
    fetch         = FetchType.LAZY,
    targetEntity  = TaxDetailImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<TaxDetail> taxes = new ArrayList<TaxDetail>();

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getOrder()
   */
  @Override public Order getOrder() {
    return order;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getFulfillmentOption()
   */
  @Override public FulfillmentOption getFulfillmentOption() {
    return fulfillmentOption;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setFulfillmentOption(org.broadleafcommerce.core.order.domain.FulfillmentOption)
   */
  @Override public void setFulfillmentOption(FulfillmentOption fulfillmentOption) {
    this.fulfillmentOption = fulfillmentOption;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getReferenceNumber()
   */
  @Override public String getReferenceNumber() {
    return referenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setReferenceNumber(java.lang.String)
   */
  @Override public void setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getFulfillmentGroupItems()
   */
  @Override public List<FulfillmentGroupItem> getFulfillmentGroupItems() {
    return fulfillmentGroupItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getDiscreteOrderItems()
   */
  @Override public List<DiscreteOrderItem> getDiscreteOrderItems() {
    List<DiscreteOrderItem> discreteOrderItems = new ArrayList<DiscreteOrderItem>();

    for (FulfillmentGroupItem fgItem : fulfillmentGroupItems) {
      OrderItem orderItem = fgItem.getOrderItem();

      if (orderItem instanceof BundleOrderItemImpl) {
        BundleOrderItemImpl bundleOrderItem = (BundleOrderItemImpl) orderItem;

        for (DiscreteOrderItem discreteOrderItem : bundleOrderItem.getDiscreteOrderItems()) {
          discreteOrderItems.add(discreteOrderItem);
        }
      } else {
        DiscreteOrderItem discreteOrderItem = (DiscreteOrderItem) orderItem;
        discreteOrderItems.add(discreteOrderItem);
      }
    }

    return discreteOrderItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setFulfillmentGroupItems(java.util.List)
   */
  @Override public void setFulfillmentGroupItems(List<FulfillmentGroupItem> fulfillmentGroupItems) {
    this.fulfillmentGroupItems = fulfillmentGroupItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#addFulfillmentGroupItem(org.broadleafcommerce.core.order.domain.FulfillmentGroupItem)
   */
  @Override public void addFulfillmentGroupItem(FulfillmentGroupItem fulfillmentGroupItem) {
    if (this.fulfillmentGroupItems == null) {
      this.fulfillmentGroupItems = new Vector<FulfillmentGroupItem>();
    }

    this.fulfillmentGroupItems.add(fulfillmentGroupItem);

  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getAddress()
   */
  @Override public Address getAddress() {
    return address;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setAddress(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getPhone()
   */
  @Override public Phone getPhone() {
    return phone;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setPhone(org.broadleafcommerce.profile.core.domain.Phone)
   */
  @Override public void setPhone(Phone phone) {
    this.phone = phone;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getMethod()
   */
  @Deprecated @Override public String getMethod() {
    return method;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setMethod(java.lang.String)
   */
  @Deprecated @Override public void setMethod(String fulfillmentMethod) {
    this.method = fulfillmentMethod;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getRetailFulfillmentPrice()
   */
  @Override public Money getRetailFulfillmentPrice() {
    return (retailFulfillmentPrice == null)
      ? null : BroadleafCurrencyUtils.getMoney(retailFulfillmentPrice, getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setRetailFulfillmentPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setRetailFulfillmentPrice(Money retailFulfillmentPrice) {
    this.retailFulfillmentPrice = Money.toAmount(retailFulfillmentPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getRetailShippingPrice()
   */
  @Override public Money getRetailShippingPrice() {
    return getRetailFulfillmentPrice();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setRetailShippingPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setRetailShippingPrice(Money retailShippingPrice) {
    setRetailFulfillmentPrice(retailShippingPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getType()
   */
  @Override public FulfillmentType getType() {
    return FulfillmentType.getInstance(type);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setType(org.broadleafcommerce.core.order.service.type.FulfillmentType)
   */
  @Override public void setType(FulfillmentType type) {
    this.type = (type == null) ? null : type.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#addCandidateFulfillmentGroupOffer(org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer)
   */
  @Override public void addCandidateFulfillmentGroupOffer(CandidateFulfillmentGroupOffer candidateOffer) {
    candidateOffers.add(candidateOffer);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getCandidateFulfillmentGroupOffers()
   */
  @Override public List<CandidateFulfillmentGroupOffer> getCandidateFulfillmentGroupOffers() {
    return candidateOffers;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setCandidateFulfillmentGroupOffer(java.util.List)
   */
  @Override public void setCandidateFulfillmentGroupOffer(List<CandidateFulfillmentGroupOffer> candidateOffers) {
    this.candidateOffers = candidateOffers;

  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#removeAllCandidateOffers()
   */
  @Override public void removeAllCandidateOffers() {
    if (candidateOffers != null) {
      for (CandidateFulfillmentGroupOffer offer : candidateOffers) {
        offer.setFulfillmentGroup(null);
      }

      candidateOffers.clear();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getFulfillmentGroupAdjustments()
   */
  @Override public List<FulfillmentGroupAdjustment> getFulfillmentGroupAdjustments() {
    return this.fulfillmentGroupAdjustments;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getFulfillmentGroupAdjustmentsValue()
   */
  @Override public Money getFulfillmentGroupAdjustmentsValue() {
    Money adjustmentsValue = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getOrder().getCurrency());

    for (FulfillmentGroupAdjustment adjustment : fulfillmentGroupAdjustments) {
      adjustmentsValue = adjustmentsValue.add(adjustment.getValue());
    }

    return adjustmentsValue;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#removeAllAdjustments()
   */
  @Override public void removeAllAdjustments() {
    if (fulfillmentGroupAdjustments != null) {
      for (FulfillmentGroupAdjustment adjustment : fulfillmentGroupAdjustments) {
        adjustment.setFulfillmentGroup(null);
      }

      fulfillmentGroupAdjustments.clear();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setFulfillmentGroupAdjustments(java.util.List)
   */
  @Override public void setFulfillmentGroupAdjustments(List<FulfillmentGroupAdjustment> fulfillmentGroupAdjustments) {
    this.fulfillmentGroupAdjustments = fulfillmentGroupAdjustments;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getSaleFulfillmentPrice()
   */
  @Override public Money getSaleFulfillmentPrice() {
    return (saleFulfillmentPrice == null)
      ? null : BroadleafCurrencyUtils.getMoney(saleFulfillmentPrice,
        getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setSaleFulfillmentPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setSaleFulfillmentPrice(Money saleFulfillmentPrice) {
    this.saleFulfillmentPrice = Money.toAmount(saleFulfillmentPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getSaleShippingPrice()
   */
  @Override public Money getSaleShippingPrice() {
    return getSaleFulfillmentPrice();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setSaleShippingPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setSaleShippingPrice(Money saleShippingPrice) {
    setSaleFulfillmentPrice(saleShippingPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getFulfillmentPrice()
   */
  @Override public Money getFulfillmentPrice() {
    return (fulfillmentPrice == null) ? null
                                      : BroadleafCurrencyUtils.getMoney(fulfillmentPrice,
        getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setFulfillmentPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setFulfillmentPrice(Money fulfillmentPrice) {
    this.fulfillmentPrice = Money.toAmount(fulfillmentPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getShippingPrice()
   */
  @Override public Money getShippingPrice() {
    return getFulfillmentPrice();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setShippingPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setShippingPrice(Money shippingPrice) {
    setFulfillmentPrice(shippingPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getTaxes()
   */
  @Override public List<TaxDetail> getTaxes() {
    return taxes;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setTaxes(java.util.List)
   */
  @Override public void setTaxes(List<TaxDetail> taxes) {
    this.taxes = taxes;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getTotalTax()
   */
  @Override public Money getTotalTax() {
    return (totalTax == null) ? null : BroadleafCurrencyUtils.getMoney(totalTax, getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setTotalTax(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalTax(Money totalTax) {
    this.totalTax = Money.toAmount(totalTax);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getTotalItemTax()
   */
  @Override public Money getTotalItemTax() {
    return (totalItemTax == null) ? null : BroadleafCurrencyUtils.getMoney(totalItemTax, getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setTotalItemTax(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalItemTax(Money totalItemTax) {
    this.totalItemTax = Money.toAmount(totalItemTax);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getTotalFeeTax()
   */
  @Override public Money getTotalFeeTax() {
    return (totalFeeTax == null) ? null : BroadleafCurrencyUtils.getMoney(totalFeeTax, getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setTotalFeeTax(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalFeeTax(Money totalFeeTax) {
    this.totalFeeTax = Money.toAmount(totalFeeTax);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getTotalFulfillmentGroupTax()
   */
  @Override public Money getTotalFulfillmentGroupTax() {
    return (totalFulfillmentGroupTax == null)
      ? null : BroadleafCurrencyUtils.getMoney(totalFulfillmentGroupTax,
        getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setTotalFulfillmentGroupTax(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalFulfillmentGroupTax(Money totalFulfillmentGroupTax) {
    this.totalFulfillmentGroupTax = Money.toAmount(totalFulfillmentGroupTax);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getDeliveryInstruction()
   */
  @Override public String getDeliveryInstruction() {
    return deliveryInstruction;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setDeliveryInstruction(java.lang.String)
   */
  @Override public void setDeliveryInstruction(String deliveryInstruction) {
    this.deliveryInstruction = deliveryInstruction;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getPersonalMessage()
   */
  @Override public PersonalMessage getPersonalMessage() {
    return personalMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setPersonalMessage(org.broadleafcommerce.core.order.domain.PersonalMessage)
   */
  @Override public void setPersonalMessage(PersonalMessage personalMessage) {
    this.personalMessage = personalMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#isPrimary()
   */
  @Override public boolean isPrimary() {
    return primary;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setPrimary(boolean)
   */
  @Override public void setPrimary(boolean primary) {
    this.primary = primary;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getMerchandiseTotal()
   */
  @Override public Money getMerchandiseTotal() {
    return (merchandiseTotal == null) ? null
                                      : BroadleafCurrencyUtils.getMoney(merchandiseTotal,
        getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setMerchandiseTotal(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setMerchandiseTotal(Money merchandiseTotal) {
    this.merchandiseTotal = Money.toAmount(merchandiseTotal);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getTotal()
   */
  @Override public Money getTotal() {
    return (total == null) ? null : BroadleafCurrencyUtils.getMoney(total, getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setTotal(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotal(Money orderTotal) {
    this.total = Money.toAmount(orderTotal);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getStatus()
   */
  @Override public FulfillmentGroupStatusType getStatus() {
    return FulfillmentGroupStatusType.getInstance(status);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setStatus(org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType)
   */
  @Override public void setStatus(FulfillmentGroupStatusType status) {
    this.status = status.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getFulfillmentGroupFees()
   */
  @Override public List<FulfillmentGroupFee> getFulfillmentGroupFees() {
    return fulfillmentGroupFees;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setFulfillmentGroupFees(java.util.List)
   */
  @Override public void setFulfillmentGroupFees(List<FulfillmentGroupFee> fulfillmentGroupFees) {
    this.fulfillmentGroupFees = fulfillmentGroupFees;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#addFulfillmentGroupFee(org.broadleafcommerce.core.order.domain.FulfillmentGroupFee)
   */
  @Override public void addFulfillmentGroupFee(FulfillmentGroupFee fulfillmentGroupFee) {
    if (fulfillmentGroupFees == null) {
      fulfillmentGroupFees = new ArrayList<FulfillmentGroupFee>();
    }

    fulfillmentGroupFees.add(fulfillmentGroupFee);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#removeAllFulfillmentGroupFees()
   */
  @Override public void removeAllFulfillmentGroupFees() {
    if (fulfillmentGroupFees != null) {
      fulfillmentGroupFees.clear();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#isShippingPriceTaxable()
   */
  @Override public Boolean isShippingPriceTaxable() {
    return isShippingPriceTaxable;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setIsShippingPriceTaxable(java.lang.Boolean)
   */
  @Override public void setIsShippingPriceTaxable(Boolean isShippingPriceTaxable) {
    this.isShippingPriceTaxable = isShippingPriceTaxable;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setSequence(java.lang.Integer)
   */
  @Override public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getSequence()
   */
  @Override public Integer getSequence() {
    return this.sequence;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#getService()
   */
  @Deprecated @Override public String getService() {
    return service;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentGroup#setService(java.lang.String)
   */
  @Deprecated @Override public void setService(String service) {
    this.service = service;
  }

  /**
   * @see  org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable#getCurrencyCode()
   */
  @Override public String getCurrencyCode() {
    if (getOrder().getCurrency() != null) {
      return getOrder().getCurrency().getCurrencyCode();
    }

    return null;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((address == null) ? 0 : address.hashCode());
    result = (prime * result) + ((fulfillmentGroupItems == null) ? 0 : fulfillmentGroupItems.hashCode());

    return result;
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

    FulfillmentGroupImpl other = (FulfillmentGroupImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (address == null) {
      if (other.address != null) {
        return false;
      }
    } else if (!address.equals(other.address)) {
      return false;
    }

    if (fulfillmentGroupItems == null) {
      if (other.fulfillmentGroupItems != null) {
        return false;
      }
    } else if (!fulfillmentGroupItems.equals(other.fulfillmentGroupItems)) {
      return false;
    }

    return true;
  } // end method equals

  public static class Presentation {
    public static class Tab {
      public static class Name {
        public static final String Items    = "FulfillmentGroupImpl_Items_Tab";
        public static final String Pricing  = "FulfillmentGroupImpl_Pricing_Tab";
        public static final String Address  = "FulfillmentGroupImpl_Address_Tab";
        public static final String Advanced = "FulfillmentGroupImpl_Advanced_Tab";
      }

      public static class Order {
        public static final int Items    = 2000;
        public static final int Pricing  = 3000;
        public static final int Address  = 4000;
        public static final int Advanced = 5000;
      }
    }

    public static class Group {
      public static class Name {
        public static final String Pricing = "FulfillmentGroupImpl_Pricing";
      }

      public static class Order {
        public static final int General = 1000;
        public static final int Pricing = 2000;
      }
    }

    public static class FieldOrder {
      public static final int REFNUMBER          = 3000;
      public static final int STATUS             = 4000;
      public static final int TYPE               = 5000;
      public static final int DELIVERINSTRUCTION = 6000;
      public static final int PRIMARY            = 7000;
      public static final int PHONE              = 8000;

      public static final int RETAIL           = 1000;
      public static final int SALE             = 2000;
      public static final int PRICE            = 3000;
      public static final int ITEMTAX          = 4000;
      public static final int FEETAX           = 5000;
      public static final int FGTAX            = 6000;
      public static final int TOTALTAX         = 7000;
      public static final int MERCHANDISETOTAL = 8000;
      public static final int TOTAL            = 9000;
      public static final int TAXABLE          = 10000;
    }
  } // end class Presentation
} // end class FulfillmentGroupImpl
