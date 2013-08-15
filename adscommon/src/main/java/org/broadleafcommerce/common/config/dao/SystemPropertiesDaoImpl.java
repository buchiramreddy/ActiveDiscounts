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

package org.broadleafcommerce.common.config.dao;

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.config.domain.SystemProperty;
import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.hibernate.ejb.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * This DAO enables access to manage system properties that can be stored in the database.
 *
 * <p>User: Kelly Tisdell Date: 6/20/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blSystemPropertiesDao")
public class SystemPropertiesDaoImpl implements SystemPropertiesDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.SystemPropertiesDao#createNewSystemProperty()
   */
  @Override public SystemProperty createNewSystemProperty() {
    return (SystemProperty) entityConfiguration.createEntityInstance(SystemProperty.class.getName());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.SystemPropertiesDao#deleteSystemProperty(org.broadleafcommerce.common.config.domain.SystemProperty)
   */
  @Override public void deleteSystemProperty(SystemProperty systemProperty) {
    em.remove(systemProperty);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.SystemPropertiesDao#readAllSystemProperties()
   */
  @Override public List<SystemProperty> readAllSystemProperties() {
    Query query = em.createNamedQuery("BC_READ_ALL_SYSTEM_PROPERTIES");
    query.setHint(QueryHints.HINT_CACHEABLE, true);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.SystemPropertiesDao#readSystemPropertyByName(java.lang.String)
   */
  @Override public SystemProperty readSystemPropertyByName(String name) {
    Query query = em.createNamedQuery("BC_READ_SYSTEM_PROPERTIES_BY_NAME");
    query.setParameter("propertyName", name);
    query.setHint(QueryHints.HINT_CACHEABLE, true);

    List<SystemProperty> props = query.getResultList();

    if ((props != null) && !props.isEmpty()) {
      return props.get(0);
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.SystemPropertiesDao#saveSystemProperty(org.broadleafcommerce.common.config.domain.SystemProperty)
   */
  @Override public SystemProperty saveSystemProperty(SystemProperty systemProperty) {
    return em.merge(systemProperty);
  }
} // end class SystemPropertiesDaoImpl
