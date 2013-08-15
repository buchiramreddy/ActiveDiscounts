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

package org.broadleafcommerce.core.search.domain;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.broadleafcommerce.core.search.domain.solr.FieldType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FIELD")
public class FieldImpl implements Field, Serializable {
  /** DOCUMENT ME! */
  private static final long serialVersionUID = 2915813511754425605L;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FieldImpl_ID",
    group        = "FieldImpl_descrpition",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "FIELD_ID")
  @GeneratedValue(generator = "FieldId")
  @GenericGenerator(
    name       = "FieldId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FieldImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.search.domain.FieldImpl"
      )
    }
  )
  @Id protected Long id;

  // This is a broadleaf enumeration
  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FieldImpl_EntityType",
    group        = "FieldImpl_descrpition",
    order        = 2,
    prominent    = true
  )
  @Column(
    name     = "ENTITY_TYPE",
    nullable = false
  )
  @Index(
    name        = "ENTITY_TYPE_INDEX",
    columnNames = { "ENTITY_TYPE" }
  )
  protected String entityType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FieldImpl_propertyName",
    group        = "FieldImpl_descrpition",
    order        = 1,
    prominent    = true
  )
  @Column(
    name     = "PROPERTY_NAME",
    nullable = false
  )
  protected String propertyName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FieldImpl_abbreviation",
    group        = "FieldImpl_descrpition",
    order        = 3,
    prominent    = true
  )
  @Column(name = "ABBREVIATION")
  protected String abbreviation;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FieldImpl_searchable",
    group        = "FieldImpl_descrpition",
    order        = 4,
    prominent    = true
  )
  @Column(name = "SEARCHABLE")
  protected Boolean searchable = false;

  // This is a broadleaf enumeration
  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FieldImpl_facetFieldType",
    group        = "FieldImpl_descrpition",
    excluded     = true
  )
  @Column(name = "FACET_FIELD_TYPE")
  protected String facetFieldType;

  // This is a broadleaf enumeration
  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST })
  @CollectionTable(
    name        = "BLC_FIELD_SEARCH_TYPES",
    joinColumns = @JoinColumn(name = "FIELD_ID")
  )
  @Column(name = "SEARCHABLE_FIELD_TYPE")
  @ElementCollection protected List<String> searchableFieldTypes = new ArrayList<String>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FieldImpl_translatable",
    group        = "FieldImpl_description"
  )
  @Column(name = "TRANSLATABLE")
  protected Boolean translatable = false;

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getQualifiedFieldName()
   */
  @Override public String getQualifiedFieldName() {
    return getEntityType().getFriendlyType() + "." + propertyName;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getEntityType()
   */
  @Override public FieldEntity getEntityType() {
    return FieldEntity.getInstance(entityType);
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#setEntityType(org.broadleafcommerce.core.search.domain.FieldEntity)
   */
  @Override public void setEntityType(FieldEntity entityType) {
    this.entityType = entityType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getPropertyName()
   */
  @Override public String getPropertyName() {
    return propertyName;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#setPropertyName(java.lang.String)
   */
  @Override public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getAbbreviation()
   */
  @Override public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#setAbbreviation(java.lang.String)
   */
  @Override public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getSearchable()
   */
  @Override public Boolean getSearchable() {
    return searchable;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#setSearchable(java.lang.Boolean)
   */
  @Override public void setSearchable(Boolean searchable) {
    this.searchable = searchable;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getFacetFieldType()
   */
  @Override public FieldType getFacetFieldType() {
    return FieldType.getInstance(facetFieldType);
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#setFacetFieldType(org.broadleafcommerce.core.search.domain.solr.FieldType)
   */
  @Override public void setFacetFieldType(FieldType facetFieldType) {
    this.facetFieldType = (facetFieldType == null) ? null : facetFieldType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getSearchableFieldTypes()
   */
  @Override public List<FieldType> getSearchableFieldTypes() {
    List<FieldType> fieldTypes = new ArrayList<FieldType>();

    for (String fieldType : searchableFieldTypes) {
      fieldTypes.add(FieldType.getInstance(fieldType));
    }

    return fieldTypes;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#setSearchableFieldTypes(java.util.List)
   */
  @Override public void setSearchableFieldTypes(List<FieldType> searchableFieldTypes) {
    List<String> fieldTypes = new ArrayList<String>();

    for (FieldType fieldType : searchableFieldTypes) {
      fieldTypes.add(fieldType.getType());
    }

    this.searchableFieldTypes = fieldTypes;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getTranslatable()
   */
  @Override public Boolean getTranslatable() {
    return (translatable == null) ? false : translatable;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#setTranslatable(java.lang.Boolean)
   */
  @Override public void setTranslatable(Boolean translatable) {
    this.translatable = translatable;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#getSearchConfigs()
   */
  @Override public List<SearchConfig> getSearchConfigs() {
    throw new UnsupportedOperationException("The default Field implementation does not support search configs");
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.Field#setSearchConfigs(java.util.List)
   */
  @Override public void setSearchConfigs(List<SearchConfig> searchConfigs) {
    throw new UnsupportedOperationException("The default Field implementation does not support search configs");
  }

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

    Field other = (Field) obj;

    return getEntityType().getType().equals(other.getEntityType().getType())
      && getPropertyName().equals(other.getPropertyName());

  }
} // end class FieldImpl
