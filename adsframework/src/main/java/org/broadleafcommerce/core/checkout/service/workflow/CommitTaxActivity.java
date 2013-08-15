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

package org.broadleafcommerce.core.checkout.service.workflow;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.pricing.service.TaxService;
import org.broadleafcommerce.core.workflow.BaseActivity;


/**
 * This is an optional activity to allow a committal of taxes to a tax sub system. Many tax providers store tax details
 * for reference, debugging, reporting, and reconciliation.
 *
 * @author   Kelly Tisdell
 * @version  $Revision$, $Date$
 */
public class CommitTaxActivity extends BaseActivity<CheckoutContext> {
  /** DOCUMENT ME! */
  @Resource(name = "blTaxService")
  protected TaxService taxService;

  /**
   * Creates a new CommitTaxActivity object.
   */
  public CommitTaxActivity() {
    super();

    // We can automatically register a rollback handler because the state will be in the process context.
    super.setAutomaticallyRegisterRollbackHandler(true);
  }

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#execute(org.broadleafcommerce.core.checkout.service.workflow.CheckoutContext)
   */
  @Override public CheckoutContext execute(CheckoutContext context) throws Exception {
    Order order = context.getSeedData().getOrder();
    order = taxService.commitTaxForOrder(order);
    context.getSeedData().setOrder(order);

    return context;
  }

} // end class CommitTaxActivity
