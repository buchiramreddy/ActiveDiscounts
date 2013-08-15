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

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.core.order.domain.FulfillmentOption;
import org.broadleafcommerce.core.order.domain.Order;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Phone;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class FulfillmentGroupRequest {
  /** DOCUMENT ME! */
  protected Address address;

  /** DOCUMENT ME! */
  protected Order   order;

  /** DOCUMENT ME! */
  protected Phone   phone;

  /** Both of these fields uses are superceded by the FulfillmentOption paradigm. */
  @Deprecated protected String method;

  /** DOCUMENT ME! */
  @Deprecated protected String service;

  /** DOCUMENT ME! */
  protected FulfillmentOption option;

  /** DOCUMENT ME! */
  protected List<FulfillmentGroupItemRequest> fulfillmentGroupItemRequests =
    new ArrayList<FulfillmentGroupItemRequest>();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Address getAddress() {
    return address;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  address  DOCUMENT ME!
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Order getOrder() {
    return order;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Phone getPhone() {
    return phone;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  phone  DOCUMENT ME!
   */
  public void setPhone(Phone phone) {
    this.phone = phone;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FulfillmentOption getOption() {
    return option;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  option  DOCUMENT ME!
   */
  public void setOption(FulfillmentOption option) {
    this.option = option;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<FulfillmentGroupItemRequest> getFulfillmentGroupItemRequests() {
    return fulfillmentGroupItemRequests;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupItemRequests  DOCUMENT ME!
   */
  public void setFulfillmentGroupItemRequests(List<FulfillmentGroupItemRequest> fulfillmentGroupItemRequests) {
    this.fulfillmentGroupItemRequests = fulfillmentGroupItemRequests;
  }

  /**
   * Deprecated in favor of {@link #getOption()}.
   *
   * @return  deprecated in favor of {@link #getOption()}.
   *
   * @see     {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
   */
  @Deprecated public String getMethod() {
    return method;
  }

  /**
   * Deprecated in favor of {@link #setOption(org.broadleafcommerce.core.order.domain.FulfillmentOption)}.
   *
   * @param  method  DOCUMENT ME!
   *
   * @see    {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
   */
  @Deprecated public void setMethod(String method) {
    this.method = method;
  }

  /**
   * Deprecated in favor of {@link #getOption()}.
   *
   * @return  deprecated in favor of {@link #getOption()}.
   *
   * @see     {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
   */
  @Deprecated public String getService() {
    return service;
  }

  /**
   * Deprecated in favor of {@link #setOption(org.broadleafcommerce.core.order.domain.FulfillmentOption)}.
   *
   * @param  service  DOCUMENT ME!
   *
   * @see    {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
   */
  @Deprecated public void setService(String service) {
    this.service = service;
  }

} // end class FulfillmentGroupRequest
