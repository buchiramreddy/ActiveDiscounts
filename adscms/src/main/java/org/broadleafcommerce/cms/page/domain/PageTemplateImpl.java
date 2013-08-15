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
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.broadleafcommerce.cms.field.domain.FieldGroup;
import org.broadleafcommerce.cms.field.domain.FieldGroupImpl;

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.locale.domain.LocaleImpl;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

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
  friendlyName        = "PageTemplateImpl_basePageTemplate"
)
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blCMSElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PAGE_TMPLT")
public class PageTemplateImpl implements PageTemplate, AdminMainEntity {
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
    name               = "BLC_PGTMPLT_FLDGRP_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "PAGE_TMPLT_ID",
        referencedColumnName = "PAGE_TMPLT_ID"
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
  @AdminPresentation(
    friendlyName = "PageTemplateImpl_Template_Id",
    visibility   = VisibilityEnum.HIDDEN_ALL,
    readOnly     = true
  )
  @Column(name = "PAGE_TMPLT_ID")
  @GeneratedValue(generator = "PageTemplateId")
  @GenericGenerator(
    name       = "PageTemplateId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "PageTemplateImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.page.domain.PageTemplateImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "LOCALE_CODE")
  @ManyToOne(targetEntity = LocaleImpl.class)
  protected Locale locale;

  /** DOCUMENT ME! */
  @Column(name = "TMPLT_DESCR")
  protected String templateDescription;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageTemplateImpl_Template_Name",
    prominent    = true,
    gridOrder    = 1
  )
  @Column(name = "TMPLT_NAME")
  protected String templateName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageTemplateImpl_Template_Path",
    visibility   = VisibilityEnum.HIDDEN_ALL,
    readOnly     = true
  )
  @Column(name = "TMPLT_PATH")
  protected String templatePath;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#getFieldGroups()
   */
  @Override public List<FieldGroup> getFieldGroups() {
    return fieldGroups;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#getLocale()
   */
  @Override public Locale getLocale() {
    return locale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.admin.domain.AdminMainEntity#getMainEntityName()
   */
  @Override public String getMainEntityName() {
    return getTemplateName();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#getTemplateDescription()
   */
  @Override public String getTemplateDescription() {
    return templateDescription;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#getTemplateName()
   */
  @Override public String getTemplateName() {
    return templateName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#getTemplatePath()
   */
  @Override public String getTemplatePath() {
    return templatePath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#setFieldGroups(java.util.List)
   */
  @Override public void setFieldGroups(List<FieldGroup> fieldGroups) {
    this.fieldGroups = fieldGroups;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#setLocale(org.broadleafcommerce.common.locale.domain.Locale)
   */
  @Override public void setLocale(Locale locale) {
    this.locale = locale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#setTemplateDescription(java.lang.String)
   */
  @Override public void setTemplateDescription(String templateDescription) {
    this.templateDescription = templateDescription;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#setTemplateName(java.lang.String)
   */
  @Override public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.PageTemplate#setTemplatePath(java.lang.String)
   */
  @Override public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }
} // end class PageTemplateImpl
