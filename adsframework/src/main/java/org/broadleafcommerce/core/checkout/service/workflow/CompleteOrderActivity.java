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

package org.broadleafcommerce.core.checkout.service.workflow;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import org.broadleafcommerce.common.time.SystemTime;

import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.workflow.BaseActivity;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CompleteOrderActivity extends BaseActivity<CheckoutContext> {
  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#execute(org.broadleafcommerce.core.checkout.service.workflow.CheckoutContext)
   */
  @Override public CheckoutContext execute(CheckoutContext context) throws Exception {
    CheckoutSeed seed = context.getSeedData();

    seed.getOrder().setStatus(OrderStatus.SUBMITTED);
    seed.getOrder().setOrderNumber(new SimpleDateFormat("yyyyMMddHHmmssS").format(SystemTime.asDate())
      + seed.getOrder().getId());
    seed.getOrder().setSubmitDate(Calendar.getInstance().getTime());

    return context;
  }

}
