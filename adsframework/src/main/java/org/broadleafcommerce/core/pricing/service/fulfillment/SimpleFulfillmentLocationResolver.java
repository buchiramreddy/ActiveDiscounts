/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.pricing.service.fulfillment;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;

import org.broadleafcommerce.profile.core.domain.Address;


/**
 * Default implementation of {@link org.broadleafcommerce.core.pricing.service.fulfillment.FulfillmentLocationResolver}
 * that stores a single Address. Useful for businesses that do not have a complicated warehouse solution and fulfill
 * from a single location.
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
public class SimpleFulfillmentLocationResolver implements FulfillmentLocationResolver {
  /** DOCUMENT ME! */
  protected Address address;

  /**
   * @see  org.broadleafcommerce.core.pricing.service.fulfillment.FulfillmentLocationResolver#resolveLocationForFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup)
   */
  @Override public Address resolveLocationForFulfillmentGroup(FulfillmentGroup group) {
    return address;
  }

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

} // end class SimpleFulfillmentLocationResolver
