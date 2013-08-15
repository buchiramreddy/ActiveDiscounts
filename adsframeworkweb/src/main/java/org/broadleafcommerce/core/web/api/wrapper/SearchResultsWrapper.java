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

package org.broadleafcommerce.core.web.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.search.domain.ProductSearchResult;
import org.broadleafcommerce.core.search.domain.SearchFacetDTO;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "searchResults")
public class SearchResultsWrapper extends BaseWrapper implements APIWrapper<ProductSearchResult> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Integer page;

  /*
   * Indicates the requested or default page size.
   */
  /** DOCUMENT ME! */
  @XmlElement protected Integer pageSize;

  /*
   * List of products associated with a search
   */
  /** DOCUMENT ME! */
  @XmlElement(name = "product")
  @XmlElementWrapper(name = "products")
  protected List<ProductWrapper> products;

  /*
   * List of available facets to be used for searching
   */
  /** DOCUMENT ME! */
  @XmlElement(name = "searchFacet")
  @XmlElementWrapper(name = "searchFacets")
  protected List<SearchFacetWrapper> searchFacets;

  /*
   * Indicates the number of pages
   */
  /** DOCUMENT ME! */
  @XmlElement protected Integer totalPages;

  /*
   * Indicates the actual results
   */
  /** DOCUMENT ME! */
  @XmlElement protected Integer totalResults;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.search.domain.ProductSearchResult,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(ProductSearchResult model, HttpServletRequest request) {
    page         = model.getPage();
    pageSize     = model.getPageSize();
    totalResults = model.getTotalResults();
    totalPages   = model.getTotalPages();

    if (model.getProducts() != null) {
      products = new ArrayList<ProductWrapper>();

      for (Product product : model.getProducts()) {
        ProductWrapper productSummary = (ProductWrapper) context.getBean(ProductWrapper.class.getName());
        productSummary.wrapSummary(product, request);
        this.products.add(productSummary);
      }
    }

    if (model.getFacets() != null) {
      this.searchFacets = new ArrayList<SearchFacetWrapper>();

      for (SearchFacetDTO facet : model.getFacets()) {
        SearchFacetWrapper facetWrapper = (SearchFacetWrapper) context.getBean(SearchFacetWrapper.class.getName());
        facetWrapper.wrapSummary(facet, request);
        this.searchFacets.add(facetWrapper);
      }
    }
  } // end method wrapDetails

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.search.domain.ProductSearchResult,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(ProductSearchResult model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class SearchResultsWrapper
