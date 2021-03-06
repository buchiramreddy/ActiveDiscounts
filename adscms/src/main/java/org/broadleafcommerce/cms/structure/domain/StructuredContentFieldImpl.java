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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;
import org.broadleafcommerce.openadmin.audit.AdminAuditableListener;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Entity
@EntityListeners(value = { AdminAuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SC_FLD")
public class StructuredContentFieldImpl implements StructuredContentField {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Embedded protected AdminAuditable auditable = new AdminAuditable();

  /** DOCUMENT ME! */
  @Column(name = "FLD_KEY")
  protected String fieldKey;

  /** DOCUMENT ME! */
  @Column(name = "SC_FLD_ID")
  @GeneratedValue(generator = "StructuredContentFieldId")
  @GenericGenerator(
    name       = "StructuredContentFieldId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "StructuredContentFieldImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.structure.domain.StructuredContentFieldImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(
    name   = "LOB_VALUE",
    length = Integer.MAX_VALUE - 1
  )
  @Lob
  @Type(type = "org.hibernate.type.StringClobType")
  protected String      lobValue;

  /** DOCUMENT ME! */
  @Column(name = "VALUE")
  protected String stringValue;

  /** DOCUMENT ME! */
  @JoinColumn(name = "SC_ID")
  @ManyToOne(targetEntity = StructuredContentImpl.class)
  protected StructuredContent structuredContent;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#cloneEntity()
   */
  @Override public StructuredContentField cloneEntity() {
    StructuredContentFieldImpl newContentField = new StructuredContentFieldImpl();
    newContentField.fieldKey          = fieldKey;
    newContentField.structuredContent = structuredContent;
    newContentField.lobValue          = lobValue;
    newContentField.stringValue       = stringValue;

    return newContentField;

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#getAuditable()
   */
  @Override public AdminAuditable getAuditable() {
    return auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#getFieldKey()
   */
  @Override public String getFieldKey() {
    return fieldKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#getStructuredContent()
   */
  @Override public StructuredContent getStructuredContent() {
    return structuredContent;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#getValue()
   */
  @Override public String getValue() {
    if ((stringValue != null) && (stringValue.length() > 0)) {
      return stringValue;
    } else {
      return lobValue;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#setAuditable(org.broadleafcommerce.openadmin.audit.AdminAuditable)
   */
  @Override public void setAuditable(AdminAuditable auditable) {
    this.auditable = auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#setFieldKey(java.lang.String)
   */
  @Override public void setFieldKey(String fieldKey) {
    this.fieldKey = fieldKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#setStructuredContent(org.broadleafcommerce.cms.structure.domain.StructuredContent)
   */
  @Override public void setStructuredContent(StructuredContent structuredContent) {
    this.structuredContent = structuredContent;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentField#setValue(java.lang.String)
   */
  @Override public void setValue(String value) {
    if (value != null) {
      if (value.length() <= 256) {
        stringValue = value;
        lobValue    = null;
      } else {
        stringValue = null;
        lobValue    = value;
      }
    } else {
      lobValue    = null;
      stringValue = null;
    }
  }
} // end class StructuredContentFieldImpl
