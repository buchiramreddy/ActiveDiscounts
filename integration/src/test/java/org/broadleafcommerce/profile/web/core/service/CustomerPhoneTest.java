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

package org.broadleafcommerce.profile.web.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerPhone;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;
import org.broadleafcommerce.profile.core.service.CustomerPhoneService;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.dataprovider.CustomerPhoneDataProvider;

import org.broadleafcommerce.test.BaseTest;

import org.springframework.test.annotation.Rollback;

import org.springframework.transaction.annotation.Transactional;

import org.testng.annotations.Test;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CustomerPhoneTest extends BaseTest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  List<Long> customerPhoneIds = new ArrayList<Long>();

  /** DOCUMENT ME! */
  Long       userId;

  /** DOCUMENT ME! */
  String     userName = new String();

  @Resource private CustomerPhoneService customerPhoneService;

  @Resource private CustomerService customerService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerPhone  DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups            = "createCustomerPhone",
    dataProvider      = "setupCustomerPhone",
    dataProviderClass = CustomerPhoneDataProvider.class,
    dependsOnGroups   = "readCustomer"
  )
  @Transactional public void createCustomerPhone(CustomerPhone customerPhone) {
    userName = "customer1";

    Customer customer = customerService.readCustomerByUsername(userName);
    assert customerPhone.getId() == null;
    customerPhone.setCustomer(customer);

    Phone phone = new PhoneImpl();
    phone.setPhoneNumber("214-214-2134");
    customerPhone.setPhone(phone);
    customerPhone = customerPhoneService.saveCustomerPhone(customerPhone);
    assert customer.equals(customerPhone.getCustomer());
    userId = customerPhone.getCustomer().getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = "readCustomerPhone",
    dependsOnGroups = "createCustomerPhone"
  )
  @Transactional public void readCustomerPhoneByUserId() {
    List<CustomerPhone> customerPhoneList = customerPhoneService.readActiveCustomerPhonesByCustomerId(userId);

    for (CustomerPhone customerPhone : customerPhoneList) {
      assert customerPhone != null;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = "readCustomerPhone",
    dependsOnGroups = "createCustomerPhone"
  )
  @Transactional public void readDeafultCustomerPhoneByUserId() {
    CustomerPhone customerPhone = customerPhoneService.findDefaultCustomerPhone(userId);
    assert customerPhone != null;
  }
} // end class CustomerPhoneTest
