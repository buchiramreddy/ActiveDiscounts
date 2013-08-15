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

package org.broadleafcommerce.core.order.dao;

import java.util.List;

import org.broadleafcommerce.core.extension.AbstractExtensionHandler;
import org.broadleafcommerce.core.extension.ExtensionResultStatusType;
import org.broadleafcommerce.core.order.domain.Order;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini), bpolster
 * @version  $Revision$, $Date$
 */
public class AbstractOrderDaoExtensionHandler extends AbstractExtensionHandler implements OrderDaoExtensionHandler {
  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderDaoExtensionHandler#attachAdditionalDataToNewCart(org.broadleafcommerce.profile.core.domain.Customer,
   *       org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public ExtensionResultStatusType attachAdditionalDataToNewCart(Customer customer, Order cart) {
    return ExtensionResultStatusType.NOT_HANDLED;
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderDaoExtensionHandler#applyAdditionalOrderLookupFilter(org.broadleafcommerce.profile.core.domain.Customer,
   *       java.lang.String, java.util.List)
   */
  @Override public ExtensionResultStatusType applyAdditionalOrderLookupFilter(Customer customer, String name,
    List<Order> orders) {
    return ExtensionResultStatusType.NOT_HANDLED;
  }

}
