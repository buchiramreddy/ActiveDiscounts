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
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "CountryImpl_baseCountry")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_COUNTRY")
public class CountryImpl implements Country {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "ABBREVIATION")
  @Id protected String abbreviation;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CountryImpl_Country",
    order        = 12,
    group        = "CountryImpl_Address",
    prominent    = true
  )
  @Column(
    name     = "NAME",
    nullable = false
  )
  protected String name;

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Country#getAbbreviation()
   */
  @Override public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Country#setAbbreviation(java.lang.String)
   */
  @Override public void setAbbreviation(String Abbreviation) {
    this.abbreviation = Abbreviation;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Country#getName()
   */
  @Override public String getName() {
    return name;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Country#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
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

    CountryImpl other = (CountryImpl) obj;

    if (abbreviation == null) {
      if (other.abbreviation != null) {
        return false;
      }
    } else if (!abbreviation.equals(other.abbreviation)) {
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

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((abbreviation == null) ? 0 : abbreviation.hashCode());
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());

    return result;
  }
} // end class CountryImpl
