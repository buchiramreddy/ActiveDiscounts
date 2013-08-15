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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.i18n.service.DynamicTranslationProvider;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationAdornedTargetCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
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
@Table(name = "BLC_SEARCH_FACET")
public class SearchFacetImpl implements SearchFacet, Serializable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchFacetImpl_ID",
    order        = 1,
    group        = "SearchFacetImpl_description",
    groupOrder   = 1,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "SEARCH_FACET_ID")
  @GeneratedValue(generator = "SearchFacetId")
  @GenericGenerator(
    name       = "SearchFacetId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SearchFacetImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.search.domain.SearchFacetImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchFacetImpl_field",
    order        = 2,
    group        = "SearchFacetImpl_description",
    prominent    = true,
    gridOrder    = 1
  )
  @AdminPresentationToOneLookup(lookupDisplayProperty = "propertyName")
  @JoinColumn(name = "FIELD_ID")
  @ManyToOne(
    optional     = false,
    targetEntity = FieldImpl.class
  )
  protected Field field;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchFacetImpl_label",
    order        = 3,
    group        = "SearchFacetImpl_description",
    groupOrder   = 1,
    prominent    = true,
    translatable = true,
    gridOrder    = 2
  )
  @Column(name = "LABEL")
  protected String label;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchFacetImpl_showOnSearch",
    order        = 4,
    group        = "SearchFacetImpl_description",
    groupOrder   = 1,
    prominent    = false
  )
  @Column(name = "SHOW_ON_SEARCH")
  protected Boolean showOnSearch = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchFacetImpl_searchPriority",
    order        = 5,
    group        = "SearchFacetImpl_description",
    groupOrder   = 1,
    prominent    = true
  )
  @Column(name = "SEARCH_DISPLAY_PRIORITY")
  protected Integer searchDisplayPriority = 1;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchFacetImpl_multiselect",
    order        = 6,
    group        = "SearchFacetImpl_description",
    groupOrder   = 1
  )
  @Column(name = "MULTISELECT")
  protected Boolean canMultiselect = true;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    addType      = AddMethodType.PERSIST,
    friendlyName = "newRangeTitle"
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "searchFacet",
    targetEntity = SearchFacetRangeImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<SearchFacetRange> searchFacetRanges = new ArrayList<SearchFacetRange>();

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    targetObjectProperty = "requiredFacet",
    friendlyName         = "requiredFacetTitle",
    gridVisibleFields    = { "label", "searchDisplayPriority", "canMultiselect", "requiresAllDependentFacets" }
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "searchFacet",
    targetEntity = RequiredFacetImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<RequiredFacet> requiredFacets = new ArrayList<RequiredFacet>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchFacetImpl_requiresAllDependentFacets",
    order        = 6,
    group        = "SearchFacetImpl_description",
    groupOrder   = 1
  )
  @Column(name = "REQUIRES_ALL_DEPENDENT")
  protected Boolean requiresAllDependentFacets = false;

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#getField()
   */
  @Override public Field getField() {
    return field;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#setField(org.broadleafcommerce.core.search.domain.Field)
   */
  @Override public void setField(Field field) {
    this.field = field;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#getLabel()
   */
  @Override public String getLabel() {
    return DynamicTranslationProvider.getValue(this, "label", label);
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#setLabel(java.lang.String)
   */
  @Override public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#getShowOnSearch()
   */
  @Override public Boolean getShowOnSearch() {
    return showOnSearch;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#setShowOnSearch(java.lang.Boolean)
   */
  @Override public void setShowOnSearch(Boolean showOnSearch) {
    this.showOnSearch = showOnSearch;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#getSearchDisplayPriority()
   */
  @Override public Integer getSearchDisplayPriority() {
    return searchDisplayPriority;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#setSearchDisplayPriority(java.lang.Integer)
   */
  @Override public void setSearchDisplayPriority(Integer searchDisplayPriority) {
    this.searchDisplayPriority = searchDisplayPriority;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#getCanMultiselect()
   */
  @Override public Boolean getCanMultiselect() {
    return canMultiselect;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#setCanMultiselect(java.lang.Boolean)
   */
  @Override public void setCanMultiselect(Boolean canMultiselect) {
    this.canMultiselect = canMultiselect;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#getRequiredFacets()
   */
  @Override public List<RequiredFacet> getRequiredFacets() {
    return requiredFacets;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#setRequiredFacets(java.util.List)
   */
  @Override public void setRequiredFacets(List<RequiredFacet> requiredFacets) {
    this.requiredFacets = requiredFacets;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#getRequiresAllDependentFacets()
   */
  @Override public Boolean getRequiresAllDependentFacets() {
    return (requiresAllDependentFacets == null) ? false : requiresAllDependentFacets;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#setRequiresAllDependentFacets(java.lang.Boolean)
   */
  @Override public void setRequiresAllDependentFacets(Boolean requiresAllDependentFacets) {
    this.requiresAllDependentFacets = requiresAllDependentFacets;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#getSearchFacetRanges()
   */
  @Override public List<SearchFacetRange> getSearchFacetRanges() {
    return searchFacetRanges;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacet#setSearchFacetRanges(java.util.List)
   */
  @Override public void setSearchFacetRanges(List<SearchFacetRange> searchFacetRanges) {
    this.searchFacetRanges = searchFacetRanges;
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

    SearchFacet other = (SearchFacet) obj;

    return getField().equals(other.getField());
  }

} // end class SearchFacetImpl
