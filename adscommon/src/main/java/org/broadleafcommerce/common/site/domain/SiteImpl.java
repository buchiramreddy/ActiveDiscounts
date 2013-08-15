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

package org.broadleafcommerce.common.site.domain;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.persistence.ArchiveStatus;
import org.broadleafcommerce.common.persistence.Status;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.RequiredOverride;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.sandbox.domain.SandBoxImpl;
import org.broadleafcommerce.common.site.service.type.SiteResolutionType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "baseSite")
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blCMSElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE BLC_SITE SET ARCHIVED = 'Y' WHERE SITE_ID = ?")
@Table(name = "BLC_SITE")
public class SiteImpl implements Site, Status {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;
  private static final Log  LOG              = LogFactory.getLog(SiteImpl.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Embedded protected ArchiveStatus archiveStatus = new ArchiveStatus();

  /** DOCUMENT ME! */
  @Column(name = "SITE_ID")
  @GeneratedValue(generator = "SiteId")
  @GenericGenerator(
    name       = "SiteId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SiteImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.common.site.domain.SiteImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "SiteImpl_Site_Name",
    order            = 1,
    gridOrder        = 1,
    group            = "SiteImpl_Site",
    prominent        = true,
    requiredOverride = RequiredOverride.REQUIRED
  )
  @Column(name = "NAME")
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    addType      = AddMethodType.LOOKUP,
    friendlyName = "siteCatalogTitle",
    manyToField  = "sites"
  )
  @BatchSize(size = 50)
  @JoinTable(
    name               = "BLC_SITE_CATALOG",
    joinColumns        = @JoinColumn(name = "SITE_ID"),
    inverseJoinColumns = @JoinColumn(name = "CATALOG_ID")
  )
  @ManyToMany(
    targetEntity = CatalogImpl.class,
    cascade      = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH }
  )
  protected List<Catalog> catalogs = new ArrayList<Catalog>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SiteImpl_Deactivated",
    order        = 4,
    gridOrder    = 4,
    group        = "SiteImpl_Site"
  )
  @Column(name = "DEACTIVATED")
  protected Boolean deactivated = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SiteImpl_Production_SandBox",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @JoinColumn(name = "PRODUCTION_SANDBOX_ID")
  @ManyToOne(targetEntity = SandBoxImpl.class)
  protected SandBox productionSandbox;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "SiteImpl_Site_Identifier_Type",
    order                = 2,
    gridOrder            = 2,
    group                = "SiteImpl_Site",
    prominent            = true,
    broadleafEnumeration = "org.broadleafcommerce.common.site.service.type.SiteResolutionType",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    requiredOverride     = RequiredOverride.REQUIRED
  )
  @Column(name = "SITE_IDENTIFIER_TYPE")
  protected String siteIdentifierType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "SiteImpl_Site_Identifier_Value",
    order            = 3,
    gridOrder        = 3,
    group            = "SiteImpl_Site",
    prominent        = true,
    requiredOverride = RequiredOverride.REQUIRED
  )
  @Column(name = "SITE_IDENTIFIER_VALUE")
  @Index(
    name        = "BLC_SITE_ID_VAL_INDEX",
    columnNames = { "SITE_IDENTIFIER_VALUE" }
  )
  protected String siteIdentifierValue;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   site  DOCUMENT ME!
   *
   * @throws  CloneNotSupportedException  DOCUMENT ME!
   * @throws  SecurityException           DOCUMENT ME!
   * @throws  NoSuchMethodException       DOCUMENT ME!
   */
  public void checkCloneable(Site site) throws CloneNotSupportedException, SecurityException, NoSuchMethodException {
    Method cloneMethod = site.getClass().getMethod("clone", new Class[] {});

    if (cloneMethod.getDeclaringClass().getName().startsWith("org.broadleafcommerce")
          && !site.getClass().getName().startsWith("org.broadleafcommerce")) {
      // subclass is not implementing the clone method
      throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#clone()
   */
  @Override public Site clone() {
    Site clone;

    try {
      clone = (Site) Class.forName(this.getClass().getName()).newInstance();

      try {
        checkCloneable(clone);
      } catch (CloneNotSupportedException e) {
        LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: "
          + clone.getClass().getName(), e);
      }

      clone.setId(id);
      clone.setName(name);
      clone.setDeactivated(isDeactivated());
      ((Status) clone).setArchived(getArchived());

      for (Catalog catalog : getCatalogs()) {
        Catalog cloneCatalog = new CatalogImpl();
        cloneCatalog.setId(catalog.getId());
        cloneCatalog.setName(catalog.getName());
        clone.getCatalogs().add(cloneCatalog);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return clone;
  } // end method clone

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#getArchived()
   */
  @Override public Character getArchived() {
    if (archiveStatus == null) {
      archiveStatus = new ArchiveStatus();
    }

    return archiveStatus.getArchived();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#getCatalogs()
   */
  @Override public List<Catalog> getCatalogs() {
    return catalogs;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#getProductionSandbox()
   */
  @Override public SandBox getProductionSandbox() {
    return productionSandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#getSiteIdentifierType()
   */
  @Override public String getSiteIdentifierType() {
    return siteIdentifierType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#getSiteIdentifierValue()
   */
  @Override public String getSiteIdentifierValue() {
    return siteIdentifierValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#getSiteResolutionType()
   */
  @Override public SiteResolutionType getSiteResolutionType() {
    return SiteResolutionType.getInstance(siteIdentifierType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#isActive()
   */
  @Override public boolean isActive() {
    if (LOG.isDebugEnabled()) {
      if (isDeactivated()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("site, " + id + ", inactive due to deactivated property");
        }
      }

      if ('Y' == getArchived()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("site, " + id + ", inactive due to archived status");
        }
      }
    }

    return !isDeactivated() && ('Y' != getArchived());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#isDeactivated()
   */
  @Override public boolean isDeactivated() {
    if (deactivated == null) {
      return false;
    } else {
      return deactivated;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#setArchived(java.lang.Character)
   */
  @Override public void setArchived(Character archived) {
    if (archiveStatus == null) {
      archiveStatus = new ArchiveStatus();
    }

    archiveStatus.setArchived(archived);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#setCatalogs(java.util.List)
   */
  @Override public void setCatalogs(List<Catalog> catalogs) {
    this.catalogs = catalogs;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#setDeactivated(boolean)
   */
  @Override public void setDeactivated(boolean deactivated) {
    this.deactivated = deactivated;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#setProductionSandbox(org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void setProductionSandbox(SandBox productionSandbox) {
    this.productionSandbox = productionSandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#setSiteIdentifierType(java.lang.String)
   */
  @Override public void setSiteIdentifierType(String siteIdentifierType) {
    this.siteIdentifierType = siteIdentifierType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#setSiteIdentifierValue(java.lang.String)
   */
  @Override public void setSiteIdentifierValue(String siteIdentifierValue) {
    this.siteIdentifierValue = siteIdentifierValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Site#setSiteResolutionType(org.broadleafcommerce.common.site.service.type.SiteResolutionType)
   */
  @Override public void setSiteResolutionType(SiteResolutionType siteResolutionType) {
    this.siteIdentifierType = siteResolutionType.getType();
  }
} // end class SiteImpl
