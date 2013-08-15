/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.i18n.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "baseProduct"
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

// multi-column indexes don't appear to get exported correctly when declared at the field level, so declaring here as a workaround
@Table(
  appliesTo = "BLC_TRANSLATION",
  indexes   = {
    @Index(
      name  = "TRANSLATION_INDEX",
      columnNames = { "ENTITY_TYPE", "ENTITY_ID", "FIELD_NAME", "LOCALE_CODE" }
    )
  }
)
@javax.persistence.Table(name = "BLC_TRANSLATION")
public class TranslationImpl implements Serializable, Translation {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = -122818474469147685L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "ENTITY_ID")
  protected String entityId;

  /** DOCUMENT ME! */
  @Column(name = "ENTITY_TYPE")
  protected String entityType;

  /** DOCUMENT ME! */
  @Column(name = "FIELD_NAME")
  protected String fieldName;

  /** DOCUMENT ME! */
  @Column(name = "TRANSLATION_ID")
  @GeneratedValue(generator = "TranslationId")
  @GenericGenerator(
    name       = "TranslationId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "TranslationImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.common.i18n.domain.TranslationImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "LOCALE_CODE")
  protected String localeCode;

  /** DOCUMENT ME! */
  @Column(
    name   = "TRANSLATED_VALUE",
    length = Integer.MAX_VALUE - 1
  )
  @Lob
  @Type(type = "org.hibernate.type.StringClobType")
  protected String translatedValue;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#getEntityId()
   */
  @Override public String getEntityId() {
    return entityId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************ */
  /* CUSTOM GETTERS / SETTERS */
  /* ************************ */

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#getEntityType()
   */
  @Override public TranslatedEntity getEntityType() {
    return TranslatedEntity.getInstanceFromFriendlyType(entityType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#getFieldName()
   */
  @Override public String getFieldName() {
    return fieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************** */
  /* STANDARD GETTERS / SETTERS */
  /* ************************** */

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#getLocaleCode()
   */
  @Override public String getLocaleCode() {
    return localeCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#getTranslatedValue()
   */
  @Override public String getTranslatedValue() {
    return translatedValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#setEntityId(java.lang.String)
   */
  @Override public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#setEntityType(org.broadleafcommerce.common.i18n.domain.TranslatedEntity)
   */
  @Override public void setEntityType(TranslatedEntity entityType) {
    this.entityType = entityType.getFriendlyType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#setFieldName(java.lang.String)
   */
  @Override public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#setLocaleCode(java.lang.String)
   */
  @Override public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.domain.Translation#setTranslatedValue(java.lang.String)
   */
  @Override public void setTranslatedValue(String translatedValue) {
    this.translatedValue = translatedValue;
  }

} // end class TranslationImpl
