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

package org.broadleafcommerce.common.enumeration.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "DataDrivenEnumerationImpl_friendyName"
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_DATA_DRVN_ENUM_VAL")
public class DataDrivenEnumerationValueImpl implements DataDrivenEnumerationValue {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "DISPLAY")
  protected String display;

  /** DOCUMENT ME! */
  @Column(name = "HIDDEN")
  @Index(
    name        = "HIDDEN_INDEX",
    columnNames = { "HIDDEN" }
  )
  protected Boolean hidden = false;

  /** DOCUMENT ME! */
  @Column(name = "ENUM_VAL_ID")
  @GeneratedValue(generator = "DataDrivenEnumerationValueId")
  @GenericGenerator(
    name       = "DataDrivenEnumerationValueId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "DataDrivenEnumerationValueImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValueImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "ENUM_KEY")
  @Index(
    name        = "ENUM_VAL_KEY_INDEX",
    columnNames = { "ENUM_KEY" }
  )
  protected String key;

  /** DOCUMENT ME! */
  @JoinColumn(name = "ENUM_TYPE")
  @ManyToOne(targetEntity = DataDrivenEnumerationImpl.class)
  protected DataDrivenEnumeration type;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#getDisplay()
   */
  @Override public String getDisplay() {
    return display;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#getHidden()
   */
  @Override public Boolean getHidden() {
    if (hidden == null) {
      return Boolean.FALSE;
    } else {
      return hidden;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#getKey()
   */
  @Override public String getKey() {
    return key;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#getType()
   */
  @Override public DataDrivenEnumeration getType() {
    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#setDisplay(java.lang.String)
   */
  @Override public void setDisplay(String display) {
    this.display = display;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#setHidden(java.lang.Boolean)
   */
  @Override public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#setKey(java.lang.String)
   */
  @Override public void setKey(String key) {
    this.key = key;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue#setType(org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration)
   */
  @Override public void setType(DataDrivenEnumeration type) {
    this.type = type;
  }
} // end class DataDrivenEnumerationValueImpl
