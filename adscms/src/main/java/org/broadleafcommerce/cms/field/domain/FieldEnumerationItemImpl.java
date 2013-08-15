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

package org.broadleafcommerce.cms.field.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * Created by jfischer.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blCMSElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FLD_ENUM_ITEM")
public class FieldEnumerationItemImpl implements FieldEnumerationItem {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @JoinColumn(name = "FLD_ENUM_ID")
  @ManyToOne(targetEntity = FieldEnumerationImpl.class)
  protected FieldEnumeration fieldEnumeration;

  /** DOCUMENT ME! */
  @Column(name = "FLD_ORDER")
  protected int fieldOrder;

  /** DOCUMENT ME! */
  @Column(name = "FRIENDLY_NAME")
  protected String friendlyName;

  /** DOCUMENT ME! */
  @Column(name = "FLD_ENUM_ITEM_ID")
  @GeneratedValue(generator = "FieldEnumerationItemId")
  @GenericGenerator(
    name       = "FieldEnumerationItemId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FieldEnumerationItemImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.field.domain.FieldEnumerationItemImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "NAME")
  protected String name;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#getFieldEnumeration()
   */
  @Override public FieldEnumeration getFieldEnumeration() {
    return fieldEnumeration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#getFieldOrder()
   */
  @Override public int getFieldOrder() {
    return fieldOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#getFriendlyName()
   */
  @Override public String getFriendlyName() {
    return friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#setFieldEnumeration(org.broadleafcommerce.cms.field.domain.FieldEnumeration)
   */
  @Override public void setFieldEnumeration(FieldEnumeration fieldEnumeration) {
    this.fieldEnumeration = fieldEnumeration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#setFieldOrder(int)
   */
  @Override public void setFieldOrder(int fieldOrder) {
    this.fieldOrder = fieldOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#setFriendlyName(java.lang.String)
   */
  @Override public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldEnumerationItem#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }
} // end class FieldEnumerationItemImpl
