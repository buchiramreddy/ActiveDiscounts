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

import org.broadleafcommerce.profile.core.dao.CustomerPhoneDao;
import org.broadleafcommerce.profile.core.domain.CustomerPhone;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blCustomerPhoneService")
public class CustomerPhoneServiceImpl implements CustomerPhoneService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerPhoneDao")
  protected CustomerPhoneDao customerPhoneDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPhoneService#create()
   */
  @Override public CustomerPhone create() {
    return customerPhoneDao.create();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPhoneService#deleteCustomerPhoneById(java.lang.Long)
   */
  @Override public void deleteCustomerPhoneById(Long customerPhoneId) {
    customerPhoneDao.deleteCustomerPhoneById(customerPhoneId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPhoneService#findDefaultCustomerPhone(java.lang.Long)
   */
  @Override public CustomerPhone findDefaultCustomerPhone(Long customerId) {
    return customerPhoneDao.findDefaultCustomerPhone(customerId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPhoneService#makeCustomerPhoneDefault(java.lang.Long, java.lang.Long)
   */
  @Override public void makeCustomerPhoneDefault(Long customerPhoneId, Long customerId) {
    customerPhoneDao.makeCustomerPhoneDefault(customerPhoneId, customerId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPhoneService#readActiveCustomerPhonesByCustomerId(java.lang.Long)
   */
  @Override public List<CustomerPhone> readActiveCustomerPhonesByCustomerId(Long customerId) {
    return customerPhoneDao.readActiveCustomerPhonesByCustomerId(customerId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPhoneService#readAllCustomerPhonesByCustomerId(java.lang.Long)
   */
  @Override public List<CustomerPhone> readAllCustomerPhonesByCustomerId(Long customerId) {
    return customerPhoneDao.readAllCustomerPhonesByCustomerId(customerId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPhoneService#readCustomerPhoneById(java.lang.Long)
   */
  @Override public CustomerPhone readCustomerPhoneById(Long customerPhoneId) {
    return customerPhoneDao.readCustomerPhoneById(customerPhoneId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerPhoneService#saveCustomerPhone(org.broadleafcommerce.profile.core.domain.CustomerPhone)
   */
  @Override public CustomerPhone saveCustomerPhone(CustomerPhone customerPhone) {
    List<CustomerPhone> activeCustomerPhones = readActiveCustomerPhonesByCustomerId(customerPhone.getCustomer()
        .getId());

    if ((activeCustomerPhones != null) && activeCustomerPhones.isEmpty()) {
      customerPhone.getPhone().setDefault(true);
    } else {
      // if parameter customerPhone is set as default, unset all other default phones
      if (customerPhone.getPhone().isDefault()) {
        for (CustomerPhone activeCustomerPhone : activeCustomerPhones) {
          if ((activeCustomerPhone.getId() != customerPhone.getId()) && activeCustomerPhone.getPhone().isDefault()) {
            activeCustomerPhone.getPhone().setDefault(false);
            customerPhoneDao.save(activeCustomerPhone);
          }
        }
      }
    }

    return customerPhoneDao.save(customerPhone);
  }

} // end class CustomerPhoneServiceImpl
