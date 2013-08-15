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
/*
 *
 */
package org.broadleafcommerce.core.catalog.domain;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.broadleafcommerce.common.media.domain.Media;

import org.broadleafcommerce.core.inventory.service.type.InventoryType;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;
import org.broadleafcommerce.core.search.domain.CategorySearchFacet;
import org.broadleafcommerce.core.search.domain.SearchFacet;


/**
 * Implementations of this interface are used to hold data about a Category. A category is a group of products.<br>
 * <br>
 * You should implement this class if you want to make significant changes to how the Category is persisted. If you just
 * want to add additional fields then you should extend {@link org.broadleafcommerce.core.catalog.domain.CategoryImpl}.
 *
 * @see      {@link org.broadleafcommerce.core.catalog.domain.CategoryImpl}
 * @author   btaylor
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public interface Category extends Serializable {
  /**
   * Gets the primary key.
   *
   * @return  the primary key
   */
  @Nullable Long getId();

  /**
   * Sets the primary key.
   *
   * @param  id  the new primary key
   */
  void setId(@Nullable Long id);

  /**
   * Gets the name.
   *
   * @return  the name
   */
  @Nonnull String getName();

  /**
   * Sets the name.
   *
   * @param  name  the new name
   */
  void setName(@Nonnull String name);

  /**
   * Gets the default parent category.
   *
   * @return  the default parent category
   */
  @Nullable Category getDefaultParentCategory();

  /**
   * Sets the default parent category.
   *
   * @param  defaultParentCategory  the new default parent category
   */
  void setDefaultParentCategory(@Nullable Category defaultParentCategory);

  /**
   * Gets the url. The url represents the presentation layer destination for this category. For example, if using Spring
   * MVC, you could send the user to this destination by returning {@code "redirect:"+currentCategory.getUrl();} from a
   * controller.
   *
   * @return  the url for the presentation layer component for this category
   */
  @Nullable String getUrl();

  /**
   * Sets the url. The url represents the presentation layer destination for this category. For example, if using Spring
   * MVC, you could send the user to this destination by returning {@code "redirect:"+currentCategory.getUrl();} from a
   * controller.
   *
   * @param  url  the new url for the presentation layer component for this category
   */
  void setUrl(@Nullable String url);

  /**
   * Gets the url key. The url key is used as part of SEO url generation for this category. Each segment of the url
   * leading to a category is comprised of the url keys of the various associated categories in a hierarchy leading to
   * this one. If the url key is null, the the name for the category formatted with dashes for spaces.
   *
   * @return  the url key for this category to appear in the SEO url
   */
  @Nullable String getUrlKey();

  /**
   * Creates the SEO url starting from this category and recursing up the hierarchy of default parent categories until
   * the topmost category is reached. The url key for each category is used for each segment of the SEO url.
   *
   * @return  the generated SEO url for this category
   */
  @Nullable String getGeneratedUrl();

  /**
   * Sets the url key. The url key is used as part of SEO url generation for this category. Each segment of the url
   * leading to a category is comprised of the url keys of the various associated categories in a hierarchy leading to
   * this one.
   *
   * @param  urlKey  the new url key for this category to appear in the SEO url
   */
  void setUrlKey(@Nullable String urlKey);

  /**
   * Gets the description.
   *
   * @return  the description
   */
  @Nullable String getDescription();

  /**
   * Sets the description.
   *
   * @param  description  the new description
   */
  void setDescription(@Nullable String description);

  /**
   * Gets the active start date. If the current date is before activeStartDate, then this category will not be visible
   * on the site.
   *
   * @return  the active start date
   */
  @Nullable Date getActiveStartDate();

  /**
   * Sets the active start date. If the current date is before activeStartDate, then this category will not be visible
   * on the site.
   *
   * @param  activeStartDate  the new active start date
   */
  void setActiveStartDate(@Nullable Date activeStartDate);

  /**
   * Gets the active end date. If the current date is after activeEndDate, the this category will not be visible on the
   * site.
   *
   * @return  the active end date
   */
  @Nullable Date getActiveEndDate();

  /**
   * Sets the active end date. If the current date is after activeEndDate, the this category will not be visible on the
   * site.
   *
   * @param  activeEndDate  the new active end date
   */
  void setActiveEndDate(@Nullable Date activeEndDate);

  /**
   * Checks if is active. Returns true if the startDate is null or if the current date is after the start date, or if
   * the endDate is null or if the current date is before the endDate.
   *
   * @return  true, if is active
   */
  boolean isActive();

  /**
   * Gets the display template. The display template can be used to help create a unique key that drives the
   * presentation layer destination for this category. For example, if using Spring MVC, you might derive the view
   * destination in this way: {@code view = categoryTemplatePrefix + currentCategory.getDisplayTemplate();}
   *
   * @return  the display template
   */
  @Nullable String getDisplayTemplate();

  /**
   * Sets the display template. The display template can be used to help create a unique key that drives the
   * presentation layer destination for this category. For example, if using Spring MVC, you might derive the view
   * destination in this way: {@code view = categoryTemplatePrefix + currentCategory.getDisplayTemplate();}
   *
   * @param  displayTemplate  the new display template
   */
  void setDisplayTemplate(@Nullable String displayTemplate);

  /**
   * Gets the child category url map. This map is keyed off of the {@link #getGeneratedUrl()} values for this category
   * and all of its child categories. By calling get on this map using the generated url for a given category, you will
   * receive the list of immediate child categories. This is inefficient, so its use is highly discouraged.
   *
   * @return      the child category url map
   *
   * @deprecated  This approach is inherently inefficient and should no longer be used
   */
  @Deprecated @Nonnull Map<String, List<Long>> getChildCategoryURLMap();

  /**
   * Set the child category url map. This approach is inefficient, so its use is highly discouraged.
   *
   * @param       childCategoryURLMap  DOCUMENT ME!
   *
   * @deprecated  This approach is inherently inefficient and should no longer be used
   */
  @Deprecated void setChildCategoryURLMap(@Nonnull Map<String, List<Long>> childCategoryURLMap);

  /**
   * Gets the category images.
   *
   * @deprecated  replaced by {@link #getCategoryMedia()}
   *
   * @return      the category images
   */
  @Deprecated @Nonnull Map<String, String> getCategoryImages();

  /**
   * Gets the category image.
   *
   * @deprecated  replaced by {@link #getCategoryMedia()}
   *
   * @param       imageKey  the image key
   *
   * @return      the category image
   */
  @Deprecated @Nullable String getCategoryImage(@Nonnull String imageKey);

  /**
   * Sets the category images.
   *
   * @deprecated  replaced by {@link #setCategoryMedia(java.util.Map)}
   *
   * @param       categoryImages  the category images
   */
  @Deprecated void setCategoryImages(@Nonnull Map<String, String> categoryImages);

  /**
   * Gets the category media map. The key is of arbitrary meaning and the {@code Media} instance stores information
   * about the media itself (image url, etc...)
   *
   * @return  the category Media
   */
  @Nonnull Map<String, Media> getCategoryMedia();

  /**
   * Sets the category media. The key is of arbitrary meaning and the {@code Media} instance stores information about
   * the media itself (image url, etc...)
   *
   * @param  categoryMedia  the category media
   */
  void setCategoryMedia(@Nonnull Map<String, Media> categoryMedia);

  /**
   * Gets the long description.
   *
   * @return  the long description
   */
  @Nullable String getLongDescription();

  /**
   * Sets the long description.
   *
   * @param  longDescription  the new long description
   */
  void setLongDescription(@Nullable String longDescription);

  /**
   * Gets the featured products. Featured products are a special list of products you would like to showcase for this
   * category.
   *
   * @return  the featured products
   */
  @Nonnull List<FeaturedProduct> getFeaturedProducts();

  /**
   * Sets the featured products. Featured products are a special list of products you would like to showcase for this
   * category.
   *
   * @param  featuredProducts  the featured products
   */
  void setFeaturedProducts(@Nonnull List<FeaturedProduct> featuredProducts);

  /**
   * Returns a list of cross sale products that are related to this category.
   *
   * @return  a list of cross sale products
   */
  List<RelatedProduct> getCrossSaleProducts();

  /**
   * Sets the cross sale products that are related to this category.
   *
   * @see    #getCrossSaleProducts()
   *
   * @param  crossSaleProducts  DOCUMENT ME!
   */
  void setCrossSaleProducts(List<RelatedProduct> crossSaleProducts);

  /**
   * Returns a list of cross sale products that are related to this category.
   *
   * @return  a list of cross sale products
   */
  List<RelatedProduct> getUpSaleProducts();

  /**
   * Sets the upsale products that are related to this category.
   *
   * @see    #getUpSaleProducts()
   *
   * @param  upSaleProducts  DOCUMENT ME!
   */
  void setUpSaleProducts(List<RelatedProduct> upSaleProducts);

  /**
   * Returns a list of the cross sale products in this category as well as all cross sale products in all parent
   * categories of this category.
   *
   * @return  the cumulative cross sale products
   */
  List<RelatedProduct> getCumulativeCrossSaleProducts();

  /**
   * Returns a list of the upsale products in this category as well as all upsale products in all parent categories of
   * this category.
   *
   * @return  the cumulative upsale products
   */
  List<RelatedProduct> getCumulativeUpSaleProducts();

  /**
   * Returns a list of the featured products in this category as well as all featured products in all parent categories
   * of this category.
   *
   * @return  the cumulative featured products
   */
  List<FeaturedProduct> getCumulativeFeaturedProducts();

  /**
   * Returns all of the SearchFacets that are directly associated with this Category.
   *
   * @return  related SearchFacets
   */
  List<CategorySearchFacet> getSearchFacets();

  /**
   * Sets the SearchFacets that are directly associated with this Category.
   *
   * @param  searchFacets  DOCUMENT ME!
   */
  void setSearchFacets(List<CategorySearchFacet> searchFacets);

  /**
   * Sets the SearchFacets that should not be rendered by this Category. Typically, this will include facets from parent
   * categories that do not apply to this category.
   *
   * @param  excludedSearchFacets  DOCUMENT ME!
   */
  void setExcludedSearchFacets(List<SearchFacet> excludedSearchFacets);

  /**
   * Gets the excluded SearchFacets.
   *
   * @return  the excluded SearchFacets
   */
  List<SearchFacet> getExcludedSearchFacets();

  /**
   * Returns a list of CategorySearchFacets that takes into consideration the search facets for this Category, the
   * search facets for all parent categories, and the search facets that should be excluded from this Category. This
   * method will order the resulting list based on the
   * {@link org.broadleafcommerce.core.search.domain.CategorySearchFacet#getPosition()} method for each category level.
   * That is, the facets on this Category will be ordered by their position relative to each other with the ordered
   * parent facets after that, etc.
   *
   * @return  the current active search facets for this category and all parent categories
   */
  List<CategorySearchFacet> getCumulativeSearchFacets();

  /**
   * Build category hierarchy by walking the default category tree up to the root category. If the passed in tree is
   * null then create the initial list.
   *
   * @param   currentHierarchy  DOCUMENT ME!
   *
   * @return  build category hierarchy by walking the default category tree up to the root category.
   */

  List<Category> buildCategoryHierarchy(List<Category> currentHierarchy);

  /**
   * Build the full category hierarchy by walking up the default category tree and the all parent category tree.
   *
   * @param   currentHierarchy  DOCUMENT ME!
   *
   * @return  the full hierarchy
   */
  List<Category> buildFullCategoryHierarchy(List<Category> currentHierarchy);

  /**
   * Gets the attributes for this {@link org.broadleafcommerce.core.catalog.domain.Category}. In smaller sites, using
   * these attributes might be preferred to extending the domain object itself.
   *
   * @return  gets the attributes for this {@link org.broadleafcommerce.core.catalog.domain.Category}.
   *
   * @see     {@link #getMappedCategoryAttributes()}
   */
  Map<String, CategoryAttribute> getCategoryAttributesMap();

  /**
   * DOCUMENT ME!
   *
   * @param  categoryAttributes  DOCUMENT ME!
   */
  void setCategoryAttributesMap(Map<String, CategoryAttribute> categoryAttributes);

  /**
   * Gets the attributes for this {@link org.broadleafcommerce.core.catalog.domain.Category}. In smaller sites, using
   * these attributes might be preferred to extending the domain object itself.
   *
   * @return      gets the attributes for this {@link org.broadleafcommerce.core.catalog.domain.Category}.
   *
   * @see         {@link #getMappedCategoryAttributes()}
   * @deprecated  This will be replaced with {@link #getCategoryAttributesMap()} in 3.1.0.
   */
  List<CategoryAttribute> getCategoryAttributes();

  /**
   * Sets the attributes for this {@link org.broadleafcommerce.core.catalog.domain.Category}. In smaller sites, using
   * these attributes might be preferred to extending the domain object and creating a new table to store custom
   * properties.
   *
   * @param       categoryAttributes  DOCUMENT ME!
   *
   * @deprecated  This will be replaced with {@link #setCategoryAttributesMap()} in 3.1.0.
   */
  void setCategoryAttributes(List<CategoryAttribute> categoryAttributes);

  /**
   * Convenience method to get a {@link org.broadleafcommerce.core.catalog.domain.CategoryAttribute} by name.
   *
   * @param       name  DOCUMENT ME!
   *
   * @return      convenience method to get a {@link org.broadleafcommerce.core.catalog.domain.CategoryAttribute} by
   *              name.
   *
   * @see         {@link #getCategoryAttributes()}, {@link #getMappedCategoryAttributes()}
   * @deprecated  This will be removed in 3.1.0
   */
  CategoryAttribute getCategoryAttributeByName(String name);

  /**
   * Convenience method to return the {@link org.broadleafcommerce.core.catalog.domain.CategoryAttribute}s for the
   * {@link org.broadleafcommerce.core.catalog.domain.Category} in an easily-consumable form.
   *
   * @return      convenience method to return the {@link org.broadleafcommerce.core.catalog.domain.CategoryAttribute}s
   *              for the {@link org.broadleafcommerce.core.catalog.domain.Category} in an easily-consumable form.
   *
   * @deprecated  This will be removed in 3.1.0
   */
  Map<String, CategoryAttribute> getMappedCategoryAttributes();

  /**
   * Returns the type of inventory for this category.
   *
   * @return  the {@link org.broadleafcommerce.core.inventory.service.type.InventoryType} for this category
   */
  InventoryType getInventoryType();

  /**
   * Sets the type of inventory for this category.
   *
   * @param  inventoryType  the {@link org.broadleafcommerce.core.inventory.service.type.InventoryType} for this
   *                        category
   */
  void setInventoryType(InventoryType inventoryType);

  /**
   * Returns the default fulfillment type for skus in this category. May be null.
   *
   * @return  the default fulfillment type for skus in this category.
   */
  FulfillmentType getFulfillmentType();

  /**
   * Sets the default fulfillment type for skus in this category. May return null.
   *
   * @param  fulfillmentType  DOCUMENT ME!
   */
  void setFulfillmentType(FulfillmentType fulfillmentType);

  /**
   * Gets the child categories. This list includes all categories, regardless of whether or not they are active.
   *
   * @deprecated  use getAllChildCategoryXrefs() instead.
   *
   * @return      the list of active and inactive child categories.
   */
  @Deprecated @Nonnull List<Category> getAllChildCategories();

  /**
   * Checks for child categories.
   *
   * @return  true, if this category has any children (active or not)
   */
  boolean hasAllChildCategories();

  /**
   * Sets the list of child categories (active and inactive).
   *
   * @deprecated  Use setAllChildCategoryXrefs() instead.
   *
   * @param       childCategories  the list of child categories
   */
  @Deprecated void setAllChildCategories(@Nonnull List<Category> childCategories);

  /**
   * Gets the child categories. If child categories has not been previously set, then the list of active only categories
   * will be returned.
   *
   * @deprecated  Use getChildCategoryXrefs() instead.
   *
   * @return      the list of active child categories
   */
  @Deprecated @Nonnull List<Category> getChildCategories();

  /**
   * Gets the child category ids. If child categories has not been previously set, then the list of active only
   * categories will be returned. This method is optimized with Hydrated cache, which means that the algorithm required
   * to harvest active child categories will not need to be rebuilt as long as the parent category (this category) is
   * not evicted from second level cache.
   *
   * @return  the list of active child category ids
   */
  @Nonnull List<Long> getChildCategoryIds();

  /**
   * Sets the all child category ids. This should be a list of active only child categories.
   *
   * @param  childCategoryIds  the list of active child category ids.
   */
  void setChildCategoryIds(@Nonnull List<Long> childCategoryIds);

  /**
   * Checks for child categories.
   *
   * @return  true, if this category contains any active child categories.
   */
  boolean hasChildCategories();

  /**
   * Sets the all child categories. This should be a list of active only child categories.
   *
   * @deprecated  Use setChildCategoryXrefs() instead.
   *
   * @param       childCategories  the list of active child categories.
   */
  @Deprecated void setChildCategories(@Nonnull List<Category> childCategories);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CategoryXref> getAllChildCategoryXrefs();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CategoryXref> getChildCategoryXrefs();

  /**
   * DOCUMENT ME!
   *
   * @param  childCategories  DOCUMENT ME!
   */
  void setChildCategoryXrefs(List<CategoryXref> childCategories);

  /**
   * DOCUMENT ME!
   *
   * @param  childCategories  DOCUMENT ME!
   */
  void setAllChildCategoryXrefs(List<CategoryXref> childCategories);


  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CategoryXref> getAllParentCategoryXrefs();

  /**
   * DOCUMENT ME!
   *
   * @param  allParentCategories  DOCUMENT ME!
   */
  void setAllParentCategoryXrefs(List<CategoryXref> allParentCategories);

  /**
   * Retrieve all parent categories.
   *
   * @deprecated  Use getAllParentCategoryXrefs() instead.
   *
   * @return      the list of parent categories
   */
  @Deprecated @Nonnull List<Category> getAllParentCategories();

  /**
   * Sets the list of parent categories.
   *
   * @deprecated  Use setAllParentCategoryXrefs() instead.
   *
   * @param       allParentCategories  the list of parent categories
   */
  @Deprecated void setAllParentCategories(@Nonnull List<Category> allParentCategories);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CategoryProductXref> getActiveProductXrefs();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CategoryProductXref> getAllProductXrefs();

  /**
   * DOCUMENT ME!
   *
   * @param  allProducts  DOCUMENT ME!
   */
  void setAllProductXrefs(List<CategoryProductXref> allProducts);

  /**
   * Convenience method to retrieve all of this {@link org.broadleafcommerce.core.catalog.domain.Category}'s
   * {@link org.broadleafcommerce.core.catalog.domain.Product}s filtered by active. If you want all of the
   * {@link org.broadleafcommerce.core.catalog.domain.Product}s (whether inactive or not) consider using
   * {@link #getAllProducts()}.
   *
   * @deprecated  Use getActiveProductXrefs() instead.
   *
   * @return      the list of active {@link org.broadleafcommerce.core.catalog.domain.Product}s for this
   *              {@link org.broadleafcommerce.core.catalog.domain.Category}
   *
   * @see         {@link org.broadleafcommerce.core.catalog.domain.Product#isActive()}
   */
  @Deprecated List<Product> getActiveProducts();

  /**
   * Retrieve all the {@code Product} instances associated with this category.<br />
   * <b>Note:</b> this method does not take into account whether or not the
   * {@link org.broadleafcommerce.core.catalog.domain.Product}s are active or not. If you need this functionality, use
   * {@link #getActiveProducts()}
   *
   * @deprecated  Use getAllProductXrefs() instead.
   *
   * @return      the list of products associated with this category.
   */
  @Deprecated @Nonnull List<Product> getAllProducts();

  /**
   * Set all the {@code Product} instances associated with this category.
   *
   * @deprecated  Use setAllProductXrefs() instead.
   *
   * @param       allProducts  the list of products to associate with this category
   */
  @Deprecated void setAllProducts(@Nonnull List<Product> allProducts);

  /**
   * Returns the tax code of this category.
   *
   * @return  taxCode
   */
  String getTaxCode();

  /**
   * Sets the tax code of this category.
   *
   * @param  taxCode  DOCUMENT ME!
   */
  void setTaxCode(String taxCode);
} // end interface Category
