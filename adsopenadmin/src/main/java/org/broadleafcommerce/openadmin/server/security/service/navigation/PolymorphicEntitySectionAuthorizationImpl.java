/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.security.service.navigation;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;

import org.springframework.stereotype.Component;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Component("blPolymorphicEntityCheckSectionAuthorization")
public class PolymorphicEntitySectionAuthorizationImpl implements SectionAuthorization {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blDynamicEntityDao")
  protected DynamicEntityDao dynamicEntityDao;

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @PostConstruct public void init() {
    dynamicEntityDao.setStandardEntityManager(em);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.navigation.SectionAuthorization#isUserAuthorizedToViewSection(org.broadleafcommerce.openadmin.server.security.domain.AdminUser,
   *       org.broadleafcommerce.openadmin.server.security.domain.AdminSection)
   */
  @Override public boolean isUserAuthorizedToViewSection(AdminUser adminUser, AdminSection section) {
    try {
      // Only display this section if there are 1 or more entities relative to the ceiling
      // for this section that are qualified to be created by the admin
      return
        dynamicEntityDao.getAllPolymorphicEntitiesFromCeiling(
          Class.forName(section.getCeilingEntity()), false).length > 0;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

} // end class PolymorphicEntitySectionAuthorizationImpl
