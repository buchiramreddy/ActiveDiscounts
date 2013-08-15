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

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.config.domain.AbstractModuleConfiguration;
import org.broadleafcommerce.common.config.domain.ModuleConfiguration;
import org.broadleafcommerce.common.config.service.type.ModuleConfigurationType;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.common.persistence.Status;
import org.broadleafcommerce.common.time.SystemTime;

import org.hibernate.annotations.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blModuleConfigurationDao")
public class ModuleConfigurationDaoImpl implements ModuleConfigurationDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Date currentDate = SystemTime.asDate();

  /** DOCUMENT ME! */
  protected Long currentDateResolution = 10000L;

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  private final String DATE_LOCK = "DATE_LOCK"; // for use in synchronization

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.ModuleConfigurationDao#delete(org.broadleafcommerce.common.config.domain.ModuleConfiguration)
   */
  @Override public void delete(ModuleConfiguration config) {
    ((Status) config).setArchived('Y');
    em.merge(config);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.ModuleConfigurationDao#getCurrentDateResolution()
   */
  @Override public Long getCurrentDateResolution() {
    return currentDateResolution;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.ModuleConfigurationDao#readActiveByType(org.broadleafcommerce.common.config.service.type.ModuleConfigurationType)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<ModuleConfiguration> readActiveByType(ModuleConfigurationType type) {
    Query query = em.createNamedQuery("BC_READ_ACTIVE_MODULE_CONFIG_BY_TYPE");
    query.setParameter("configType", type.getType());

    Date myDate = SystemTime.asDate();
    myDate = getDateFactoringInDateResolution(myDate);

    query.setParameter("currentDate", myDate);
    query.setHint(QueryHints.CACHEABLE, true);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.ModuleConfigurationDao#readAllByType(org.broadleafcommerce.common.config.service.type.ModuleConfigurationType)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<ModuleConfiguration> readAllByType(ModuleConfigurationType type) {
    Query query = em.createNamedQuery("BC_READ_MODULE_CONFIG_BY_TYPE");
    query.setParameter("configType", type.getType());
    query.setHint(QueryHints.CACHEABLE, true);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.ModuleConfigurationDao#readById(java.lang.Long)
   */
  @Override public ModuleConfiguration readById(Long id) {
    return em.find(AbstractModuleConfiguration.class, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.ModuleConfigurationDao#readByType(java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<ModuleConfiguration> readByType(Class<? extends ModuleConfiguration> type) {
    // TODO change this to a JPA criteria expression
    Query query = em.createQuery("SELECT config FROM " + type.getName() + " config");
    query.setHint(QueryHints.CACHEABLE, true);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.ModuleConfigurationDao#save(org.broadleafcommerce.common.config.domain.ModuleConfiguration)
   */
  @Override public ModuleConfiguration save(ModuleConfiguration config) {
    if (config.getIsDefault()) {
      Query batchUpdate = em.createNamedQuery("BC_BATCH_UPDATE_MODULE_CONFIG_DEFAULT");
      batchUpdate.setParameter("configType", config.getModuleConfigurationType().getType());
      batchUpdate.executeUpdate();
    }

    return em.merge(config);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.dao.ModuleConfigurationDao#setCurrentDateResolution(java.lang.Long)
   */
  @Override public void setCurrentDateResolution(Long currentDateResolution) {
    this.currentDateResolution = currentDateResolution;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   currentDate  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Date getDateFactoringInDateResolution(Date currentDate) {
    Date myDate;
    Long myCurrentDateResolution = currentDateResolution;

    synchronized (DATE_LOCK) {
      if ((currentDate.getTime() - this.currentDate.getTime()) > myCurrentDateResolution) {
        this.currentDate = new Date(currentDate.getTime());
        myDate           = currentDate;
      } else {
        myDate = this.currentDate;
      }
    }

    return myDate;
  }
} // end class ModuleConfigurationDaoImpl
