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

package org.broadleafcommerce.cms.page.dao;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.cms.page.domain.Page;
import org.broadleafcommerce.cms.page.domain.PageField;
import org.broadleafcommerce.cms.page.domain.PageTemplate;

import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.sandbox.domain.SandBox;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PageDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   clonedPage  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Page addPage(Page clonedPage);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  page  DOCUMENT ME!
   */
  void delete(Page page);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  page  DOCUMENT ME!
   */
  void detachPage(Page page);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox  DOCUMENT ME!
   * @param   locale   DOCUMENT ME!
   * @param   uri      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Page> findPageByURI(SandBox sandBox, Locale locale, String uri);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox             DOCUMENT ME!
   * @param   fullLocale          DOCUMENT ME!
   * @param   languageOnlyLocale  DOCUMENT ME!
   * @param   uri                 DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Page> findPageByURI(SandBox sandBox, Locale fullLocale, Locale languageOnlyLocale, String uri);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns all pages, regardless of any sandbox they are apart of.
   *
   * @return  all Pages configured in the system
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
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Page readPageById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   page  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, PageField> readPageFieldsByPage(Page page);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PageTemplate readPageTemplateById(Long id);

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
   * @param   page  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Page updatePage(Page page);
} // end interface PageDao
