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

package org.broadleafcommerce.core.util.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Deprecated @Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CODE_TYPES")
public class CodeTypeImpl implements CodeType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(
    name     = "CODE_TYPE",
    nullable = false
  )
  protected String codeType;

  /** DOCUMENT ME! */
  @Column(name = "CODE_DESC")
  protected String description;

  /** DOCUMENT ME! */
  @Column(name = "CODE_ID")
  @GeneratedValue(
    generator = "CodeTypeId",
    strategy  = GenerationType.TABLE
  )
  @GenericGenerator(
    name       = "CodeTypeId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CodeTypeImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.util.domain.CodeTypeImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(
    name     = "CODE_KEY",
    nullable = false
  )
  protected String key;

  /** DOCUMENT ME! */
  @Column(name = "MODIFIABLE")
  protected Character modifiable;

  //~ Methods ----------------------------------------------------------------------------------------------------------

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

    CodeTypeImpl other = (CodeTypeImpl) obj;

    if (codeType == null) {
      if (other.codeType != null) {
        return false;
      }
    } else if (!codeType.equals(other.codeType)) {
      return false;
    }

    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }

    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }

    if (key == null) {
      if (other.key != null) {
        return false;
      }
    } else if (!key.equals(other.key)) {
      return false;
    }

    if (modifiable == null) {
      if (other.modifiable != null) {
        return false;
      }
    } else if (!modifiable.equals(other.modifiable)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#getCodeType()
   */
  @Override public String getCodeType() {
    return codeType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#getDescription()
   */
  @Override public String getDescription() {
    return description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#getKey()
   */
  @Override public String getKey() {
    return key;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#getModifiable()
   */
  @Override public Boolean getModifiable() {
    return isModifiable();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result)
      + ((codeType == null) ? 0 : codeType.hashCode());
    result = (prime * result)
      + ((description == null) ? 0 : description.hashCode());
    result = (prime * result) + ((id == null) ? 0 : id.hashCode());
    result = (prime * result) + ((key == null) ? 0 : key.hashCode());
    result = (prime * result)
      + ((modifiable == null) ? 0 : modifiable.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#isModifiable()
   */
  @Override public Boolean isModifiable() {
    if (modifiable == null) {
      return null;
    }

    return (modifiable == 'Y') ? Boolean.TRUE : Boolean.FALSE;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#setCodeType(java.lang.String)
   */
  @Override public void setCodeType(String codeType) {
    this.codeType = codeType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#setKey(java.lang.String)
   */
  @Override public void setKey(String key) {
    this.key = key;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.domain.CodeType#setModifiable(java.lang.Boolean)
   */
  @Override public void setModifiable(Boolean modifiable) {
    if (modifiable == null) {
      this.modifiable = null;
    } else {
      this.modifiable = modifiable ? 'Y' : 'N';
    }
  }


} // end class CodeTypeImpl
