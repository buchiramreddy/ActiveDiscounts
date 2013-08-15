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

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerPayment;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CustomerPaymentService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPayment create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerPaymentId  DOCUMENT ME!
   */
  void deleteCustomerPaymentById(Long customerPaymentId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   * @param   payment   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer deleteCustomerPaymentFromCustomer(Customer customer, CustomerPayment payment);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPayment findDefaultPaymentForCustomer(Customer customer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerPaymentId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPayment readCustomerPaymentById(Long customerPaymentId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   token  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPayment readCustomerPaymentByToken(String token);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CustomerPayment> readCustomerPaymentsByCustomerId(Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerPayment  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPayment saveCustomerPayment(CustomerPayment customerPayment);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   payment  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPayment setAsDefaultPayment(CustomerPayment payment);

} // end interface CustomerPaymentService
