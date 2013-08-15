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

package org.broadleafcommerce.cms.structure.dao;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.cms.structure.domain.StructuredContent;
import org.broadleafcommerce.cms.structure.domain.StructuredContentField;
import org.broadleafcommerce.cms.structure.domain.StructuredContentType;

import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.sandbox.domain.SandBox;


/**
 * Responsible for querying and updating {@link org.broadleafcommerce.cms.structure.domain.StructuredContent} items.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface StructuredContentDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Persists the changes or saves a new content item.
   *
   * @param   content  DOCUMENT ME!
   *
   * @return  the newly saved or persisted item
   */
  StructuredContent addOrUpdateContentItem(StructuredContent content);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Removes the passed in item from the underlying storage.
   *
   * @param  content  DOCUMENT ME!
   */
  void delete(StructuredContent content);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Detaches the item from the JPA session. This is intended for internal use by the CMS system. It supports the need
   * to clone an item as part of the editing process.
   *
   * @param  sc  - the item to detach
   */
  void detach(StructuredContent sc);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Pass through function for backwards compatibility to get a list of structured content.
   *
   * @param   sandBox  DOCUMENT ME!
   * @param   name     DOCUMENT ME!
   * @param   locale   DOCUMENT ME!
   *
   * @return  pass through function for backwards compatibility to get a list of structured content.
   */
  List<StructuredContent> findActiveStructuredContentByName(SandBox sandBox, String name, Locale locale);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Called by the <code>DisplayContentTag</code> to locate content based on the current SandBox, StructuredContentType,
   * Name, fullLocale and/or languageOnlyLocale.
   *
   * @param   sandBox             DOCUMENT ME!
   * @param   name                DOCUMENT ME!
   * @param   fullLocale          DOCUMENT ME!
   * @param   languageOnlyLocale  DOCUMENT ME!
   *
   * @return  called by the <code>DisplayContentTag</code> to locate content based on the current SandBox,
   *          StructuredContentType, Name, fullLocale and/or languageOnlyLocale.
   */
  List<StructuredContent> findActiveStructuredContentByName(SandBox sandBox, String name, Locale fullLocale,
    Locale languageOnlyLocale);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Pass through function for backwards compatibility to get a list of structured content.
   *
   * @param   sandBox  DOCUMENT ME!
   * @param   type     DOCUMENT ME!
   * @param   name     DOCUMENT ME!
   * @param   locale   DOCUMENT ME!
   *
   * @return  pass through function for backwards compatibility to get a list of structured content.
   */
  List<StructuredContent> findActiveStructuredContentByNameAndType(SandBox sandBox, StructuredContentType type,
    String name, Locale locale);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Called by the <code>DisplayContentTag</code> to locate content based on the current SandBox, StructuredContentType,
   * Name, fullLocale and/or languageOnlyLocale.
   *
   * @param   sandBox             DOCUMENT ME!
   * @param   type                DOCUMENT ME!
   * @param   name                DOCUMENT ME!
   * @param   fullLocale          DOCUMENT ME!
   * @param   languageOnlyLocale  DOCUMENT ME!
   *
   * @return  called by the <code>DisplayContentTag</code> to locate content based on the current SandBox,
   *          StructuredContentType, Name, fullLocale and/or languageOnlyLocale.
   */
  List<StructuredContent> findActiveStructuredContentByNameAndType(SandBox sandBox, StructuredContentType type,
    String name, Locale fullLocale, Locale languageOnlyLocale);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Pass through function for backwards compatibility to get a list of structured content.
   *
   * @param   sandBox  to search for the content
   * @param   type     of content to search for
   * @param   locale   to restrict the search to
   *
   * @return  a list of all matching content
   *
   * @see     org.broadleafcommerce.cms.web.structure.DisplayContentTag
   */
  List<StructuredContent> findActiveStructuredContentByType(SandBox sandBox, StructuredContentType type, Locale locale);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Called by the <code>DisplayContentTag</code> to locate content based on the current SandBox, StructuredContentType,
   * fullLocale and/or languageOnlyLocale.
   *
   * @param   sandBox             to search for the content
   * @param   type                of content to search for
   * @param   fullLocale          to restrict the search to
   * @param   languageOnlyLocale  locale based only on a language specified
   *
   * @return  a list of all matching content
   *
   * @see     org.broadleafcommerce.cms.web.structure.DisplayContentTag
   */
  List<StructuredContent> findActiveStructuredContentByType(SandBox sandBox, StructuredContentType type,
    Locale fullLocale, Locale languageOnlyLocale);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Finds all content regardless of the {@link Sandbox} they are a member of.
   *
   * @return  the list of {@link org.broadleafcommerce.cms.structure.domain.StructuredContent}, an empty list of none
   *          are found
   */
  List<StructuredContent> findAllContentItems();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the <code>StructuredContent</code> item that matches the passed in Id.
   *
   * @param   contentId  DOCUMENT ME!
   *
   * @return  the found item or null if it does not exist
   */
  StructuredContent findStructuredContentById(Long contentId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the <code>StructuredContentType</code> that matches the passed in contentTypeId.
   *
   * @param   contentTypeId  DOCUMENT ME!
   *
   * @return  the found item or null if it does not exist
   */
  StructuredContentType findStructuredContentTypeById(Long contentTypeId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Used to lookup the StructuredContentType by name.
   *
   * @param   name  DOCUMENT ME!
   *
   * @return  used to lookup the StructuredContentType by name.
   */
  StructuredContentType findStructuredContentTypeByName(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sc  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, StructuredContentField> readFieldsForStructuredContentItem(StructuredContent sc);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the list of all <code>StructuredContentType</code>s.
   *
   * @return  the list of found items
   */
  List<StructuredContentType> retrieveAllStructuredContentTypes();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Saves the given <b>type</b> and returns the merged instance.
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  saves the given <b>type</b> and returns the merged instance.
   */
  StructuredContentType saveStructuredContentType(StructuredContentType type);
} // end interface StructuredContentDao
