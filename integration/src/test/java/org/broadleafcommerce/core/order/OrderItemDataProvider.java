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

package org.broadleafcommerce.core.order;

import java.math.BigDecimal;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.DiscreteOrderItemImpl;
import org.broadleafcommerce.core.order.domain.GiftWrapOrderItemImpl;
import org.broadleafcommerce.core.order.domain.OrderItemImpl;

import org.testng.annotations.DataProvider;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OrderItemDataProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @DataProvider(name = "basicDiscreteOrderItem")
  public static Object[][] provideBasicDiscreteSalesOrderItem() {
    OrderItemImpl soi = new DiscreteOrderItemImpl();
    soi.setRetailPrice(new Money(BigDecimal.valueOf(10.25)));
    soi.setQuantity(3);

    return new Object[][] {
        { soi }
      };
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @DataProvider(name = "basicGiftWrapOrderItem")
  public static Object[][] provideBasicGiftWrapSalesOrderItem() {
    OrderItemImpl soi = new GiftWrapOrderItemImpl();
    soi.setRetailPrice(new Money(BigDecimal.valueOf(1.25)));
    soi.setQuantity(1);

    return new Object[][] {
        { soi }
      };
  }
} // end class OrderItemDataProvider
