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
import org.broadleafcommerce.profile.core.domain.IdGeneration;
import org.broadleafcommerce.profile.core.domain.IdGenerationImpl;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.dataprovider.CustomerDataProvider;

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
public class CustomerTest extends BaseTest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  List<Long> userIds = new ArrayList<Long>();

  /** DOCUMENT ME! */
  List<String> userNames = new ArrayList<String>();

  @Resource private CustomerService customerService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /*@Test(groups = { "readCustomer1" }, dependsOnGroups = { "createCustomers" })
  public void readCustomersByUsername1() {
      for (String userName : userNames) {
          UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
          assert userDetails != null && userDetails.getPassword().equals(userDetails.getUsername() + "Password");
      }
  }*/

  /**
   * DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups          = { "changeCustomerPassword" },
    dependsOnGroups = { "readCustomer" }
  )
  @Transactional public void changeCustomerPasswords() {
    for (String userName : userNames) {
      Customer customer = customerService.readCustomerByUsername(userName);
      customer.setPassword(customer.getPassword() + "-Changed");
      customerService.saveCustomer(customer);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerInfo  DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups            = "createCustomers",
    dependsOnGroups   = "createCustomerIdGeneration",
    dataProvider      = "setupCustomers",
    dataProviderClass = CustomerDataProvider.class
  )
  public void createCustomer(Customer customerInfo) {
    Customer customer = customerService.createCustomerFromId(null);
    customer.setPassword(customerInfo.getPassword());
    customer.setUsername(customerInfo.getUsername());

    Long customerId = customer.getId();
    assert customerId != null;
    customer = customerService.saveCustomer(customer);
    assert customer.getId() == customerId;
    userIds.add(customer.getId());
    userNames.add(customer.getUsername());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Rollback(false)
  @Test(groups = { "createCustomerIdGeneration" })
  public void createCustomerIdGeneration() {
    IdGeneration idGeneration = new IdGenerationImpl();
    idGeneration.setType("org.broadleafcommerce.profile.core.domain.Customer");
    idGeneration.setBatchStart(1L);
    idGeneration.setBatchSize(10L);
    em.persist(idGeneration);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "readCustomer" },
    dependsOnGroups = { "createCustomers" }
  )
  public void readCustomersById() {
    for (Long userId : userIds) {
      Customer customer = customerService.readCustomerById(userId);
      assert customer.getId() == userId;
    }
  }

  /*@Test(groups = { "readCustomer2" }, dependsOnGroups = { "changeCustomerPassword" })
  public void readCustomersByUsername2() {
      for (String userName : userNames) {
          UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
          assert userDetails != null && userDetails.getPassword().equals(userDetails.getUsername() + "Password-Changed");
      }
  }*/
} // end class CustomerTest
