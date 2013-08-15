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

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blCustomerDao")
public class CustomerDaoImpl implements CustomerDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerDao#create()
   */
  @Override public Customer create() {
    Customer customer = (Customer) entityConfiguration.createEntityInstance(Customer.class.getName());

    return customer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerDao#readCustomerByEmail(java.lang.String)
   */
  @Override public Customer readCustomerByEmail(String emailAddress) {
    List<Customer> customers = readCustomersByEmail(emailAddress);

    return ((customers == null) || customers.isEmpty()) ? null : customers.get(0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerDao#readCustomerById(java.lang.Long)
   */
  @Override public Customer readCustomerById(Long id) {
    return em.find(CustomerImpl.class, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerDao#readCustomerByUsername(java.lang.String)
   */
  @Override public Customer readCustomerByUsername(String username) {
    List<Customer> customers = readCustomersByUsername(username);

    return ((customers == null) || customers.isEmpty()) ? null : customers.get(0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerDao#readCustomersByEmail(java.lang.String)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<Customer> readCustomersByEmail(String emailAddress) {
    Query query = em.createNamedQuery("BC_READ_CUSTOMER_BY_EMAIL");
    query.setParameter("email", emailAddress);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerDao#readCustomersByUsername(java.lang.String)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<Customer> readCustomersByUsername(String username) {
    Query query = em.createNamedQuery("BC_READ_CUSTOMER_BY_USER_NAME");
    query.setParameter("username", username);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerDao#save(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public Customer save(Customer customer) {
    return em.merge(customer);
  }

} // end class CustomerDaoImpl
