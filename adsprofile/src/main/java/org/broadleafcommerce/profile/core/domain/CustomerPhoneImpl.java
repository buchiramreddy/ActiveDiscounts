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
      name = "phone.phoneNumber",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.PROMINENT,
          booleanOverrideValue = true
        )
    )
  }
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
  name              = "BLC_CUSTOMER_PHONE",
  uniqueConstraints = @UniqueConstraint(columnNames = { "CUSTOMER_ID", "PHONE_NAME" })
)
public class CustomerPhoneImpl implements CustomerPhone {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

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
  @Column(name = "CUSTOMER_PHONE_ID")
  @GeneratedValue(generator = "CustomerPhoneId")
  @GenericGenerator(
    name       = "CustomerPhoneId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CustomerPhoneImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.profile.core.domain.CustomerPhoneImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Index(
    name        = "CUSTPHONE_PHONE_INDEX",
    columnNames = { "PHONE_ID" }
  )
  @JoinColumn(name = "PHONE_ID")
  @ManyToOne(
    cascade      = CascadeType.ALL,
    targetEntity = PhoneImpl.class,
    optional     = false
  )
  protected Phone phone;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerPhoneImpl_Phone_Name",
    order        = 1,
    group        = "CustomerPhoneImpl_Identification",
    groupOrder   = 1,
    prominent    = true,
    gridOrder    = 1
  )
  @Column(name = "PHONE_NAME")
  protected String phoneName;

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

    CustomerPhoneImpl other = (CustomerPhoneImpl) obj;

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

    if (phone == null) {
      if (other.phone != null) {
        return false;
      }
    } else if (!phone.equals(other.phone)) {
      return false;
    }

    if (phoneName == null) {
      if (other.phoneName != null) {
        return false;
      }
    } else if (!phoneName.equals(other.phoneName)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPhone#getCustomer()
   */
  @Override public Customer getCustomer() {
    return customer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPhone#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPhone#getPhone()
   */
  @Override public Phone getPhone() {
    return phone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPhone#getPhoneName()
   */
  @Override public String getPhoneName() {
    return phoneName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((customer == null) ? 0 : customer.hashCode());
    result = (prime * result) + ((phone == null) ? 0 : phone.hashCode());
    result = (prime * result) + ((phoneName == null) ? 0 : phoneName.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPhone#setCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPhone#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPhone#setPhone(org.broadleafcommerce.profile.core.domain.Phone)
   */
  @Override public void setPhone(Phone phone) {
    this.phone = phone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerPhone#setPhoneName(java.lang.String)
   */
  @Override public void setPhoneName(String phoneName) {
    this.phoneName = phoneName;
  }
} // end class CustomerPhoneImpl
