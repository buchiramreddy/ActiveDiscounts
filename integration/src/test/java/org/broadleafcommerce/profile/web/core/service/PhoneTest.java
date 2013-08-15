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

import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.service.PhoneService;
import org.broadleafcommerce.profile.dataprovider.PhoneDataProvider;

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
public class PhoneTest extends BaseTest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  List<Long> phoneIds = new ArrayList<Long>();

  /** DOCUMENT ME! */
  String     userName = new String();

  private Long phoneId;

  @Resource private PhoneService phoneService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  phone  DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups            = { "createPhone" },
    dataProvider      = "setupPhone",
    dataProviderClass = PhoneDataProvider.class,
    dependsOnGroups   = { "readCustomer" }
  )
  @Transactional public void createPhone(Phone phone) {
    userName = "customer1";
    assert phone.getId() == null;
    phone = phoneService.savePhone(phone);
    assert phone.getId() != null;
    phoneId = phone.getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "readPhoneById" },
    dependsOnGroups = { "createPhone" }
  )
  public void readPhoneById() {
    Phone phone = phoneService.readPhoneById(phoneId);
    assert phone != null;
    assert phone.getId() == phoneId;
  }
} // end class PhoneTest
