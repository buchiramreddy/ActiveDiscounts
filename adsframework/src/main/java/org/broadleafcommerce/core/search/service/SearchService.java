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

package org.broadleafcommerce.core.search.service;

import java.io.IOException;

import java.util.List;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;
import org.broadleafcommerce.core.search.domain.ProductSearchResult;
import org.broadleafcommerce.core.search.domain.SearchFacetDTO;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface SearchService {
  /**
   * This method delegates to {@link org.broadleafcommerce.core.search.service.solr.SolrIndexService#rebuildIndex()}. It
   * is here to preserve backwards-compatibility with sites that were originally configured to run Broadleaf with Solr
   * before 2.2.0.
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   * @throws  java.io.IOException
   */
  void rebuildIndex() throws ServiceException, IOException;

  /**
   * Performs a search for products in the given category, taking into consideration the ProductSearchCriteria.
   *
   * <p>This method will return products that are in any sub-level of a given category. For example, if you had a
   * "Routers" category and a "Enterprise Routers" sub-category, asking for products in "Routers", would return products
   * that are in the "Enterprise Routers" category.</p>
   *
   * @see     #findExplicitProductsByCategory(org.broadleafcommerce.core.catalog.domain.Category, org.broadleafcommerce.core.search.domain.ProductSearchCriteria)
   *
   * @param   category        DOCUMENT ME!
   * @param   searchCriteria  DOCUMENT ME!
   *
   * @return  the result of the search
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  ProductSearchResult findProductsByCategory(Category category, ProductSearchCriteria searchCriteria)
    throws ServiceException;

  /**
   * Performs a search for products in the given category, taking into consideration the ProductSearchCriteria.
   *
   * <p>This method will NOT return products that are in a sub-level of a given category. For example, if you had a
   * "Routers" category and a "Enterprise Routers" sub-category, asking for products in "Routers", would NOT return
   * products that are in the "Enterprise Routers" category.</p>
   *
   * @see     #findProductsByCategory(org.broadleafcommerce.core.catalog.domain.Category, org.broadleafcommerce.core.search.domain.ProductSearchCriteria)
   *
   * @param   category        DOCUMENT ME!
   * @param   searchCriteria  DOCUMENT ME!
   *
   * @return  performs a search for products in the given category, taking into consideration the ProductSearchCriteria.
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  ProductSearchResult findExplicitProductsByCategory(Category category, ProductSearchCriteria searchCriteria)
    throws ServiceException;

  /**
   * Performs a search for products across all categories for the given query, taking into consideration the
   * ProductSearchCriteria.
   *
   * @param   query           DOCUMENT ME!
   * @param   searchCriteria  DOCUMENT ME!
   *
   * @return  the result of the search
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  ProductSearchResult findProductsByQuery(String query, ProductSearchCriteria searchCriteria) throws ServiceException;

  /**
   * Performs a search for products in the given category for the given query, taking into consideration the
   * ProductSearchCriteria.
   *
   * @param   category        DOCUMENT ME!
   * @param   query           DOCUMENT ME!
   * @param   searchCriteria  DOCUMENT ME!
   *
   * @return  performs a search for products in the given category for the given query, taking into consideration the
   *          ProductSearchCriteria.
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  ProductSearchResult findProductsByCategoryAndQuery(Category category, String query,
    ProductSearchCriteria searchCriteria) throws ServiceException;

  /**
   * Gets all available facets for search results page.
   *
   * @return  the available facets
   */
  List<SearchFacetDTO> getSearchFacets();

  /**
   * Gets all available facets for a given category.
   *
   * @param   category  DOCUMENT ME!
   *
   * @return  the available facets
   */
  List<SearchFacetDTO> getCategoryFacets(Category category);

} // end interface SearchService
