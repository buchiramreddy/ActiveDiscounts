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

package org.broadleafcommerce.profile.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.dao.CustomerPaymentDao;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerPayment;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blCustomerPaymentService")
public class CustomerPaymentServiceImpl implements CustomerPaymentService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** Services. */
  @Resource(name = "blCustomerPaymentDao")
  protected CustomerPaymentDao customerPaymentDao;

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerService")
  protected CustomerService customerService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPaymentService#create()
   */
  @Override
  @Transactional("blTransactionManager")
  public CustomerPayment create() {
    return customerPaymentDao.create();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPaymentService#deleteCustomerPaymentById(java.lang.Long)
   */
  @Override
  @Transactional("blTransactionManager")
  public void deleteCustomerPaymentById(Long customerPaymentId) {
    customerPaymentDao.deleteCustomerPaymentById(customerPaymentId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPaymentService#deleteCustomerPaymentFromCustomer(org.broadleafcommerce.profile.core.domain.Customer,
   *       org.broadleafcommerce.profile.core.domain.CustomerPayment)
   */
  @Override
  @Transactional("blTransactionManager")
  public Customer deleteCustomerPaymentFromCustomer(Customer customer, CustomerPayment payment) {
    List<CustomerPayment> payments = customer.getCustomerPayments();

    for (CustomerPayment customerPayment : payments) {
      if (customerPayment.getId().equals(payment.getId())) {
        customer.getCustomerPayments().remove(customerPayment);

        break;
      }
    }

    return customerService.saveCustomer(customer);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPaymentService#findDefaultPaymentForCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public CustomerPayment findDefaultPaymentForCustomer(Customer customer) {
    if (customer == null) {
      return null;
    }

    List<CustomerPayment> payments = readCustomerPaymentsByCustomerId(customer.getId());

    for (CustomerPayment payment : payments) {
      if (payment.isDefault()) {
        return payment;
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPaymentService#readCustomerPaymentById(java.lang.Long)
   */
  @Override public CustomerPayment readCustomerPaymentById(Long customerPaymentId) {
    return customerPaymentDao.readCustomerPaymentById(customerPaymentId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPaymentService#readCustomerPaymentByToken(java.lang.String)
   */
  @Override public CustomerPayment readCustomerPaymentByToken(String token) {
    return customerPaymentDao.readCustomerPaymentByToken(token);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPaymentService#readCustomerPaymentsByCustomerId(java.lang.Long)
   */
  @Override public List<CustomerPayment> readCustomerPaymentsByCustomerId(Long customerId) {
    return customerPaymentDao.readCustomerPaymentsByCustomerId(customerId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPaymentService#saveCustomerPayment(org.broadleafcommerce.profile.core.domain.CustomerPayment)
   */
  @Override
  @Transactional("blTransactionManager")
  public CustomerPayment saveCustomerPayment(CustomerPayment customerPayment) {
    return customerPaymentDao.save(customerPayment);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPaymentService#setAsDefaultPayment(org.broadleafcommerce.profile.core.domain.CustomerPayment)
   */
  @Override
  @Transactional("blTransactionManager")
  public CustomerPayment setAsDefaultPayment(CustomerPayment payment) {
    CustomerPayment oldDefault = findDefaultPaymentForCustomer(payment.getCustomer());

    if (oldDefault != null) {
      oldDefault.setDefault(false);
      saveCustomerPayment(oldDefault);
    }

    payment.setDefault(true);

    return saveCustomerPayment(payment);
  }

} // end class CustomerPaymentServiceImpl
