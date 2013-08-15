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

package org.broadleafcommerce.common.currency.domain;


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
 * Author: jerryocanas Date: 9/6/12
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */

@AdminPresentationClass(friendlyName = "BroadleafCurrencyImpl_baseCurrency")
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blCMSElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CURRENCY")
public class BroadleafCurrencyImpl implements BroadleafCurrency {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "BroadleafCurrencyImpl_Currency_Code",
    order        = 1,
    group        = "BroadleafCurrencyImpl_Details",
    prominent    = true
  )
  @Column(name = "CURRENCY_CODE")
  @Id protected String currencyCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "BroadleafCurrencyImpl_Name",
    order        = 2,
    group        = "BroadleafCurrencyImpl_Details",
    prominent    = true
  )
  @Column(name = "FRIENDLY_NAME")
  protected String friendlyName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "BroadleafCurrencyImpl_Is_Default",
    group        = "BroadleafCurrencyImpl_Details",
    excluded     = true
  )
  @Column(name = "DEFAULT_FLAG")
  protected Boolean defaultFlag = false;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BroadleafCurrency)) {
      return false;
    }

    BroadleafCurrencyImpl currency = (BroadleafCurrencyImpl) o;

    if ((currencyCode != null) ? (!currencyCode.equals(currency.currencyCode)) : (currency.currencyCode != null)) {
      return false;
    }

    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#getCurrencyCode()
   */
  @Override public String getCurrencyCode() {
    return currencyCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#getDefaultFlag()
   */
  @Override public boolean getDefaultFlag() {
    if (defaultFlag == null) {
      return false;
    }

    return defaultFlag.booleanValue();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#getFriendlyName()
   */
  @Override public String getFriendlyName() {
    return friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (currencyCode != null) ? currencyCode.hashCode() : 0;

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#setCurrencyCode(java.lang.String)
   */
  @Override public void setCurrencyCode(String code) {
    this.currencyCode = code;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#setDefaultFlag(boolean)
   */
  @Override public void setDefaultFlag(boolean defaultFlag) {
    this.defaultFlag = new Boolean(defaultFlag);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#setFriendlyName(java.lang.String)
   */
  @Override public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }
} // end class BroadleafCurrencyImpl
