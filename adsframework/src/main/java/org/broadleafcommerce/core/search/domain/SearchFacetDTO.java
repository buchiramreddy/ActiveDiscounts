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

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class SearchFacetDTO {
  /** DOCUMENT ME! */
  protected SearchFacet                facet;

  /** DOCUMENT ME! */
  protected boolean                    showQuantity;

  /** DOCUMENT ME! */
  protected List<SearchFacetResultDTO> facetValues = new ArrayList<SearchFacetResultDTO>();

  /** DOCUMENT ME! */
  protected boolean                    active;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SearchFacet getFacet() {
    return facet;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  facet  DOCUMENT ME!
   */
  public void setFacet(SearchFacet facet) {
    this.facet = facet;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isShowQuantity() {
    return showQuantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  showQuantity  DOCUMENT ME!
   */
  public void setShowQuantity(boolean showQuantity) {
    this.showQuantity = showQuantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<SearchFacetResultDTO> getFacetValues() {
    return facetValues;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  facetValues  DOCUMENT ME!
   */
  public void setFacetValues(List<SearchFacetResultDTO> facetValues) {
    this.facetValues = facetValues;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isActive() {
    return active;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  active  DOCUMENT ME!
   */
  public void setActive(boolean active) {
    this.active = active;
  }

} // end class SearchFacetDTO
