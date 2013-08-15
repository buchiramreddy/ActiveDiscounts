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

package org.broadleafcommerce.core.web.order.security;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.security.MergeCartProcessor;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.MergeCartService;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.MergeCartResponse;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.web.core.security.CustomerStateRequestProcessor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Component;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Component("blMergeCartProcessor")
public class MergeCartProcessorImpl implements MergeCartProcessor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource(name = "blCustomerService")
  private CustomerService customerService;

  private String mergeCartResponseKey = "bl_merge_cart_response";

  @Resource(name = "blMergeCartService")
  private MergeCartService mergeCartService;

  @Resource(name = "blOrderService")
  private OrderService orderService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.security.MergeCartProcessor#execute(org.springframework.web.context.request.WebRequest,
   *       org.springframework.security.core.Authentication)
   */
  @Override public void execute(WebRequest request, Authentication authResult) {
    Customer loggedInCustomer  = customerService.readCustomerByUsername(authResult.getName());
    Customer anonymousCustomer = (Customer) request.getAttribute(
        CustomerStateRequestProcessor.ANONYMOUS_CUSTOMER_SESSION_ATTRIBUTE_NAME, WebRequest.SCOPE_GLOBAL_SESSION);

    Order cart = null;

    if (anonymousCustomer != null) {
      cart = orderService.findCartForCustomer(anonymousCustomer);
    }

    MergeCartResponse mergeCartResponse;

    try {
      mergeCartResponse = mergeCartService.mergeCart(loggedInCustomer, cart);
    } catch (PricingException e) {
      throw new RuntimeException(e);
    } catch (RemoveFromCartException e) {
      throw new RuntimeException(e);
    }

    request.setAttribute(mergeCartResponseKey, mergeCartResponse, WebRequest.SCOPE_GLOBAL_SESSION);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.security.MergeCartProcessor#execute(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,
   *       org.springframework.security.core.Authentication)
   */
  @Override public void execute(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
    execute(new ServletWebRequest(request, response), authResult);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getMergeCartResponseKey() {
    return mergeCartResponseKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mergeCartResponseKey  DOCUMENT ME!
   */
  public void setMergeCartResponseKey(String mergeCartResponseKey) {
    this.mergeCartResponseKey = mergeCartResponseKey;
  }

} // end class MergeCartProcessorImpl
