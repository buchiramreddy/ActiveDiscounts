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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.broadleafcommerce.cms.field.domain.FieldGroup;
import org.broadleafcommerce.cms.field.domain.FieldGroupImpl;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;

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
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "StructuredContentFieldTemplateImpl_baseStructuredContentFieldTemplate"
)
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blCMSElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SC_FLD_TMPLT")
public class StructuredContentFieldTemplateImpl implements StructuredContentFieldTemplate {
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
  @JoinTable(
    name               = "BLC_SC_FLDGRP_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "SC_FLD_TMPLT_ID",
        referencedColumnName = "SC_FLD_TMPLT_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "FLD_GROUP_ID",
        referencedColumnName = "FLD_GROUP_ID"
      )
  )
  @ManyToMany(
    targetEntity = FieldGroupImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @OrderColumn(name = "GROUP_ORDER")
  protected List<FieldGroup> fieldGroups;

  /** DOCUMENT ME! */
  @Column(name = "SC_FLD_TMPLT_ID")
  @GeneratedValue(generator = "StructuredContentFieldTemplateId")
  @GenericGenerator(
    name       = "StructuredContentFieldTemplateId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "StructuredContentFieldTemplateImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.structure.domain.StructuredContentFieldTemplateImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentFieldTemplateImpl_Field_Template_Name",
    order        = 1,
    gridOrder    = 2,
    group        = "StructuredContentFieldTemplateImpl_Details",
    prominent    = true
  )
  @Column(name = "NAME")
  protected String name;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentFieldTemplate#getFieldGroups()
   */
  @Override public List<FieldGroup> getFieldGroups() {
    return fieldGroups;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentFieldTemplate#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentFieldTemplate#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentFieldTemplate#setFieldGroups(java.util.List)
   */
  @Override public void setFieldGroups(List<FieldGroup> fieldGroups) {
    this.fieldGroups = fieldGroups;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentFieldTemplate#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContentFieldTemplate#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }
} // end class StructuredContentFieldTemplateImpl
