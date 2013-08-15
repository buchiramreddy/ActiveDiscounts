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

import org.broadleafcommerce.common.config.domain.SystemProperty;


/**
 * This DAO enables access to manage system properties that can be stored in the database.
 *
 * <p>User: Kelly Tisdell Date: 6/25/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface SystemPropertiesDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SystemProperty createNewSystemProperty();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  systemProperty  DOCUMENT ME!
   */
  void deleteSystemProperty(SystemProperty systemProperty);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<SystemProperty> readAllSystemProperties();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   name  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SystemProperty readSystemPropertyByName(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   systemProperty  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SystemProperty saveSystemProperty(SystemProperty systemProperty);
} // end interface SystemPropertiesDao
