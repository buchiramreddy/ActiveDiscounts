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

package org.broadleafcommerce.core.catalog.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductBundle;
import org.broadleafcommerce.core.catalog.domain.ProductOption;
import org.broadleafcommerce.core.catalog.domain.ProductOptionValue;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuFee;
import org.broadleafcommerce.core.catalog.service.type.ProductType;
import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CatalogService {
  /**
   * DOCUMENT ME!
   *
   * @param   product  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Product saveProduct(Product product);

  /**
   * DOCUMENT ME!
   *
   * @param   productId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Product findProductById(Long productId);

  /**
   * DOCUMENT ME!
   *
   * @param   searchName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Product> findProductsByName(String searchName);

  /**
   * Find a subset of {@code Product} instances whose name starts with or is equal to the passed in search parameter.
   * Res
   *
   * @param   searchName  DOCUMENT ME!
   * @param   limit       the maximum number of results
   * @param   offset      the starting point in the record set
   *
   * @return  the list of product instances that fit the search criteria
   */
  List<Product> findProductsByName(String searchName, int limit, int offset);

  /**
   * DOCUMENT ME!
   *
   * @param   category     DOCUMENT ME!
   * @param   currentDate  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Product> findActiveProductsByCategory(Category category, Date currentDate);

  /**
   * Given a category and a ProudctSearchCriteria, returns the appropriate matching products.
   *
   * @param   category        DOCUMENT ME!
   * @param   currentDate     DOCUMENT ME!
   * @param   searchCriteria  DOCUMENT ME!
   *
   * @return  the matching products
   */
  List<Product> findFilteredActiveProductsByCategory(Category category, Date currentDate,
    ProductSearchCriteria searchCriteria);

  /**
   * Given a search query and a ProductSearchCriteria, returns the appropriate matching products.
   *
   * @param   query           DOCUMENT ME!
   * @param   currentDate     DOCUMENT ME!
   * @param   searchCriteria  DOCUMENT ME!
   *
   * @return  the matching products
   */
  List<Product> findFilteredActiveProductsByQuery(String query, Date currentDate, ProductSearchCriteria searchCriteria);

  /**
   * DOCUMENT ME!
   *
   * @param   category     DOCUMENT ME!
   * @param   currentDate  DOCUMENT ME!
   * @param   limit        DOCUMENT ME!
   * @param   offset       DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Product> findActiveProductsByCategory(Category category, Date currentDate, int limit, int offset);

  /**
   * Find all ProductBundles whose automatic attribute is set to true.
   *
   * <p>Automatic product bundles are collections of products that can receive special pricing. With automatic product
   * bundles, if a customer adds all of the components of the bundle individually to the cart, they will automatically
   * get assembeled into a bundle.</p>
   *
   * @return  find all ProductBundles whose automatic attribute is set to true.
   */
  List<ProductBundle> findAutomaticProductBundles();


  /**
   * DOCUMENT ME!
   *
   * @param   category  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Category saveCategory(Category category);

  /**
   * DOCUMENT ME!
   *
   * @param  category  DOCUMENT ME!
   */
  void removeCategory(Category category);

  /**
   * DOCUMENT ME!
   *
   * @param   categoryId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Category findCategoryById(Long categoryId);

  /**
   * Retrieve a {@code Category} instance based on its name property.
   *
   * <p>Broadleaf allows more than one category to have the same name. Calling this method could produce an exception in
   * such situations. Use {@link #findCategoriesByName(String)} instead.</p>
   *
   * @param   categoryName  the category name to search by
   *
   * @return  the Category instance matching the categoryName
   */
  @Deprecated Category findCategoryByName(String categoryName);

  /**
   * Retrieve a list of {@code Category} instance based on the name property.
   *
   * @param   categoryName  the category name to search by
   *
   * @return  the list of matching Category instances
   */
  List<Category> findCategoriesByName(String categoryName);

  /**
   * Retrieve a list of {@code Category} instances based on the search criteria.
   *
   * @param   categoryName  the name of the category to search by
   * @param   limit         the maximum number of results to return
   * @param   offset        the starting point of the records to return
   *
   * @return  a list of category instances that match the search criteria
   */
  List<Category> findCategoriesByName(String categoryName, int limit, int offset);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Category> findAllCategories();

  /**
   * DOCUMENT ME!
   *
   * @param   limit   DOCUMENT ME!
   * @param   offset  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Category> findAllCategories(int limit, int offset);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Product> findAllProducts();

  /**
   * DOCUMENT ME!
   *
   * @param   limit   DOCUMENT ME!
   * @param   offset  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Product> findAllProducts(int limit, int offset);

  /**
   * DOCUMENT ME!
   *
   * @param   category  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Product> findProductsForCategory(Category category);

  /**
   * DOCUMENT ME!
   *
   * @param   category  DOCUMENT ME!
   * @param   limit     DOCUMENT ME!
   * @param   offset    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Product> findProductsForCategory(Category category, int limit, int offset);

  /**
   * DOCUMENT ME!
   *
   * @param   sku  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Sku saveSku(Sku sku);

  /**
   * DOCUMENT ME!
   *
   * @param   fee  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SkuFee saveSkuFee(SkuFee fee);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Sku> findAllSkus();

  /**
   * DOCUMENT ME!
   *
   * @param   ids  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Sku> findSkusByIds(List<Long> ids);

  /**
   * DOCUMENT ME!
   *
   * @param   skuId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Sku findSkuById(Long skuId);

  /**
   * Get a hierarchical map of all child categories keyed on the url.
   *
   * @param       categoryId  the parent category to which the children belong
   *
   * @return      hierarchical map of all child categories
   *
   * @deprecated  this approach is inherently inefficient - don't use.
   */
  @Deprecated Map<String, List<Long>> getChildCategoryURLMapByCategoryId(Long categoryId);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Category createCategory();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Sku createSku();

  /**
   * DOCUMENT ME!
   *
   * @param   productType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Product createProduct(ProductType productType);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Category> findAllParentCategories();

  /**
   * DOCUMENT ME!
   *
   * @param   category  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Category> findAllSubCategories(Category category);

  /**
   * DOCUMENT ME!
   *
   * @param   category  DOCUMENT ME!
   * @param   limit     DOCUMENT ME!
   * @param   offset    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Category> findAllSubCategories(Category category, int limit, int offset);

  /**
   * DOCUMENT ME!
   *
   * @param   category  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Category> findActiveSubCategoriesByCategory(Category category);

  /**
   * DOCUMENT ME!
   *
   * @param   category  DOCUMENT ME!
   * @param   limit     DOCUMENT ME!
   * @param   offset    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Category> findActiveSubCategoriesByCategory(Category category, int limit, int offset);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<ProductOption> readAllProductOptions();

  /**
   * DOCUMENT ME!
   *
   * @param   option  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ProductOption saveProductOption(ProductOption option);

  /**
   * DOCUMENT ME!
   *
   * @param   productOptionId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ProductOption findProductOptionById(Long productOptionId);

  /**
   * DOCUMENT ME!
   *
   * @param   productOptionValueId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ProductOptionValue findProductOptionValueById(Long productOptionValueId);

  /**
   * Returns a category associated with the passed in URI or null if no Category is mapped to this URI.
   *
   * @param   uri  DOCUMENT ME!
   *
   * @return  a category associated with the passed in URI or null if no Category is mapped to this URI.
   */
  Category findCategoryByURI(String uri);

  /**
   * Returns a product associated with the passed in URI or null if no Product is mapped to this URI.
   *
   * @param   uri  DOCUMENT ME!
   *
   * @return  a product associated with the passed in URI or null if no Product is mapped to this URI.
   */
  Product findProductByURI(String uri);

} // end interface CatalogService
