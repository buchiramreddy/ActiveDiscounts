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

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;


/**
 * A form to model adding a payment to the order.
 *
 * <p>This form is primarily to support the Broadleaf Demo application.</p>
 *
 * <p>Typically, clients will utilize 3rd party payment integrations as the final checkout step. See documentation
 * specific to the integration(s) you are using (e.g. PayPal, Braintree, Cybersource).</p>
 *
 * <p>This form could be used for simple payment methods where only a paymentMethod and amount is required.</p>
 *
 * <p>For example, a custom implementation might have a payment method of "points" or "payAtPickup" which don't require
 * a complex Payment integration.</p>
 *
 * @author   Elbert Bautista (elbertbautista)
 * @author   Brian Polster (bpolster)
 * @version  $Revision$, $Date$
 */
public class BillingInfoForm implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 7408792703984771616L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Address address                = new AddressImpl();
  private String  creditCardCvvCode;
  private String  creditCardExpMonth;
  private String  creditCardExpYear;
  private String  creditCardName;
  private String  creditCardNumber;
  private String  paymentMethod;
  private String  selectedCreditCardType;
  private boolean useShippingAddress;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new BillingInfoForm object.
   */
  public BillingInfoForm() {
    address.setPhonePrimary(new PhoneImpl());
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Address getAddress() {
    return address;
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
  public String getCreditCardName() {
    return creditCardName;
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
  public String getPaymentMethod() {
    return paymentMethod;
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
  public boolean isUseShippingAddress() {
    return useShippingAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  address  DOCUMENT ME!
   */
  public void setAddress(Address address) {
    this.address = address;
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
   * @param  creditCardName  DOCUMENT ME!
   */
  public void setCreditCardName(String creditCardName) {
    this.creditCardName = creditCardName;
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
   * @param  paymentMethod  DOCUMENT ME!
   */
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
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
   * @param  useShippingAddress  DOCUMENT ME!
   */
  public void setUseShippingAddress(boolean useShippingAddress) {
    this.useShippingAddress = useShippingAddress;
  }
} // end class BillingInfoForm
