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

package org.broadleafcommerce.profile.core.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;
import org.broadleafcommerce.common.time.domain.TemporalTimestampListener;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@AdminPresentationMergeOverrides(
  {
    @AdminPresentationMergeOverride(
      name = "billingAddress.addressLine1",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.PROMINENT,
          booleanOverrideValue = true
        )
    )
  }
)
@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
  name              = "BLC_CUSTOMER_PAYMENT",
  uniqueConstraints = @UniqueConstraint(columnNames = { "CUSTOMER_ID", "PAYMENT_TOKEN" })
)
public class CustomerPaymentImpl implements CustomerPayment {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "CUSTOMER_PAYMENT_ID")
  @GeneratedValue(generator = "CustomerPaymentId")
  @GenericGenerator(
    name       = "CustomerPaymentId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CustomerPaymentImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.profile.core.domain.CustomerPaymentImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "CUSTOMER_ID")
  @ManyToOne(
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE },
    targetEntity = CustomerImpl.class,
    optional     = false
  )
  protected Customer customer;

  /** DOCUMENT ME! */
  @JoinColumn(name = "ADDRESS_ID")
  @ManyToOne(
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE },
    targetEntity = AddressImpl.class,
    optional     = false
  )
  protected Address billingAddress;

  /** DOCUMENT ME! */
  @Column(name = "PAYMENT_TOKEN")
  protected String paymentToken;

  /** DOCUMENT ME! */
  @Column(name = "IS_DEFAULT")
  protected boolean isDefault = false;

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  @CollectionTable(
    name        = "BLC_CUSTOMER_PAYMENT_FIELDS",
    joinColumns = @JoinColumn(name = "CUSTOMER_PAYMENT_ID")
  )
  @Column(name = "FIELD_VALUE")
  @ElementCollection
  @MapKeyColumn(
    name     = "FIELD_NAME",
    nullable = false
  )
  protected Map<String, String> additionalFields = new HashMap<String, String>();

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#getCustomer()
   */
  @Override public Customer getCustomer() {
    return customer;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#setCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#getBillingAddress()
   */
  @Override public Address getBillingAddress() {
    return billingAddress;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#setBillingAddress(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#getPaymentToken()
   */
  @Override public String getPaymentToken() {
    return paymentToken;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#setPaymentToken(java.lang.String)
   */
  @Override public void setPaymentToken(String paymentToken) {
    this.paymentToken = paymentToken;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#isDefault()
   */
  @Override public boolean isDefault() {
    return isDefault;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#setDefault(boolean)
   */
  @Override public void setDefault(boolean aDefault) {
    this.isDefault = aDefault;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#getAdditionalFields()
   */
  @Override public Map<String, String> getAdditionalFields() {
    return additionalFields;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPayment#setAdditionalFields(java.util.Map)
   */
  @Override public void setAdditionalFields(Map<String, String> additionalFields) {
    this.additionalFields = additionalFields;
  }
} // end class CustomerPaymentImpl
