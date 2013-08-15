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

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.presentation.override.AdminPresentationOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationOverrides;

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
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@AdminPresentationOverrides(
  {
    @AdminPresentationOverride(
      name = "priceList.friendlyName",
      value =
        @AdminPresentation(
          excluded     = false,
          friendlyName = "PriceListImpl_Friendly_Name",
          order        = 1,
          group        = "SearchFacetRangeImpl_Description",
          prominent    = true,
          visibility   = VisibilityEnum.FORM_HIDDEN
        )
    )
  }
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SEARCH_FACET_RANGE")
public class SearchFacetRangeImpl implements SearchFacetRange, Serializable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "SEARCH_FACET_RANGE_ID")
  @GeneratedValue(generator = "SearchFacetRangeId")
  @GenericGenerator(
    name       = "SearchFacetRangeId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SearchFacetRangeImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.search.domain.SearchFacetRangeImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    excluded   = true,
    visibility = VisibilityEnum.HIDDEN_ALL
  )
  @Index(
    name        = "SEARCH_FACET_INDEX",
    columnNames = { "SEARCH_FACET_ID" }
  )
  @JoinColumn(name = "SEARCH_FACET_ID")
  @ManyToOne(targetEntity = SearchFacetImpl.class)
  protected SearchFacet searchFacet = new SearchFacetImpl();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchFacetRangeImpl_minValue",
    order        = 2,
    group        = "SearchFacetRangeImpl_Description",
    prominent    = true
  )
  @Column(
    name      = "MIN_VALUE",
    precision = 19,
    scale     = 5,
    nullable  = false
  )
  protected BigDecimal minValue;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchFacetRangeImpl_maxValue",
    order        = 3,
    group        = "SearchFacetRangeImpl_Description",
    prominent    = true
  )
  @Column(
    name      = "MAX_VALUE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal maxValue;

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacetRange#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacetRange#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacetRange#getSearchFacet()
   */
  @Override public SearchFacet getSearchFacet() {
    return searchFacet;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacetRange#setSearchFacet(org.broadleafcommerce.core.search.domain.SearchFacet)
   */
  @Override public void setSearchFacet(SearchFacet searchFacet) {
    this.searchFacet = searchFacet;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacetRange#getMinValue()
   */
  @Override public BigDecimal getMinValue() {
    return minValue;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacetRange#setMinValue(java.math.BigDecimal)
   */
  @Override public void setMinValue(BigDecimal minValue) {
    this.minValue = minValue;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacetRange#getMaxValue()
   */
  @Override public BigDecimal getMaxValue() {
    return maxValue;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.SearchFacetRange#setMaxValue(java.math.BigDecimal)
   */
  @Override public void setMaxValue(BigDecimal maxValue) {
    this.maxValue = maxValue;
  }

} // end class SearchFacetRangeImpl
