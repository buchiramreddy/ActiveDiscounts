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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
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
@Table(name = "BLC_FLD_GROUP")
public class FieldGroupImpl implements FieldGroup {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @BatchSize(size = 20)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blCMSElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "fieldGroup",
    targetEntity = FieldDefinitionImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @OrderColumn(name = "FLD_ORDER")
  protected List<FieldDefinition> fieldDefinitions;

  /** DOCUMENT ME! */
  @Column(name = "FLD_GROUP_ID")
  @GeneratedValue(generator = "FieldGroupId")
  @GenericGenerator(
    name       = "FieldGroupId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FieldGroupImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.field.domain.FieldGroupImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "NAME")
  protected String name;

  /** DOCUMENT ME! */
  @Column(name = "INIT_COLLAPSED_FLAG")
  protected Boolean initCollapsedFlag = false;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldGroup#getFieldDefinitions()
   */
  @Override public List<FieldDefinition> getFieldDefinitions() {
    return fieldDefinitions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldGroup#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldGroup#getInitCollapsedFlag()
   */
  @Override public Boolean getInitCollapsedFlag() {
    return initCollapsedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldGroup#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldGroup#setFieldDefinitions(java.util.List)
   */
  @Override public void setFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
    this.fieldDefinitions = fieldDefinitions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldGroup#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldGroup#setInitCollapsedFlag(java.lang.Boolean)
   */
  @Override public void setInitCollapsedFlag(Boolean initCollapsedFlag) {
    this.initCollapsedFlag = initCollapsedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.field.domain.FieldGroup#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

} // end class FieldGroupImpl
