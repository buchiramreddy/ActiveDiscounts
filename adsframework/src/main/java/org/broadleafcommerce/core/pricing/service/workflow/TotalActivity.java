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
import org.broadleafcommerce.core.order.domain.FulfillmentGroupFee;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.TaxDetail;
import org.broadleafcommerce.core.workflow.BaseActivity;


/**
 * The TotalActivity is responsible for calculating and setting totals for a given order. It must set the sum of the the
 * taxes in the appropriate places as well as fulfillment group subtotals / totals and order subtotals / totals.
 *
 * @author   aazzolini
 * @version  $Revision$, $Date$
 */
public class TotalActivity extends BaseActivity<PricingContext> {
  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#execute(org.broadleafcommerce.core.pricing.service.workflow.PricingContext)
   */
  @Override public PricingContext execute(PricingContext context) throws Exception {
    Order order = context.getSeedData();

    setTaxSums(order);

    Money total = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());
    total = total.add(order.getSubTotal());
    total = total.subtract(order.getOrderAdjustmentsValue());
    total = total.add(order.getTotalShipping());

    // There may not be any taxes on the order
    if (order.getTotalTax() != null) {
      total = total.add(order.getTotalTax());
    }

    Money fees = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());

    for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
      Money fgTotal = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());
      fgTotal = fgTotal.add(fulfillmentGroup.getMerchandiseTotal());
      fgTotal = fgTotal.add(fulfillmentGroup.getShippingPrice());
      fgTotal = fgTotal.add(fulfillmentGroup.getTotalTax());

      for (FulfillmentGroupFee fulfillmentGroupFee : fulfillmentGroup.getFulfillmentGroupFees()) {
        fgTotal = fgTotal.add(fulfillmentGroupFee.getAmount());
        fees    = fees.add(fulfillmentGroupFee.getAmount());
      }

      fulfillmentGroup.setTotal(fgTotal);
    }

    total = total.add(fees);
    order.setTotal(total);

    context.setSeedData(order);

    return context;
  } // end method execute

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  protected void setTaxSums(Order order) {
    Money orderTotalTax = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());

    for (FulfillmentGroup fg : order.getFulfillmentGroups()) {
      Money fgTotalFgTax   = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());
      Money fgTotalItemTax = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());
      Money fgTotalFeeTax  = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());

      // Add in all FG specific taxes (such as shipping tax)
      if (fg.getTaxes() != null) {
        for (TaxDetail tax : fg.getTaxes()) {
          fgTotalFgTax = fgTotalFgTax.add(tax.getAmount());
        }
      }

      for (FulfillmentGroupItem item : fg.getFulfillmentGroupItems()) {
        Money itemTotalTax = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());

        // Add in all taxes for this item
        if (item.getTaxes() != null) {
          for (TaxDetail tax : item.getTaxes()) {
            itemTotalTax = itemTotalTax.add(tax.getAmount());
          }
        }

        item.setTotalTax(itemTotalTax);
        fgTotalItemTax = fgTotalItemTax.add(itemTotalTax);
      }

      for (FulfillmentGroupFee fee : fg.getFulfillmentGroupFees()) {
        Money feeTotalTax = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());

        // Add in all taxes for this fee
        if (fee.getTaxes() != null) {
          for (TaxDetail tax : fee.getTaxes()) {
            feeTotalTax = feeTotalTax.add(tax.getAmount());
          }
        }

        fee.setTotalTax(feeTotalTax);
        fgTotalFeeTax = fgTotalFeeTax.add(feeTotalTax);
      }

      Money fgTotalTax = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency()).add(fgTotalFgTax).add(
          fgTotalItemTax).add(fgTotalFeeTax);

      // Set the fulfillment group tax sums
      fg.setTotalFulfillmentGroupTax(fgTotalFgTax);
      fg.setTotalItemTax(fgTotalItemTax);
      fg.setTotalFeeTax(fgTotalFeeTax);
      fg.setTotalTax(fgTotalTax);

      orderTotalTax = orderTotalTax.add(fgTotalTax);
    } // end for

    order.setTotalTax(orderTotalTax);
  } // end method setTaxSums
} // end class TotalActivity
