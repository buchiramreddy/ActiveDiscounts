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

import org.broadleafcommerce.core.order.domain.FulfillmentOption;
import org.broadleafcommerce.core.order.domain.PersonalMessage;
import org.broadleafcommerce.core.order.domain.PersonalMessageImpl;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;


/**
 * A form to model adding a shipping address with shipping options.
 *
 * @author   Elbert Bautista (ebautista)
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class ShippingInfoForm implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = -7895489234675056031L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Address           address             = new AddressImpl();

  /** DOCUMENT ME! */
  protected String            addressName;

  /** DOCUMENT ME! */
  protected String            deliveryMessage;

  /** DOCUMENT ME! */
  protected FulfillmentOption fulfillmentOption;

  /** DOCUMENT ME! */
  protected Long              fulfillmentOptionId;

  /** DOCUMENT ME! */
  protected PersonalMessage   personalMessage = new PersonalMessageImpl();

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ShippingInfoForm object.
   */
  public ShippingInfoForm() {
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
  public String getAddressName() {
    return addressName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDeliveryMessage() {
    return deliveryMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FulfillmentOption getFulfillmentOption() {
    return fulfillmentOption;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getFulfillmentOptionId() {
    return fulfillmentOptionId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersonalMessage getPersonalMessage() {
    return personalMessage;
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
   * @param  addressName  DOCUMENT ME!
   */
  public void setAddressName(String addressName) {
    this.addressName = addressName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  deliveryMessage  DOCUMENT ME!
   */
  public void setDeliveryMessage(String deliveryMessage) {
    this.deliveryMessage = deliveryMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentOption  DOCUMENT ME!
   */
  public void setFulfillmentOption(FulfillmentOption fulfillmentOption) {
    this.fulfillmentOption = fulfillmentOption;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentOptionId  DOCUMENT ME!
   */
  public void setFulfillmentOptionId(Long fulfillmentOptionId) {
    this.fulfillmentOptionId = fulfillmentOptionId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  personalMessage  DOCUMENT ME!
   */
  public void setPersonalMessage(PersonalMessage personalMessage) {
    this.personalMessage = personalMessage;
  }

} // end class ShippingInfoForm
