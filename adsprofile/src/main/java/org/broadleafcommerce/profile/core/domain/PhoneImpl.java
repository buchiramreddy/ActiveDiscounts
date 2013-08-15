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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;

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
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PHONE")
public class PhoneImpl implements Phone {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "PHONE_ID")
  @GeneratedValue(generator = "PhoneId")
  @GenericGenerator(
    name       = "PhoneId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "PhoneImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.profile.core.domain.PhoneImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PhoneImpl_Active_Phone",
    order        = 3,
    group        = "PhoneImpl_Phone"
  )
  @Column(name = "IS_ACTIVE")
  protected boolean isActive = true;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PhoneImpl_Default_Phone",
    order        = 2,
    group        = "PhoneImpl_Phone"
  )
  @Column(name = "IS_DEFAULT")
  protected boolean isDefault = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PhoneImpl_Phone_Number",
    order        = 1,
    group        = "PhoneImpl_Phone"
  )
  @Column(
    name     = "PHONE_NUMBER",
    nullable = false
  )
  protected String phoneNumber;

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

    PhoneImpl other = (PhoneImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (isActive != other.isActive) {
      return false;
    }

    if (isDefault != other.isDefault) {
      return false;
    }

    if (phoneNumber == null) {
      if (other.phoneNumber != null) {
        return false;
      }
    } else if (!phoneNumber.equals(other.phoneNumber)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Phone#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Phone#getPhoneNumber()
   */
  @Override public String getPhoneNumber() {
    return phoneNumber;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + (isActive ? 1231 : 1237);
    result = (prime * result) + (isDefault ? 1231 : 1237);
    result = (prime * result) + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Phone#isActive()
   */
  @Override public boolean isActive() {
    return isActive;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Phone#isDefault()
   */
  @Override public boolean isDefault() {
    return isDefault;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Phone#setActive(boolean)
   */
  @Override public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Phone#setDefault(boolean)
   */
  @Override public void setDefault(boolean isDefault) {
    this.isDefault = isDefault;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Phone#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Phone#setPhoneNumber(java.lang.String)
   */
  @Override public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
} // end class PhoneImpl
