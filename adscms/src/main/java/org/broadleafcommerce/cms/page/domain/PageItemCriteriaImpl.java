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

package org.broadleafcommerce.cms.page.domain;

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
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "PageItemCriteriaImpl_basePageItemCriteria")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PAGE_ITEM_CRITERIA")
public class PageItemCriteriaImpl implements PageItemCriteria {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageItemCriteriaImpl_Item_Criteria_Id",
    group        = "PageItemCriteriaImpl_Description",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "PAGE_ITEM_CRITERIA_ID")
  @GeneratedValue(generator = "PageItemCriteriaId")
  @GenericGenerator(
    name       = "PageItemCriteriaId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "PageItemCriteriaImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.page.domain.PageItemCriteriaImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageItemCriteriaImpl_Order_Item_Match_Rule",
    group        = "PageItemCriteriaImpl_Description",
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
  @JoinTable(
    name               = "BLC_QUAL_CRIT_PAGE_XREF",
    joinColumns        = @JoinColumn(name = "PAGE_ITEM_CRITERIA_ID"),
    inverseJoinColumns = @JoinColumn(name = "PAGE_ID")
  )
  @ManyToOne(targetEntity = PageImpl.class)
  protected Page page;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageItemCriteriaImpl_Quantity",
    group        = "PageItemCriteriaImpl_Description",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(
    name     = "QUANTITY",
    nullable = false
  )
  protected Integer quantity;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageItemCriteria#cloneEntity()
   */
  @Override public PageItemCriteria cloneEntity() {
    PageItemCriteriaImpl newField = new PageItemCriteriaImpl();
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

    PageItemCriteriaImpl other = (PageItemCriteriaImpl) obj;

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

  /**
   * @see  org.broadleafcommerce.common.rule.QuantityBasedRule#getId()
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

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageItemCriteria#getPage()
   */
  @Override public Page getPage() {
    return page;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.rule.QuantityBasedRule#getQuantity()
   */
  @Override public Integer getQuantity() {
    return quantity;
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

  /**
   * @see  org.broadleafcommerce.common.rule.QuantityBasedRule#setId(java.lang.Long)
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

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageItemCriteria#setPage(org.broadleafcommerce.cms.page.domain.Page)
   */
  @Override public void setPage(Page page) {
    this.page = page;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.rule.QuantityBasedRule#setQuantity(java.lang.Integer)
   */
  @Override public void setQuantity(Integer receiveQuantity) {
    this.quantity = receiveQuantity;
  }

} // end class PageItemCriteriaImpl
