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

package org.broadleafcommerce.core.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;
import org.broadleafcommerce.core.search.domain.SearchFacetDTO;
import org.broadleafcommerce.core.search.domain.SearchFacetResultDTO;


/**
 * Provides methods that facilitate interactions with SearchFacetDTOs and SearchFacetResultDTOs.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface SearchFacetDTOService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Given a servlet request and a list of available facets for this request (could be search or category based), this
   * method will build out a ProductSearchCriteria object to be used by the ProductSearchService. It will perform
   * translations from query string parameters to the ProductSearchCriteria.
   *
   * @param   request          DOCUMENT ME!
   * @param   availableFacets  DOCUMENT ME!
   *
   * @return  the ProductSearchCriteria
   */
  ProductSearchCriteria buildSearchCriteria(HttpServletRequest request, List<SearchFacetDTO> availableFacets);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the url abbreviation associated with a given SearchFacetResultDTO.
   *
   * @param   result  DOCUMENT ME!
   *
   * @return  the key associated with a SearchFacetResultDTO
   */
  String getUrlKey(SearchFacetResultDTO result);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the value of the given SearchFacetResultDTO. The default Broadleaf implementation will return the String value
   * of the result if the value is not empty, or "range[<min-value>:<max-value>]" if the value was empty.
   *
   * @param   result  DOCUMENT ME!
   *
   * @return  the value of the SearchFacetResultDTO
   */
  String getValue(SearchFacetResultDTO result);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns whether or not the SearchFacetResultDTO's key/value pair is present in the servlet request.
   *
   * @param   result   DOCUMENT ME!
   * @param   request  DOCUMENT ME!
   *
   * @return  if the result is active
   */
  boolean isActive(SearchFacetResultDTO result, HttpServletRequest request);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the "active" boolean on a given SearchFacetResultDTO as determined by the current request.
   *
   * @param  facets   DOCUMENT ME!
   * @param  request  DOCUMENT ME!
   */
  void setActiveFacetResults(List<SearchFacetDTO> facets, HttpServletRequest request);


} // end interface SearchFacetDTOService
