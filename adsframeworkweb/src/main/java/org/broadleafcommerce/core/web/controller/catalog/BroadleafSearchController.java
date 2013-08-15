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

package org.broadleafcommerce.core.web.controller.catalog;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.security.service.ExploitProtectionService;
import org.broadleafcommerce.common.util.UrlUtil;

import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;
import org.broadleafcommerce.core.search.domain.ProductSearchResult;
import org.broadleafcommerce.core.search.domain.SearchFacetDTO;
import org.broadleafcommerce.core.search.redirect.domain.SearchRedirect;
import org.broadleafcommerce.core.search.redirect.service.SearchRedirectService;
import org.broadleafcommerce.core.search.service.SearchService;
import org.broadleafcommerce.core.web.service.SearchFacetDTOService;
import org.broadleafcommerce.core.web.util.ProcessorUtils;

import org.springframework.ui.Model;


/**
 * Handles searching the catalog for a given search term. Will apply product search criteria such as filters, sorts, and
 * pagination if applicable
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class BroadleafSearchController extends AbstractCatalogController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static String searchView = "catalog/search";

  /** DOCUMENT ME! */
  protected static String PRODUCTS_ATTRIBUTE_NAME              = "products";

  /** DOCUMENT ME! */
  protected static String FACETS_ATTRIBUTE_NAME                = "facets";

  /** DOCUMENT ME! */
  protected static String PRODUCT_SEARCH_RESULT_ATTRIBUTE_NAME = "result";

  /** DOCUMENT ME! */
  protected static String ACTIVE_FACETS_ATTRIBUTE_NAME  = "activeFacets";

  /** DOCUMENT ME! */
  protected static String ORIGINAL_QUERY_ATTRIBUTE_NAME = "originalQuery";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blExploitProtectionService")
  protected ExploitProtectionService exploitProtectionService;

  /** DOCUMENT ME! */
  @Resource(name = "blSearchFacetDTOService")
  protected SearchFacetDTOService facetService;

  /** DOCUMENT ME! */
  @Resource(name = "blSearchService")
  protected SearchService       searchService;
  @Resource(name = "blSearchRedirectService")
  private SearchRedirectService searchRedirectService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSearchView() {
    return searchView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   model     DOCUMENT ME!
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   query     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServletException  DOCUMENT ME!
   * @throws  IOException       DOCUMENT ME!
   * @throws  ServiceException  DOCUMENT ME!
   */
  public String search(Model model, HttpServletRequest request, HttpServletResponse response, String query)
    throws ServletException, IOException, ServiceException {
    try {
      if (StringUtils.isNotEmpty(query)) {
        query = StringUtils.trim(query);
        query = exploitProtectionService.cleanString(query);
      }
    } catch (ServiceException e) {
      query = null;
    }

    if ((query == null) || (query.length() == 0)) {
      return "redirect:/";
    }

    if (request.getParameterMap().containsKey("facetField")) {
      // If we receive a facetField parameter, we need to convert the field to the
      // product search criteria expected format. This is used in multi-facet selection. We
      // will send a redirect to the appropriate URL to maintain canonical URLs

      String                fieldName          = request.getParameter("facetField");
      List<String>          activeFieldFilters = new ArrayList<String>();
      Map<String, String[]> parameters         = new HashMap<String, String[]>(request.getParameterMap());

      for (Iterator<Entry<String, String[]>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
        Entry<String, String[]> entry = iter.next();
        String                  key   = entry.getKey();

        if (key.startsWith(fieldName + "-")) {
          activeFieldFilters.add(key.substring(key.indexOf('-') + 1));
          iter.remove();
        }
      }

      parameters.remove(ProductSearchCriteria.PAGE_NUMBER);
      parameters.put(fieldName, activeFieldFilters.toArray(new String[activeFieldFilters.size()]));
      parameters.remove("facetField");

      String newUrl = ProcessorUtils.getUrl(request.getRequestURL().toString(), parameters);

      return "redirect:" + newUrl;
    } else {
      // Else, if we received a GET to the category URL (either the user performed a search or we redirected
      // from the POST method, we can actually process the results
      SearchRedirect handler = searchRedirectService.findSearchRedirectBySearchTerm(query);

      if (handler != null) {
        String contextPath = request.getContextPath();
        String url         = UrlUtil.fixRedirectUrl(contextPath, handler.getUrl());
        response.sendRedirect(url);

        return null;
      }

      if (StringUtils.isNotEmpty(query)) {
        List<SearchFacetDTO>  availableFacets = searchService.getSearchFacets();
        ProductSearchCriteria searchCriteria  = facetService.buildSearchCriteria(request, availableFacets);
        ProductSearchResult   result          = searchService.findProductsByQuery(query, searchCriteria);

        facetService.setActiveFacetResults(result.getFacets(), request);

        model.addAttribute(PRODUCTS_ATTRIBUTE_NAME, result.getProducts());
        model.addAttribute(FACETS_ATTRIBUTE_NAME, result.getFacets());
        model.addAttribute(PRODUCT_SEARCH_RESULT_ATTRIBUTE_NAME, result);
        model.addAttribute(ORIGINAL_QUERY_ATTRIBUTE_NAME, query);
      }

    } // end if-else

    return getSearchView();
  } // end method search

} // end class BroadleafSearchController
