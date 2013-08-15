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

package org.broadleafcommerce.common.sandbox.domain;

import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.site.domain.Site;
import org.broadleafcommerce.common.site.domain.SiteImpl;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blSandBoxElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SANDBOX")
public class SandBoxImpl implements SandBox {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log  LOG              = LogFactory.getLog(SandBoxImpl.class);
  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "AUTHOR")
  protected Long author;

  /** DOCUMENT ME! */
  @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
  @Column(name = "SANDBOX_ID")
  @GeneratedValue(generator = "SandBoxId")
  @GenericGenerator(
    name       = "SandBoxId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SandBoxImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.common.sandbox.domain.SandBoxImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "SANDBOX_NAME")
  @Index(
    name        = "SANDBOX_NAME_INDEX",
    columnNames = { "SANDBOX_NAME" }
  )
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "SandBoxImpl_SandBox_Type",
    group                = "SandBoxImpl_Description",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.common.sandbox.domain.SandBoxType"
  )
  @Column(name = "SANDBOX_TYPE")
  protected String sandboxType;

  /** DOCUMENT ME! */
  @JoinTable(
    name               = "BLC_SITE_SANDBOX",
    joinColumns        = @JoinColumn(name = "SANDBOX_ID"),
    inverseJoinColumns = @JoinColumn(name = "SITE_ID")
  )
  @ManyToOne(targetEntity = SiteImpl.class)
  protected Site site;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox  DOCUMENT ME!
   *
   * @throws  CloneNotSupportedException  DOCUMENT ME!
   * @throws  SecurityException           DOCUMENT ME!
   * @throws  NoSuchMethodException       DOCUMENT ME!
   */
  public void checkCloneable(SandBox sandBox) throws CloneNotSupportedException, SecurityException,
    NoSuchMethodException {
    Method cloneMethod = sandBox.getClass().getMethod("clone", new Class[] {});

    if (cloneMethod.getDeclaringClass().getName().startsWith("org.broadleafcommerce")
          && !sandBox.getClass().getName().startsWith("org.broadleafcommerce")) {
      // subclass is not implementing the clone method
      throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.sandbox.domain.SandBox#clone()
   */
  @Override public SandBox clone() {
    SandBox clone;

    try {
      clone = (SandBox) Class.forName(this.getClass().getName()).newInstance();

      try {
        checkCloneable(clone);
      } catch (CloneNotSupportedException e) {
        LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: "
          + clone.getClass().getName(), e);
      }

      clone.setId(id);
      clone.setName(name);
      clone.setAuthor(author);
      clone.setSandBoxType(getSandBoxType());

      if (site != null) {
        clone.setSite(site.clone());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return clone;
  } // end method clone

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    SandBoxImpl other = (SandBoxImpl) obj;

    if (author == null) {
      if (other.author != null) {
        return false;
      }
    } else if (!author.equals(other.author)) {
      return false;
    }

    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.sandbox.domain.SandBox#getAuthor()
   */
  @Override public Long getAuthor() {
    return author;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.openadmin.domain.SandBox#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.openadmin.domain.SandBox#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.sandbox.domain.SandBox#getSandBoxType()
   */
  @Override public SandBoxType getSandBoxType() {
    return SandBoxType.getInstance(sandboxType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.sandbox.domain.SandBox#getSite()
   */
  @Override public Site getSite() {
    return site;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((author == null) ? 0 : author.hashCode());
    result = (prime * result) + ((id == null) ? 0 : id.hashCode());
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.sandbox.domain.SandBox#setAuthor(java.lang.Long)
   */
  @Override public void setAuthor(Long author) {
    this.author = author;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.openadmin.domain.SandBox#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.openadmin.domain.SandBox#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.sandbox.domain.SandBox#setSandBoxType(org.broadleafcommerce.common.sandbox.domain.SandBoxType)
   */
  @Override public void setSandBoxType(final SandBoxType sandboxType) {
    if (sandboxType != null) {
      this.sandboxType = sandboxType.getType();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.sandbox.domain.SandBox#setSite(org.broadleafcommerce.common.site.domain.Site)
   */
  @Override public void setSite(Site site) {
    this.site = site;
  }
} // end class SandBoxImpl
