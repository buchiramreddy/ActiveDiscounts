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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;
import org.broadleafcommerce.openadmin.audit.AdminAuditableListener;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


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
@Table(name = "BLC_STATIC_ASSET_DESC")
public class StaticAssetDescriptionImpl implements StaticAssetDescription {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Embedded protected AdminAuditable auditable = new AdminAuditable();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetDescriptionImpl_Description",
    prominent    = true
  )
  @Column(name = "DESCRIPTION")
  protected String description;

  /** DOCUMENT ME! */
  @Column(name = "STATIC_ASSET_DESC_ID")
  @GeneratedValue(generator = "StaticAssetDescriptionId")
  @GenericGenerator(
    name       = "StaticAssetDescriptionId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "StaticAssetDescriptionImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.file.domain.StaticAssetDescriptionImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetDescriptionImpl_Long_Description",
    largeEntry   = true,
    visibility   = VisibilityEnum.GRID_HIDDEN
  )
  @Column(name = "LONG_DESCRIPTION")
  protected String longDescription;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetDescription#cloneEntity()
   */
  @Override public StaticAssetDescription cloneEntity() {
    StaticAssetDescriptionImpl newAssetDescription = new StaticAssetDescriptionImpl();
    newAssetDescription.description     = description;
    newAssetDescription.longDescription = longDescription;

    return newAssetDescription;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetDescription#getAuditable()
   */
  @Override public AdminAuditable getAuditable() {
    return auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetDescription#getDescription()
   */
  @Override public String getDescription() {
    return description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetDescription#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetDescription#getLongDescription()
   */
  @Override public String getLongDescription() {
    return longDescription;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetDescription#setAuditable(org.broadleafcommerce.openadmin.audit.AdminAuditable)
   */
  @Override public void setAuditable(AdminAuditable auditable) {
    this.auditable = auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetDescription#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetDescription#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAssetDescription#setLongDescription(java.lang.String)
   */
  @Override public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }
} // end class StaticAssetDescriptionImpl
