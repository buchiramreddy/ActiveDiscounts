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

package org.broadleafcommerce.cms.structure.service;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.cms.structure.domain.StructuredContent;
import org.broadleafcommerce.cms.structure.domain.StructuredContentField;
import org.broadleafcommerce.cms.structure.domain.StructuredContentType;
import org.broadleafcommerce.cms.structure.dto.StructuredContentDTO;

import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.sandbox.domain.SandBox;

import org.broadleafcommerce.openadmin.server.domain.SandBoxItemListener;

import org.hibernate.Criteria;


/**
 * Provides services to manage <code>StructuredContent</code> items.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface StructuredContentService extends SandBoxItemListener {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * This method is intended to be called from within the CMS admin only.
   *
   * <p>Adds the passed in contentItem to the DB.</p>
   *
   * <p>Creates a sandbox/site if one doesn't already exist.</p>
   *
   * @param   content             DOCUMENT ME!
   * @param   destinationSandbox  DOCUMENT ME!
   *
   * @return  this method is intended to be called from within the CMS admin only.
   */
  StructuredContent addStructuredContent(StructuredContent content, SandBox destinationSandbox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Follows the same rules as
   * {@link #findContentItems(org.broadleafcommerce.common.sandbox.domain.SandBox, org.hibernate.Criteria) findContentItems}.
   *
   * @param   sandBox  DOCUMENT ME!
   * @param   c        DOCUMENT ME!
   *
   * @return  the count of items in this sandbox that match the passed in Criteria
   */
  Long countContentItems(SandBox sandBox, Criteria c);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * If deleting and item where content.originalItemId != null then the item is deleted from the database.
   *
   * <p>If the originalItemId is null, then this method marks the items as deleted within the passed in sandbox.</p>
   *
   * @param  content             DOCUMENT ME!
   * @param  destinationSandbox  DOCUMENT ME!
   */
  void deleteStructuredContent(StructuredContent content, SandBox destinationSandbox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Finds all content items regardless of the {@link Sandbox} they are a member of.
   *
   * @return  finds all content items regardless of the {@link Sandbox} they are a member of.
   */
  List<StructuredContent> findAllContentItems();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method is intended to be called solely from the CMS admin. Similar methods exist that are intended for other
   * clients (e.g. lookupStructuredContentItemsBy....<br>
   * Returns content items for the passed in sandbox that match the passed in criteria. The criteria acts as a where
   * clause to be used in the search for content items. Implementations should automatically add criteria such that no
   * archived items are returned from this method.<br>
   * The SandBox parameter impacts the results as follows. If a <code>SandBoxType</code> of production is passed in,
   * only those items in that SandBox are returned.<br>
   * If a non-production SandBox is passed in, then the method will return the items associatd with the related
   * production SandBox and then merge in the results of the passed in SandBox.
   *
   * @param   sandbox   - the sandbox to find structured content items (null indicates items that are in production for
   *                    sites that are single tenant.
   * @param   criteria  - the criteria used to search for content
   *
   * @return  this method is intended to be called solely from the CMS admin.
   */
  List<StructuredContent> findContentItems(SandBox sandbox, Criteria criteria);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the fields associated with the passed in contentId. This is preferred over the direct access from the
   * ContentItem so that the two items can be cached distinctly
   *
   * @param   contentId  - The id of the content.
   *
   * @return  Map of fields for this content id
   */
  Map<String, StructuredContentField> findFieldsByContentId(Long contentId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the StructuredContent item associated with the passed in id.
   *
   * @param   contentId  - The id of the content item.
   *
   * @return  The associated structured content item.
   */
  StructuredContent findStructuredContentById(Long contentId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the <code>StructuredContentType</code> associated with the passed in id.
   *
   * @param   id  - The id of the content type.
   *
   * @return  The associated <code>StructuredContentType</code>.
   */
  StructuredContentType findStructuredContentTypeById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the <code>StructuredContentType</code> associated with the passed in String value.
   *
   * @param   name  - The name of the content type.
   *
   * @return  The associated <code>StructuredContentType</code>.
   */
  StructuredContentType findStructuredContentTypeByName(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isAutomaticallyApproveAndPromoteStructuredContent();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method returns content by name only.<br>
   * Returns active content items for the passed in sandbox that match the passed in type.<br>
   * The SandBox parameter impacts the results as follows. If a <code>SandBoxType</code> of production is passed in,
   * only those items in that SandBox are returned.<br>
   * If a non-production SandBox is passed in, then the method will return the items associatd with the related
   * production SandBox and then merge in the results of the passed in SandBox.
   *
   * @param   sandBox      - the sandbox to find structured content items (null indicates items that are in production
   *                       for sites that are single tenant.
   * @param   contentName  - the name of content to return
   * @param   locale       DOCUMENT ME!
   * @param   count        - the max number of content items to return
   * @param   ruleDTOs     - a Map of objects that will be used in MVEL processing.
   * @param   secure       - set to true if the request is being served over https
   *
   * @return  - The matching items
   *
   * @see     org.broadleafcommerce.cms.web.structure.DisplayContentTag
   */
  List<StructuredContentDTO> lookupStructuredContentItemsByName(SandBox sandBox, String contentName, Locale locale,
    Integer count, Map<String, Object> ruleDTOs, boolean secure);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method returns content by name and type.<br>
   * Returns active content items for the passed in sandbox that match the passed in type.<br>
   * The SandBox parameter impacts the results as follows. If a <code>SandBoxType</code> of production is passed in,
   * only those items in that SandBox are returned.<br>
   * If a non-production SandBox is passed in, then the method will return the items associatd with the related
   * production SandBox and then merge in the results of the passed in SandBox.
   *
   * @param   sandBox      - the sandbox to find structured content items (null indicates items that are in production
   *                       for sites that are single tenant.
   * @param   contentType  - the type of content to return
   * @param   contentName  - the name of content to return
   * @param   locale       DOCUMENT ME!
   * @param   count        - the max number of content items to return
   * @param   ruleDTOs     - a Map of objects that will be used in MVEL processing.
   * @param   secure       - set to true if the request is being served over https
   *
   * @return  - The matching items
   *
   * @see     org.broadleafcommerce.cms.web.structure.DisplayContentTag
   */
  List<StructuredContentDTO> lookupStructuredContentItemsByName(SandBox sandBox, StructuredContentType contentType,
    String contentName, Locale locale, Integer count, Map<String, Object> ruleDTOs, boolean secure);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method returns content<br>
   * Returns active content items for the passed in sandbox that match the passed in type.<br>
   * The SandBox parameter impacts the results as follows. If a <code>SandBoxType</code> of production is passed in,
   * only those items in that SandBox are returned.<br>
   * If a non-production SandBox is passed in, then the method will return the items associatd with the related
   * production SandBox and then merge in the results of the passed in SandBox.<br>
   * The secure item is used in cases where the structured content item contains an image path that needs to be
   * rewritten to use https.
   *
   * @param   sandBox      - the sandbox to find structured content items (null indicates items that are in production
   *                       for sites that are single tenant.
   * @param   contentType  - the type of content to return
   * @param   locale       DOCUMENT ME!
   * @param   count        - the max number of content items to return
   * @param   ruleDTOs     - a Map of objects that will be used in MVEL processing.
   * @param   secure       - set to true if the request is being served over https
   *
   * @return  - The matching items
   *
   * @see     org.broadleafcommerce.cms.web.structure.DisplayContentTag
   */
  List<StructuredContentDTO> lookupStructuredContentItemsByType(SandBox sandBox, StructuredContentType contentType,
    Locale locale, Integer count, Map<String, Object> ruleDTOs, boolean secure);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Removes the items from cache that match the passed in name and page keys.
   *
   * @param  nameKey  - key for a specific content item
   * @param  typeKey  - key for a type of content item
   */
  void removeItemFromCache(String nameKey, String typeKey);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * A list of all <code>StructuredContentType</code>s.
   *
   * @return  a list of all <code>StructuredContentType</code>s
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

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  automaticallyApproveAndPromoteStructuredContent  DOCUMENT ME!
   */
  void setAutomaticallyApproveAndPromoteStructuredContent(boolean automaticallyApproveAndPromoteStructuredContent);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method is intended to be called from within the CMS admin only.
   *
   * <p>Updates the structuredContent according to the following rules:</p>
   *
   * <p>1. If sandbox has changed from null to a value This means that the user is editing an item in production and the
   * edit is taking place in a sandbox.</p>
   *
   * <p>Clone the item and add it to the new sandbox and set the cloned item's originalItemId to the id of the item
   * being updated.</p>
   *
   * <p>2. If the sandbox has changed from one value to another This means that the user is moving the item from one
   * sandbox to another.</p>
   *
   * <p>Update the siteId for the item to the one associated with the new sandbox</p>
   *
   * <p>3. If the sandbox has changed from a value to null This means that the item is moving from the sandbox to
   * production.</p>
   *
   * <p>If the item has an originalItemId, then update that item by setting it's archived flag to true.</p>
   *
   * <p>Then, update the siteId of the item being updated to be the siteId of the original item.</p>
   *
   * <p>4. If the sandbox is the same then just update the item.</p>
   *
   * @param   content  DOCUMENT ME!
   * @param   sandbox  DOCUMENT ME!
   *
   * @return  this method is intended to be called from within the CMS admin only.
   */
  StructuredContent updateStructuredContent(StructuredContent content, SandBox sandbox);

} // end interface StructuredContentService
