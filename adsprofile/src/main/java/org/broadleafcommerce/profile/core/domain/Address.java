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

import java.io.Serializable;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface Address extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getAddressLine1();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getAddressLine2();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getAddressLine3();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCompanyName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Country getCountry();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCounty();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getEmailAddress();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  Should use {@link #getPhoneFax()} instead
   * @see         {@link org.broadleafcommerce.profile.core.domain.Phone}
   */
  @Deprecated String getFax();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getFirstName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getLastName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Phone getPhoneFax();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Phone getPhonePrimary();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Phone getPhoneSecondary();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getPostalCode();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  Should use {@link #getPhonePrimary()} instead
   * @see         {@link org.broadleafcommerce.profile.core.domain.Phone}
   */
  @Deprecated String getPrimaryPhone();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  Should use {@link #getPhoneSecondary()} instead
   * @see         {@link org.broadleafcommerce.profile.core.domain.Phone}
   */
  @Deprecated String getSecondaryPhone();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean getStandardized();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  State getState();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getTokenizedAddress();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getVerificationLevel();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getZipFour();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isActive();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isBusiness();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isDefault();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isActive  DOCUMENT ME!
   */
  void setActive(boolean isActive);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  addressLine1  DOCUMENT ME!
   */
  void setAddressLine1(String addressLine1);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  addressLine2  DOCUMENT ME!
   */
  void setAddressLine2(String addressLine2);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  addressLine3  DOCUMENT ME!
   */
  void setAddressLine3(String addressLine3);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isBusiness  DOCUMENT ME!
   */
  void setBusiness(boolean isBusiness);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  city  DOCUMENT ME!
   */
  void setCity(String city);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  companyName  DOCUMENT ME!
   */
  void setCompanyName(String companyName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  country  DOCUMENT ME!
   */
  void setCountry(Country country);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  county  DOCUMENT ME!
   */
  void setCounty(String county);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isDefault  DOCUMENT ME!
   */
  void setDefault(boolean isDefault);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  emailAddress  DOCUMENT ME!
   */
  void setEmailAddress(String emailAddress);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param       fax  DOCUMENT ME!
   *
   * @deprecated  Should use {@link #setPhoneFax(org.broadleafcommerce.profile.core.domain.Phone)} instead
   * @see         {@link org.broadleafcommerce.profile.core.domain.Phone}
   */
  @Deprecated void setFax(String fax);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  firstName  DOCUMENT ME!
   */
  void setFirstName(String firstName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  lastName  DOCUMENT ME!
   */
  void setLastName(String lastName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  phone  DOCUMENT ME!
   */
  void setPhoneFax(Phone phone);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  phonePrimary  DOCUMENT ME!
   */
  void setPhonePrimary(Phone phonePrimary);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  phoneSecondary  DOCUMENT ME!
   */
  void setPhoneSecondary(Phone phoneSecondary);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  postalCode  DOCUMENT ME!
   */
  void setPostalCode(String postalCode);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param       primaryPhone  DOCUMENT ME!
   *
   * @deprecated  Should use {@link #setPhonePrimary(org.broadleafcommerce.profile.core.domain.Phone)} instead
   * @see         {@link org.broadleafcommerce.profile.core.domain.Phone}
   */
  @Deprecated void setPrimaryPhone(String primaryPhone);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param       secondaryPhone  DOCUMENT ME!
   *
   * @deprecated  Should use {@link #setPhoneSecondary(org.broadleafcommerce.profile.core.domain.Phone)} instead
   * @see         {@link org.broadleafcommerce.profile.core.domain.Phone}
   */
  @Deprecated void setSecondaryPhone(String secondaryPhone);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  standardized  DOCUMENT ME!
   */
  void setStandardized(Boolean standardized);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  state  DOCUMENT ME!
   */
  void setState(State state);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  tAddress  DOCUMENT ME!
   */
  void setTokenizedAddress(String tAddress);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  verificationLevel  DOCUMENT ME!
   */
  void setVerificationLevel(String verificationLevel);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  zipFour  DOCUMENT ME!
   */
  void setZipFour(String zipFour);
} // end interface Address
