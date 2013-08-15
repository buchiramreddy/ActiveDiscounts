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

package org.broadleafcommerce.profile.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ID_GENERATION")
public class IdGenerationImpl implements IdGeneration {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(
    name     = "BATCH_SIZE",
    nullable = false
  )
  protected Long batchSize;

  /** DOCUMENT ME! */
  @Column(
    name     = "BATCH_START",
    nullable = false
  )
  protected Long batchStart;

  /** DOCUMENT ME! */
  @Column(
    name     = "ID_MIN",
    nullable = true
  )
  protected Long begin;

  /** DOCUMENT ME! */
  @Column(
    name     = "ID_MAX",
    nullable = true
  )
  protected Long end;

  /** DOCUMENT ME! */
  @Column(
    name     = "ID_TYPE",
    nullable = false
  )
  @Id protected String type;

  /** DOCUMENT ME! */
  @Version protected Integer version;

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

    IdGenerationImpl other = (IdGenerationImpl) obj;

    if (batchSize == null) {
      if (other.batchSize != null) {
        return false;
      }
    } else if (!batchSize.equals(other.batchSize)) {
      return false;
    }

    if (batchStart == null) {
      if (other.batchStart != null) {
        return false;
      }
    } else if (!batchStart.equals(other.batchStart)) {
      return false;
    }

    if (begin == null) {
      if (other.begin != null) {
        return false;
      }
    } else if (!begin.equals(other.begin)) {
      return false;
    }

    if (end == null) {
      if (other.end != null) {
        return false;
      }
    } else if (!end.equals(other.end)) {
      return false;
    }

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    if (version == null) {
      if (other.version != null) {
        return false;
      }
    } else if (!version.equals(other.version)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#getBatchSize()
   */
  @Override public Long getBatchSize() {
    return batchSize;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#getBatchStart()
   */
  @Override public Long getBatchStart() {
    return batchStart;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#getBegin()
   */
  @Override public Long getBegin() {
    return begin;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#getEnd()
   */
  @Override public Long getEnd() {
    return end;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#getType()
   */
  @Override public String getType() {
    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#getVersion()
   */
  @Override public Integer getVersion() {
    return version;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((batchSize == null) ? 0 : batchSize.hashCode());
    result = (prime * result) + ((batchStart == null) ? 0 : batchStart.hashCode());
    result = (prime * result) + ((begin == null) ? 0 : begin.hashCode());
    result = (prime * result) + ((end == null) ? 0 : end.hashCode());
    result = (prime * result) + ((type == null) ? 0 : type.hashCode());
    result = (prime * result) + ((version == null) ? 0 : version.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#setBatchSize(java.lang.Long)
   */
  @Override public void setBatchSize(Long batchSize) {
    this.batchSize = batchSize;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#setBatchStart(java.lang.Long)
   */
  @Override public void setBatchStart(Long batchStart) {
    this.batchStart = batchStart;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#setBegin(java.lang.Long)
   */
  @Override public void setBegin(Long begin) {
    this.begin = begin;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#setEnd(java.lang.Long)
   */
  @Override public void setEnd(Long end) {
    this.end = end;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.IdGeneration#setType(java.lang.String)
   */
  @Override public void setType(String type) {
    this.type = type;
  }

} // end class IdGenerationImpl
