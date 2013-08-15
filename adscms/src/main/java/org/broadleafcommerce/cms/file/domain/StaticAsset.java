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

package org.broadleafcommerce.cms.file.domain;

import java.io.Serializable;

import java.util.Map;

import org.broadleafcommerce.cms.field.type.StorageType;

import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.site.domain.Site;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface StaticAsset extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  StaticAsset cloneEntity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the altText of this asset.
   *
   * @return  the altText of this asset.
   */
  String getAltText();

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
   * @deprecated  - Use {@link #getTitle()} or {@link #getAltText()}getAltText() instead.
   *
   * @return      DOCUMENT ME!
   */
  Map<String, StaticAssetDescription> getContentMessageValues();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean getDeletedFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the file extension of the asset.
   *
   * @return  the file extension of the asset.
   */
  String getFileExtension();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Filesize of the asset.
   *
   * @return  filesize of the asset.
   */
  Long getFileSize();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * URL used to retrieve this asset.
   *
   * @return  uRL used to retrieve this asset.
   */
  String getFullUrl();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the id of the static asset.
   *
   * @return  the id of the static asset.
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
   * Returns the mimeType of the asset.
   *
   * @return  the mimeType of the asset.
   */
  String getMimeType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The name of the static asset.
   *
   * @return  the name of the static asset.
   */
  String getName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getOriginalAssetId();

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
  SandBox getSandbox();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @deprecated  - not currently used
   *
   * @return      DOCUMENT ME!
   */
  Site getSite();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns how the underlying asset is stored. Typically on the FileSystem or the Database.
   *
   * <p>If null, this method returns <code>StorageType.DATABASE</code> for backwards compatibility.</p>
   *
   * @see     {@link org.broadleafcommerce.cms.file.service.StaticAssetService}
   *
   * @return  how the underlying asset is stored.
   */
  StorageType getStorageType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the title of this asset.
   *
   * @return  the title of this asset.
   */
  String getTitle();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set the altText of the static asset.
   *
   * @param  altText  title
   */
  void setAltText(String altText);

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
   *
   * DOCUMENT ME!
   *
   * @deprecated  - Use {@link #setTitle(String)} or {@link #setAltText(String)} instead.
   *
   * @param       contentMessageValues  DOCUMENT ME!
   */
  void setContentMessageValues(Map<String, StaticAssetDescription> contentMessageValues);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  deletedFlag  DOCUMENT ME!
   */
  void setDeletedFlag(Boolean deletedFlag);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the fileExtension of the asset.
   *
   * @param  fileExtension  DOCUMENT ME!
   */
  void setFileExtension(String fileExtension);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the filesize of the asset.
   *
   * @param  fileSize  DOCUMENT ME!
   */
  void setFileSize(Long fileSize);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the URL for the asset.
   *
   * @param  fullUrl  DOCUMENT ME!
   */
  void setFullUrl(String fullUrl);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the id of the static asset.
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
   * Sets the mimeType of the asset.
   *
   * @param  mimeType  DOCUMENT ME!
   */
  void setMimeType(String mimeType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the name of the static asset. Used primarily for
   *
   * @param  name  DOCUMENT ME!
   */
  void setName(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  originalPageId  DOCUMENT ME!
   */
  void setOriginalAssetId(Long originalPageId);

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
   * @param  sandbox  DOCUMENT ME!
   */
  void setSandbox(SandBox sandbox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @deprecated  - not currently used
   *
   * @param       site  DOCUMENT ME!
   */
  void setSite(Site site);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns how the asset was stored in the backend (e.g. DATABASE or FILESYSTEM)
   *
   * @param  storageType  DOCUMENT ME!
   */
  void setStorageType(StorageType storageType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set the title of the static asset.
   *
   * @param  title  DOCUMENT ME!
   */
  void setTitle(String title);

} // end interface StaticAsset
