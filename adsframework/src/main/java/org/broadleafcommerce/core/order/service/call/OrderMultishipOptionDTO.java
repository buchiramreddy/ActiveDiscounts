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

package org.broadleafcommerce.core.order.service.call;

/**
 * This DTO is used to bind multiship options in a way that doesn't require the actual objects to be instantiated -- we
 * handle that at the controller level.
 *
 * @see      OrderMultishipOptionForm
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class OrderMultishipOptionDTO {
  /** DOCUMENT ME! */
  protected Long id;

  /** DOCUMENT ME! */
  protected Long orderItemId;

  /** DOCUMENT ME! */
  protected Long addressId;

  /** DOCUMENT ME! */
  protected Long fulfillmentOptionId;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getId() {
    return id;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getOrderItemId() {
    return orderItemId;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  orderItemId  DOCUMENT ME!
   */
  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getAddressId() {
    return addressId;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  addressId  DOCUMENT ME!
   */
  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getFulfillmentOptionId() {
    return fulfillmentOptionId;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentOptionId  DOCUMENT ME!
   */
  public void setFulfillmentOptionId(Long fulfillmentOptionId) {
    this.fulfillmentOptionId = fulfillmentOptionId;
  }

} // end class OrderMultishipOptionDTO
