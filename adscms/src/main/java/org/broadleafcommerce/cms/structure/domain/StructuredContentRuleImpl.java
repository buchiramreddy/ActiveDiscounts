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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SC_RULE")
public class StructuredContentRuleImpl implements StructuredContentRule {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "SC_RULE_ID")
  @GeneratedValue(generator = "SCRuleId")
  @GenericGenerator(
    name       = "SCRuleId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "StructuredContentRuleImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.offer.domain.StructuredContentRuleImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(
    name   = "MATCH_RULE",
    length = Integer.MAX_VALUE - 1
  )
  @Lob
  @Type(type = "org.hibernate.type.StringClobType")
  protected String matchRule;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentRule#cloneEntity()
   */
  @Override public StructuredContentRule cloneEntity() {
    StructuredContentRuleImpl newField = new StructuredContentRuleImpl();
    newField.matchRule = matchRule;

    return newField;
  }

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

    StructuredContentRuleImpl other = (StructuredContentRuleImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (matchRule == null) {
      if (other.matchRule != null) {
        return false;
      }
    } else if (!matchRule.equals(other.matchRule)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.offer.domain.StructuredContentRule#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.offer.domain.StructuredContentRule#getMatchRule()
   */
  @Override public String getMatchRule() {
    return matchRule;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((id == null) ? 0 : id.hashCode());
    result = (prime * result) + ((matchRule == null) ? 0 : matchRule.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.offer.domain.StructuredContentRule#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.offer.domain.StructuredContentRule#setMatchRule(java.lang.String)
   */
  @Override public void setMatchRule(String matchRule) {
    this.matchRule = matchRule;
  }
} // end class StructuredContentRuleImpl
