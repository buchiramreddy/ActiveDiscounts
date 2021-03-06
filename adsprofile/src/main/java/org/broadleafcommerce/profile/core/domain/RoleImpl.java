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
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.time.domain.TemporalTimestampListener;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ROLE")
public class RoleImpl implements Role {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "ROLE_ID")
  @GeneratedValue(generator = "RoleId")
  @GenericGenerator(
    name       = "RoleId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "RoleImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.profile.core.domain.RoleImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(
    name     = "ROLE_NAME",
    nullable = false
  )
  @Index(
    name        = "ROLE_NAME_INDEX",
    columnNames = { "ROLE_NAME" }
  )
  protected String roleName;

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Role#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Role#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Role#getRoleName()
   */
  @Override public String getRoleName() {
    return roleName;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Role#setRoleName(java.lang.String)
   */
  @Override public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((roleName == null) ? 0 : roleName.hashCode());

    return result;
  }

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

    RoleImpl other = (RoleImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (roleName == null) {
      if (other.roleName != null) {
        return false;
      }
    } else if (!roleName.equals(other.roleName)) {
      return false;
    }

    return true;
  } // end method equals

} // end class RoleImpl
