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

package org.broadleafcommerce.core.web.checkout.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.common.payment.CreditCardType;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.CountryImpl;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;
import org.broadleafcommerce.profile.core.domain.StateImpl;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CheckoutForm implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 8866879738364589339L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Address billingAddress     = new AddressImpl();
  private String  creditCardCvvCode;
  private String  creditCardExpMonth;
  private String  creditCardExpYear;
  private String  creditCardNumber;

  private String  emailAddress;
  private boolean isSameAddress;
  private String  selectedCreditCardType;
  private Address shippingAddress    = new AddressImpl();

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new CheckoutForm object.
   */
  public CheckoutForm() {
    shippingAddress = new AddressImpl();
    billingAddress  = new AddressImpl();
    shippingAddress.setPhonePrimary(new PhoneImpl());
    billingAddress.setPhonePrimary(new PhoneImpl());
    shippingAddress.setCountry(new CountryImpl());
    billingAddress.setCountry(new CountryImpl());
    shippingAddress.setState(new StateImpl());
    billingAddress.setState(new StateImpl());
    isSameAddress = true;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<CreditCardType> getApprovedCreditCardTypes() {
    List<CreditCardType> approvedCCTypes = new ArrayList<CreditCardType>();
    approvedCCTypes.add(CreditCardType.VISA);
    approvedCCTypes.add(CreditCardType.MASTERCARD);
    approvedCCTypes.add(CreditCardType.AMEX);

    return approvedCCTypes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Address getBillingAddress() {
    return (billingAddress == null) ? new AddressImpl() : billingAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCreditCardCvvCode() {
    return creditCardCvvCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCreditCardExpMonth() {
    return creditCardExpMonth;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCreditCardExpYear() {
    return creditCardExpYear;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean getIsSameAddress() {
    return isSameAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSelectedCreditCardType() {
    return selectedCreditCardType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Address getShippingAddress() {
    return (shippingAddress == null) ? new AddressImpl() : shippingAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  billingAddress  DOCUMENT ME!
   */
  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  creditCardCvvCode  DOCUMENT ME!
   */
  public void setCreditCardCvvCode(String creditCardCvvCode) {
    this.creditCardCvvCode = creditCardCvvCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  creditCardExpMonth  DOCUMENT ME!
   */
  public void setCreditCardExpMonth(String creditCardExpMonth) {
    this.creditCardExpMonth = creditCardExpMonth;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  creditCardExpYear  DOCUMENT ME!
   */
  public void setCreditCardExpYear(String creditCardExpYear) {
    this.creditCardExpYear = creditCardExpYear;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  creditCardNumber  DOCUMENT ME!
   */
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  emailAddress  DOCUMENT ME!
   */
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isSameAddress  DOCUMENT ME!
   */
  public void setIsSameAddress(boolean isSameAddress) {
    this.isSameAddress = isSameAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  selectedCreditCardType  DOCUMENT ME!
   */
  public void setSelectedCreditCardType(String selectedCreditCardType) {
    this.selectedCreditCardType = selectedCreditCardType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  shippingAddress  DOCUMENT ME!
   */
  public void setShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

} // end class CheckoutForm
