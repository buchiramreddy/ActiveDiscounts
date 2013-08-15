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

package org.broadleafcommerce.core.store.service;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.core.store.domain.Store;

import org.broadleafcommerce.profile.core.domain.Address;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface StoreService {
  /**
   * DOCUMENT ME!
   *
   * @param   storeCode  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Store readStoreByStoreCode(String storeCode);

  /**
   * DOCUMENT ME!
   *
   * @param   searchAddress  DOCUMENT ME!
   * @param   distance       DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<Store, Double> findStoresByAddress(Address searchAddress, double distance);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Store> readAllStores();

} // end interface StoreService
