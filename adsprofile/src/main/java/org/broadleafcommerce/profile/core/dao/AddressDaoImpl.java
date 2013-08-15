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

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blAddressDao")
public class AddressDaoImpl implements AddressDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.AddressDao#create()
   */
  @Override public Address create() {
    return (Address) entityConfiguration.createEntityInstance(Address.class.getName());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.AddressDao#delete(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public void delete(Address address) {
    if (!em.contains(address)) {
      address = readAddressById(address.getId());
    }

    em.remove(address);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.AddressDao#readAddressById(java.lang.Long)
   */
  @Override public Address readAddressById(Long id) {
    return (Address) em.find(AddressImpl.class, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.AddressDao#save(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public Address save(Address address) {
    return em.merge(address);
  }
} // end class AddressDaoImpl
