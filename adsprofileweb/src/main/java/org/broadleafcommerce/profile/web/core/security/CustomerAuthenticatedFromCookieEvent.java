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

package org.broadleafcommerce.profile.web.core.security;

import org.broadleafcommerce.profile.core.domain.Customer;

import org.springframework.context.ApplicationEvent;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CustomerAuthenticatedFromCookieEvent extends ApplicationEvent {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Customer customer;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new CustomerAuthenticatedFromCookieEvent object.
   *
   * @param  customer  DOCUMENT ME!
   * @param  source    DOCUMENT ME!
   */
  public CustomerAuthenticatedFromCookieEvent(Customer customer, Object source) {
    super(source);
    this.customer = customer;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Customer getCustomer() {
    return customer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customer  DOCUMENT ME!
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

} // end class CustomerAuthenticatedFromCookieEvent
