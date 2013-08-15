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

package org.broadleafcommerce.core.offer.dao;

import java.util.List;

import org.broadleafcommerce.core.offer.domain.CustomerOffer;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CustomerOfferDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerOffer create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerOffer  DOCUMENT ME!
   */
  void delete(CustomerOffer customerOffer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerOfferId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerOffer readCustomerOfferById(Long customerOfferId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CustomerOffer> readCustomerOffersByCustomer(Customer customer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerOffer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerOffer save(CustomerOffer customerOffer);
} // end interface CustomerOfferDao
