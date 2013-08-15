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

package org.broadleafcommerce.common.locale.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Created by jfischer.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "LocaleImpl_baseLocale")
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blCMSElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_LOCALE")
public class LocaleImpl implements Locale {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "LocaleImpl_Currency",
    order        = 4,
    group        = "LocaleImpl_Details",
    prominent    = true
  )
  @JoinColumn(name = "CURRENCY_CODE")
  @ManyToOne(targetEntity = BroadleafCurrencyImpl.class)
  protected BroadleafCurrency defaultCurrency;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "LocaleImpl_Name",
    order        = 2,
    group        = "LocaleImpl_Details",
    prominent    = true,
    gridOrder    = 1
  )
  @Column(name = "FRIENDLY_NAME")
  protected String friendlyName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "LocaleImpl_Is_Default",
    order        = 3,
    group        = "LocaleImpl_Details",
    prominent    = true,
    gridOrder    = 3
  )
  @Column(name = "DEFAULT_FLAG")
  protected Boolean defaultFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "LocaleImpl_Locale_Code",
    order        = 1,
    group        = "LocaleImpl_Details",
    prominent    = true,
    gridOrder    = 2
  )
  @Column(name = "LOCALE_CODE")
  @Id protected String localeCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "LocaleImpl_Use_In_Search_Index",
    order        = 5,
    group        = "LocaleImpl_Details",
    prominent    = true,
    gridOrder    = 3
  )
  @Column(name = "USE_IN_SEARCH_INDEX")
  protected Boolean useInSearchIndex = false;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Locale)) {
      return false;
    }

    LocaleImpl locale = (LocaleImpl) o;

    if ((localeCode != null) ? (!localeCode.equals(locale.localeCode)) : (locale.localeCode != null)) {
      return false;
    }

    if ((friendlyName != null) ? (!friendlyName.equals(locale.friendlyName)) : (locale.friendlyName != null)) {
      return false;
    }

    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#getDefaultCurrency()
   */
  @Override public BroadleafCurrency getDefaultCurrency() {
    return defaultCurrency;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#getDefaultFlag()
   */
  @Override public Boolean getDefaultFlag() {
    if (defaultFlag == null) {
      return Boolean.FALSE;
    } else {
      return defaultFlag;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#getFriendlyName()
   */
  @Override public String getFriendlyName() {
    return friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#getLocaleCode()
   */
  @Override public String getLocaleCode() {
    return localeCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#getUseInSearchIndex()
   */
  @Override public Boolean getUseInSearchIndex() {
    return (useInSearchIndex == null) ? false : useInSearchIndex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (localeCode != null) ? localeCode.hashCode() : 0;
    result = (31 * result) + ((friendlyName != null) ? friendlyName.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#setDefaultCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setDefaultCurrency(BroadleafCurrency defaultCurrency) {
    this.defaultCurrency = defaultCurrency;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#setDefaultFlag(java.lang.Boolean)
   */
  @Override public void setDefaultFlag(Boolean defaultFlag) {
    this.defaultFlag = defaultFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#setFriendlyName(java.lang.String)
   */
  @Override public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#setLocaleCode(java.lang.String)
   */
  @Override public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.domain.Locale#setUseInSearchIndex(java.lang.Boolean)
   */
  @Override public void setUseInSearchIndex(Boolean useInSearchIndex) {
    this.useInSearchIndex = useInSearchIndex;
  }
} // end class LocaleImpl
