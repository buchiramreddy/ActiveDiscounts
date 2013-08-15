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

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.service.exception.AddressVerificationException;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface AddressService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Address create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  address  DOCUMENT ME!
   */
  void delete(Address address);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   addressId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Address readAddressById(Long addressId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   address  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Address saveAddress(Address address);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Verifies the address and returns a collection of addresses. If the address was invalid but close to a match, this
   * method should return a list of one or more addresses that may be valid. If the address is valid, implementations
   * should return the valid address in the list. Implementations may set the tokenized address, zip four, and
   * verification level. If the address could not be validated, implementors should throw an <code>
   * AddressValidationException</code>.
   *
   * <p>For example, an address may be close, but missing zip four. This service should return the address in question
   * with zip four populated.</p>
   *
   * @param   address  DOCUMENT ME!
   *
   * @return  verifies the address and returns a collection of addresses.
   *
   * @throws  AddressVerificationException  DOCUMENT ME!
   */
  List<Address> verifyAddress(Address address) throws AddressVerificationException;

} // end interface AddressService
