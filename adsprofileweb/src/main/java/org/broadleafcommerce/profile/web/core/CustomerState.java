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

package org.broadleafcommerce.profile.web.core;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.web.BroadleafRequestContext;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.security.CustomerStateRequestProcessor;

import org.springframework.stereotype.Component;

import org.springframework.web.context.request.WebRequest;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Component("blCustomerState")
public class CustomerState {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Utilizes the current BroadleafRequestContext to lookup a customer from the request.
   *
   * @return  utilizes the current BroadleafRequestContext to lookup a customer from the request.
   */
  public static Customer getCustomer() {
    WebRequest request = BroadleafRequestContext.getBroadleafRequestContext().getWebRequest();

    return (Customer) request.getAttribute(CustomerStateRequestProcessor.getCustomerRequestAttributeName(),
        WebRequest.SCOPE_REQUEST);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Customer getCustomer(HttpServletRequest request) {
    return (Customer) request.getAttribute(CustomerStateRequestProcessor.getCustomerRequestAttributeName());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Customer getCustomer(WebRequest request) {
    return (Customer) request.getAttribute(CustomerStateRequestProcessor.getCustomerRequestAttributeName(),
        WebRequest.SCOPE_REQUEST);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customer  DOCUMENT ME!
   */
  public static void setCustomer(Customer customer) {
    WebRequest request = BroadleafRequestContext.getBroadleafRequestContext().getWebRequest();
    request.setAttribute(CustomerStateRequestProcessor.getCustomerRequestAttributeName(), customer,
      WebRequest.SCOPE_REQUEST);
  }

} // end class CustomerState
