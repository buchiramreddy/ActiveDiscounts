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

package org.broadleafcommerce.common.site.dao;

import java.util.List;

import org.broadleafcommerce.common.site.domain.Site;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface SiteDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * A List of all sites in the system.
   *
   * @return  a List of all sites in the system
   */
  List<Site> readAllActiveSites();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Finds a site by its id.
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  finds a site by its id.
   */
  Site retrieve(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a default site. This method returns null in the out of box implementation of Broadleaf. Extend for
   * implementation specific behavior.
   *
   * @return  a default site.
   */
  Site retrieveDefaultSite();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Finds a site by its domain or domain prefix.
   *
   * @param   domain  DOCUMENT ME!
   * @param   prefix  DOCUMENT ME!
   *
   * @return  finds a site by its domain or domain prefix.
   */
  Site retrieveSiteByDomainOrDomainPrefix(String domain, String prefix);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Persists the site changes.
   *
   * @param   site  DOCUMENT ME!
   *
   * @return  persists the site changes.
   */
  Site save(Site site);
} // end interface SiteDao
