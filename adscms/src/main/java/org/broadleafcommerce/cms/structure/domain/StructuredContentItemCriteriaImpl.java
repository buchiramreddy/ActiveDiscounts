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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "StructuredContentItemCriteriaImpl_baseStructuredContentItemCriteria")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SC_ITEM_CRITERIA")
public class StructuredContentItemCriteriaImpl implements StructuredContentItemCriteria {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentItemCriteriaImpl_Item_Criteria_Id",
    group        = "StructuredContentItemCriteriaImpl_Description",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "SC_ITEM_CRITERIA_ID")
  @GeneratedValue(generator = "SCItemCriteriaId")
  @GenericGenerator(
    name       = "SCItemCriteriaId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "StructuredContentItemCriteriaImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.page.domain.StructuredContentItemCriteriaImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentItemCriteriaImpl_Order_Item_Match_Rule",
    group        = "StructuredContentItemCriteriaImpl_Description",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(
    name   = "ORDER_ITEM_MATCH_RULE",
    length = Integer.MAX_VALUE - 1
  )
  @Lob
  @Type(type = "org.hibernate.type.StringClobType")
  protected String      orderItemMatchRule;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentItemCriteriaImpl_Quantity",
    group        = "StructuredContentItemCriteriaImpl_Description",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(
    name     = "QUANTITY",
    nullable = false
  )
  protected Integer quantity;

  /** DOCUMENT ME! */
  @JoinTable(
    name               = "BLC_QUAL_CRIT_SC_XREF",
    joinColumns        = @JoinColumn(name = "SC_ITEM_CRITERIA_ID"),
    inverseJoinColumns = @JoinColumn(name = "SC_ID")
  )
  @ManyToOne(targetEntity = StructuredContentImpl.class)
  protected StructuredContent structuredContent;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentItemCriteria#cloneEntity()
   */
  @Override public StructuredContentItemCriteria cloneEntity() {
    StructuredContentItemCriteriaImpl newField = new StructuredContentItemCriteriaImpl();
    newField.quantity           = quantity;
    newField.orderItemMatchRule = orderItemMatchRule;

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

    StructuredContentItemCriteriaImpl other = (StructuredContentItemCriteriaImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (orderItemMatchRule == null) {
      if (other.orderItemMatchRule != null) {
        return false;
      }
    } else if (!orderItemMatchRule.equals(other.orderItemMatchRule)) {
      return false;
    }

    if (quantity == null) {
      if (other.quantity != null) {
        return false;
      }
    } else if (!quantity.equals(other.quantity)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.offer.domain.StructuredContentItemCriteria#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.rule.QuantityBasedRule#getMatchRule()
   */
  @Override public String getMatchRule() {
    return orderItemMatchRule;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.offer.domain.StructuredContentItemCriteria#getReceiveQuantity()
   */
  @Override public Integer getQuantity() {
    return quantity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentItemCriteria#getStructuredContent()
   */
  @Override public StructuredContent getStructuredContent() {
    return structuredContent;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((id == null) ? 0 : id.hashCode());
    result = (prime * result) + ((orderItemMatchRule == null) ? 0 : orderItemMatchRule.hashCode());
    result = (prime * result) + ((quantity == null) ? 0 : quantity.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.offer.domain.StructuredContentItemCriteria#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.rule.QuantityBasedRule#setMatchRule(java.lang.String)
   */
  @Override public void setMatchRule(String matchRule) {
    this.orderItemMatchRule = matchRule;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.offer.domain.StructuredContentItemCriteria#setReceiveQuantity(java.lang.Integer)
   */
  @Override public void setQuantity(Integer receiveQuantity) {
    this.quantity = receiveQuantity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentItemCriteria#setStructuredContent(org.broadleafcommerce.cms.structure.domain.StructuredContent)
   */
  @Override public void setStructuredContent(StructuredContent structuredContent) {
    this.structuredContent = structuredContent;
  }

} // end class StructuredContentItemCriteriaImpl
