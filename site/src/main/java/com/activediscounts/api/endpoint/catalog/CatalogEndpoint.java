/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.activediscounts.api.endpoint.catalog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.broadleafcommerce.core.web.api.wrapper.CategoriesWrapper;
import org.broadleafcommerce.core.web.api.wrapper.CategoryAttributeWrapper;
import org.broadleafcommerce.core.web.api.wrapper.CategoryWrapper;
import org.broadleafcommerce.core.web.api.wrapper.MediaWrapper;
import org.broadleafcommerce.core.web.api.wrapper.ProductAttributeWrapper;
import org.broadleafcommerce.core.web.api.wrapper.ProductWrapper;
import org.broadleafcommerce.core.web.api.wrapper.RelatedProductWrapper;
import org.broadleafcommerce.core.web.api.wrapper.SearchResultsWrapper;
import org.broadleafcommerce.core.web.api.wrapper.SkuAttributeWrapper;
import org.broadleafcommerce.core.web.api.wrapper.SkuWrapper;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;


/**
 * This is a reference REST API endpoint for catalog. This can be modified, used as is, or removed. The purpose is to
 * provide an out of the box RESTful catalog service implementation, but also to allow the implementor to have fine
 * control over the actual API, URIs, and general JAX-RS annotations.
 *
 * @author   Kelly Tisdell
 * @version  $Revision$, $Date$
 */
@Component
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Path("/catalog/")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Scope("singleton")
public class CatalogEndpoint extends org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findActiveSubCategories(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, int, int)
   */
  @GET @Override
  @Path("category/{id}/activeSubcategories")
  public CategoriesWrapper findActiveSubCategories(@Context HttpServletRequest request,
    @PathParam("id") Long id,
    @DefaultValue("20")
    @QueryParam("limit")
    int limit,
    @DefaultValue("0")
    @QueryParam("offset")
    int offset) {
    return super.findActiveSubCategories(request, id, limit, offset);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findAllCategories(javax.servlet.http.HttpServletRequest,
   *       java.lang.String, int, int)
   */
  @GET @Override
  @Path("categories")
  public CategoriesWrapper findAllCategories(@Context HttpServletRequest request,
    @QueryParam("name") String name,
    @DefaultValue("20")
    @QueryParam("limit")
    int limit,
    @DefaultValue("0")
    @QueryParam("offset")
    int offset) {
    return super.findAllCategories(request, name, limit, offset);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findCategoryAttributesForCategory(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("category/{id}/category-attributes")
  public List<CategoryAttributeWrapper> findCategoryAttributesForCategory(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findCategoryAttributesForCategory(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findCategoryById(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, int, int, int, int)
   */
  @GET @Override
  @Path("category/{id}")
  public CategoryWrapper findCategoryById(@Context HttpServletRequest request,
    @PathParam("id") Long id,
    @DefaultValue("20")
    @QueryParam("productLimit")
    int productLimit,
    @DefaultValue("1")
    @QueryParam("productOffset")
    int productOffset,
    @DefaultValue("20")
    @QueryParam("subcategoryLimit")
    int subcategoryLimit,
    @DefaultValue("1")
    @QueryParam("subcategoryOffset")
    int subcategoryOffset) {
    return super.findCategoryById(request, id, productLimit, productOffset,
        subcategoryLimit, subcategoryOffset);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findCategoryByIdOrName(javax.servlet.http.HttpServletRequest,
   *       java.lang.String, int, int, int, int)
   */
  @GET @Override
  @Path("category")
  public CategoryWrapper findCategoryByIdOrName(@Context HttpServletRequest request,
    @QueryParam("searchParameter") String searchParameter,
    @DefaultValue("20")
    @QueryParam("productLimit")
    int productLimit,
    @DefaultValue("1")
    @QueryParam("productOffset")
    int productOffset,
    @DefaultValue("20")
    @QueryParam("subcategoryLimit")
    int subcategoryLimit,
    @DefaultValue("1")
    @QueryParam("subcategoryOffset")
    int subcategoryOffset) {
    return super.findCategoryByIdOrName(request, searchParameter,
        productLimit, productOffset, subcategoryLimit, subcategoryOffset);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findCrossSaleProductsByProduct(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, int, int)
   */
  @GET @Override
  @Path("product/{id}/related-products/crosssale")
  public List<RelatedProductWrapper> findCrossSaleProductsByProduct(@Context HttpServletRequest request,
    @PathParam("id") Long id,
    @DefaultValue("20")
    @QueryParam("limit")
    int limit,
    @DefaultValue("0")
    @QueryParam("offset")
    int offset) {
    return super.findCrossSaleProductsByProduct(request, id, limit, offset);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findDefaultSkuByProductId(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("product/{id}/defaultSku")
  public SkuWrapper findDefaultSkuByProductId(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findDefaultSkuByProductId(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findMediaForCategory(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("category/{id}/media")
  public List<MediaWrapper> findMediaForCategory(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findMediaForCategory(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findMediaForProduct(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("product/{id}/media")
  public List<MediaWrapper> findMediaForProduct(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findMediaForProduct(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findMediaForSku(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("sku/{id}/media")
  public List<MediaWrapper> findMediaForSku(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findMediaForSku(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findParentCategoriesForProduct(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("product/{id}/categories")
  public CategoriesWrapper findParentCategoriesForProduct(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findParentCategoriesForProduct(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findProductAttributesForProduct(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("product/{id}/product-attributes")
  public List<ProductAttributeWrapper> findProductAttributesForProduct(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findProductAttributesForProduct(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findProductById(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("product/{id}")
  public ProductWrapper findProductById(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findProductById(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findProductsByCategoryAndQuery(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, java.lang.String, java.lang.Integer, java.lang.Integer)
   */
  @GET @Override
  @Path("search/category/{categoryId}/products")
  public SearchResultsWrapper findProductsByCategoryAndQuery(@Context HttpServletRequest request,
    @PathParam("categoryId") Long categoryId,
    @QueryParam("q") String q,
    @DefaultValue("15")
    @QueryParam("pageSize")
    Integer pageSize,
    @DefaultValue("1")
    @QueryParam("page")
    Integer page) {
    return super.findProductsByCategoryAndQuery(request, categoryId, q, pageSize, page);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findProductsByQuery(javax.servlet.http.HttpServletRequest,
   *       java.lang.String, java.lang.Integer, java.lang.Integer)
   */
  @GET @Override
  @Path("search/products")
  public SearchResultsWrapper findProductsByQuery(@Context HttpServletRequest request,
    @QueryParam("q") String q,
    @DefaultValue("15")
    @QueryParam("pageSize")
    Integer pageSize,
    @DefaultValue("1")
    @QueryParam("page")
    Integer page) {
    return super.findProductsByQuery(request, q, pageSize, page);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findSkuAttributesForSku(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("sku/{id}/sku-attributes")
  public List<SkuAttributeWrapper> findSkuAttributesForSku(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findSkuAttributesForSku(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findSkuById(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("sku/{id}")
  public SkuWrapper findSkuById(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findSkuById(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findSkusByProductById(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long)
   */
  @GET @Override
  @Path("product/{id}/skus")
  public List<SkuWrapper> findSkusByProductById(@Context HttpServletRequest request,
    @PathParam("id") Long id) {
    return super.findSkusByProductById(request, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findSubCategories(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, int, int, boolean)
   */
  @GET @Override
  @Path("category/{id}/categories")
  public CategoriesWrapper findSubCategories(@Context HttpServletRequest request,
    @PathParam("id") Long id,
    @DefaultValue("20")
    @QueryParam("limit")
    int limit,
    @DefaultValue("0")
    @QueryParam("offset")
    int offset,
    @DefaultValue("true")
    @QueryParam("active")
    boolean active) {
    return super.findSubCategories(request, id, limit, offset, active);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint#findUpSaleProductsByProduct(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, int, int)
   */
  @GET @Override
  @Path("product/{id}/related-products/upsale")
  public List<RelatedProductWrapper> findUpSaleProductsByProduct(@Context HttpServletRequest request,
    @PathParam("id") Long id,
    @DefaultValue("20")
    @QueryParam("limit")
    int limit,
    @DefaultValue("0")
    @QueryParam("offset")
    int offset) {
    return super.findUpSaleProductsByProduct(request, id, limit, offset);
  }
} // end class CatalogEndpoint
