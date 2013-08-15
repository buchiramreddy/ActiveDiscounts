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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.profile.core.domain.CustomerPayment;
import org.broadleafcommerce.profile.core.domain.CustomerPaymentImpl;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blCustomerPaymentDao")
public class CustomerPaymentDaoImpl implements CustomerPaymentDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPaymentDao#create()
   */
  @Override public CustomerPayment create() {
    return (CustomerPayment) entityConfiguration.createEntityInstance(CustomerPayment.class.getName());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPaymentDao#deleteCustomerPaymentById(java.lang.Long)
   */
  @Override public void deleteCustomerPaymentById(Long customerPaymentId) {
    CustomerPayment customerPayment = readCustomerPaymentById(customerPaymentId);

    if (customerPayment != null) {
      em.remove(customerPayment);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPaymentDao#readCustomerPaymentById(java.lang.Long)
   */
  @Override public CustomerPayment readCustomerPaymentById(Long customerPaymentId) {
    return (CustomerPayment) em.find(CustomerPaymentImpl.class, customerPaymentId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPaymentDao#readCustomerPaymentByToken(java.lang.String)
   */
  @Override public CustomerPayment readCustomerPaymentByToken(String token) {
    Query query = em.createNamedQuery("BC_READ_CUSTOMER_PAYMENT_BY_TOKEN");
    query.setParameter("token", token);

    CustomerPayment payment = null;

    try {
      payment = (CustomerPayment) query.getSingleResult();
    } catch (NoResultException e) {
      // do nothing
    }

    return payment;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPaymentDao#readCustomerPaymentsByCustomerId(java.lang.Long)
   */
  @Override public List<CustomerPayment> readCustomerPaymentsByCustomerId(Long customerId) {
    Query query = em.createNamedQuery("BC_READ_CUSTOMER_PAYMENTS_BY_CUSTOMER_ID");
    query.setParameter("customerId", customerId);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerPaymentDao#save(org.broadleafcommerce.profile.core.domain.CustomerPayment)
   */
  @Override public CustomerPayment save(CustomerPayment customerPayment) {
    return em.merge(customerPayment);
  }

} // end class CustomerPaymentDaoImpl
