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

package org.broadleafcommerce.cms.structure.domain;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.sandbox.domain.SandBox;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;


/**
 * StructuredContent implementations provide a representation of a generic content item with a set of predefined fields.
 * The fields associated with an instance of StructuredContent are defined by its associated
 * {@link org.broadleafcommerce.cms.structure.domain.StructuredContentType}.<br>
 * StructuredContent items are typically maintained via the Broadleaf Commerce admin.<br>
 * Display structured content items is typically done using the
 * {@link org.broadleafcommerce.cms.web.structure.DisplayContentTag} taglib.<br>
 * An typical usage for <code>StructuredContent</code> is to display targeted ads. Consider a <code>
 * StructuredContentType</code> of "ad" with fields "ad-image" and "target-url". This "ad" might show on a websites home
 * page. By adding <code>StructuredContentMatchRules</code> and setting the <code>priority</code>, different ads could
 * be shown to different users.
 *
 * <p>It would not be typical in a Broadleaf implementation to extend this interface or to use any implementation other
 * than {@link org.broadleafcommerce.cms.structure.domain.StructuredContentImpl}.</p>
 *
 * @see      {@link org.broadleafcommerce.cms.structure.domain.StructuredContentType}
 * @see      {@link org.broadleafcommerce.cms.structure.domain.StructuredContentImpl}
 * @see      {@link org.broadleafcommerce.cms.web.structure.DisplayContentTag}
 * @author   Brian Polster
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public interface StructuredContent extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Builds a copy of this content item. Used by the content management system when an item is edited.
   *
   * @return  a copy of this item
   */
  @Nonnull StructuredContent cloneEntity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the archived indicator. The archivedFlag indicates that the item is no longer of importance to the system.
   * Items that have been archived may be removed by a data cleanup process.
   *
   * @return  true if this item has been archived. Returns false if not set.
   */
  @Nonnull Boolean getArchivedFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns audit information for this content item.
   *
   * @return  audit information for this content item.
   */
  @Nullable AdminAuditable getAuditable();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the name.
   *
   * @return  the name
   */
  @Nonnull String getContentName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the "deleted" indicator. Deleted means that the item has been marked for deletion. If this method returns
   * true, the item will not be returned as part
   * {@link org.broadleafcommerce.cms.structure.service.StructuredContentService#lookupStructuredContentItemsByType(org.broadleafcommerce.common.sandbox.domain.SandBox, org.broadleafcommerce.cms.structure.domain.StructuredContentType, org.broadleafcommerce.common.locale.domain.Locale, Integer, java.util.Map)}'s}.
   *
   * <p>In a "production sandbox", an item that returns true for <code>getDeletedFlag</code> should also return true for
   * <code>getArchivedFlag</code></p>
   *
   * @return  the deleted indicator or false if none found
   */
  @Nonnull Boolean getDeletedFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the primary key.
   *
   * @return  the primary key
   */
  @Nullable Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the {@link org.broadleafcommerce.common.locale.domain.Locale} associated with this content item.
   *
   * @return  gets the {@link org.broadleafcommerce.common.locale.domain.Locale} associated with this content item.
   */
  @Nonnull Locale getLocale();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the locked flag. If an item is locked, it is being edited by another user.
   *
   * @return  true if this item is locked for editing.
   */
  @Nonnull Boolean getLockedFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the offlineFlag. Indicates that the item should no longer appear on the site. The item will still appear
   * within the content administration program but no longer be returned as part of the client facing APIs.
   *
   * @return  true if this item is offline
   */
  @Nullable Boolean getOfflineFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the id of a related content item on which this item is based. This value is used internally by the content
   * management system. Generally, when an item is promoted through a content workflow to production, the system will
   * set mark the item associated with the originalItemId as archived.
   *
   * @return  the id of the originalItemId
   */
  @Nullable Long getOriginalItemId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the <code>SandBox</code> which originally edited this item. Used by the Content Management System to determine
   * where to return an item that is being rejected.
   *
   * @return  gets the <code>SandBox</code> which originally edited this item.
   */
  @Nullable SandBox getOriginalSandBox();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the integer priority of this content item. Items with a lower priority should be displayed before items with a
   * higher priority.
   *
   * @return  the priority as a numeric value
   */
  @Nullable Integer getPriority();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the item (or cart) based rules associated with this content item.
   *
   * @return  the item (or cart) based rules associated with this content item.
   */
  @Nullable Set<StructuredContentItemCriteria> getQualifyingItemCriteria();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the Sandbox associated with the content item. SandBoxes allow for segmentation of data. A result of null
   * indicates that this item is in "Production" in a single-site architecture.<br>
   * The processing may differ depending on which type of SandBox is returned.
   *
   * @return  gets the Sandbox associated with the content item.
   */
  @Nullable SandBox getSandbox();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets a map with the custom fields associated with this content item.<br>
   * The map keys are based on the field types. For example, consider a content item with a <code>
   * StructuredContentType</code> of ad which defined a field named targetUrl. The field could be accessed with <code>
   * structuredContentItem.getStructuredContentFields().get("targetUrl")</code>
   *
   * @return  gets a map with the custom fields associated with this content item.
   */
  @Nullable Map<String, StructuredContentField> getStructuredContentFields();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a map of the targeting rules associated with this content item.
   *
   * <p>Targeting rules are defined in the content mangagement system and used to enforce which items are returned to
   * the client.</p>
   *
   * @return  a map of the targeting rules associated with this content item.
   */
  @Nullable Map<String, StructuredContentRule> getStructuredContentMatchRules();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the {@link org.broadleafcommerce.cms.structure.domain.StructuredContentType} associated with this content
   * item.
   *
   * @return  gets the {@link org.broadleafcommerce.cms.structure.domain.StructuredContentType} associated with this
   *          content item.
   */
  @Nonnull StructuredContentType getStructuredContentType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the archived flag for this item. Would not typically be called outside the Content Administration system.
   *
   * <p>Content items with an archived flag of true will not be returned from most APIs and can be deleted from the
   * system.</p>
   *
   * @param  archivedFlag  DOCUMENT ME!
   */
  void setArchivedFlag(@Nonnull Boolean archivedFlag);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets audit information for this content item. Default implementations automatically populate this data during
   * persistence.
   *
   * @param  auditable  DOCUMENT ME!
   */
  void setAuditable(@Nullable AdminAuditable auditable);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the name.
   *
   * @param  contentName  DOCUMENT ME!
   */
  void setContentName(@Nonnull String contentName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the deleted flag for this item. Would not typically be called outside of the Content Administration system.
   *
   * @param  deletedFlag  DOCUMENT ME!
   */
  void setDeletedFlag(@Nonnull Boolean deletedFlag);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the primary key.
   *
   * @param  id  the new primary key
   */
  void setId(@Nullable Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the locale associated with this content item.
   *
   * @param  locale  DOCUMENT ME!
   */
  void setLocale(@Nonnull Locale locale);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the lockedFlag for this item.
   *
   * @param  lockedFlag  DOCUMENT ME!
   */
  void setLockedFlag(@Nullable Boolean lockedFlag);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the offline flag.
   *
   * @param  offlineFlag  DOCUMENT ME!
   */
  void setOfflineFlag(@Nullable Boolean offlineFlag);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The id of the item on which this content item is based. This property gets set by the content management system
   * when an item is edited.
   *
   * @param  originalItemId  DOCUMENT ME!
   */
  void setOriginalItemId(@Nullable Long originalItemId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the originalSandBox for this item. The Content Management System will set this value when an item is promoted
   * from a user sandbox.
   *
   * @param  originalSandBox  DOCUMENT ME!
   */
  void setOriginalSandBox(@Nullable SandBox originalSandBox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the display priority of this item. Lower priorities should be displayed first.
   *
   * @param  priority  DOCUMENT ME!
   */
  void setPriority(@Nullable Integer priority);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the item (e.g. cart) based rules associated with this content item.
   *
   * @param  qualifyingItemCriteria  DOCUMENT ME!
   */
  void setQualifyingItemCriteria(@Nullable Set<StructuredContentItemCriteria> qualifyingItemCriteria);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the SandBox for this content item. This method is typically only called by the BLC Content Management System
   * during content-item lifecycle operations like New, Promote, Approve, Deploy.
   *
   * @param  sandbox  DOCUMENT ME!
   */
  void setSandbox(@Nullable SandBox sandbox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the structured content fields for this item. Would not typically called outside of the
   * ContentManagementSystem.
   *
   * @param  structuredContentFields  DOCUMENT ME!
   */
  void setStructuredContentFields(@Nullable Map<String, StructuredContentField> structuredContentFields);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the targeting rules for this content item.
   *
   * @param  structuredContentMatchRules  DOCUMENT ME!
   */
  void setStructuredContentMatchRules(@Nullable Map<String, StructuredContentRule> structuredContentMatchRules);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the {@link org.broadleafcommerce.cms.structure.domain.StructuredContentType} associated with this content
   * item.
   *
   * @param  structuredContentType  DOCUMENT ME!
   */
  void setStructuredContentType(@Nonnull StructuredContentType structuredContentType);

} // end interface StructuredContent
