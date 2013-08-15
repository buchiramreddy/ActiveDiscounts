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
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "StateImpl_baseState")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_STATE")
public class StateImpl implements State {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "ABBREVIATION")
  @Id protected String abbreviation;

  /** DOCUMENT ME! */
  @JoinColumn(name = "COUNTRY")
  @ManyToOne(
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE },
    targetEntity = CountryImpl.class,
    optional     = false
  )
  protected Country country;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StateImpl_State",
    order        = 9,
    group        = "StateImpl_Address",
    prominent    = true
  )
  @Column(
    name     = "NAME",
    nullable = false
  )
  @Index(
    name        = "STATE_NAME_INDEX",
    columnNames = { "NAME" }
  )
  protected String name;

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

    StateImpl other = (StateImpl) obj;

    if (abbreviation == null) {
      if (other.abbreviation != null) {
        return false;
      }
    } else if (!abbreviation.equals(other.abbreviation)) {
      return false;
    }

    if (country == null) {
      if (other.country != null) {
        return false;
      }
    } else if (!country.equals(other.country)) {
      return false;
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.State#getAbbreviation()
   */
  @Override public String getAbbreviation() {
    return abbreviation;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.State#getCountry()
   */
  @Override public Country getCountry() {
    return country;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.State#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((abbreviation == null) ? 0 : abbreviation.hashCode());
    result = (prime * result) + ((country == null) ? 0 : country.hashCode());
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.State#setAbbreviation(java.lang.String)
   */
  @Override public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.State#setCountry(org.broadleafcommerce.profile.core.domain.Country)
   */
  @Override public void setCountry(Country country) {
    this.country = country;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.State#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }
} // end class StateImpl
