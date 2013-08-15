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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.broadleafcommerce.core.catalog.dao.CategoryDao;
import org.broadleafcommerce.core.catalog.dao.ProductDao;
import org.broadleafcommerce.core.catalog.dao.ProductOptionDao;
import org.broadleafcommerce.core.catalog.dao.SkuDao;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductBundle;
import org.broadleafcommerce.core.catalog.domain.ProductBundleComparator;
import org.broadleafcommerce.core.catalog.domain.ProductOption;
import org.broadleafcommerce.core.catalog.domain.ProductOptionValue;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuFee;
import org.broadleafcommerce.core.catalog.service.type.ProductType;
import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blCatalogService")
public class CatalogServiceImpl implements CatalogService {
  /** DOCUMENT ME! */
  @Resource(name = "blCategoryDao")
  protected CategoryDao categoryDao;

  /** DOCUMENT ME! */
  @Resource(name = "blProductDao")
  protected ProductDao productDao;

  /** DOCUMENT ME! */
  @Resource(name = "blSkuDao")
  protected SkuDao skuDao;

  /** DOCUMENT ME! */
  @Resource(name = "blProductOptionDao")
  protected ProductOptionDao productOptionDao;

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findProductById(java.lang.Long)
   */
  @Override public Product findProductById(Long productId) {
    return productDao.readProductById(productId);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findProductsByName(java.lang.String)
   */
  @Override public List<Product> findProductsByName(String searchName) {
    return productDao.readProductsByName(searchName);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findProductsByName(java.lang.String, int, int)
   */
  @Override public List<Product> findProductsByName(String searchName, int limit, int offset) {
    return productDao.readProductsByName(searchName, limit, offset);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findActiveProductsByCategory(org.broadleafcommerce.core.catalog.domain.Category,
   *       java.util.Date)
   */
  @Override public List<Product> findActiveProductsByCategory(Category category, Date currentDate) {
    return productDao.readActiveProductsByCategory(category.getId(), currentDate);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findFilteredActiveProductsByCategory(org.broadleafcommerce.core.catalog.domain.Category,
   *       java.util.Date, org.broadleafcommerce.core.search.domain.ProductSearchCriteria)
   */
  @Override public List<Product> findFilteredActiveProductsByCategory(Category category, Date currentDate,
    ProductSearchCriteria searchCriteria) {
    return productDao.readFilteredActiveProductsByCategory(category.getId(), currentDate, searchCriteria);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findFilteredActiveProductsByQuery(java.lang.String,
   *       java.util.Date, org.broadleafcommerce.core.search.domain.ProductSearchCriteria)
   */
  @Override public List<Product> findFilteredActiveProductsByQuery(String query, Date currentDate,
    ProductSearchCriteria searchCriteria) {
    return productDao.readFilteredActiveProductsByQuery(query, currentDate, searchCriteria);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findActiveProductsByCategory(org.broadleafcommerce.core.catalog.domain.Category,
   *       java.util.Date, int, int)
   */
  @Override public List<Product> findActiveProductsByCategory(Category category, Date currentDate, int limit,
    int offset) {
    return productDao.readActiveProductsByCategory(category.getId(), currentDate, limit, offset);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findAutomaticProductBundles()
   */
  @Override public List<ProductBundle> findAutomaticProductBundles() {
    List<ProductBundle> bundles = productDao.readAutomaticProductBundles();
    Collections.sort(bundles, new ProductBundleComparator());

    return bundles;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#saveProduct(org.broadleafcommerce.core.catalog.domain.Product)
   */
  @Override
  @Transactional("blTransactionManager")
  public Product saveProduct(Product product) {
    return productDao.save(product);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findCategoryById(java.lang.Long)
   */
  @Override public Category findCategoryById(Long categoryId) {
    return categoryDao.readCategoryById(categoryId);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findCategoryByName(java.lang.String)
   */
  @Deprecated @Override public Category findCategoryByName(String categoryName) {
    return categoryDao.readCategoryByName(categoryName);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findCategoriesByName(java.lang.String)
   */
  @Override public List<Category> findCategoriesByName(String categoryName) {
    return categoryDao.readCategoriesByName(categoryName);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findCategoriesByName(java.lang.String, int, int)
   */
  @Override public List<Category> findCategoriesByName(String categoryName, int limit, int offset) {
    return categoryDao.readCategoriesByName(categoryName, limit, offset);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#saveCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override
  @Transactional("blTransactionManager")
  public Category saveCategory(Category category) {
    return categoryDao.save(category);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#removeCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override
  @Transactional("blTransactionManager")
  public void removeCategory(Category category) {
    categoryDao.delete(category);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findAllCategories()
   */
  @Override public List<Category> findAllCategories() {
    return categoryDao.readAllCategories();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findAllCategories(int, int)
   */
  @Override public List<Category> findAllCategories(int limit, int offset) {
    return categoryDao.readAllCategories(limit, offset);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findAllParentCategories()
   */
  @Override public List<Category> findAllParentCategories() {
    return categoryDao.readAllParentCategories();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findAllSubCategories(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public List<Category> findAllSubCategories(Category category) {
    return categoryDao.readAllSubCategories(category);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findAllSubCategories(org.broadleafcommerce.core.catalog.domain.Category,
   *       int, int)
   */
  @Override public List<Category> findAllSubCategories(Category category, int limit, int offset) {
    return categoryDao.readAllSubCategories(category, limit, offset);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findActiveSubCategoriesByCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public List<Category> findActiveSubCategoriesByCategory(Category category) {
    return categoryDao.readActiveSubCategoriesByCategory(category);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findActiveSubCategoriesByCategory(org.broadleafcommerce.core.catalog.domain.Category,
   *       int, int)
   */
  @Override public List<Category> findActiveSubCategoriesByCategory(Category category, int limit, int offset) {
    return categoryDao.readActiveSubCategoriesByCategory(category, limit, offset);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findAllProducts()
   */
  @Override public List<Product> findAllProducts() {
    return categoryDao.readAllProducts();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findAllProducts(int, int)
   */
  @Override public List<Product> findAllProducts(int limit, int offset) {
    return categoryDao.readAllProducts(limit, offset);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findAllSkus()
   */
  @Override public List<Sku> findAllSkus() {
    return skuDao.readAllSkus();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findSkuById(java.lang.Long)
   */
  @Override public Sku findSkuById(Long skuId) {
    return skuDao.readSkuById(skuId);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#saveSku(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override
  @Transactional("blTransactionManager")
  public Sku saveSku(Sku sku) {
    return skuDao.save(sku);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#saveSkuFee(org.broadleafcommerce.core.catalog.domain.SkuFee)
   */
  @Override
  @Transactional("blTransactionManager")
  public SkuFee saveSkuFee(SkuFee fee) {
    return skuDao.saveSkuFee(fee);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findSkusByIds(java.util.List)
   */
  @Override public List<Sku> findSkusByIds(List<Long> ids) {
    return skuDao.readSkusById(ids);
  }

  /**
   * DOCUMENT ME!
   *
   * @param  productDao  DOCUMENT ME!
   */
  public void setProductDao(ProductDao productDao) {
    this.productDao = productDao;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  skuDao  DOCUMENT ME!
   */
  public void setSkuDao(SkuDao skuDao) {
    this.skuDao = skuDao;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findProductsForCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public List<Product> findProductsForCategory(Category category) {
    return productDao.readProductsByCategory(category.getId());
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findProductsForCategory(org.broadleafcommerce.core.catalog.domain.Category,
   *       int, int)
   */
  @Override public List<Product> findProductsForCategory(Category category, int limit, int offset) {
    return productDao.readProductsByCategory(category.getId(), limit, offset);
  }

  /**
   * DOCUMENT ME!
   *
   * @param  categoryDao  DOCUMENT ME!
   */
  public void setCategoryDao(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#getChildCategoryURLMapByCategoryId(java.lang.Long)
   */
  @Deprecated @Override public Map<String, List<Long>> getChildCategoryURLMapByCategoryId(Long categoryId) {
    Category category = findCategoryById(categoryId);

    if (category != null) {
      return category.getChildCategoryURLMap();
    }

    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#createCategory()
   */
  @Override public Category createCategory() {
    return categoryDao.create();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#createSku()
   */
  @Override public Sku createSku() {
    return skuDao.create();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#createProduct(org.broadleafcommerce.core.catalog.service.type.ProductType)
   */
  @Override public Product createProduct(ProductType productType) {
    return productDao.create(productType);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#readAllProductOptions()
   */
  @Override public List<ProductOption> readAllProductOptions() {
    return productOptionDao.readAllProductOptions();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#saveProductOption(org.broadleafcommerce.core.catalog.domain.ProductOption)
   */
  @Override
  @Transactional("blTransactionManager")
  public ProductOption saveProductOption(ProductOption option) {
    return productOptionDao.saveProductOption(option);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findProductOptionById(java.lang.Long)
   */
  @Override public ProductOption findProductOptionById(Long productOptionId) {
    return productOptionDao.readProductOptionById(productOptionId);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findProductOptionValueById(java.lang.Long)
   */
  @Override public ProductOptionValue findProductOptionValueById(Long productOptionValueId) {
    return productOptionDao.readProductOptionValueById(productOptionValueId);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findCategoryByURI(java.lang.String)
   */
  @Override public Category findCategoryByURI(String uri) {
    return categoryDao.findCategoryByURI(uri);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.service.CatalogService#findProductByURI(java.lang.String)
   */
  @Override public Product findProductByURI(String uri) {
    List<Product> products = productDao.findProductByURI(uri);

    if ((products == null) || (products.size() == 0)) {
      return null;
    } else if (products.size() == 1) {
      return products.get(0);
    } else {
      // First check for a direct hit on the url
      for (Product product : products) {
        if (uri.equals(product.getUrl())) {
          return product;
        }
      }

      for (Product product : products) {
        // Next check for a direct hit on the generated URL.
        if (uri.equals(product.getGeneratedUrl())) {
          return product;
        }
      }

      // Otherwise, return the first product
      return products.get(0);
    }
  } // end method findProductByURI

} // end class CatalogServiceImpl
