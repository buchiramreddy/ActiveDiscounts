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

import org.broadleafcommerce.common.config.dao.ModuleConfigurationDao;
import org.broadleafcommerce.common.config.domain.ModuleConfiguration;
import org.broadleafcommerce.common.config.service.type.ModuleConfigurationType;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blModuleConfigurationService")
@Transactional("blTransactionManager")
public class ModuleConfigurationServiceImpl implements ModuleConfigurationService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blModuleConfigurationDao")
  protected ModuleConfigurationDao moduleConfigDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.ModuleConfigurationService#delete(org.broadleafcommerce.common.config.domain.ModuleConfiguration)
   */
  @Override public void delete(ModuleConfiguration config) {
    moduleConfigDao.delete(config);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.ModuleConfigurationService#findActiveConfigurationsByType(org.broadleafcommerce.common.config.service.type.ModuleConfigurationType)
   */
  @Override public List<ModuleConfiguration> findActiveConfigurationsByType(ModuleConfigurationType type) {
    return moduleConfigDao.readActiveByType(type);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.ModuleConfigurationService#findAllConfigurationByType(org.broadleafcommerce.common.config.service.type.ModuleConfigurationType)
   */
  @Override public List<ModuleConfiguration> findAllConfigurationByType(ModuleConfigurationType type) {
    return moduleConfigDao.readAllByType(type);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.ModuleConfigurationService#findById(java.lang.Long)
   */
  @Override public ModuleConfiguration findById(Long id) {
    return moduleConfigDao.readById(id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.ModuleConfigurationService#findByType(java.lang.Class)
   */
  @Override public List<ModuleConfiguration> findByType(Class<? extends ModuleConfiguration> type) {
    return moduleConfigDao.readByType(type);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.service.ModuleConfigurationService#save(org.broadleafcommerce.common.config.domain.ModuleConfiguration)
   */
  @Override public ModuleConfiguration save(ModuleConfiguration config) {
    return moduleConfigDao.save(config);
  }

} // end class ModuleConfigurationServiceImpl
