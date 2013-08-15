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

package org.broadleafcommerce.cms.structure.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The purpose of this entity is to simply enforce the not null constraints on the two columns in this join table.
 * Otherwise, during DDL creation, Hibernate was creating incompatible SQL for MS SQLServer.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_QUAL_CRIT_SC_XREF")
public class CriteriaStructuredContentXref {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** The category id. */
  @EmbeddedId CriteriaStructuredContentXrefPK criteriaStructuredContentXrefPK = new CriteriaStructuredContentXrefPK();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public CriteriaStructuredContentXrefPK getCriteriaStructuredContentXrefPK() {
    return criteriaStructuredContentXrefPK;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  criteriaStructuredContentXrefPK  DOCUMENT ME!
   */
  public void setCriteriaStructuredContentXrefPK(
    final CriteriaStructuredContentXrefPK criteriaStructuredContentXrefPK) {
    this.criteriaStructuredContentXrefPK = criteriaStructuredContentXrefPK;
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  public static class CriteriaStructuredContentXrefPK implements Serializable {
    //~ Static fields/initializers -------------------------------------------------------------------------------------

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    //~ Instance fields ------------------------------------------------------------------------------------------------

    @JoinColumn(name = "SC_ID")
    @ManyToOne(
      targetEntity = StructuredContentImpl.class,
      optional     = false
    )
    protected StructuredContent structuredContent = new StructuredContentImpl();

    @JoinColumn(name = "SC_ITEM_CRITERIA_ID")
    @ManyToOne(
      targetEntity = StructuredContentItemCriteriaImpl.class,
      optional     = false
    )
    protected StructuredContentItemCriteria structuredContentItemCriteria = new StructuredContentItemCriteriaImpl();

    //~ Methods --------------------------------------------------------------------------------------------------------

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

      CriteriaStructuredContentXrefPK other = (CriteriaStructuredContentXrefPK) obj;

      if (structuredContent == null) {
        if (other.structuredContent != null) {
          return false;
        }
      } else if (!structuredContent.equals(other.structuredContent)) {
        return false;
      }

      if (structuredContentItemCriteria == null) {
        if (other.structuredContentItemCriteria != null) {
          return false;
        }
      } else if (!structuredContentItemCriteria.equals(other.structuredContentItemCriteria)) {
        return false;
      }

      return true;
    } // end method equals

    //~ ----------------------------------------------------------------------------------------------------------------

    public StructuredContent getStructuredContent() {
      return structuredContent;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public StructuredContentItemCriteria getStructuredContentItemCriteria() {
      return structuredContentItemCriteria;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public int hashCode() {
      final int prime  = 31;
      int       result = 1;
      result = (prime * result) + ((structuredContent == null) ? 0 : structuredContent.hashCode());
      result = (prime * result)
        + ((structuredContentItemCriteria == null) ? 0 : structuredContentItemCriteria.hashCode());

      return result;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setStructuredContent(StructuredContent structuredContent) {
      this.structuredContent = structuredContent;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setStructuredContentItemCriteria(StructuredContentItemCriteria structuredContentItemCriteria) {
      this.structuredContentItemCriteria = structuredContentItemCriteria;
    }

  } // end class CriteriaStructuredContentXrefPK
} // end class CriteriaStructuredContentXref
