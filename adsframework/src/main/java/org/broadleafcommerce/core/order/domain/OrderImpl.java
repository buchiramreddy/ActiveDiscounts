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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.audit.Auditable;
import org.broadleafcommerce.common.audit.AuditableListener;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.locale.domain.LocaleImpl;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationMap;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.offer.domain.CandidateOrderOffer;
import org.broadleafcommerce.core.offer.domain.CandidateOrderOfferImpl;
import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.domain.OfferCodeImpl;
import org.broadleafcommerce.core.offer.domain.OfferImpl;
import org.broadleafcommerce.core.offer.domain.OfferInfo;
import org.broadleafcommerce.core.offer.domain.OfferInfoImpl;
import org.broadleafcommerce.core.offer.domain.OrderAdjustment;
import org.broadleafcommerce.core.offer.domain.OrderAdjustmentImpl;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentInfoImpl;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
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
  friendlyName        = "OrderImpl_baseOrder"
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
@EntityListeners(value = { AuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER")
public class OrderImpl implements Order, AdminMainEntity, CurrencyCodeIdentifiable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "ORDER_ID")
  @GeneratedValue(generator = "OrderId")
  @GenericGenerator(
    name       = "OrderId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OrderImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.OrderImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Embedded protected Auditable auditable = new Auditable();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderImpl_Order_Name",
    group        = Presentation.Group.Name.General,
    order        = Presentation.FieldOrder.NAME,
    prominent    = true,
    groupOrder   = Presentation.Group.Order.General,
    gridOrder    = 2000
  )
  @Column(name = "NAME")
  @Index(
    name        = "ORDER_NAME_INDEX",
    columnNames = { "NAME" }
  )
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderImpl_Customer",
    group        = Presentation.Group.Name.General,
    order        = Presentation.FieldOrder.CUSTOMER,
    groupOrder   = Presentation.Group.Order.General
  )
  @AdminPresentationToOneLookup
  @Index(
    name        = "ORDER_CUSTOMER_INDEX",
    columnNames = { "CUSTOMER_ID" }
  )
  @JoinColumn(
    name     = "CUSTOMER_ID",
    nullable = false
  )
  @ManyToOne(
    targetEntity = CustomerImpl.class,
    optional     = false
  )
  protected Customer                               customer;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "OrderImpl_Order_Status",
    group                = Presentation.Group.Name.General,
    order                = Presentation.FieldOrder.STATUS,
    prominent            = true,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.order.service.type.OrderStatus",
    groupOrder           = Presentation.Group.Order.General,
    gridOrder            = 3000
  )
  @Column(name = "ORDER_STATUS")
  @Index(
    name        = "ORDER_STATUS_INDEX",
    columnNames = { "ORDER_STATUS" }
  )
  protected String status;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderImpl_Order_Total_Tax",
    group        = Presentation.Group.Name.General,
    order        = Presentation.FieldOrder.TOTALTAX,
    fieldType    = SupportedFieldType.MONEY,
    groupOrder   = Presentation.Group.Order.General
  )
  @Column(
    name      = "TOTAL_TAX",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal totalTax;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderImpl_Order_Total_Shipping",
    group        = Presentation.Group.Name.General,
    order        = Presentation.FieldOrder.TOTALFGCHARGES,
    fieldType    = SupportedFieldType.MONEY,
    groupOrder   = Presentation.Group.Order.General
  )
  @Column(
    name      = "TOTAL_SHIPPING",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal totalFulfillmentCharges;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderImpl_Order_Subtotal",
    group        = Presentation.Group.Name.General,
    order        = Presentation.FieldOrder.SUBTOTAL,
    fieldType    = SupportedFieldType.MONEY,
    prominent    = true,
    groupOrder   = Presentation.Group.Order.General,
    gridOrder    = 4000
  )
  @Column(
    name      = "ORDER_SUBTOTAL",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal subTotal;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderImpl_Order_Total",
    group        = Presentation.Group.Name.General,
    order        = Presentation.FieldOrder.TOTAL,
    fieldType    = SupportedFieldType.MONEY,
    groupOrder   = Presentation.Group.Order.General
  )
  @Column(
    name      = "ORDER_TOTAL",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal total;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderImpl_Order_Submit_Date",
    group        = Presentation.Group.Name.General,
    order        = Presentation.FieldOrder.SUBMITDATE,
    groupOrder   = Presentation.Group.Order.General,
    prominent    = true,
    gridOrder    = 5000
  )
  @Column(name = "SUBMIT_DATE")
  protected Date submitDate;

  @AdminPresentation(
    friendlyName = "OrderImpl_Order_Number",
    group        = Presentation.Group.Name.General,
    order        = Presentation.FieldOrder.ORDERNUMBER,
    prominent    = true,
    groupOrder   = Presentation.Group.Order.General,
    gridOrder    = 1000
  )
  @Column(name = "ORDER_NUMBER")
  @Index(
    name        = "ORDER_NUMBER_INDEX",
    columnNames = { "ORDER_NUMBER" }
  )
  private String orderNumber;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderImpl_Order_Email_Address",
    group        = Presentation.Group.Name.General,
    order        = Presentation.FieldOrder.EMAILADDRESS,
    groupOrder   = Presentation.Group.Order.General
  )
  @Column(name = "EMAIL_ADDRESS")
  @Index(
    name        = "ORDER_EMAIL_INDEX",
    columnNames = { "EMAIL_ADDRESS" }
  )
  protected String emailAddress;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "OrderImpl_Order_Items",
    tab          = Presentation.Tab.Name.OrderItems,
    tabOrder     = Presentation.Tab.Order.OrderItems
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy     = "order",
    targetEntity = OrderItemImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<OrderItem> orderItems = new ArrayList<OrderItem>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "OrderImpl_Fulfillment_Groups",
    tab          = Presentation.Tab.Name.FulfillmentGroups,
    tabOrder     = Presentation.Tab.Order.FulfillmentGroups
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy     = "order",
    targetEntity = FulfillmentGroupImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<FulfillmentGroup> fulfillmentGroups = new ArrayList<FulfillmentGroup>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "OrderImpl_Adjustments",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    order        = Presentation.FieldOrder.ADJUSTMENTS
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "order",
    targetEntity  = OrderAdjustmentImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<OrderAdjustment> orderAdjustments = new ArrayList<OrderAdjustment>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "OrderImpl_Offer_Codes",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    manyToField  = "orders",
    order        = Presentation.FieldOrder.OFFERCODES
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @JoinTable(
    name               = "BLC_ORDER_OFFER_CODE_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "ORDER_ID",
        referencedColumnName = "ORDER_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "OFFER_CODE_ID",
        referencedColumnName = "OFFER_CODE_ID"
      )
  )
  @ManyToMany(
    fetch        = FetchType.LAZY,
    targetEntity = OfferCodeImpl.class
  )
  protected List<OfferCode> addedOfferCodes = new ArrayList<OfferCode>();

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "order",
    targetEntity  = CandidateOrderOfferImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<CandidateOrderOffer> candidateOrderOffers = new ArrayList<CandidateOrderOffer>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "OrderImpl_Payment_Infos",
    tab          = Presentation.Tab.Name.Payment,
    tabOrder     = Presentation.Tab.Order.Payment
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "order",
    targetEntity  = PaymentInfoImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<PaymentInfo> paymentInfos = new ArrayList<PaymentInfo>();

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_ADDITIONAL_OFFER_INFO",
    joinColumns        =
      @JoinColumn(
        name           = "BLC_ORDER_ORDER_ID",
        referencedColumnName = "ORDER_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "OFFER_INFO_ID",
        referencedColumnName = "OFFER_INFO_ID"
      )
  )
  @ManyToMany(targetEntity = OfferInfoImpl.class)
  @MapKeyClass(OfferImpl.class)
  @MapKeyJoinColumn(name = "OFFER_ID")
  protected Map<Offer, OfferInfo> additionalOfferInformation = new HashMap<Offer, OfferInfo>();

  /** DOCUMENT ME! */
  @AdminPresentationMap(
    friendlyName            = "OrderImpl_Attributes",
    forceFreeFormKeys       = true,
    keyPropertyFriendlyName = "OrderImpl_Attributes_Key_Name"
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @MapKey(name = "name")
  @OneToMany(
    mappedBy      = "order",
    targetEntity  = OrderAttributeImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected Map<String, OrderAttribute> orderAttributes = new HashMap<String, OrderAttribute>();

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "CURRENCY_CODE")
  @ManyToOne(targetEntity = BroadleafCurrencyImpl.class)
  protected BroadleafCurrency currency;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "LOCALE_CODE")
  @ManyToOne(targetEntity = LocaleImpl.class)
  protected Locale locale;

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getAuditable()
   */
  @Override public Auditable getAuditable() {
    return auditable;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setAuditable(org.broadleafcommerce.common.audit.Auditable)
   */
  @Override public void setAuditable(Auditable auditable) {
    this.auditable = auditable;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getSubTotal()
   */
  @Override public Money getSubTotal() {
    return (subTotal == null) ? null : BroadleafCurrencyUtils.getMoney(subTotal, getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setSubTotal(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setSubTotal(Money subTotal) {
    this.subTotal = Money.toAmount(subTotal);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#calculateSubTotal()
   */
  @Override public Money calculateSubTotal() {
    Money calculatedSubTotal = BroadleafCurrencyUtils.getMoney(getCurrency());

    for (OrderItem orderItem : orderItems) {
      calculatedSubTotal = calculatedSubTotal.add(orderItem.getTotalPrice());
    }

    return calculatedSubTotal;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#assignOrderItemsFinalPrice()
   */
  @Override public void assignOrderItemsFinalPrice() {
    for (OrderItem orderItem : orderItems) {
      orderItem.assignFinalPrice();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotal()
   */
  @Override public Money getTotal() {
    return (total == null) ? null : BroadleafCurrencyUtils.getMoney(total, getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setTotal(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotal(Money orderTotal) {
    this.total = Money.toAmount(orderTotal);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getRemainingTotal()
   */
  @Override public Money getRemainingTotal() {
    Money myTotal = getTotal();

    if (myTotal == null) {
      return null;
    }

    Money totalPayments = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());

    for (PaymentInfo pi : getPaymentInfos()) {
      if (pi.getAmount() != null) {
        totalPayments = totalPayments.add(pi.getAmount());
      }
    }

    return myTotal.subtract(totalPayments);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getCapturedTotal()
   */
  @Override public Money getCapturedTotal() {
    Money totalCaptured = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());

    for (PaymentInfo pi : getPaymentInfos()) {
      totalCaptured = totalCaptured.add(pi.getPaymentCapturedAmount());
    }

    return totalCaptured;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getSubmitDate()
   */
  @Override public Date getSubmitDate() {
    return submitDate;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setSubmitDate(java.util.Date)
   */
  @Override public void setSubmitDate(Date submitDate) {
    this.submitDate = submitDate;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getCustomer()
   */
  @Override public Customer getCustomer() {
    return customer;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getStatus()
   */
  @Override public OrderStatus getStatus() {
    return OrderStatus.getInstance(status);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setStatus(org.broadleafcommerce.core.order.service.type.OrderStatus)
   */
  @Override public void setStatus(OrderStatus status) {
    this.status = status.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderItems()
   */
  @Override public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setOrderItems(java.util.List)
   */
  @Override public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#addOrderItem(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getFulfillmentGroups()
   */
  @Override public List<FulfillmentGroup> getFulfillmentGroups() {
    return fulfillmentGroups;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setFulfillmentGroups(java.util.List)
   */
  @Override public void setFulfillmentGroups(List<FulfillmentGroup> fulfillmentGroups) {
    this.fulfillmentGroups = fulfillmentGroups;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setCandidateOrderOffers(java.util.List)
   */
  @Override public void setCandidateOrderOffers(List<CandidateOrderOffer> candidateOrderOffers) {
    this.candidateOrderOffers = candidateOrderOffers;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getCandidateOrderOffers()
   */
  @Override public List<CandidateOrderOffer> getCandidateOrderOffers() {
    return candidateOrderOffers;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getName()
   */
  @Override public String getName() {
    return name;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotalTax()
   */
  @Override public Money getTotalTax() {
    return (totalTax == null) ? null : BroadleafCurrencyUtils.getMoney(totalTax, getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setTotalTax(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalTax(Money totalTax) {
    this.totalTax = Money.toAmount(totalTax);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotalShipping()
   */
  @Override public Money getTotalShipping() {
    return getTotalFulfillmentCharges();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setTotalShipping(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalShipping(Money totalShipping) {
    setTotalFulfillmentCharges(totalShipping);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotalFulfillmentCharges()
   */
  @Override public Money getTotalFulfillmentCharges() {
    return (totalFulfillmentCharges == null) ? null
                                             : BroadleafCurrencyUtils.getMoney(totalFulfillmentCharges,
        getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setTotalFulfillmentCharges(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalFulfillmentCharges(Money totalFulfillmentCharges) {
    this.totalFulfillmentCharges = Money.toAmount(totalFulfillmentCharges);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getPaymentInfos()
   */
  @Override public List<PaymentInfo> getPaymentInfos() {
    return paymentInfos;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setPaymentInfos(java.util.List)
   */
  @Override public void setPaymentInfos(List<PaymentInfo> paymentInfos) {
    this.paymentInfos = paymentInfos;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#hasCategoryItem(java.lang.String)
   */
  @Override public boolean hasCategoryItem(String categoryName) {
    for (OrderItem orderItem : orderItems) {
      if (orderItem.isInCategory(categoryName)) {
        return true;
      }
    }

    return false;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderAdjustments()
   */
  @Override public List<OrderAdjustment> getOrderAdjustments() {
    return this.orderAdjustments;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  orderAdjustments  DOCUMENT ME!
   */
  protected void setOrderAdjustments(List<OrderAdjustment> orderAdjustments) {
    this.orderAdjustments = orderAdjustments;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getDiscreteOrderItems()
   */
  @Override public List<DiscreteOrderItem> getDiscreteOrderItems() {
    List<DiscreteOrderItem> discreteOrderItems = new ArrayList<DiscreteOrderItem>();

    for (OrderItem orderItem : orderItems) {
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
   * @see  org.broadleafcommerce.core.order.domain.Order#containsSku(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public boolean containsSku(Sku sku) {
    for (OrderItem orderItem : getOrderItems()) {
      if (orderItem instanceof DiscreteOrderItem) {
        DiscreteOrderItem discreteOrderItem = (DiscreteOrderItem) orderItem;

        if ((discreteOrderItem.getSku() != null) && discreteOrderItem.getSku().equals(sku)) {
          return true;
        }
      } else if (orderItem instanceof BundleOrderItem) {
        BundleOrderItem bundleOrderItem = (BundleOrderItem) orderItem;

        if ((bundleOrderItem.getSku() != null) && bundleOrderItem.getSku().equals(sku)) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getAddedOfferCodes()
   */
  @Override public List<OfferCode> getAddedOfferCodes() {
    return addedOfferCodes;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderNumber()
   */
  @Override public String getOrderNumber() {
    return orderNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setOrderNumber(java.lang.String)
   */
  @Override public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getFulfillmentStatus()
   */
  @Override public String getFulfillmentStatus() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getEmailAddress()
   */
  @Override public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setEmailAddress(java.lang.String)
   */
  @Override public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getAdditionalOfferInformation()
   */
  @Override public Map<Offer, OfferInfo> getAdditionalOfferInformation() {
    return additionalOfferInformation;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setAdditionalOfferInformation(java.util.Map)
   */
  @Override public void setAdditionalOfferInformation(Map<Offer, OfferInfo> additionalOfferInformation) {
    this.additionalOfferInformation = additionalOfferInformation;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getItemAdjustmentsValue()
   */
  @Override public Money getItemAdjustmentsValue() {
    Money itemAdjustmentsValue = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());

    for (OrderItem orderItem : orderItems) {
      itemAdjustmentsValue = itemAdjustmentsValue.add(orderItem.getTotalAdjustmentValue());
    }

    return itemAdjustmentsValue;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getFulfillmentGroupAdjustmentsValue()
   */
  @Override public Money getFulfillmentGroupAdjustmentsValue() {
    Money adjustmentValue = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());

    for (FulfillmentGroup fulfillmentGroup : fulfillmentGroups) {
      adjustmentValue = adjustmentValue.add(fulfillmentGroup.getFulfillmentGroupAdjustmentsValue());
    }

    return adjustmentValue;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderAdjustmentsValue()
   */
  @Override public Money getOrderAdjustmentsValue() {
    Money orderAdjustmentsValue = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());

    for (OrderAdjustment orderAdjustment : orderAdjustments) {
      orderAdjustmentsValue = orderAdjustmentsValue.add(orderAdjustment.getValue());
    }

    return orderAdjustmentsValue;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotalAdjustmentsValue()
   */
  @Override public Money getTotalAdjustmentsValue() {
    Money totalAdjustmentsValue = getItemAdjustmentsValue();
    totalAdjustmentsValue = totalAdjustmentsValue.add(getOrderAdjustmentsValue());
    totalAdjustmentsValue = totalAdjustmentsValue.add(getFulfillmentGroupAdjustmentsValue());

    return totalAdjustmentsValue;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#updatePrices()
   */
  @Override public boolean updatePrices() {
    boolean updated = false;

    for (OrderItem orderItem : orderItems) {
      if (orderItem.updateSaleAndRetailPrices()) {
        updated = true;
      }
    }

    return updated;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#finalizeItemPrices()
   */
  @Override public boolean finalizeItemPrices() {
    boolean updated = false;

    for (OrderItem orderItem : orderItems) {
      orderItem.finalizePrice();
    }

    return updated;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderAttributes()
   */
  @Override public Map<String, OrderAttribute> getOrderAttributes() {
    return orderAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setOrderAttributes(java.util.Map)
   */
  @Override public void setOrderAttributes(Map<String, OrderAttribute> orderAttributes) {
    this.orderAttributes = orderAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#addAddedOfferCode(org.broadleafcommerce.core.offer.domain.OfferCode)
   */
  @Deprecated @Override public void addAddedOfferCode(OfferCode offerCode) {
    addOfferCode(offerCode);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#addOfferCode(org.broadleafcommerce.core.offer.domain.OfferCode)
   */
  @Override public void addOfferCode(OfferCode offerCode) {
    getAddedOfferCodes().add(offerCode);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return currency;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setCurrency(BroadleafCurrency currency) {
    this.currency = currency;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getLocale()
   */
  @Override public Locale getLocale() {
    return locale;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setLocale(org.broadleafcommerce.common.locale.domain.Locale)
   */
  @Override public void setLocale(Locale locale) {
    this.locale = locale;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getItemCount()
   */
  @Override public int getItemCount() {
    int count = 0;

    for (DiscreteOrderItem doi : getDiscreteOrderItems()) {
      count += doi.getQuantity();
    }

    return count;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getHasOrderAdjustments()
   */
  @Override public boolean getHasOrderAdjustments() {
    Money orderAdjustmentsValue = getOrderAdjustmentsValue();

    if (orderAdjustmentsValue != null) {
      return (orderAdjustmentsValue.compareTo(BigDecimal.ZERO) != 0);
    }

    return false;
  }

  /**
   * @see  org.broadleafcommerce.common.admin.domain.AdminMainEntity#getMainEntityName()
   */
  @Override public String getMainEntityName() {
    String customerName = null;
    String orderNumber  = getOrderNumber();

    if (!StringUtils.isEmpty(getCustomer().getFirstName()) && !StringUtils.isEmpty(getCustomer().getLastName())) {
      customerName = getCustomer().getFirstName() + " " + getCustomer().getLastName();
    }

    if (!StringUtils.isEmpty(orderNumber) && !StringUtils.isEmpty(customerName)) {
      return orderNumber + " - " + customerName;
    }

    if (!StringUtils.isEmpty(orderNumber)) {
      return orderNumber;
    }

    if (!StringUtils.isEmpty(customerName)) {
      return customerName;
    }

    return "";
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

    OrderImpl other = (OrderImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (customer == null) {
      if (other.customer != null) {
        return false;
      }
    } else if (!customer.equals(other.customer)) {
      return false;
    }

    Date myDateCreated    = (auditable != null) ? auditable.getDateCreated() : null;
    Date otherDateCreated = (other.auditable != null) ? other.auditable.getDateCreated() : null;

    if (myDateCreated == null) {
      if (otherDateCreated != null) {
        return false;
      }
    } else if (!myDateCreated.equals(otherDateCreated)) {
      return false;
    }

    return true;
  } // end method equals

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = super.hashCode();
    result = (prime * result) + ((customer == null) ? 0 : customer.hashCode());

    Date myDateCreated = (auditable != null) ? auditable.getDateCreated() : null;
    result = (prime * result) + ((myDateCreated == null) ? 0 : myDateCreated.hashCode());

    return result;
  }

  public static class Presentation {
    public static class Tab {
      public static class Name {
        public static final String OrderItems        = "OrderImpl_Order_Items_Tab";
        public static final String FulfillmentGroups = "OrderImpl_Fulfillment_Groups_Tab";
        public static final String Payment           = "OrderImpl_Payment_Tab";
        public static final String Advanced          = "OrderImpl_Advanced_Tab";
      }

      public static class Order {
        public static final int OrderItems        = 2000;
        public static final int FulfillmentGroups = 3000;
        public static final int Payment           = 4000;
        public static final int Advanced          = 5000;
      }
    }

    public static class Group {
      public static class Name {
        public static final String General = "OrderImpl_Order";
      }

      public static class Order {
        public static final int General = 1000;
      }
    }

    public static class FieldOrder {
      public static final int NAME           = 1000;
      public static final int CUSTOMER       = 2000;
      public static final int TOTAL          = 3000;
      public static final int STATUS         = 4000;
      public static final int SUBTOTAL       = 5000;
      public static final int ORDERNUMBER    = 6000;
      public static final int TOTALTAX       = 7000;
      public static final int TOTALFGCHARGES = 8000;
      public static final int SUBMITDATE     = 9000;
      public static final int EMAILADDRESS   = 10000;

      public static final int ADJUSTMENTS = 1000;
      public static final int OFFERCODES  = 2000;
    }
  } // end class Presentation
} // end class OrderImpl
