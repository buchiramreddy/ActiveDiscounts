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

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.domain.CustomerAddressImpl;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blCustomerAddressDao")
public class CustomerAddressDaoImpl implements CustomerAddressDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerAddressDao#create()
   */
  @Override public CustomerAddress create() {
    return (CustomerAddress) entityConfiguration.createEntityInstance(CustomerAddress.class.getName());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerAddressDao#deleteCustomerAddressById(java.lang.Long)
   */
  @Override public void deleteCustomerAddressById(Long customerAddressId) {
    CustomerAddress customerAddress = readCustomerAddressById(customerAddressId);

    if (customerAddress != null) {
      em.remove(customerAddress);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerAddressDao#findDefaultCustomerAddress(java.lang.Long)
   */
  @Override
  @SuppressWarnings("unchecked")
  public CustomerAddress findDefaultCustomerAddress(Long customerId) {
    Query query = em.createNamedQuery("BC_FIND_DEFAULT_ADDRESS_BY_CUSTOMER_ID");
    query.setParameter("customerId", customerId);

    List<CustomerAddress> customerAddresses = query.getResultList();

    return customerAddresses.isEmpty() ? null : customerAddresses.get(0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerAddressDao#makeCustomerAddressDefault(java.lang.Long, java.lang.Long)
   */
  @Override public void makeCustomerAddressDefault(Long customerAddressId, Long customerId) {
    List<CustomerAddress> customerAddresses = readActiveCustomerAddressesByCustomerId(customerId);

    for (CustomerAddress customerAddress : customerAddresses) {
      customerAddress.getAddress().setDefault(customerAddress.getId().equals(customerAddressId));
      em.merge(customerAddress);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerAddressDao#readActiveCustomerAddressesByCustomerId(java.lang.Long)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<CustomerAddress> readActiveCustomerAddressesByCustomerId(Long customerId) {
    Query query = em.createNamedQuery("BC_READ_ACTIVE_CUSTOMER_ADDRESSES_BY_CUSTOMER_ID");
    query.setParameter("customerId", customerId);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerAddressDao#readCustomerAddressById(java.lang.Long)
   */
  @Override public CustomerAddress readCustomerAddressById(Long customerAddressId) {
    return (CustomerAddress) em.find(CustomerAddressImpl.class, customerAddressId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerAddressDao#save(org.broadleafcommerce.profile.core.domain.CustomerAddress)
   */
  @Override public CustomerAddress save(CustomerAddress customerAddress) {
    return em.merge(customerAddress);
  }
} // end class CustomerAddressDaoImpl
