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

import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * Created by bpolster.
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
@Table(name = "BLC_FLD_DEF")
public class FieldDefinitionImpl implements FieldDefinition {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "COLUMN_WIDTH")
  protected String columnWidth;

  /** DOCUMENT ME! */
  @JoinColumn(name = "FLD_ENUM_ID")
  @ManyToOne(targetEntity = FieldEnumerationImpl.class)
  protected FieldEnumeration fieldEnumeration;

  /** DOCUMENT ME! */
  @Column(name = "FLD_TYPE")
  protected String fieldType;

  /** DOCUMENT ME! */
  @Column(name = "FRIENDLY_NAME")
  protected String friendlyName;

  /** DOCUMENT ME! */
  @Column(name = "FLD_DEF_ID")
  @GeneratedValue(generator = "FieldDefinitionId")
  @GenericGenerator(
    name       = "FieldDefinitionId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FieldDefinitionImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.field.domain.FieldDefinitionImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "NAME")
  protected String name;

  /** DOCUMENT ME! */
  @Column(name = "ALLOW_MULTIPLES")
  protected Boolean allowMultiples = false;

  /** DOCUMENT ME! */
  @JoinColumn(name = "FLD_GROUP_ID")
  @ManyToOne(targetEntity = FieldGroupImpl.class)
  protected FieldGroup fieldGroup;

  /** DOCUMENT ME! */
  @Column(name = "FLD_ORDER")
  protected int fieldOrder;

  /** DOCUMENT ME! */
  @Column(name = "HIDDEN_FLAG")
  protected Boolean hiddenFlag = false;

  /** DOCUMENT ME! */
  @Column(name = "MAX_LENGTH")
  protected Integer maxLength;

  /** DOCUMENT ME! */
  @Column(name = "SECURITY_LEVEL")
  protected String securityLevel;

  /** DOCUMENT ME! */
  @Column(name = "TEXT_AREA_FLAG")
  protected Boolean textAreaFlag = false;

  /** DOCUMENT ME! */
  @Column(name = "VLDTN_ERROR_MSSG_KEY")
  protected String validationErrorMesageKey;

  /** DOCUMENT ME! */
  @Column(name = "VLDTN_REGEX")
  protected String validationRegEx;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getAllowMultiples()
   */
  @Override public Boolean getAllowMultiples() {
    return allowMultiples;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getColumnWidth()
   */
  @Override public String getColumnWidth() {
    return columnWidth;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getFieldEnumeration()
   */
  @Override public FieldEnumeration getFieldEnumeration() {
    return fieldEnumeration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getFieldGroup()
   */
  @Override public FieldGroup getFieldGroup() {
    return fieldGroup;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getFieldOrder()
   */
  @Override public int getFieldOrder() {
    return fieldOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getFieldType()
   */
  @Override public SupportedFieldType getFieldType() {
    return (fieldType != null) ? SupportedFieldType.valueOf(fieldType) : null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getFriendlyName()
   */
  @Override public String getFriendlyName() {
    return friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getHiddenFlag()
   */
  @Override public Boolean getHiddenFlag() {
    return hiddenFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getMaxLength()
   */
  @Override public Integer getMaxLength() {
    return maxLength;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getSecurityLevel()
   */
  @Override public String getSecurityLevel() {
    return securityLevel;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getTextAreaFlag()
   */
  @Override public Boolean getTextAreaFlag() {
    return textAreaFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getValidationErrorMesageKey()
   */
  @Override public String getValidationErrorMesageKey() {
    return validationErrorMesageKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#getValidationRegEx()
   */
  @Override public String getValidationRegEx() {
    return validationRegEx;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setAllowMultiples(java.lang.Boolean)
   */
  @Override public void setAllowMultiples(Boolean allowMultiples) {
    this.allowMultiples = allowMultiples;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setColumnWidth(java.lang.String)
   */
  @Override public void setColumnWidth(String columnWidth) {
    this.columnWidth = columnWidth;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setFieldEnumeration(org.broadleafcommerce.cms.field.domain.FieldEnumeration)
   */
  @Override public void setFieldEnumeration(FieldEnumeration fieldEnumeration) {
    this.fieldEnumeration = fieldEnumeration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setFieldGroup(org.broadleafcommerce.cms.field.domain.FieldGroup)
   */
  @Override public void setFieldGroup(FieldGroup fieldGroup) {
    this.fieldGroup = fieldGroup;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setFieldOrder(int)
   */
  @Override public void setFieldOrder(int fieldOrder) {
    this.fieldOrder = fieldOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setFieldType(org.broadleafcommerce.common.presentation.client.SupportedFieldType)
   */
  @Override public void setFieldType(SupportedFieldType fieldType) {
    this.fieldType = (fieldType != null) ? fieldType.toString() : null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setFriendlyName(java.lang.String)
   */
  @Override public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setHiddenFlag(java.lang.Boolean)
   */
  @Override public void setHiddenFlag(Boolean hiddenFlag) {
    this.hiddenFlag = hiddenFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setMaxLength(java.lang.Integer)
   */
  @Override public void setMaxLength(Integer maxLength) {
    this.maxLength = maxLength;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setSecurityLevel(java.lang.String)
   */
  @Override public void setSecurityLevel(String securityLevel) {
    this.securityLevel = securityLevel;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setTextAreaFlag(java.lang.Boolean)
   */
  @Override public void setTextAreaFlag(Boolean textAreaFlag) {
    this.textAreaFlag = textAreaFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setValidationErrorMesageKey(java.lang.String)
   */
  @Override public void setValidationErrorMesageKey(String validationErrorMesageKey) {
    this.validationErrorMesageKey = validationErrorMesageKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldDefinition#setValidationRegEx(java.lang.String)
   */
  @Override public void setValidationRegEx(String validationRegEx) {
    this.validationRegEx = validationRegEx;
  }
} // end class FieldDefinitionImpl
