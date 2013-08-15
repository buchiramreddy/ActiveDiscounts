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

import org.broadleafcommerce.profile.core.dao.CustomerAddressDao;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blCustomerAddressService")
public class CustomerAddressServiceImpl implements CustomerAddressService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerAddressDao")
  protected CustomerAddressDao customerAddressDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerAddressService#create()
   */
  @Override public CustomerAddress create() {
    return customerAddressDao.create();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerAddressService#deleteCustomerAddressById(java.lang.Long)
   */
  @Override public void deleteCustomerAddressById(Long customerAddressId) {
    customerAddressDao.deleteCustomerAddressById(customerAddressId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerAddressService#findDefaultCustomerAddress(java.lang.Long)
   */
  @Override public CustomerAddress findDefaultCustomerAddress(Long customerId) {
    return customerAddressDao.findDefaultCustomerAddress(customerId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerAddressService#makeCustomerAddressDefault(java.lang.Long, java.lang.Long)
   */
  @Override public void makeCustomerAddressDefault(Long customerAddressId, Long customerId) {
    customerAddressDao.makeCustomerAddressDefault(customerAddressId, customerId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerAddressService#readActiveCustomerAddressesByCustomerId(java.lang.Long)
   */
  @Override public List<CustomerAddress> readActiveCustomerAddressesByCustomerId(Long customerId) {
    return customerAddressDao.readActiveCustomerAddressesByCustomerId(customerId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerAddressService#readCustomerAddressById(java.lang.Long)
   */
  @Override public CustomerAddress readCustomerAddressById(Long customerAddressId) {
    return customerAddressDao.readCustomerAddressById(customerAddressId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerAddressService#saveCustomerAddress(org.broadleafcommerce.profile.core.domain.CustomerAddress)
   */
  @Override public CustomerAddress saveCustomerAddress(CustomerAddress customerAddress) {
    // if parameter address is set as default, unset all other default addresses
    List<CustomerAddress> activeCustomerAddresses = readActiveCustomerAddressesByCustomerId(
        customerAddress.getCustomer().getId());

    if ((activeCustomerAddresses != null) && activeCustomerAddresses.isEmpty()) {
      customerAddress.getAddress().setDefault(true);
    } else {
      if (customerAddress.getAddress().isDefault()) {
        for (CustomerAddress activeCustomerAddress : activeCustomerAddresses) {
          if ((activeCustomerAddress.getId() != customerAddress.getId())
                && activeCustomerAddress.getAddress().isDefault()) {
            activeCustomerAddress.getAddress().setDefault(false);
            customerAddressDao.save(activeCustomerAddress);
          }
        }
      }
    }

    return customerAddressDao.save(customerAddress);
  }
} // end class CustomerAddressServiceImpl
