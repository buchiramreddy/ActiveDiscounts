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

package org.broadleafcommerce.core.pricing.service.workflow;

import java.math.BigDecimal;

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.pricing.service.ShippingService;
import org.broadleafcommerce.core.workflow.BaseActivity;


/**
 * DOCUMENT ME!
 *
 * @deprecated  Should use the {@link org.broadleafcommerce.core.order.domain.FulfillmentOption} paradigm, implemented
 *              in {@link org.broadleafcommerce.core.pricing.service.FulfillmentPricingService}. This activity was
 *              replaced by {@link org.broadleafcommerce.core.pricing.service.workflow.FulfillmentGroupPricingActivity}.
 * @see         {@link FulfillmentPricingActivity},
 *              {@link org.broadleafcommerce.core.pricing.service.FulfillmentPricingService},
 *              {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
 * @author      $author$
 * @version     $Revision$, $Date$
 */
@Deprecated public class ShippingActivity extends BaseActivity<PricingContext> {
  private ShippingService shippingService;

  /**
   * DOCUMENT ME!
   *
   * @param  shippingService  DOCUMENT ME!
   */
  public void setShippingService(ShippingService shippingService) {
    this.shippingService = shippingService;
  }

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#execute(org.broadleafcommerce.core.pricing.service.workflow.PricingContext)
   */
  @Override public PricingContext execute(PricingContext context) throws Exception {
    Order order = context.getSeedData();

    /*
     * 1. Get FGs from Order
     * 2. take each FG and call shipping module with the shipping svc
     * 3. add FG back to order
     */

    Money totalShipping = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());

    for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
      fulfillmentGroup = shippingService.calculateShippingForFulfillmentGroup(fulfillmentGroup);
      totalShipping    = totalShipping.add(fulfillmentGroup.getShippingPrice());
    }

    order.setTotalShipping(totalShipping);
    context.setSeedData(order);

    return context;
  }

} // end class ShippingActivity
