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

import org.broadleafcommerce.core.order.domain.PersonalMessage;
import org.broadleafcommerce.core.order.domain.PersonalMessageImpl;


/**
 * This form is used to bind multiship options in a way that doesn't require the actual objects to be instantiated -- we
 * handle that at the controller level.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class MultiShipInstructionForm implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String          deliveryMessage;

  /** DOCUMENT ME! */
  protected Long            fulfillmentGroupId;

  /** DOCUMENT ME! */
  protected PersonalMessage personalMessage = new PersonalMessageImpl();

  //~ Methods ----------------------------------------------------------------------------------------------------------

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
  public Long getFulfillmentGroupId() {
    return fulfillmentGroupId;
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
   * @param  deliveryMessage  DOCUMENT ME!
   */
  public void setDeliveryMessage(String deliveryMessage) {
    this.deliveryMessage = deliveryMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  public void setFulfillmentGroupId(Long id) {
    this.fulfillmentGroupId = id;
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

} // end class MultiShipInstructionForm
