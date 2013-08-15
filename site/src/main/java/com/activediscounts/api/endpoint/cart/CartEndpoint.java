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

package com.activediscounts.api.endpoint.cart;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.broadleafcommerce.core.web.api.wrapper.OrderWrapper;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;


/**
 * This is a reference REST API endpoint for cart. This can be modified, used as is, or removed. The purpose is to
 * provide an out of the box RESTful cart service implementation, but also to allow the implementor to have fine control
 * over the actual API, URIs, and general JAX-RS annotations.
 *
 * @author   Kelly Tisdell
 * @version  $Revision$, $Date$
 */
@Component
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Path("/cart/")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Scope("singleton")
public class CartEndpoint extends org.broadleafcommerce.core.web.api.endpoint.order.CartEndpoint {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.CartEndpoint#addOfferCode(javax.servlet.http.HttpServletRequest,
   *       java.lang.String, boolean)
   */
  @Override @POST
  @Path("offer")
  public OrderWrapper addOfferCode(@Context HttpServletRequest request,
    @QueryParam("promoCode") String promoCode,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.addOfferCode(request, promoCode, priceOrder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.CartEndpoint#addProductToOrder(javax.servlet.http.HttpServletRequest,
   *       javax.ws.rs.core.UriInfo, java.lang.Long, java.lang.Long, int, boolean)
   */
  @Override @POST
  @Path("{productId}")
  public OrderWrapper addProductToOrder(@Context HttpServletRequest request, @Context UriInfo uriInfo,
    @PathParam("productId") Long productId,
    @QueryParam("categoryId") Long categoryId,
    @DefaultValue("1")
    @QueryParam("quantity")
    int quantity,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.addProductToOrder(request, uriInfo, productId, categoryId, quantity, priceOrder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.CartEndpoint#createNewCartForCustomer(javax.servlet.http.HttpServletRequest)
   */
  @Override @POST public OrderWrapper createNewCartForCustomer(@Context HttpServletRequest request) {
    return super.createNewCartForCustomer(request);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.CartEndpoint#findCartForCustomer(javax.servlet.http.HttpServletRequest)
   */
  @GET @Override public OrderWrapper findCartForCustomer(@Context HttpServletRequest request) {
    return super.findCartForCustomer(request);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.CartEndpoint#removeAllOfferCodes(javax.servlet.http.HttpServletRequest,
   *       boolean)
   */
  @DELETE @Override
  @Path("offers")
  public OrderWrapper removeAllOfferCodes(@Context HttpServletRequest request,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.removeAllOfferCodes(request, priceOrder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.CartEndpoint#removeItemFromOrder(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, boolean)
   */
  @DELETE @Override
  @Path("items/{itemId}")
  public OrderWrapper removeItemFromOrder(@Context HttpServletRequest request,
    @PathParam("itemId") Long itemId,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.removeItemFromOrder(request, itemId, priceOrder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.CartEndpoint#removeOfferCode(javax.servlet.http.HttpServletRequest,
   *       java.lang.String, boolean)
   */
  @DELETE @Override
  @Path("offer")
  public OrderWrapper removeOfferCode(@Context HttpServletRequest request,
    @QueryParam("promoCode") String promoCode,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.removeOfferCode(request, promoCode, priceOrder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.CartEndpoint#updateItemQuantity(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, java.lang.Integer, boolean)
   */
  @Override @PUT
  @Path("items/{itemId}")
  public OrderWrapper updateItemQuantity(@Context HttpServletRequest request,
    @PathParam("itemId") Long itemId,
    @QueryParam("quantity") Integer quantity,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.updateItemQuantity(request, itemId, quantity, priceOrder);
  }
} // end class CartEndpoint
