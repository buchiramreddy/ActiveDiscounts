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
 * This can be used by various third-party fulfillment pricing services in order to resolve a location that items will
 * be shipped from in order to properly calculate the cost of fulfilling that particular fulfillment group.
 *
 * <p>Note: the bean name in XML should be blFulfillmentLocationResolver</p>
 *
 * @author   Phillip Verheyden
 * @see      {@link org.broadleafcommerce.core.pricing.service.fulfillment.SimpleFulfillmentLocationResolver}
 * @version  $Revision$, $Date$
 */
public interface FulfillmentLocationResolver {
  /**
   * This method should give an {@link org.broadleafcommerce.profile.core.domain.Address} that a particular
   * {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup} will be fulfilled from. Implementations could
   * store this information in the database or integrate with an existing warehouse solution.
   *
   * @param   group  DOCUMENT ME!
   *
   * @return  the {@link org.broadleafcommerce.profile.core.domain.Address} that <b>group</b> should be fulfilled from
   */
  Address resolveLocationForFulfillmentGroup(FulfillmentGroup group);

}
