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

import java.util.List;

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

import org.broadleafcommerce.core.web.api.wrapper.FulfillmentGroupItemWrapper;
import org.broadleafcommerce.core.web.api.wrapper.FulfillmentGroupWrapper;
import org.broadleafcommerce.core.web.api.wrapper.FulfillmentOptionWrapper;
import org.broadleafcommerce.core.web.api.wrapper.OrderWrapper;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;


/**
 * This is a reference REST API endpoint for fulfillment groups. This can be modified, used as is, or removed. The
 * purpose is to provide an out of the box RESTful fulfillment group service implementation, but also to allow the
 * implementor to have fine control over the actual API, URIs, and general JAX-RS annotations.
 *
 * @author   Kelly Tisdell
 * @version  $Revision$, $Date$
 */
@Component
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Path("/cart/fulfillment/")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Scope("singleton")
public class FulfillmentEndpoint extends org.broadleafcommerce.core.web.api.endpoint.order.FulfillmentEndpoint {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.FulfillmentEndpoint#addFulfillmentGroupToOrder(javax.servlet.http.HttpServletRequest,
   *       org.broadleafcommerce.core.web.api.wrapper.FulfillmentGroupWrapper, boolean)
   */
  @Override @POST
  @Path("group")
  public FulfillmentGroupWrapper addFulfillmentGroupToOrder(@Context HttpServletRequest request,
    FulfillmentGroupWrapper wrapper,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.addFulfillmentGroupToOrder(request, wrapper, priceOrder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.FulfillmentEndpoint#addFulfillmentOptionToFulfillmentGroup(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, java.lang.Long, boolean)
   */
  @Override @PUT
  @Path("group/{fulfillmentGroupId}/option/{fulfillmentOptionId}")
  public FulfillmentGroupWrapper addFulfillmentOptionToFulfillmentGroup(@Context HttpServletRequest request,
    @PathParam("fulfillmentGroupId") Long fulfillmentGroupId,
    @PathParam("fulfillmentOptionId") Long fulfillmentOptionId,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.addFulfillmentOptionToFulfillmentGroup(request,
        fulfillmentGroupId, fulfillmentOptionId, priceOrder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.FulfillmentEndpoint#addItemToFulfillmentGroup(javax.servlet.http.HttpServletRequest,
   *       java.lang.Long, org.broadleafcommerce.core.web.api.wrapper.FulfillmentGroupItemWrapper, boolean)
   */
  @Override @PUT
  @Path("group/{fulfillmentGroupId}")
  public FulfillmentGroupWrapper addItemToFulfillmentGroup(@Context HttpServletRequest request,
    @PathParam("fulfillmentGroupId") Long fulfillmentGroupId,
    FulfillmentGroupItemWrapper wrapper,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.addItemToFulfillmentGroup(request, fulfillmentGroupId, wrapper, priceOrder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.FulfillmentEndpoint#findFulfillmentGroupsForOrder(javax.servlet.http.HttpServletRequest)
   */
  @GET @Override
  @Path("groups")
  public List<FulfillmentGroupWrapper> findFulfillmentGroupsForOrder(@Context HttpServletRequest request) {
    return super.findFulfillmentGroupsForOrder(request);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.FulfillmentEndpoint#findFulfillmentOptions(javax.servlet.http.HttpServletRequest,
   *       java.lang.String)
   */
  @GET @Override
  @Path("options")
  public List<FulfillmentOptionWrapper> findFulfillmentOptions(@Context HttpServletRequest request,
    @QueryParam("fulfillmentType") String fulfillmentType) {
    return super.findFulfillmentOptions(request, fulfillmentType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.endpoint.order.FulfillmentEndpoint#removeAllFulfillmentGroupsFromOrder(javax.servlet.http.HttpServletRequest,
   *       boolean)
   */
  @DELETE @Override
  @Path("groups")
  public OrderWrapper removeAllFulfillmentGroupsFromOrder(@Context HttpServletRequest request,
    @DefaultValue("true")
    @QueryParam("priceOrder")
    boolean priceOrder) {
    return super.removeAllFulfillmentGroupsFromOrder(request, priceOrder);
  }


} // end class FulfillmentEndpoint
