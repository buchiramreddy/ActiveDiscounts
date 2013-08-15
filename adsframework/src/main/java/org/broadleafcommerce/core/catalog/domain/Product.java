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

package org.broadleafcommerce.core.catalog.domain;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.common.vendor.service.type.ContainerShapeType;
import org.broadleafcommerce.common.vendor.service.type.ContainerSizeType;


/**
 * Implementations of this interface are used to hold data for a Product. A product is a general description of an item
 * that can be sold (for example: a hat). Products are not sold or added to a cart.
 * {@link org.broadleafcommerce.core.catalog.domain.Sku}s which are specific items (for example: a XL Blue Hat) are sold
 * or added to a cart.<br>
 * <br>
 * You should implement this class if you want to make significant changes to how the Product is persisted. If you just
 * want to add additional fields then you should extend {@link org.broadleafcommerce.core.catalog.domain.ProductImpl}.
 *
 * @author   btaylor
 * @see      {@link org.broadleafcommerce.core.catalog.domain.ProductImpl},
 *           {@link org.broadleafcommerce.core.catalog.domain.Sku},
 *           {@link org.broadleafcommerce.core.catalog.domain.Category}
 * @version  $Revision$, $Date$
 */
public interface Product extends Serializable {
  /**
   * The id of the Product.
   *
   * @return  the id of the Product
   */
  Long getId();

  /**
   * Sets the id of the Product.
   *
   * @param  id  - the id of the product
   */
  void setId(Long id);

  /**
   * Returns the name of the product that is used for display purposes.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  the name of the product
   */
  String getName();

  /**
   * Sets the name of the product that is used for display purposes.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  name  - the name of the Product
   */
  void setName(String name);

  /**
   * Returns a brief description of the product that is used for display.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  a brief description of the product
   */
  String getDescription();

  /**
   * Sets a brief description of the product that is used for display.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  description  - a brief description of the product
   */
  void setDescription(String description);

  /**
   * Returns a long description of the product that is used for display.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  a long description of the product
   */
  String getLongDescription();

  /**
   * Sets a long description of the product that is used for display.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  longDescription  the long description
   */
  void setLongDescription(String longDescription);

  /**
   * Returns the first date a product will be available that is used to determine whether to display the product.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  the first date the product will be available
   */
  Date getActiveStartDate();

  /**
   * Sets the first date a product will be available that is used to determine whether to display the product.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  activeStartDate  - the first day the product is available
   */
  void setActiveStartDate(Date activeStartDate);

  /**
   * Returns the last date a product will be available that is used to determine whether to display the product.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  the last day the product is available
   */
  Date getActiveEndDate();

  /**
   * Sets the last date a product will be available that is used to determine whether to display the product.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  activeEndDate  - the last day the product is available
   */
  void setActiveEndDate(Date activeEndDate);

  /**
   * Returns a boolean that indicates if the product is currently active.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  a boolean indicates if the product is active.
   */
  boolean isActive();

  /**
   * Gets the default {@link org.broadleafcommerce.core.catalog.domain.Sku} associated with this Product. A Product is
   * required to have a default Sku which holds specific information about the Product like weight, dimensions, price,
   * etc. Many of the Product attributes that have getters and setters on Product are actually pass-through to the
   * default Sku.<br />
   * <br />
   * Products can also have multiple Skus associated with it that are represented by
   * {@link org.broadleafcommerce.core.catalog.domain.ProductOption}s. For instance, a large, blue shirt. For more
   * information on that relationship see {@link #getAdditionalSkus()}.
   *
   * @return  the default Sku for this Product
   */
  Sku getDefaultSku();

  /**
   * Sets the default Sku for this Product<br />
   * <br />
   * Note: this operation is cascaded with CascadeType.ALL which saves from having to persist the Product in 2
   * operations: first persist the Sku and then take the merged Sku, set it as this Product's default Sku, and then
   * persist this Product.
   *
   * @param  defaultSku  - the Sku that should be the default for this Product
   */
  void setDefaultSku(Sku defaultSku);

  /**
   * Whether or not the default sku can be used for a multi-sku product in the case that no product options are set.
   * Defaults to false if not specified. Note that this only affects multi-sku products.
   *
   * @return  whether or not the default sku can be used for a multi-sku product in the case that no product options are
   *          set. Defaults to false if not specified. Note that this only affects multi-sku products.
   */
  Boolean getCanSellWithoutOptions();

  /**
   * Sets whether or not the default sku can be sold in the case that no product options are specified. Note that this
   * only affects multi-sku products.
   *
   * @param  canSellWithoutOptions  DOCUMENT ME!
   */
  void setCanSellWithoutOptions(Boolean canSellWithoutOptions);

  /**
   * Returns a list of {@link org.broadleafcommerce.core.catalog.domain.Sku}s filtered by whether the Skus are active or
   * not. This list does not contain the {@link #getDefaultSku()} and filters by
   * {@link org.broadleafcommerce.core.catalog.domain.Sku#isActive()}.
   *
   * @return  a list of active Skus from {@link #getAdditionalSkus()} for this Product
   */
  List<Sku> getSkus();

  /**
   * Gets all the additional Skus associated with this Product. For instance, if this Product represented a T-shirt and
   * you could pick the size of the T-shirt as a {@link org.broadleafcommerce.core.catalog.domain.ProductOption} (like
   * "small", "medium", "large") this would return 3 Skus if you had different inventory or price constraints on each
   * {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}.<br />
   * <br />
   * This list does not take into account whether any of these additional Skus are active or not, nor does it contain
   * the {@link #getDefaultSku()} for this Product. For this functionality, see {@link #getSkus()} and
   * {@link #getAllSkus()}, respectively.
   *
   * @return  the additional Skus for this Product
   *
   * @see     {@link org.broadleafcommerce.core.catalog.domain.ProductOption},
   *          {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}
   */
  List<Sku> getAdditionalSkus();

  /**
   * Sets the additional Skus associated to this Product. These additional Skus should come from
   * {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}s and are used in instance where you need to
   * track inventory or change pricing on a per-option value basis.
   *
   * @param  skus  - a List of {@link org.broadleafcommerce.core.catalog.domain.Sku}s to associate with this Product,
   *               usually based off of {@link org.broadleafcommerce.core.catalog.domain.ProductOption}s
   *
   * @see    {@link #getAdditionalSkus()}, {@link org.broadleafcommerce.core.catalog.domain.ProductOption},
   *         {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}
   */
  void setAdditionalSkus(List<Sku> skus);

  /**
   * Returns all the {@link org.broadleafcommerce.core.catalog.domain.Sku}s that are associated with this Product
   * (including {@link #getDefaultSku()}) regardless of whether or not the
   * {@link org.broadleafcommerce.core.catalog.domain.Sku}s are active or not<br />
   * <br />
   * Note: in the event that the default Sku was added to the list of {@link #getAdditionalSkus()}, it is filtered out
   * so that only a single instance of {@link #getDefaultSku()} is contained in the resulting list
   *
   * @return  all the Skus associated to this Product
   */
  List<Sku> getAllSkus();

  /**
   * Gets the media for this product. This serves as a pass-through to the {@link getDefaultSku()} media
   *
   * @return  the Media for the default Sku associated with this Product
   *
   * @see     org.broadleafcommerce.core.catalog.domain.Sku
   */
  Map<String, Media> getMedia();

  /**
   * Gets the media for this product. This serves as a pass-through to the {@link getDefaultSku()} media
   *
   * @param  media  Media map to set on the default Sku associated with this Product
   *
   * @see    org.broadleafcommerce.core.catalog.domain.Sku
   */
  void setMedia(Map<String, Media> media);

  /**
   * Convenience method for returning all of the media associated with this Product by adding all the media in
   * {@link #getDefaultSku()} as well as all the media in the Skus represented by {@link #getAdditionalSkus()}.
   *
   * @return  all of the Media for all of the Skus for this Product
   */
  Map<String, Media> getAllSkuMedia();

  /**
   * Returns the default {@link org.broadleafcommerce.core.catalog.domain.Category} this product is associated with.
   *
   * @return  the default {@link org.broadleafcommerce.core.catalog.domain.Category} this product is associated with.
   */
  Category getDefaultCategory();

  /**
   * Sets the default {@link org.broadleafcommerce.core.catalog.domain.Category} to associate this product with.
   *
   * @param  defaultCategory  - the default {@link org.broadleafcommerce.core.catalog.domain.Category} to associate this
   *                          product with
   */
  void setDefaultCategory(Category defaultCategory);

  /**
   * Returns the model number of the product.
   *
   * @return  the model number
   */
  String getModel();

  /**
   * Sets the model number of the product.
   *
   * @param  model  DOCUMENT ME!
   */
  void setModel(String model);

  /**
   * Returns the manufacture name for this product.
   *
   * @return  the manufacture name
   */
  String getManufacturer();

  /**
   * Sets the manufacture for this product.
   *
   * @param  manufacturer  DOCUMENT ME!
   */
  void setManufacturer(String manufacturer);

  /**
   * Returns the {@link org.broadleafcommerce.core.catalog.domain.Dimension} for this product<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  a ProductDimensions object
   */
  Dimension getDimension();

  /**
   * Sets the {@link org.broadleafcommerce.core.catalog.domain.Dimension} for this product<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  dimension  DOCUMENT ME!
   */
  void setDimension(Dimension dimension);

  /**
   * Returns the dimension width<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  width dimension of the product
   */
  BigDecimal getWidth();

  /**
   * Sets the dimension width<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  width  DOCUMENT ME!
   */
  void setWidth(BigDecimal width);

  /**
   * Returns the dimension height<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  height dimension of the product
   */
  BigDecimal getHeight();

  /**
   * Sets the dimension height<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  height  DOCUMENT ME!
   */
  void setHeight(BigDecimal height);

  /**
   * Returns the dimension depth<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  width depth of the product
   */
  BigDecimal getDepth();

  /**
   * Sets the dimension depth<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  depth  DOCUMENT ME!
   */
  void setDepth(BigDecimal depth);

  /**
   * Gets the dimension girth<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  the dimension girth
   */
  BigDecimal getGirth();

  /**
   * Sets the dimension girth<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  girth  DOCUMENT ME!
   */
  void setGirth(BigDecimal girth);

  /**
   * Returns the dimension container size.<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  dimension container size
   */
  ContainerSizeType getSize();

  /**
   * Sets the dimension container size<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  size  DOCUMENT ME!
   */
  void setSize(ContainerSizeType size);

  /**
   * Gets the dimension container shape<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  dimension container shape
   */
  ContainerShapeType getContainer();

  /**
   * Sets the dimension container shape<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  container  DOCUMENT ME!
   */
  void setContainer(ContainerShapeType container);

  /**
   * Returns a String representation of the dimension<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  a dimension String
   */
  String getDimensionString();

  /**
   * Returns the weight of the product<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @return  weight of product
   */
  Weight getWeight();

  /**
   * Sets the product weight<br />
   * <br />
   * <b>Note:</b> this is a convenience method that merely serves as a pass-through to the same method via
   * {@link getDefaultSku()}
   *
   * @param  weight  DOCUMENT ME!
   */
  void setWeight(Weight weight);

  /**
   * Returns a List of this product's related Cross Sales.
   *
   * @return  a List of this product's related Cross Sales.
   */
  List<RelatedProduct> getCrossSaleProducts();

  /**
   * Sets the related Cross Sales.
   *
   * @param  crossSaleProducts  DOCUMENT ME!
   */
  void setCrossSaleProducts(List<RelatedProduct> crossSaleProducts);

  /**
   * Returns a List of this product's related Up Sales.
   *
   * @return  a List of this product's related Up Sales.
   */
  List<RelatedProduct> getUpSaleProducts();

  /**
   * Sets the related Up Sales.
   *
   * @param  upSaleProducts  DOCUMENT ME!
   */
  void setUpSaleProducts(List<RelatedProduct> upSaleProducts);

  /**
   * Returns whether or not the product is featured.
   *
   * @return  isFeaturedProduct as Boolean
   */
  boolean isFeaturedProduct();

  /**
   * Sets whether or not the product is featured.
   *
   * @param  isFeaturedProduct  DOCUMENT ME!
   */
  void setFeaturedProduct(boolean isFeaturedProduct);

  /**
   * Generic key-value pair of attributes to associate to this Product for maximum extensibility.
   *
   * @return  the attributes for this Product
   */
  Map<String, ProductAttribute> getProductAttributes();

  /**
   * Sets a generic list of key-value pairs for Product.
   *
   * @param  productAttributes  DOCUMENT ME!
   */
  void setProductAttributes(Map<String, ProductAttribute> productAttributes);

  /**
   * Gets the promotional message for this Product. For instance, this could be a limited-time Product
   *
   * @return  the Product's promotional message
   */
  String getPromoMessage();

  /**
   * Sets the promotional message for this Product.
   *
   * @param  promoMessage  DOCUMENT ME!
   */
  void setPromoMessage(String promoMessage);

  /**
   * The available {@link org.broadleafcommerce.core.catalog.domain.ProductOption}s for this Product. For instance, if
   * this Product is a T-Shirt, you might be able to specify a size and color. This would be modeled by 2
   * {@link org.broadleafcommerce.core.catalog.domain.ProductOption}s, each that could have multiple
   * {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}s (which could be "small" "medium" "large",
   * "blue", "yellow", "green"). For specific pricing or inventory needs on a per-value basis, multiple Skus can be
   * associated to this Product based off of the {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}s
   *
   * @return  the {@link org.broadleafcommerce.core.catalog.domain.ProductOption}s for this Product
   *
   * @see     org.broadleafcommerce.core.catalog.domain.Product#getAdditionalSkus(),
   *          {@link org.broadleafcommerce.core.catalog.domain.ProductOption},
   *          {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}
   */
  List<ProductOption> getProductOptions();

  /**
   * Sets the list of available ProductOptions for this Product.
   *
   * @param  productOptions  DOCUMENT ME!
   */
  void setProductOptions(List<ProductOption> productOptions);

  /**
   * A product can have a designated URL. When set, the ProductHandlerMapping will check for this URL and forward this
   * user to the {@link #getDisplayTemplate()}.
   *
   * <p>Alternatively, most sites will rely on the
   * {@link org.broadleafcommerce.core.catalog.domain.Product#getGeneratedUrl()} to define the url for a product page.
   * </p>
   *
   * @see     org.broadleafcommerce.core.web.catalog.ProductHandlerMapping
   *
   * @return  a product can have a designated URL.
   */
  String getUrl();

  /**
   * Sets the URL that a customer could type in to reach this product.
   *
   * @param  url  DOCUMENT ME!
   */
  void setUrl(String url);

  /**
   * Sets a url-fragment. By default, the system will attempt to create a unique url-fragment for this product by taking
   * the {@link org.broadleafcommerce.core.catalog.domain.Product.getName()} and removing special characters and
   * replacing dashes with spaces.
   *
   * @return  sets a url-fragment.
   */
  String getUrlKey();

  /**
   * Sets a url-fragment to be used with this product. By default, the system will attempt to create a unique
   * url-fragment for this product by taking the {@link org.broadleafcommerce.core.catalog.domain.Product.getName()} and
   * removing special characters and replacing dashes with spaces.
   *
   * @param  url  DOCUMENT ME!
   */
  void setUrlKey(String url);

  /**
   * Returns the name of a display template that is used to render this product. Most implementations have a default
   * template for all products. This allows for the user to define a specific template to be used by this product.
   *
   * @return  the name of a display template that is used to render this product.
   */
  String getDisplayTemplate();

  /**
   * Sets the name of a display template that is used to render this product. Most implementations have a default
   * template for all products. This allows for the user to define a specific template to be used by this product.
   *
   * @param  displayTemplate  DOCUMENT ME!
   */
  void setDisplayTemplate(String displayTemplate);

  /**
   * Generates a URL that can be used to access the product. Builds the url by combining the url of the default category
   * with the getUrlKey() of this product.
   *
   * @return  generates a URL that can be used to access the product.
   */
  String getGeneratedUrl();

  /**
   * Returns a list of the cross sale products for this product as well all cross sale products in all parent categories
   * of this product.
   *
   * @return  the cumulative cross sale products
   */
  List<RelatedProduct> getCumulativeCrossSaleProducts();

  /**
   * Returns a list of the upsale products for this product as well as all upsale products in all parent categories of
   * this product.
   *
   * @return  the cumulative upsale products
   */
  List<RelatedProduct> getCumulativeUpSaleProducts();

  /**
   * Removes any currently stored dynamic pricing.
   */
  void clearDynamicPrices();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CategoryProductXref> getAllParentCategoryXrefs();

  /**
   * DOCUMENT ME!
   *
   * @param  allParentCategories  DOCUMENT ME!
   */
  void setAllParentCategoryXrefs(List<CategoryProductXref> allParentCategories);

  /**
   * Returns all parent {@link org.broadleafcommerce.core.catalog.domain.Category}(s) this product is associated with.
   *
   * @deprecated  Use getAllParentCategoryXrefs() instead.
   *
   * @return      the all parent categories for this product
   */
  @Deprecated List<Category> getAllParentCategories();

  /**
   * Sets all parent {@link org.broadleafcommerce.core.catalog.domain.Category}s this product is associated with.
   *
   * @deprecated  Use setAllParentCategoryXrefs() instead.
   *
   * @param       allParentCategories  - a List of all parent {@link org.broadleafcommerce.core.catalog.domain.Category}
   *                                   (s) to associate this product with
   */
  @Deprecated void setAllParentCategories(List<Category> allParentCategories);

  /**
   * Returns the tax code of the product. If the tax code is null, then returns the tax code of this products category.
   *
   * @return  taxCode
   */
  String getTaxCode();

  /**
   * Sets the tax code for this product.
   *
   * @param  taxCode  DOCUMENT ME!
   */
  void setTaxCode(String taxCode);
} // end interface Product
