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

import org.broadleafcommerce.common.config.domain.ModuleConfiguration;
import org.broadleafcommerce.common.config.service.type.ModuleConfigurationType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface ModuleConfigurationDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  config  DOCUMENT ME!
   */
  void delete(ModuleConfiguration config);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the number of milliseconds that the current date/time will be cached for queries before refreshing. This
   * aids in query caching, otherwise every query that utilized current date would be different and caching would be
   * ineffective.
   *
   * @return  the milliseconds to cache the current date/time
   */
  Long getCurrentDateResolution();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<ModuleConfiguration> readActiveByType(ModuleConfigurationType type);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<ModuleConfiguration> readAllByType(ModuleConfigurationType type);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ModuleConfiguration readById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<ModuleConfiguration> readByType(Class<? extends ModuleConfiguration> type);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   config  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ModuleConfiguration save(ModuleConfiguration config);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the number of milliseconds that the current date/time will be cached for queries before refreshing. This aids
   * in query caching, otherwise every query that utilized current date would be different and caching would be
   * ineffective.
   *
   * @param  currentDateResolution  the milliseconds to cache the current date/time
   */
  void setCurrentDateResolution(Long currentDateResolution);
} // end interface ModuleConfigurationDao
