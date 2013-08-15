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

package org.broadleafcommerce.cms.page.service;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.cms.page.domain.Page;
import org.broadleafcommerce.cms.page.domain.PageField;
import org.broadleafcommerce.cms.page.domain.PageTemplate;
import org.broadleafcommerce.cms.page.dto.PageDTO;
import org.broadleafcommerce.cms.page.message.ArchivedPagePublisher;

import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.sandbox.domain.SandBox;

import org.hibernate.Criteria;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PageService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * This method is intended to be called from within the CMS admin only.
   *
   * <p>Adds the passed in page to the DB.</p>
   *
   * <p>Creates a sandbox/site if one doesn't already exist.</p>
   *
   * @param   page                DOCUMENT ME!
   * @param   destinationSandbox  DOCUMENT ME!
   *
   * @return  this method is intended to be called from within the CMS admin only.
   */
  Page addPage(Page page, SandBox destinationSandbox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox   DOCUMENT ME!
   * @param   criteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long countPages(SandBox sandBox, Criteria criteria);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * If deleting and item where page.originalPageId != null then the item is deleted from the database.
   *
   * <p>If the originalPageId is null, then this method marks the items as deleted within the passed in sandbox.</p>
   *
   * @param  page                DOCUMENT ME!
   * @param  destinationSandbox  DOCUMENT ME!
   */
  void deletePage(Page page, SandBox destinationSandbox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the page with the passed in id.
   *
   * @param   pageId  - The id of the page.
   *
   * @return  The associated page.
   */
  Page findPageById(Long pageId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Looks up the page from the backend datastore. Processes the page's fields to fix the URL if the site has overridden
   * the URL for images. If secure is true and images are being overridden, the system will use https.
   *
   * @param   currentSandbox  - current sandbox
   * @param   locale          - current locale
   * @param   uri             - the URI to return a page for
   * @param   ruleDTOs        - ruleDTOs that are used as the data to process page rules
   * @param   secure          - set to true if current request is over HTTPS
   *
   * @return  looks up the page from the backend datastore.
   */
  PageDTO findPageByURI(SandBox currentSandbox, Locale locale, String uri, Map<String, Object> ruleDTOs,
    boolean secure);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the page-fields associated with the passed in page-id. This is preferred over the direct access from Page
   * so that the two items can be cached distinctly
   *
   * @param   pageId  - The id of the page.
   *
   * @return  The associated page.
   */
  Map<String, PageField> findPageFieldsByPageId(Long pageId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox   DOCUMENT ME!
   * @param   criteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Page> findPages(SandBox sandBox, Criteria criteria);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the page template with the passed in id.
   *
   * @param   id  - the id of the page template
   *
   * @return  The associated page template.
   */
  PageTemplate findPageTemplateById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<ArchivedPagePublisher> getArchivedPageListeners();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isAutomaticallyApproveAndPromotePages();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns all pages, regardless of any sandbox they are apart of.
   *
   * @return  all {@link org.broadleafcommerce.cms.page.domain.Page}s configured in the system
   */
  List<Page> readAllPages();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns all page templates, regardless of any sandbox they are apart of.
   *
   * @return  all {@link org.broadleafcommerce.cms.page.domain.PageTemplate}s configured in the system
   */
  List<PageTemplate> readAllPageTemplates();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Call to evict both secure and non-secure pages matching the passed in key.
   *
   * @param  baseKey  DOCUMENT ME!
   */
  void removePageFromCache(String baseKey);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Saves the given {@link org.broadleafcommerce.cms.page.domain.PageTemplate}.
   *
   * @param   template  the {@link org.broadleafcommerce.cms.page.domain.PageTemplate} to save
   *
   * @return  the database-saved {@link org.broadleafcommerce.cms.page.domain.PageTemplate}
   */
  PageTemplate savePageTemplate(PageTemplate template);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  archivedPageListeners  DOCUMENT ME!
   */
  void setArchivedPageListeners(List<ArchivedPagePublisher> archivedPageListeners);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  automaticallyApproveAndPromotePages  DOCUMENT ME!
   */
  void setAutomaticallyApproveAndPromotePages(boolean automaticallyApproveAndPromotePages);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method is intended to be called from within the CMS admin only.
   *
   * <p>Updates the page according to the following rules:</p>
   *
   * <p>1. If sandbox has changed from null to a value This means that the user is editing an item in production and the
   * edit is taking place in a sandbox.</p>
   *
   * <p>Clone the page and add it to the new sandbox and set the cloned page's originalPageId to the id of the page
   * being updated.</p>
   *
   * <p>2. If the sandbox has changed from one value to another This means that the user is moving the item from one
   * sandbox to another.</p>
   *
   * <p>Update the siteId for the page to the one associated with the new sandbox</p>
   *
   * <p>3. If the sandbox has changed from a value to null This means that the item is moving from the sandbox to
   * production.</p>
   *
   * <p>If the page has an originalPageId, then update that page by setting it's archived flag to true.</p>
   *
   * <p>Then, update the siteId of the page being updated to be the siteId of the original page.</p>
   *
   * <p>4. If the sandbox is the same then just update the page.</p>
   *
   * @param   page     DOCUMENT ME!
   * @param   sandbox  DOCUMENT ME!
   *
   * @return  this method is intended to be called from within the CMS admin only.
   */
  Page updatePage(Page page, SandBox sandbox);
} // end interface PageService
