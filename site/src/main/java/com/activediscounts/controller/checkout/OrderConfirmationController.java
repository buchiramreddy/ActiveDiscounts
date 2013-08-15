/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.activediscounts.controller.checkout;

import java.util.HashMap;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.email.service.EmailService;
import org.broadleafcommerce.common.email.service.info.EmailInfo;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.web.controller.checkout.BroadleafOrderConfirmationController;

import org.broadleafcommerce.profile.core.dao.CustomerDao;
import org.broadleafcommerce.profile.core.domain.Customer;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Controller public class OrderConfirmationController extends BroadleafOrderConfirmationController {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerDao")
  protected CustomerDao customerDao;

  /** DOCUMENT ME! */
  @Resource(name = "blEmailService")
  protected EmailService emailService;

  /** DOCUMENT ME! */
  @Resource(name = "blOrderConfirmationEmailInfo")
  protected EmailInfo orderConfirmationEmailInfo;

  /** DOCUMENT ME! */
  @Resource(name = "blOrderService")
  protected OrderService orderService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.checkout.BroadleafOrderConfirmationController#displayOrderConfirmationByOrderNumber(java.lang.String,
   *       org.springframework.ui.Model, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  @Override
  @RequestMapping(
    value  = "/confirmation/{orderNumber}",
    method = RequestMethod.GET
  )
  public String displayOrderConfirmationByOrderNumber(@PathVariable("orderNumber") String orderNumber, Model model,
    HttpServletRequest request, HttpServletResponse response) {
    sendConfirmationEmail(orderNumber);

    return super.displayOrderConfirmationByOrderNumber(orderNumber, model, request, response);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public CustomerDao getCustomerDao() {
    return customerDao;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EmailInfo getOrderConfirmationEmailInfo() {
    return orderConfirmationEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OrderService getOrderService() {
    return orderService;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderNumber  DOCUMENT ME!
   */
  public void sendConfirmationEmail(String orderNumber) {
    Order    order    = orderService.findOrderByOrderNumber(orderNumber);
    Customer customer = customerDao.readCustomerByEmail(order.getEmailAddress());

    if (customer != null) {
      HashMap<String, Object> vars = new HashMap<String, Object>();
      vars.put("customer", customer);
      vars.put("orderNumber", orderNumber);
      vars.put("order", order);
      emailService.sendTemplateEmail(customer.getEmailAddress(), getOrderConfirmationEmailInfo(), vars);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerDao  DOCUMENT ME!
   */
  public void setCustomerDao(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderConfirmationEmailInfo  DOCUMENT ME!
   */
  public void setOrderConfirmationEmailInfo(EmailInfo orderConfirmationEmailInfo) {
    this.orderConfirmationEmailInfo = orderConfirmationEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderService  DOCUMENT ME!
   */
  public void setOrderService(OrderService orderService) {
    this.orderService = orderService;
  }
} // end class OrderConfirmationController
