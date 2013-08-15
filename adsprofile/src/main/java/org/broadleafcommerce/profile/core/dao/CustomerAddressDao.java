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

package org.broadleafcommerce.profile.core.dao;

import java.util.List;

import org.broadleafcommerce.profile.core.domain.CustomerAddress;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CustomerAddressDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerAddress create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerAddressId  DOCUMENT ME!
   */
  void deleteCustomerAddressById(Long customerAddressId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerAddress findDefaultCustomerAddress(Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerAddressId  DOCUMENT ME!
   * @param  customerId         DOCUMENT ME!
   */
  void makeCustomerAddressDefault(Long customerAddressId, Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CustomerAddress> readActiveCustomerAddressesByCustomerId(Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerAddressId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerAddress readCustomerAddressById(Long customerAddressId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerAddress  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerAddress save(CustomerAddress customerAddress);

} // end interface CustomerAddressDao
