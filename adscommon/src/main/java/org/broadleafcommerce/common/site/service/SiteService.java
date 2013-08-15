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

package org.broadleafcommerce.common.site.service;

import java.util.List;

import org.broadleafcommerce.common.site.domain.Site;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface SiteService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * A List of all sites in the system.
   *
   * @return  a List of all sites in the system
   */
  List<Site> findAllActiveSites();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the default site.
   *
   * @see     {@link org.broadleafcommerce.common.site.dao.SiteDaoImpl}
   *
   * @return  the default site.
   */
  Site retrieveDefaultSite();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Find a site by its domain.
   *
   * @param   domain  id
   *
   * @return  find a site by its domain.
   */
  Site retrieveSiteByDomainName(String domain);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Find a site by its id.
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  find a site by its id.
   */
  Site retrieveSiteById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Save updates to a site.
   *
   * @param   site  id
   *
   * @return  save updates to a site.
   */
  Site save(Site site);
} // end interface SiteService
