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

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.openadmin.server.security.domain.ForgotPasswordSecurityToken;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
@Repository("blForgotPasswordSecurityTokenDao")
public class ForgotPasswordSecurityTokenDaoImpl implements ForgotPasswordSecurityTokenDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.dao.ForgotPasswordSecurityTokenDao#readToken(java.lang.String)
   */
  @Override public ForgotPasswordSecurityToken readToken(String token) {
    return (ForgotPasswordSecurityToken) em.find(entityConfiguration.lookupEntityClass(
          "org.broadleafcommerce.openadmin.server.security.domain.ForgotPasswordSecurityToken"), token);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.dao.ForgotPasswordSecurityTokenDao#saveToken(org.broadleafcommerce.openadmin.server.security.domain.ForgotPasswordSecurityToken)
   */
  @Override public ForgotPasswordSecurityToken saveToken(ForgotPasswordSecurityToken token) {
    return em.merge(token);
  }
} // end class ForgotPasswordSecurityTokenDaoImpl
