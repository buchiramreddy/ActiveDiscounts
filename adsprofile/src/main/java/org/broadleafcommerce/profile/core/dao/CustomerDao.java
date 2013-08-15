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

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CustomerDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the first customer that matches the passed in email.
   *
   * @param   emailAddress  DOCUMENT ME!
   *
   * @return  the first customer that matches the passed in email.
   */
  Customer readCustomerByEmail(String emailAddress);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer readCustomerById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the first customer that match the passed in username.
   *
   * @param   username  DOCUMENT ME!
   *
   * @return  the first customer that match the passed in username.
   */
  Customer readCustomerByUsername(String username);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns all customers that matches the passed in email.
   *
   * @param   emailAddress  DOCUMENT ME!
   *
   * @return  all customers that matches the passed in email.
   */
  List<Customer> readCustomersByEmail(String emailAddress);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns all customers that match the passed in username.
   *
   * @param   username  DOCUMENT ME!
   *
   * @return  all customers that match the passed in username.
   */
  List<Customer> readCustomersByUsername(String username);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer save(Customer customer);

} // end interface CustomerDao
