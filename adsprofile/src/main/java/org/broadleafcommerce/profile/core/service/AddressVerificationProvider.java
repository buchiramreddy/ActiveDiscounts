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

import org.broadleafcommerce.common.config.domain.ModuleConfiguration;
import org.broadleafcommerce.common.config.service.ModuleProvider;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.service.exception.AddressVerificationException;


/**
 * Allows for pluggable address validators.
 *
 * @author   Kelly Tisdell
 * @version  $Revision$, $Date$
 */
public interface AddressVerificationProvider extends ModuleProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   address  DOCUMENT ME!
   * @param   config   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  AddressVerificationException  DOCUMENT ME!
   */
  List<Address> validateAddress(Address address, ModuleConfiguration config) throws AddressVerificationException;

}
