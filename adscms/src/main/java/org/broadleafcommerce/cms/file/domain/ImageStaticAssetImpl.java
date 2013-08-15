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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;

import org.broadleafcommerce.openadmin.audit.AdminAuditableListener;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blCMSElements"
)
@Entity
@EntityListeners(value = { AdminAuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_IMG_STATIC_ASSET")
public class ImageStaticAssetImpl extends StaticAssetImpl implements ImageStaticAsset {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ImageStaticAssetImpl_Height",
    order        = Presentation.FieldOrder.LAST + 2000,
    tab          = Presentation.Tab.Name.File_Details,
    tabOrder     = Presentation.Tab.Order.File_Details,
    readOnly     = true
  )
  @Column(name = "HEIGHT")
  protected Integer height;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ImageStaticAssetImpl_Width",
    order        = Presentation.FieldOrder.LAST + 1000,
    tab          = Presentation.Tab.Name.File_Details,
    tabOrder     = Presentation.Tab.Order.File_Details,
    readOnly     = true
  )
  @Column(name = "WIDTH")
  protected Integer width;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetImpl#cloneEntity()
   */
  @Override public ImageStaticAsset cloneEntity() {
    ImageStaticAssetImpl asset = new ImageStaticAssetImpl();
    asset.name            = name;
    asset.site            = site;
    asset.archivedFlag    = archivedFlag;
    asset.deletedFlag     = deletedFlag;
    asset.fullUrl         = fullUrl;
    asset.fileSize        = fileSize;
    asset.mimeType        = mimeType;
    asset.sandbox         = sandbox;
    asset.originalAssetId = originalAssetId;
    asset.width           = width;
    asset.height          = height;

    for (String key : contentMessageValues.keySet()) {
      StaticAssetDescription oldAssetDescription = contentMessageValues.get(key);
      StaticAssetDescription newAssetDescription = oldAssetDescription.cloneEntity();
      asset.getContentMessageValues().put(key, newAssetDescription);
    }

    return asset;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.ImageStaticAsset#getHeight()
   */
  @Override public Integer getHeight() {
    return height;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.ImageStaticAsset#getWidth()
   */
  @Override public Integer getWidth() {
    return width;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.ImageStaticAsset#setHeight(java.lang.Integer)
   */
  @Override public void setHeight(Integer height) {
    this.height = height;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.ImageStaticAsset#setWidth(java.lang.Integer)
   */
  @Override public void setWidth(Integer width) {
    this.width = width;
  }
} // end class ImageStaticAssetImpl
