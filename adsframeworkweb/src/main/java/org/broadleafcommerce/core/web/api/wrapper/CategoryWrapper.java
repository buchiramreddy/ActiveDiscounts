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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.util.xml.ISO8601DateAdapter;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryAttribute;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;
import org.broadleafcommerce.core.search.domain.ProductSearchResult;
import org.broadleafcommerce.core.search.service.SearchService;


/**
 * This is a JAXB wrapper for a Broadleaf Category. There may be several reasons to extend this class. First, you may
 * want to extend Broadleaf's CategroryImpl and expose those extensions via a RESTful service. You may also want to
 * suppress properties that are being serialized. To expose new properties, or suppress properties that have been
 * exposed, do the following:<br/>
 * <br/>
 * 1. Extend this class<br/>
 * 2. Override the <code>wrap</code> method.<br/>
 * 3. Within the wrap method, either override all properties that you want to set, or call <code>
 * super.wrap(Category)</code><br/>
 * 4. Set additional property values that you have added.<br/>
 * 5. Set super properties to null if you do not want them serialized. (e.g. <code>super.name = null;</code><br/>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "category")
public class CategoryWrapper extends BaseWrapper implements APIWrapper<Category> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Boolean active;

  /** DOCUMENT ME! */
  @XmlElement
  @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
  protected Date             activeEndDate;

  /** DOCUMENT ME! */
  @XmlElement
  @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
  protected Date             activeStartDate;

  /** DOCUMENT ME! */
  @XmlElement(name = "categoryAttribute")
  @XmlElementWrapper(name = "categoryAttributes")
  protected List<CategoryAttributeWrapper> categoryAttributes;

  /** DOCUMENT ME! */
  @XmlElement protected String description;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String name;

  /** DOCUMENT ME! */
  @XmlElement(name = "product")
  @XmlElementWrapper(name = "products")
  protected List<ProductWrapper> products;

  /** DOCUMENT ME! */
  @XmlElement(name = "category")
  @XmlElementWrapper(name = "subcategories")
  protected List<CategoryWrapper> subcategories;

  /** DOCUMENT ME! */
  @XmlElement protected String url;

  /** DOCUMENT ME! */
  @XmlElement protected String urlKey;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.catalog.domain.Category,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(Category category, HttpServletRequest request) {
    this.id              = category.getId();
    this.name            = category.getName();
    this.description     = category.getDescription();
    this.active          = category.isActive();
    this.activeStartDate = category.getActiveStartDate();
    this.activeEndDate   = category.getActiveEndDate();
    this.url             = category.getUrl();
    this.urlKey          = category.getUrlKey();

    if ((category.getCategoryAttributes() != null) && !category.getCategoryAttributes().isEmpty()) {
      categoryAttributes = new ArrayList<CategoryAttributeWrapper>();

      for (CategoryAttribute attribute : category.getCategoryAttributes()) {
        CategoryAttributeWrapper wrapper = (CategoryAttributeWrapper) context.getBean(CategoryAttributeWrapper.class
            .getName());
        wrapper.wrapSummary(attribute, request);
        categoryAttributes.add(wrapper);
      }
    }

    Integer productLimit      = (Integer) request.getAttribute("productLimit");
    Integer productOffset     = (Integer) request.getAttribute("productOffset");
    Integer subcategoryLimit  = (Integer) request.getAttribute("subcategoryLimit");
    Integer subcategoryOffset = (Integer) request.getAttribute("subcategoryOffset");

    if ((productLimit != null) && (productOffset == null)) {
      productOffset = 1;
    }

    if ((subcategoryLimit != null) && (subcategoryOffset == null)) {
      subcategoryOffset = 1;
    }

    if ((productLimit != null) && (productOffset != null)) {
      SearchService         searchService  = (SearchService) context.getBean("blSearchService");
      ProductSearchCriteria searchCriteria = new ProductSearchCriteria();
      searchCriteria.setPage(productOffset);
      searchCriteria.setPageSize(productLimit);
      searchCriteria.setFilterCriteria(new HashMap<String, String[]>());

      try {
        ProductSearchResult result      = searchService.findExplicitProductsByCategory(category, searchCriteria);
        List<Product>       productList = result.getProducts();

        if ((productList != null) && !productList.isEmpty()) {
          if (products == null) {
            products = new ArrayList<ProductWrapper>();
          }

          for (Product p : productList) {
            ProductWrapper productSummaryWrapper = (ProductWrapper) context.getBean(ProductWrapper.class.getName());
            productSummaryWrapper.wrapSummary(p, request);
            products.add(productSummaryWrapper);
          }
        }
      } catch (ServiceException e) {
        throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(
            MediaType.TEXT_PLAIN).entity("An unexpected error occured  " + e.getMessage()).build());
      }
    } // end if

    if ((subcategoryLimit != null) && (subcategoryOffset != null)) {
      subcategories = buildSubcategoryTree(subcategories, category, request);
    }
  } // end method wrapDetails

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.catalog.domain.Category,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(Category category, HttpServletRequest request) {
    this.id          = category.getId();
    this.name        = category.getName();
    this.description = category.getDescription();
    this.active      = category.isActive();
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   *
   * @param   wrappers  DOCUMENT ME!
   * @param   root      DOCUMENT ME!
   * @param   request   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected List<CategoryWrapper> buildSubcategoryTree(List<CategoryWrapper> wrappers, Category root,
    HttpServletRequest request) {
    CatalogService catalogService = (CatalogService) context.getBean("blCatalogService");

    Integer subcategoryLimit  = (Integer) request.getAttribute("subcategoryLimit");
    Integer subcategoryOffset = (Integer) request.getAttribute("subcategoryOffset");

    List<Category> subcategories = catalogService.findActiveSubCategoriesByCategory(root, subcategoryLimit,
        subcategoryOffset);

    if ((subcategories != null) && !subcategories.isEmpty() && (wrappers == null)) {
      wrappers = new ArrayList<CategoryWrapper>();
    }

    for (Category c : subcategories) {
      CategoryWrapper subcategoryWrapper = (CategoryWrapper) context.getBean(CategoryWrapper.class.getName());
      subcategoryWrapper.wrapSummary(c, request);
      wrappers.add(subcategoryWrapper);
    }

    return wrappers;
  }
} // end class CategoryWrapper
