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

package org.broadleafcommerce.openadmin.server.security.dao;

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;

import org.hibernate.ejb.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Repository("blAdminUserDao")
public class AdminUserDaoImpl implements AdminUserDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.dao.AdminUserDao#deleteAdminUser(org.broadleafcommerce.openadmin.server.security.domain.AdminUser)
   */
  @Override public void deleteAdminUser(AdminUser user) {
    if (!em.contains(user)) {
      user = em.find(entityConfiguration.lookupEntityClass(
            "org.broadleafcommerce.openadmin.server.security.domain.AdminUser", AdminUser.class), user.getId());
    }

    em.remove(user);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.dao.AdminUserDao#readAdminUserByEmail(java.lang.String)
   */
  @Override public List<AdminUser> readAdminUserByEmail(String emailAddress) {
    TypedQuery<AdminUser> query = em.createNamedQuery("BC_READ_ADMIN_USER_BY_EMAIL", AdminUser.class);
    query.setParameter("email", emailAddress);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.dao.AdminUserDao#readAdminUserById(java.lang.Long)
   */
  @Override public AdminUser readAdminUserById(Long id) {
    return em.find(entityConfiguration.lookupEntityClass(
          "org.broadleafcommerce.openadmin.server.security.domain.AdminUser", AdminUser.class), id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.dao.AdminUserDao#readAdminUserByUserName(java.lang.String)
   */
  @Override public AdminUser readAdminUserByUserName(String userName) {
    TypedQuery<AdminUser> query = em.createNamedQuery("BC_READ_ADMIN_USER_BY_USERNAME", AdminUser.class);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setParameter("userName", userName);

    List<AdminUser> users = query.getResultList();

    if ((users != null) && !users.isEmpty()) {
      return users.get(0);
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.dao.AdminUserDao#readAllAdminUsers()
   */
  @Override public List<AdminUser> readAllAdminUsers() {
    TypedQuery<AdminUser> query = em.createNamedQuery("BC_READ_ALL_ADMIN_USERS", AdminUser.class);
    query.setHint(QueryHints.HINT_CACHEABLE, true);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.dao.AdminUserDao#saveAdminUser(org.broadleafcommerce.openadmin.server.security.domain.AdminUser)
   */
  @Override public AdminUser saveAdminUser(AdminUser user) {
    if (em.contains(user) || (user.getId() != null)) {
      return em.merge(user);
    } else {
      em.persist(user);

      return user;
    }
  }
} // end class AdminUserDaoImpl
