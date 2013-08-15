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

package org.broadleafcommerce.core.payment.domain;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationMap;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupImpl;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderImpl;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.CustomerPayment;
import org.broadleafcommerce.profile.core.domain.CustomerPaymentImpl;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;

import org.hibernate.annotations.BatchSize;
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
  friendlyName        = "PaymentInfoImpl_basePaymentInfo"
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
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_PAYMENT")
public class PaymentInfoImpl implements PaymentInfo, CurrencyCodeIdentifiable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "PAYMENT_ID")
  @GeneratedValue(generator = "PaymentInfoId")
  @GenericGenerator(
    name       = "PaymentInfoId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "PaymentInfoImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.payment.domain.PaymentInfoImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Index(
    name        = "ORDERPAYMENT_ORDER_INDEX",
    columnNames = { "ORDER_ID" }
  )
  @JoinColumn(name = "ORDER_ID")
  @ManyToOne(
    targetEntity = OrderImpl.class,
    optional     = false
  )
  protected Order order;

  /** DOCUMENT ME! */
  @Index(
    name        = "ORDERPAYMENT_ADDRESS_INDEX",
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
    name        = "ORDERPAYMENT_PHONE_INDEX",
    columnNames = { "PHONE_ID" }
  )
  @JoinColumn(name = "PHONE_ID")
  @ManyToOne(
    targetEntity = PhoneImpl.class,
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  protected Phone phone;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentInfoImpl_Payment_Amount",
    order        = 2000,
    gridOrder    = 2000,
    prominent    = true,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "AMOUNT",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal amount;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentInfoImpl_Payment_Reference_Number",
    order        = 1000,
    prominent    = true,
    gridOrder    = 1000
  )
  @Column(name = "REFERENCE_NUMBER")
  @Index(
    name        = "ORDERPAYMENT_REFERENCE_INDEX",
    columnNames = { "REFERENCE_NUMBER" }
  )
  protected String referenceNumber;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "PaymentInfoImpl_Payment_Type",
    order                = 3000,
    gridOrder            = 3000,
    prominent            = true,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.payment.service.type.PaymentInfoType"
  )
  @Column(
    name     = "PAYMENT_TYPE",
    nullable = false
  )
  @Index(
    name        = "ORDERPAYMENT_TYPE_INDEX",
    columnNames = { "PAYMENT_TYPE" }
  )
  protected String type;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "PaymentInfoImpl_Amount_Items",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced
  )
  @OneToMany(
    mappedBy     = "paymentInfo",
    targetEntity = AmountItemImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<AmountItem> amountItems = new ArrayList<AmountItem>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentInfoImpl_Payment_IP_Address",
    order        = 4000
  )
  @Column(
    name     = "CUSTOMER_IP_ADDRESS",
    nullable = true
  )
  protected String customerIpAddress;

  /** DOCUMENT ME! */
  @AdminPresentationMap(
    friendlyName            = "PaymentInfoImpl_Additional_Fields",
    forceFreeFormKeys       = true,
    keyPropertyFriendlyName = "PaymentInfoImpl_Additional_Fields_Name"
  )
  @BatchSize(size = 50)
  @CollectionTable(
    name        = "BLC_PAYINFO_ADDITIONAL_FIELDS",
    joinColumns = @JoinColumn(name = "PAYMENT_ID")
  )
  @Column(name = "FIELD_VALUE")
  @ElementCollection
  @MapKeyColumn(name = "FIELD_NAME")
  protected Map<String, String> additionalFields = new HashMap<String, String>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "PaymentInfoImpl_Details",
    tab          = Presentation.Tab.Name.Log,
    tabOrder     = Presentation.Tab.Order.Log
  )
  @OneToMany(
    mappedBy     = "paymentInfo",
    targetEntity = PaymentInfoDetailImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<PaymentInfoDetail> details = new ArrayList<PaymentInfoDetail>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(friendlyName = "PaymentInfoImpl_Payment_Response_Items")
  @OneToMany(
    mappedBy     = "paymentInfo",
    targetEntity = PaymentResponseItemImpl.class
  )
  protected List<PaymentResponseItem> paymentResponseItems = new ArrayList<PaymentResponseItem>();

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true) // don't display the payment token info in the admin by default
  @Index(
    name        = "CUSTOMER_PAYMENT",
    columnNames = { "CUSTOMER_PAYMENT_ID" }
  )
  @JoinColumn(name = "CUSTOMER_PAYMENT_ID")
  @ManyToOne(targetEntity = CustomerPaymentImpl.class)
  protected CustomerPayment customerPayment;

  /** DOCUMENT ME! */
  @Transient protected Map<String, String[]> requestParameterMap = new HashMap<String, String[]>();

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getAmount()
   */
  @Override public Money getAmount() {
    return (amount == null) ? null : BroadleafCurrencyUtils.getMoney(amount, getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setAmount(Money amount) {
    this.amount = Money.toAmount(amount);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getOrder()
   */
  @Override public Order getOrder() {
    return order;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getAddress()
   */
  @Override public Address getAddress() {
    return address;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setAddress(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getPhone()
   */
  @Override public Phone getPhone() {
    return phone;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setPhone(org.broadleafcommerce.profile.core.domain.Phone)
   */
  @Override public void setPhone(Phone phone) {
    this.phone = phone;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getReferenceNumber()
   */
  @Override public String getReferenceNumber() {
    return referenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setReferenceNumber(java.lang.String)
   */
  @Override public void setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getType()
   */
  @Override public PaymentInfoType getType() {
    return PaymentInfoType.getInstance(type);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setType(org.broadleafcommerce.core.payment.service.type.PaymentInfoType)
   */
  @Override public void setType(PaymentInfoType type) {
    this.type = type.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getAmountItems()
   */
  @Override public List<AmountItem> getAmountItems() {
    return amountItems;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setAmountItems(java.util.List)
   */
  @Override public void setAmountItems(List<AmountItem> amountItems) {
    this.amountItems = amountItems;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getCustomerIpAddress()
   */
  @Override public String getCustomerIpAddress() {
    return customerIpAddress;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setCustomerIpAddress(java.lang.String)
   */
  @Override public void setCustomerIpAddress(String customerIpAddress) {
    this.customerIpAddress = customerIpAddress;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getAdditionalFields()
   */
  @Override public Map<String, String> getAdditionalFields() {
    return additionalFields;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setAdditionalFields(java.util.Map)
   */
  @Override public void setAdditionalFields(Map<String, String> additionalFields) {
    this.additionalFields = additionalFields;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getRequestParameterMap()
   */
  @Override public Map<String, String[]> getRequestParameterMap() {
    return requestParameterMap;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setRequestParameterMap(java.util.Map)
   */
  @Override public void setRequestParameterMap(Map<String, String[]> requestParameterMap) {
    this.requestParameterMap = requestParameterMap;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getCustomerPayment()
   */
  @Override public CustomerPayment getCustomerPayment() {
    return customerPayment;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setCustomerPayment(org.broadleafcommerce.profile.core.domain.CustomerPayment)
   */
  @Override public void setCustomerPayment(CustomerPayment customerPayment) {
    this.customerPayment = customerPayment;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getPaymentInfoDetails()
   */
  @Override public List<PaymentInfoDetail> getPaymentInfoDetails() {
    return details;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setPaymentInfoDetails(java.util.List)
   */
  @Override public void setPaymentInfoDetails(List<PaymentInfoDetail> details) {
    this.details = details;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getPaymentCapturedAmount()
   */
  @Override public Money getPaymentCapturedAmount() {
    return getDetailsAmountForType(PaymentInfoDetailType.CAPTURE);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getPaymentCreditedAmount()
   */
  @Override public Money getPaymentCreditedAmount() {
    return getDetailsAmountForType(PaymentInfoDetailType.REFUND);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getReverseAuthAmount()
   */
  @Override public Money getReverseAuthAmount() {
    return getDetailsAmountForType(PaymentInfoDetailType.REVERSE_AUTH);
  }

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getDetailsAmountForType(PaymentInfoDetailType type) {
    Money amount = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getOrder().getCurrency());

    for (PaymentInfoDetail detail : details) {
      if (type.equals(detail.getType())) {
        amount = amount.add(detail.getAmount());
      }
    }

    return amount;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    if (order != null) {
      return order.getCurrency();
    }

    return null;
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
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#getPaymentResponseItems()
   */
  @Override public List<PaymentResponseItem> getPaymentResponseItems() {
    return paymentResponseItems;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#setPaymentResponseItems(java.util.List)
   */
  @Override public void setPaymentResponseItems(List<PaymentResponseItem> paymentResponseItems) {
    this.paymentResponseItems = paymentResponseItems;
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

    PaymentInfoImpl other = (PaymentInfoImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (referenceNumber == null) {
      if (other.referenceNumber != null) {
        return false;
      }
    } else if (!referenceNumber.equals(other.referenceNumber)) {
      return false;
    }

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
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
    result = (prime * result) + ((referenceNumber == null) ? 0 : referenceNumber.hashCode());
    result = (prime * result) + ((type == null) ? 0 : type.hashCode());

    return result;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfo#createEmptyReferenced()
   */
  @Override public Referenced createEmptyReferenced() {
    if (getReferenceNumber() == null) {
      throw new RuntimeException("referenceNumber must be already set");
    }

    EmptyReferenced emptyReferenced = new EmptyReferenced();
    emptyReferenced.setReferenceNumber(getReferenceNumber());

    return emptyReferenced;
  }

  public static class Presentation {
    public static class Tab {
      public static class Name {
        public static final String Address  = "PaymentInfoImpl_Address_Tab";
        public static final String Log      = "PaymentInfoImpl_Log_Tab";
        public static final String Advanced = "PaymentInfoImpl_Advanced_Tab";
      }

      public static class Order {
        public static final int Address  = 2000;
        public static final int Log      = 4000;
        public static final int Advanced = 5000;
      }
    }

    public static class Group {
      public static class Name {
        public static final String Items = "PaymentInfoImpl_Items";
      }

      public static class Order {
        public static final int Items = 1000;
      }
    }

    public static class FieldOrder {
      public static final int REFNUMBER = 3000;
    }
  } // end class Presentation
} // end class PaymentInfoImpl
