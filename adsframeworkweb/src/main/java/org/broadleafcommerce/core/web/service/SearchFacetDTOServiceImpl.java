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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;
import org.broadleafcommerce.core.search.domain.SearchFacetDTO;
import org.broadleafcommerce.core.search.domain.SearchFacetResultDTO;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blSearchFacetDTOService")
public class SearchFacetDTOServiceImpl implements SearchFacetDTOService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Value("${web.defaultPageSize}")
  protected Integer defaultPageSize;

  /** DOCUMENT ME! */
  @Value("${web.maxPageSize}")
  protected Integer maxPageSize;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.service.SearchFacetDTOService#buildSearchCriteria(javax.servlet.http.HttpServletRequest,
   *       java.util.List)
   */
  @Override
  @SuppressWarnings("unchecked")
  public ProductSearchCriteria buildSearchCriteria(HttpServletRequest request, List<SearchFacetDTO> availableFacets) {
    ProductSearchCriteria searchCriteria = new ProductSearchCriteria();
    searchCriteria.setPageSize(defaultPageSize);

    Map<String, String[]> facets = new HashMap<String, String[]>();

    for (Iterator<Entry<String, String[]>> iter = request.getParameterMap().entrySet().iterator(); iter.hasNext();) {
      Entry<String, String[]> entry = iter.next();
      String                  key   = entry.getKey();

      if (key.equals(ProductSearchCriteria.SORT_STRING)) {
        searchCriteria.setSortQuery(StringUtils.join(entry.getValue(), ","));
      } else if (key.equals(ProductSearchCriteria.PAGE_NUMBER)) {
        searchCriteria.setPage(Integer.parseInt(entry.getValue()[0]));
      } else if (key.equals(ProductSearchCriteria.PAGE_SIZE_STRING)) {
        int requestedPageSize = Integer.parseInt(entry.getValue()[0]);

        if (maxPageSize == null) {
          maxPageSize = requestedPageSize;
        }

        searchCriteria.setPageSize(Math.min(requestedPageSize, maxPageSize));
      } else if (key.equals(ProductSearchCriteria.QUERY_STRING)) {
        continue; // This is handled by the controller
      } else {
        facets.put(key, entry.getValue());
      }
    }

    searchCriteria.setFilterCriteria(facets);

    return searchCriteria;
  } // end method buildSearchCriteria

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.service.SearchFacetDTOService#getUrlKey(org.broadleafcommerce.core.search.domain.SearchFacetResultDTO)
   */
  @Override public String getUrlKey(SearchFacetResultDTO result) {
    return result.getFacet().getField().getAbbreviation();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.service.SearchFacetDTOService#getValue(org.broadleafcommerce.core.search.domain.SearchFacetResultDTO)
   */
  @Override public String getValue(SearchFacetResultDTO result) {
    return result.getValueKey();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.service.SearchFacetDTOService#isActive(org.broadleafcommerce.core.search.domain.SearchFacetResultDTO,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean isActive(SearchFacetResultDTO result, HttpServletRequest request) {
    Map<String, String[]> params = request.getParameterMap();

    for (Entry<String, String[]> entry : params.entrySet()) {
      String key = entry.getKey();

      if (key.equals(getUrlKey(result))) {
        for (String val : entry.getValue()) {
          if (val.equals(getValue(result))) {
            return true;
          }
        }
      }
    }

    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.service.SearchFacetDTOService#setActiveFacetResults(java.util.List, javax.servlet.http.HttpServletRequest)
   */
  @Override public void setActiveFacetResults(List<SearchFacetDTO> facets, HttpServletRequest request) {
    if (facets != null) {
      for (SearchFacetDTO facet : facets) {
        for (SearchFacetResultDTO facetResult : facet.getFacetValues()) {
          facetResult.setActive(isActive(facetResult, request));
        }
      }
    }
  }

} // end class SearchFacetDTOServiceImpl
