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

import org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
@Repository("blCustomerForgotPasswordSecurityTokenDao")
public class CustomerForgotPasswordSecurityTokenDaoImpl implements CustomerForgotPasswordSecurityTokenDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerForgotPasswordSecurityTokenDao#readToken(java.lang.String)
   */
  @Override public CustomerForgotPasswordSecurityToken readToken(String token) {
    return (CustomerForgotPasswordSecurityToken) em.find(entityConfiguration.lookupEntityClass(
          "org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken"), token);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.CustomerForgotPasswordSecurityTokenDao#saveToken(org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken)
   */
  @Override public CustomerForgotPasswordSecurityToken saveToken(CustomerForgotPasswordSecurityToken token) {
    return em.merge(token);
  }
} // end class CustomerForgotPasswordSecurityTokenDaoImpl
