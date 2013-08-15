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

import javax.annotation.Resource;

import org.broadleafcommerce.core.offer.service.ShippingOfferService;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.workflow.BaseActivity;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ShippingOfferActivity extends BaseActivity<PricingContext> {
  @Resource(name = "blShippingOfferService")
  private ShippingOfferService shippingOfferService;

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#execute(org.broadleafcommerce.core.pricing.service.workflow.PricingContext)
   */
  @Override public PricingContext execute(PricingContext context) throws Exception {
    Order order = context.getSeedData();
    shippingOfferService.reviewOffers(order);
    context.setSeedData(order);

    return context;
  }

}
