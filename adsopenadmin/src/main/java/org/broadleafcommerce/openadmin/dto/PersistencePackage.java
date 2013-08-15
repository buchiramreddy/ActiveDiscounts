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

package org.broadleafcommerce.openadmin.dto;

import java.io.Serializable;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PersistencePackage implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Integer batchId;

  /** DOCUMENT ME! */
  protected String                 ceilingEntityFullyQualifiedClassname;

  /** DOCUMENT ME! */
  protected String                 csrfToken;

  /** DOCUMENT ME! */
  protected String[]               customCriteria;

  /** DOCUMENT ME! */
  protected Entity                 entity;

  /** DOCUMENT ME! */
  protected String                 fetchTypeFullyQualifiedClassname;

  /** DOCUMENT ME! */
  protected PersistencePerspective persistencePerspective;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new PersistencePackage object.
   */
  public PersistencePackage() {
    // do nothing
  }

  /**
   * Creates a new PersistencePackage object.
   *
   * @param  ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param  entity                                DOCUMENT ME!
   * @param  persistencePerspective                DOCUMENT ME!
   * @param  customCriteria                        DOCUMENT ME!
   * @param  csrfToken                             DOCUMENT ME!
   */
  public PersistencePackage(String ceilingEntityFullyQualifiedClassname, Entity entity,
    PersistencePerspective persistencePerspective, String[] customCriteria, String csrfToken) {
    this(ceilingEntityFullyQualifiedClassname, null, entity, persistencePerspective, customCriteria, csrfToken);
  }

  /**
   * Creates a new PersistencePackage object.
   *
   * @param  ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param  fetchTypeFullyQualifiedClassname      DOCUMENT ME!
   * @param  entity                                DOCUMENT ME!
   * @param  persistencePerspective                DOCUMENT ME!
   * @param  customCriteria                        DOCUMENT ME!
   * @param  csrfToken                             DOCUMENT ME!
   */
  public PersistencePackage(String ceilingEntityFullyQualifiedClassname, String fetchTypeFullyQualifiedClassname,
    Entity entity, PersistencePerspective persistencePerspective, String[] customCriteria, String csrfToken) {
    this.ceilingEntityFullyQualifiedClassname = ceilingEntityFullyQualifiedClassname;
    this.fetchTypeFullyQualifiedClassname     = fetchTypeFullyQualifiedClassname;
    this.persistencePerspective               = persistencePerspective;
    this.entity                               = entity;
    this.customCriteria                       = customCriteria;
    this.csrfToken                            = csrfToken;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof PersistencePackage)) {
      return false;
    }

    PersistencePackage that = (PersistencePackage) o;

    if (batchId != that.batchId) {
      return false;
    }

    if ((ceilingEntityFullyQualifiedClassname != null)
          ? (!ceilingEntityFullyQualifiedClassname.equals(that.ceilingEntityFullyQualifiedClassname))
          : (that.ceilingEntityFullyQualifiedClassname != null)) {
      return false;
    }

    if (!Arrays.equals(customCriteria, that.customCriteria)) {
      return false;
    }

    if ((entity != null) ? (!entity.equals(that.entity)) : (that.entity != null)) {
      return false;
    }

    if ((fetchTypeFullyQualifiedClassname != null)
          ? (!fetchTypeFullyQualifiedClassname.equals(that.fetchTypeFullyQualifiedClassname))
          : (that.fetchTypeFullyQualifiedClassname != null)) {
      return false;
    }

    if ((persistencePerspective != null) ? (!persistencePerspective.equals(that.persistencePerspective))
                                         : (that.persistencePerspective != null)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getBatchId() {
    return batchId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCeilingEntityFullyQualifiedClassname() {
    return ceilingEntityFullyQualifiedClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCsrfToken() {
    return csrfToken;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[] getCustomCriteria() {
    return customCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Entity getEntity() {
    return entity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFetchTypeFullyQualifiedClassname() {
    return fetchTypeFullyQualifiedClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePerspective getPersistencePerspective() {
    return persistencePerspective;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (ceilingEntityFullyQualifiedClassname != null) ? ceilingEntityFullyQualifiedClassname.hashCode() : 0;
    result = (31 * result)
      + ((fetchTypeFullyQualifiedClassname != null) ? fetchTypeFullyQualifiedClassname.hashCode() : 0);
    result = (31 * result) + ((persistencePerspective != null) ? persistencePerspective.hashCode() : 0);
    result = (31 * result) + ((customCriteria != null) ? Arrays.hashCode(customCriteria) : 0);
    result = (31 * result) + ((entity != null) ? entity.hashCode() : 0);
    result = (31 * result) + batchId;

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  batchId  DOCUMENT ME!
   */
  public void setBatchId(Integer batchId) {
    this.batchId = batchId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   */
  public void setCeilingEntityFullyQualifiedClassname(
    String ceilingEntityFullyQualifiedClassname) {
    this.ceilingEntityFullyQualifiedClassname = ceilingEntityFullyQualifiedClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  csrfToken  DOCUMENT ME!
   */
  public void setCsrfToken(String csrfToken) {
    this.csrfToken = csrfToken;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customCriteria  DOCUMENT ME!
   */
  public void setCustomCriteria(String[] customCriteria) {
    this.customCriteria = customCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entity  DOCUMENT ME!
   */
  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fetchTypeFullyQualifiedClassname  DOCUMENT ME!
   */
  public void setFetchTypeFullyQualifiedClassname(String fetchTypeFullyQualifiedClassname) {
    this.fetchTypeFullyQualifiedClassname = fetchTypeFullyQualifiedClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  persistencePerspective  DOCUMENT ME!
   */
  public void setPersistencePerspective(
    PersistencePerspective persistencePerspective) {
    this.persistencePerspective = persistencePerspective;
  }
} // end class PersistencePackage
