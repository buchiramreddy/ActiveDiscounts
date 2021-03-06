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

import org.broadleafcommerce.profile.core.domain.CustomerPhone;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CustomerPhoneService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPhone create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerPhoneId  DOCUMENT ME!
   */
  void deleteCustomerPhoneById(Long customerPhoneId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPhone findDefaultCustomerPhone(Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerPhoneId  DOCUMENT ME!
   * @param  customerId       DOCUMENT ME!
   */
  void makeCustomerPhoneDefault(Long customerPhoneId, Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CustomerPhone> readActiveCustomerPhonesByCustomerId(Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CustomerPhone> readAllCustomerPhonesByCustomerId(Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerPhoneId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPhone readCustomerPhoneById(Long customerPhoneId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerPhone  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPhone saveCustomerPhone(CustomerPhone customerPhone);

} // end interface CustomerPhoneService
