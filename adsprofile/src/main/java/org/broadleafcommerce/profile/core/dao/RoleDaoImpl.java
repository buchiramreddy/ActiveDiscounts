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

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.CustomerRole;
import org.broadleafcommerce.profile.core.domain.Role;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blRoleDao")
public class RoleDaoImpl implements RoleDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.RoleDao#addRoleToCustomer(org.broadleafcommerce.profile.core.domain.CustomerRole)
   */
  @Override public void addRoleToCustomer(CustomerRole customerRole) {
    em.persist(customerRole);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Address readAddressById(Long id) {
    return (Address) em.find(AddressImpl.class, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.RoleDao#readCustomerRolesByCustomerId(java.lang.Long)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<CustomerRole> readCustomerRolesByCustomerId(Long customerId) {
    Query query = em.createNamedQuery("BC_READ_CUSTOMER_ROLES_BY_CUSTOMER_ID");
    query.setParameter("customerId", customerId);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.RoleDao#readRoleByName(java.lang.String)
   */
  @Override public Role readRoleByName(String name) {
    Query query = em.createNamedQuery("BC_READ_ROLE_BY_NAME");
    query.setParameter("name", name);

    return (Role) query.getSingleResult();
  }
} // end class RoleDaoImpl
