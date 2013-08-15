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

import org.broadleafcommerce.profile.core.domain.CustomerPhone;
import org.broadleafcommerce.profile.core.domain.CustomerPhoneImpl;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blCustomerPhoneDao")
public class CustomerPhoneDaoImpl implements CustomerPhoneDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPhoneDao#create()
   */
  @Override public CustomerPhone create() {
    return (CustomerPhone) entityConfiguration.createEntityInstance(CustomerPhone.class.getName());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPhoneDao#deleteCustomerPhoneById(java.lang.Long)
   */
  @Override public void deleteCustomerPhoneById(Long customerPhoneId) {
    CustomerPhone customerPhone = readCustomerPhoneById(customerPhoneId);

    if (customerPhone != null) {
      em.remove(customerPhone);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPhoneDao#findDefaultCustomerPhone(java.lang.Long)
   */
  @Override
  @SuppressWarnings("unchecked")
  public CustomerPhone findDefaultCustomerPhone(Long customerId) {
    Query query = em.createNamedQuery("BC_FIND_DEFAULT_PHONE_BY_CUSTOMER_ID");
    query.setParameter("customerId", customerId);

    List<CustomerPhone> customerPhones = query.getResultList();

    return customerPhones.isEmpty() ? null : customerPhones.get(0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPhoneDao#makeCustomerPhoneDefault(java.lang.Long, java.lang.Long)
   */
  @Override public void makeCustomerPhoneDefault(Long customerPhoneId, Long customerId) {
    List<CustomerPhone> customerPhones = readActiveCustomerPhonesByCustomerId(customerId);

    for (CustomerPhone customerPhone : customerPhones) {
      customerPhone.getPhone().setDefault(customerPhone.getId().equals(customerPhoneId));
      em.merge(customerPhone);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPhoneDao#readActiveCustomerPhonesByCustomerId(java.lang.Long)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<CustomerPhone> readActiveCustomerPhonesByCustomerId(Long customerId) {
    Query query = em.createNamedQuery("BC_READ_ACTIVE_CUSTOMER_PHONES_BY_CUSTOMER_ID");
    query.setParameter("customerId", customerId);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPhoneDao#readAllCustomerPhonesByCustomerId(java.lang.Long)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<CustomerPhone> readAllCustomerPhonesByCustomerId(Long customerId) {
    Query query = em.createNamedQuery("BC_READ_ALL_CUSTOMER_PHONES_BY_CUSTOMER_ID");
    query.setParameter("customerId", customerId);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPhoneDao#readCustomerPhoneById(java.lang.Long)
   */
  @Override public CustomerPhone readCustomerPhoneById(Long customerPhoneId) {
    return (CustomerPhone) em.find(CustomerPhoneImpl.class, customerPhoneId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPhoneDao#save(org.broadleafcommerce.profile.core.domain.CustomerPhone)
   */
  @Override public CustomerPhone save(CustomerPhone customerPhone) {
    return em.merge(customerPhone);
  }
} // end class CustomerPhoneDaoImpl
