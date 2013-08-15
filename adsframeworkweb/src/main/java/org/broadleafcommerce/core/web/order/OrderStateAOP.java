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

package org.broadleafcommerce.core.web.order;

import org.aspectj.lang.ProceedingJoinPoint;

import org.broadleafcommerce.core.order.domain.Order;

import org.broadleafcommerce.profile.core.domain.Customer;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OrderStateAOP implements ApplicationContextAware {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private ApplicationContext applicationContext;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   call  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  Throwable  DOCUMENT ME!
   */
  public Object processOrderRetrieval(ProceedingJoinPoint call) throws Throwable {
    Object returnValue;

    /*
     * we retrieve the OrderState instance directly from the application
     * context, as this bean has a request scope.
     */
    OrderState orderState = (OrderState) applicationContext.getBean("blOrderState");
    Customer   customer   = (Customer) call.getArgs()[0];
    Order      order      = orderState.getOrder(customer);

    if (order != null) {
      returnValue = order;
    } else {
      returnValue = call.proceed();
      returnValue = orderState.setOrder(customer, (Order) returnValue);
    }

    return returnValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
} // end class OrderStateAOP
