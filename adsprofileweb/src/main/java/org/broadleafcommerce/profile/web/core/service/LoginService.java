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

import org.broadleafcommerce.profile.core.domain.Customer;

import org.springframework.security.core.Authentication;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface LoginService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Authentication loginCustomer(Customer customer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   username           DOCUMENT ME!
   * @param   clearTextPassword  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Authentication loginCustomer(String username, String clearTextPassword);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  void logoutCustomer();
} // end interface LoginService
