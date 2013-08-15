/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.security.service.navigation;

import org.broadleafcommerce.openadmin.server.security.domain.AdminMenu;
import org.broadleafcommerce.openadmin.server.security.domain.AdminModule;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface AdminNavigationService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   adminUser  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminMenu buildMenu(AdminUser adminUser);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   className  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminSection findAdminSectionByClass(String className);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   clazz  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminSection findAdminSectionByClass(Class<?> clazz);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sectionKey  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminSection findAdminSectionBySectionKey(String sectionKey);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   uri  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminSection findAdminSectionByURI(String uri);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   adminUser  DOCUMENT ME!
   * @param   module     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isUserAuthorizedToViewModule(AdminUser adminUser, AdminModule module);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   adminUser  DOCUMENT ME!
   * @param   section    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isUserAuthorizedToViewSection(AdminUser adminUser, AdminSection section);


} // end interface AdminNavigationService
