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

package org.broadleafcommerce.core.order.service;

import org.broadleafcommerce.core.extension.ExtensionManager;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini), bpolster
 * @version  $Revision$, $Date$
 */
@Service("blOrderServiceExtensionManager")
public class OrderServiceExtensionManager extends ExtensionManager<OrderServiceExtensionHandler> {
  /**
   * Creates a new OrderServiceExtensionManager object.
   */
  public OrderServiceExtensionManager() {
    super(OrderServiceExtensionHandler.class);
  }

  /**
   * By default,this extension manager will continue on handled allowing multiple handlers to interact with the order.
   *
   * @return  by default,this extension manager will continue on handled allowing multiple handlers to interact with the
   *          order.
   */
  @Override public boolean continueOnHandled() {
    return true;
  }
}
