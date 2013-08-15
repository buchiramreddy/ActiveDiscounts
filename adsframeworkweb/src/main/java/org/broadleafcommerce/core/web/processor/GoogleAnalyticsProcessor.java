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

package org.broadleafcommerce.core.web.processor;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;

import org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor;

import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemAttribute;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;

import org.broadleafcommerce.profile.core.domain.Address;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;


/**
 * A Thymeleaf processor that will on order confirmation page, submit order information via javascript to google
 * analytics.
 *
 * <p>Example usage on order confirmation page:</p>
 *
 * <pre>
    {@code
 *      <blc:googleAnalytics th:attr="orderNumber=${order != null ? order.orderNumber : null}" />
        <script th:utext="${analytics}" >
    }
 * </pre>
 * </script>
 * </pre>
 *
 * @author   tleffert
 * @version  $Revision$, $Date$
 */
@Component("blGoogleAnalyticsProcessor")
public class GoogleAnalyticsProcessor extends AbstractModelVariableModifierProcessor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Value("${googleAnalytics.affiliation}")
  protected String affiliation = "";

  /** DOCUMENT ME! */
  @Resource(name = "blOrderService")
  protected OrderService orderService;

  /**
   * This will force the domain to 127.0.0.1 which is useful to determine if the Google Analytics tag is sending a
   * request to Google.
   */
  protected boolean testLocal = false;

  /** DOCUMENT ME! */
  @Value("${googleAnalytics.webPropertyId}")
  protected String webPropertyId;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this processor to be used in Thymeleaf template.
   */
  public GoogleAnalyticsProcessor() {
    super("googleanalytics");
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getAffiliation() {
    return affiliation;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.AbstractProcessor#getPrecedence()
   */
  @Override public int getPrecedence() {
    return 100000;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean getTestLocal() {
    return testLocal;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  affiliation  DOCUMENT ME!
   */
  public void setAffiliation(String affiliation) {
    this.affiliation = affiliation;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Documentation for the recommended asynchronous GA tag is at:
   * http://code.google.com/apis/analytics/docs/tracking/gaTrackingEcommerce.html
   *
   * @param   webPropertyId  - Google Analytics ID
   * @param   order          - optionally track the order submission. This should be included on the page after the
   *                         order has been sucessfully submitted. If null, this will just track the current page
   *
   * @return  the relevant Javascript to render on the page
   */
  protected String analytics(String webPropertyId, Order order) {
    StringBuffer sb = new StringBuffer();

    sb.append("var _gaq = _gaq || [];\n");
    sb.append("_gaq.push(['_setAccount', '" + webPropertyId + "']);");
    sb.append("_gaq.push(['_trackPageview']);");

    if (testLocal) {
      sb.append("_gaq.push(['_setDomainName', '127.0.0.1']);");
    }

    if (order != null) {
      Address paymentAddress = getBillingAddress(order);

      if (paymentAddress != null) {
        sb.append("_gaq.push(['_addTrans','" + order.getOrderNumber() + "'");
        sb.append(",'" + affiliation + "'");
        sb.append(",'" + order.getTotal() + "'");
        sb.append(",'" + order.getTotalTax() + "'");
        sb.append(",'" + order.getTotalShipping() + "'");
        sb.append(",'" + paymentAddress.getCity() + "'");
        sb.append(",'" + paymentAddress.getState().getName() + "'");
        sb.append(",'" + paymentAddress.getCountry().getName() + "'");
        sb.append("]);");
      }

      for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
        for (FulfillmentGroupItem fulfillmentGroupItem : fulfillmentGroup.getFulfillmentGroupItems()) {
          OrderItem orderItem = fulfillmentGroupItem.getOrderItem();

          Sku sku = null;

          if (orderItem instanceof DiscreteOrderItem) {
            sku = ((DiscreteOrderItem) orderItem).getSku();
          } else if (orderItem instanceof BundleOrderItem) {
            sku = ((BundleOrderItem) orderItem).getSku();
          }

          sb.append("_gaq.push(['_addItem','" + order.getOrderNumber() + "'");
          sb.append(",'" + sku.getId() + "'");
          sb.append(",'" + sku.getName() + "'");
          sb.append(",'" + getVariation(orderItem) + "'");
          sb.append(",'" + orderItem.getPrice() + "'");
          sb.append(",'" + orderItem.getQuantity() + "'");
          sb.append("]);");
        }
      }

      sb.append("_gaq.push(['_trackTrans']);");
    } // end if

    sb.append(" (function() {"
      + "var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;"
      + "ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';"
      + "var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);"
      + "})();");

    return sb.toString();
  } // end method analytics

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Address getBillingAddress(Order order) {
    PaymentInfo paymentInfo = null;

    if (order.getPaymentInfos().size() > 0) {
      paymentInfo = order.getPaymentInfos().get(0);
    }

    Address address = null;

    if ((paymentInfo == null) || (paymentInfo.getAddress() == null)) {
      // in this case, no payment info object on the order or no billing
      // information received due to external payment gateway
      address = order.getFulfillmentGroups().get(0).getAddress();
    } else {
      // then the address must exist on the payment info
      address = paymentInfo.getAddress();
    }

    return address;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the product option values separated by a space if they are relevant for the item, or the product category
   * if no options are available.
   *
   * @param   item  DOCUMENT ME!
   *
   * @return  the product option values separated by a space if they are relevant for the item, or the product category
   *          if no options are available.
   */
  protected String getVariation(OrderItem item) {
    if (MapUtils.isEmpty(item.getOrderItemAttributes())) {
      return (item.getCategory() == null) ? "" : item.getCategory().getName();
    }

    // use product options instead
    String result = "";

    for (Map.Entry<String, OrderItemAttribute> entry : item.getOrderItemAttributes().entrySet()) {
      result += entry.getValue().getValue() + " ";
    }

    // the result has a space at the end, ensure that is stripped out
    return result.substring(0, result.length() - 1);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor#modifyModelAttributes(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element)
   */
  @Override protected void modifyModelAttributes(Arguments arguments, Element element) {
    String orderNumber = element.getAttributeValue("orderNumber");
    Order  order       = null;

    if (orderNumber != null) {
      order = orderService.findOrderByOrderNumber(orderNumber);
    }

    addToModel(arguments, "analytics", analytics(webPropertyId, order));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  testLocal  DOCUMENT ME!
   */
  protected void setTestLocal(boolean testLocal) {
    this.testLocal = testLocal;
  }

} // end class GoogleAnalyticsProcessor
