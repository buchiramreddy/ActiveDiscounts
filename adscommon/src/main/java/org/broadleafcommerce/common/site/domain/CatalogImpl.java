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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.presentation.AdminPresentation;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CATALOG")
public class CatalogImpl implements Catalog {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(CatalogImpl.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "CATALOG_ID")
  @GeneratedValue(generator = "CatalogId")
  @GenericGenerator(
    name       = "CatalogId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CatalogImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.common.site.domain.CatalogImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "Catalog_Name",
    order        = 1,
    prominent    = true
  )
  @Column(name = "NAME")
  protected String name;

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @JoinTable(
    name               = "BLC_SITE_CATALOG",
    joinColumns        = @JoinColumn(name = "CATALOG_ID"),
    inverseJoinColumns = @JoinColumn(name = "SITE_ID")
  )
  @ManyToMany(targetEntity = SiteImpl.class)
  protected List<Site> sites = new ArrayList<Site>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   catalog  DOCUMENT ME!
   *
   * @throws  CloneNotSupportedException  DOCUMENT ME!
   * @throws  SecurityException           DOCUMENT ME!
   * @throws  NoSuchMethodException       DOCUMENT ME!
   */
  public void checkCloneable(Catalog catalog) throws CloneNotSupportedException, SecurityException,
    NoSuchMethodException {
    Method cloneMethod = catalog.getClass().getMethod("clone", new Class[] {});

    if (cloneMethod.getDeclaringClass().getName().startsWith("org.broadleafcommerce")
          && !catalog.getClass().getName().startsWith("org.broadleafcommerce")) {
      // subclass is not implementing the clone method
      throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public Catalog clone() {
    Catalog clone;

    try {
      clone = (Catalog) Class.forName(this.getClass().getName()).newInstance();

      try {
        checkCloneable(clone);
      } catch (CloneNotSupportedException e) {
        LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: "
          + clone.getClass().getName(), e);
      }

      clone.setId(id);
      clone.setName(name);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return clone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Catalog#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Catalog#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Catalog#getSites()
   */
  @Override public List<Site> getSites() {
    return sites;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Catalog#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Catalog#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Catalog#setSites(java.util.List)
   */
  @Override public void setSites(List<Site> sites) {
    this.sites = sites;
  }

} // end class CatalogImpl
