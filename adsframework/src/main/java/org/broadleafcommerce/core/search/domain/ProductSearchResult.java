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

import java.util.List;

import org.broadleafcommerce.core.catalog.domain.Product;


/**
 * Container that holds the result of a ProductSearch.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class ProductSearchResult {
  /** DOCUMENT ME! */
  protected List<Product>        products;

  /** DOCUMENT ME! */
  protected List<SearchFacetDTO> facets;

  /** DOCUMENT ME! */
  protected Integer totalResults;

  /** DOCUMENT ME! */
  protected Integer page;

  /** DOCUMENT ME! */
  protected Integer pageSize;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<Product> getProducts() {
    return products;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  products  DOCUMENT ME!
   */
  public void setProducts(List<Product> products) {
    this.products = products;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<SearchFacetDTO> getFacets() {
    return facets;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  facets  DOCUMENT ME!
   */
  public void setFacets(List<SearchFacetDTO> facets) {
    this.facets = facets;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getTotalResults() {
    return totalResults;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  totalResults  DOCUMENT ME!
   */
  public void setTotalResults(Integer totalResults) {
    this.totalResults = totalResults;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getPage() {
    return page;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  page  DOCUMENT ME!
   */
  public void setPage(Integer page) {
    this.page = page;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  pageSize  DOCUMENT ME!
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getStartResult() {
    return ((products == null) || (products.size() == 0)) ? 0 : (((page - 1) * pageSize) + 1);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getEndResult() {
    return Math.min(page * pageSize, totalResults);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getTotalPages() {
    return ((products == null) || (products.size() == 0)) ? 1 : (int) Math.ceil(totalResults * 1.0 / pageSize);
  }

} // end class ProductSearchResult
