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

package org.broadleafcommerce.cms.page.domain;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.broadleafcommerce.common.sandbox.domain.SandBox;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface Page extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Page cloneEntity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean getArchivedFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminAuditable getAuditable();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean getDeletedFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getDescription();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getFullUrl();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean getLockedFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the offlineFlag. True indicates that the page should no longer appear on the site. The item will still
   * appear within the content administration program but no longer be returned as part of the client facing APIs.
   *
   * @return  true if this item is offline
   */
  @Nullable Boolean getOfflineFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getOriginalPageId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox getOriginalSandBox();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, PageField> getPageFields();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a map of the targeting rules associated with this page.
   *
   * <p>Targeting rules are defined in the content mangagement system and used to enforce which page is returned to the
   * client.</p>
   *
   * @return  a map of the targeting rules associated with this page.
   */
  @Nullable Map<String, PageRule> getPageMatchRules();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PageTemplate getPageTemplate();

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
  @Nullable Set<PageItemCriteria> getQualifyingItemCriteria();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox getSandbox();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  archivedFlag  DOCUMENT ME!
   */
  void setArchivedFlag(Boolean archivedFlag);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  auditable  DOCUMENT ME!
   */
  void setAuditable(AdminAuditable auditable);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  deletedFlag  DOCUMENT ME!
   */
  void setDeletedFlag(Boolean deletedFlag);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  description  DOCUMENT ME!
   */
  void setDescription(String description);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fullUrl  DOCUMENT ME!
   */
  void setFullUrl(String fullUrl);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  lockedFlag  DOCUMENT ME!
   */
  void setLockedFlag(Boolean lockedFlag);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the offline flag.
   *
   * @param  offlineFlag  DOCUMENT ME!
   */
  void setOfflineFlag(@Nullable Boolean offlineFlag);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  originalPageId  DOCUMENT ME!
   */
  void setOriginalPageId(Long originalPageId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  originalSandBox  DOCUMENT ME!
   */
  void setOriginalSandBox(SandBox originalSandBox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  pageFields  DOCUMENT ME!
   */
  void setPageFields(Map<String, PageField> pageFields);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the targeting rules for this content item.
   *
   * @param  pageRules  DOCUMENT ME!
   */
  void setPageMatchRules(@Nullable Map<String, PageRule> pageRules);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  pageTemplate  DOCUMENT ME!
   */
  void setPageTemplate(PageTemplate pageTemplate);

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
  void setQualifyingItemCriteria(@Nullable Set<PageItemCriteria> qualifyingItemCriteria);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandbox  DOCUMENT ME!
   */
  void setSandbox(SandBox sandbox);
} // end interface Page
