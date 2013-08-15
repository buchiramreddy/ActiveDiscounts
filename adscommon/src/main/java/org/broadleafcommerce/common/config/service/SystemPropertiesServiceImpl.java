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

package org.broadleafcommerce.common.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.config.dao.SystemPropertiesDao;
import org.broadleafcommerce.common.config.domain.SystemProperty;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 * To change this template use File | Settings | File Templates.
 *
 * <p>User: Kelly Tisdell Date: 6/25/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blSystemPropertiesService")
public class SystemPropertiesServiceImpl implements SystemPropertiesService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blSystemPropertiesDao")
  protected SystemPropertiesDao systemPropertiesDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.SystemPropertiesService#createNewSystemProperty()
   */
  @Override
  @Transactional("blTransactionManager")
  public SystemProperty createNewSystemProperty() {
    return systemPropertiesDao.createNewSystemProperty();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.SystemPropertiesService#deleteSystemProperty(org.broadleafcommerce.common.config.domain.SystemProperty)
   */
  @Override
  @Transactional("blTransactionManager")
  public void deleteSystemProperty(SystemProperty systemProperty) {
    systemPropertiesDao.deleteSystemProperty(systemProperty);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.SystemPropertiesService#findAllSystemProperties()
   */
  @Override
  @Transactional("blTransactionManager")
  public List<SystemProperty> findAllSystemProperties() {
    return systemPropertiesDao.readAllSystemProperties();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.SystemPropertiesService#findSystemPropertyByName(java.lang.String)
   */
  @Override
  @Transactional("blTransactionManager")
  public SystemProperty findSystemPropertyByName(String name) {
    return systemPropertiesDao.readSystemPropertyByName(name);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.SystemPropertiesService#saveSystemProperty(org.broadleafcommerce.common.config.domain.SystemProperty)
   */
  @Override
  @Transactional("blTransactionManager")
  public SystemProperty saveSystemProperty(SystemProperty systemProperty) {
    return systemPropertiesDao.saveSystemProperty(systemProperty);
  }
} // end class SystemPropertiesServiceImpl
