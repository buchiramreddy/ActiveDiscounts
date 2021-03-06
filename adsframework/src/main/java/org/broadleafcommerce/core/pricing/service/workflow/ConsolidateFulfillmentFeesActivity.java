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

package org.broadleafcommerce.core.pricing.service.workflow;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.common.rule.MvelHelper;

import org.broadleafcommerce.core.catalog.domain.SkuFee;
import org.broadleafcommerce.core.catalog.service.type.SkuFeeType;
import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupFee;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.core.workflow.BaseActivity;

import org.mvel2.MVEL;
import org.mvel2.ParserContext;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
public class ConsolidateFulfillmentFeesActivity extends BaseActivity<PricingContext> {
  /** DOCUMENT ME! */
  @SuppressWarnings("unchecked")
  protected static final Map EXPRESSION_CACHE = Collections.synchronizedMap(new LRUMap(1000));

  /** DOCUMENT ME! */
  @Resource(name = "blFulfillmentGroupService")
  protected FulfillmentGroupService fulfillmentGroupService;

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#execute(org.broadleafcommerce.core.pricing.service.workflow.PricingContext)
   */
  @Override public PricingContext execute(PricingContext context) throws Exception {
    Order order = context.getSeedData();

    for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
      // create and associate all the Fulfillment Fees
      for (FulfillmentGroupItem item : fulfillmentGroup.getFulfillmentGroupItems()) {
        List<SkuFee> fees = null;

        if (item.getOrderItem() instanceof BundleOrderItem) {
          fees = ((BundleOrderItem) item.getOrderItem()).getSku().getFees();
        } else if (item.getOrderItem() instanceof DiscreteOrderItem) {
          fees = ((DiscreteOrderItem) item.getOrderItem()).getSku().getFees();
        }

        if (fees != null) {
          for (SkuFee fee : fees) {
            if (SkuFeeType.FULFILLMENT.equals(fee.getFeeType())) {
              if (shouldApplyFeeToFulfillmentGroup(fee, fulfillmentGroup)) {
                FulfillmentGroupFee fulfillmentFee = fulfillmentGroupService.createFulfillmentGroupFee();
                fulfillmentFee.setName(fee.getName());
                fulfillmentFee.setTaxable(fee.getTaxable());
                fulfillmentFee.setAmount(fee.getAmount());
                fulfillmentFee.setFulfillmentGroup(fulfillmentGroup);

                fulfillmentGroup.addFulfillmentGroupFee(fulfillmentFee);
              }
            }
          }
        }
      } // end for

      if (fulfillmentGroup.getFulfillmentGroupFees().size() > 0) {
        fulfillmentGroup = fulfillmentGroupService.save(fulfillmentGroup);
      }
    } // end for

    context.setSeedData(order);

    return context;
  } // end method execute

  /**
   * If the SkuFee expression is null or empty, this method will always return true.
   *
   * @param   fee               DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   *
   * @return  if the SkuFee expression is null or empty, this method will always return true.
   */
  protected boolean shouldApplyFeeToFulfillmentGroup(SkuFee fee, FulfillmentGroup fulfillmentGroup) {
    boolean appliesToFulfillmentGroup = true;
    String  feeExpression             = fee.getExpression();

    if (!StringUtils.isEmpty(feeExpression)) {
      Serializable exp = (Serializable) EXPRESSION_CACHE.get(feeExpression);

      if (exp == null) {
        ParserContext mvelContext = new ParserContext();
        mvelContext.addImport("MVEL", MVEL.class);
        mvelContext.addImport("MvelHelper", MvelHelper.class);
        exp = MVEL.compileExpression(feeExpression, mvelContext);

        EXPRESSION_CACHE.put(feeExpression, exp);
      }

      HashMap<String, Object> vars = new HashMap<String, Object>();
      vars.put("fulfillmentGroup", fulfillmentGroup);

      return (Boolean) MVEL.executeExpression(feeExpression, vars);
    }

    return appliesToFulfillmentGroup;
  }

} // end class ConsolidateFulfillmentFeesActivity
