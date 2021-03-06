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

package org.broadleafcommerce.profile.web.core.controller.dataprovider;

import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestionImpl;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;
import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;

import org.testng.annotations.DataProvider;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class RegisterCustomerDataProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @DataProvider(name = "setupCustomerControllerData")
  public static Object[][] createCustomer() {
    Customer customer = new CustomerImpl();
    customer.setEmailAddress("testCase@test.com");
    customer.setFirstName("TestFirstName");
    customer.setLastName("TestLastName");
    customer.setUsername("TestCase");

    ChallengeQuestion question = new ChallengeQuestionImpl();
    question.setId(1L);
    customer.setChallengeQuestion(question);
    customer.setChallengeAnswer("Challenge CandidateItemOfferAnswer");

    RegisterCustomerForm registerCustomer = new RegisterCustomerForm();
    registerCustomer.setCustomer(customer);
    registerCustomer.setPassword("TestPassword");
    registerCustomer.setPasswordConfirm("TestPassword");

    return new Object[][] { new Object[] { registerCustomer } };
  }
} // end class RegisterCustomerDataProvider
