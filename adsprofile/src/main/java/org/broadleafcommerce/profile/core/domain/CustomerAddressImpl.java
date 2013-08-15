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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;
import org.broadleafcommerce.common.time.domain.TemporalTimestampListener;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
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
      name = "address.firstName",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = true
        )
    ),
    @AdminPresentationMergeOverride(
      name = "address.lastName",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = true
        )
    ),
    @AdminPresentationMergeOverride(
      name = "address.addressLine1",
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
  name              = "BLC_CUSTOMER_ADDRESS",
  uniqueConstraints = @UniqueConstraint(columnNames = { "CUSTOMER_ID", "ADDRESS_NAME" })
)
public class CustomerAddressImpl implements CustomerAddress {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "CUSTOMER_ADDRESS_ID")
  @GeneratedValue(generator = "CustomerAddressId")
  @GenericGenerator(
    name       = "CustomerAddressId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CustomerAddressImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.profile.core.domain.CustomerAddressImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerAddressImpl_Address_Name",
    order        = 1,
    group        = "CustomerAddressImpl_Identification",
    groupOrder   = 1,
    prominent    = true,
    gridOrder    = 1
  )
  @Column(name = "ADDRESS_NAME")
  protected String addressName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    excluded   = true,
    visibility = VisibilityEnum.HIDDEN_ALL
  )
  @JoinColumn(name = "CUSTOMER_ID")
  @ManyToOne(
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE },
    targetEntity = CustomerImpl.class,
    optional     = false
  )
  protected Customer customer;

  /** DOCUMENT ME! */
  @Index(
    name        = "CUSTOMERADDRESS_ADDRESS_INDEX",
    columnNames = { "ADDRESS_ID" }
  )
  @JoinColumn(name = "ADDRESS_ID")
  @ManyToOne(
    cascade      = CascadeType.ALL,
    targetEntity = AddressImpl.class,
    optional     = false
  )
  protected Address address;

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerAddress#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerAddress#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerAddress#getAddressName()
   */
  @Override public String getAddressName() {
    return addressName;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerAddress#setAddressName(java.lang.String)
   */
  @Override public void setAddressName(String addressName) {
    this.addressName = addressName;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerAddress#getCustomer()
   */
  @Override public Customer getCustomer() {
    return customer;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerAddress#setCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerAddress#getAddress()
   */
  @Override public Address getAddress() {
    return address;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerAddress#setAddress(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return (addressName == null) ? (address.getFirstName() + " - " + address.getAddressLine1()) : addressName;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((address == null) ? 0 : address.hashCode());
    result = (prime * result) + ((addressName == null) ? 0 : addressName.hashCode());
    result = (prime * result) + ((customer == null) ? 0 : customer.hashCode());

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

    CustomerAddressImpl other = (CustomerAddressImpl) obj;

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

    if (addressName == null) {
      if (other.addressName != null) {
        return false;
      }
    } else if (!addressName.equals(other.addressName)) {
      return false;
    }

    if (customer == null) {
      if (other.customer != null) {
        return false;
      }
    } else if (!customer.equals(other.customer)) {
      return false;
    }

    return true;
  } // end method equals


} // end class CustomerAddressImpl
