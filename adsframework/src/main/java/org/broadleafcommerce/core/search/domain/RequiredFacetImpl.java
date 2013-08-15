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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SEARCH_FACET_XREF")
public class RequiredFacetImpl implements RequiredFacet {
  /** DOCUMENT ME! */
  protected static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "ID")
  @GeneratedValue(generator = "RequiredFacetId")
  @GenericGenerator(
    name       = "RequiredFacetId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "RequiredFacetImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.search.domain.RequiredFacetImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @JoinColumn(name = "SEARCH_FACET_ID")
  @ManyToOne(targetEntity = SearchFacetImpl.class)
  protected SearchFacet searchFacet;

  /** DOCUMENT ME! */
  @JoinColumn(
    name                 = "REQUIRED_FACET_ID",
    referencedColumnName = "SEARCH_FACET_ID"
  )
  @ManyToOne(
    targetEntity = SearchFacetImpl.class,
    optional     = false
  )
  protected SearchFacet requiredFacet;

  /**
   * @see  org.broadleafcommerce.core.search.domain.RequiredFacet#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.RequiredFacet#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.RequiredFacet#getRequiredFacet()
   */
  @Override public SearchFacet getRequiredFacet() {
    return requiredFacet;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.RequiredFacet#setRequiredFacet(org.broadleafcommerce.core.search.domain.SearchFacet)
   */
  @Override public void setRequiredFacet(SearchFacet requiredFacet) {
    this.requiredFacet = requiredFacet;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.RequiredFacet#getSearchFacet()
   */
  @Override public SearchFacet getSearchFacet() {
    return searchFacet;
  }

  /**
   * @see  org.broadleafcommerce.core.search.domain.RequiredFacet#setSearchFacet(org.broadleafcommerce.core.search.domain.SearchFacet)
   */
  @Override public void setSearchFacet(SearchFacet searchFacet) {
    this.searchFacet = searchFacet;
  }
} // end class RequiredFacetImpl
