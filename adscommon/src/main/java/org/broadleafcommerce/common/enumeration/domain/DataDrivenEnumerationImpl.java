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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
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
 * @author   $author$
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
@Table(name = "BLC_DATA_DRVN_ENUM")
public class DataDrivenEnumerationImpl implements DataDrivenEnumeration {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "ENUM_ID")
  @GeneratedValue(generator = "DataDrivenEnumerationId")
  @GenericGenerator(
    name       = "DataDrivenEnumerationId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "DataDrivenEnumerationImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "ENUM_KEY")
  @Index(
    name        = "ENUM_KEY_INDEX",
    columnNames = { "KEY" }
  )
  protected String key;

  /** DOCUMENT ME! */
  @Column(name = "MODIFIABLE")
  protected Boolean modifiable = false;

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @OneToMany(
    mappedBy     = "type",
    targetEntity = DataDrivenEnumerationValueImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<DataDrivenEnumerationValue> orderItems = new ArrayList<DataDrivenEnumerationValue>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration#getKey()
   */
  @Override public String getKey() {
    return key;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration#getModifiable()
   */
  @Override public Boolean getModifiable() {
    if (modifiable == null) {
      return Boolean.FALSE;
    } else {
      return modifiable;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration#getOrderItems()
   */
  @Override public List<DataDrivenEnumerationValue> getOrderItems() {
    return orderItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration#setKey(java.lang.String)
   */
  @Override public void setKey(String key) {
    this.key = key;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration#setModifiable(java.lang.Boolean)
   */
  @Override public void setModifiable(Boolean modifiable) {
    this.modifiable = modifiable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration#setOrderItems(java.util.List)
   */
  @Override public void setOrderItems(List<DataDrivenEnumerationValue> orderItems) {
    this.orderItems = orderItems;
  }
} // end class DataDrivenEnumerationImpl
