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

package org.broadleafcommerce.core.web.api.endpoint.order;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.service.OfferService;
import org.broadleafcommerce.core.offer.service.exception.OfferMaxUseExceededException;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.ItemNotFoundException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.exception.UpdateCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.api.endpoint.BaseEndpoint;
import org.broadleafcommerce.core.web.api.wrapper.OrderWrapper;
import org.broadleafcommerce.core.web.order.CartState;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.web.core.CustomerState;


/**
 * This endpoint depends on JAX-RS to provide cart services. It should be extended by components that actually wish to
 * provide an endpoint. The annotations such as @Path, @Scope, @Context, @PathParam, @QueryParam,
 *
 * @GET      , @POST, @PUT, and @DELETE are purposely not provided here to allow implementors finer control over the
 *           details of the endpoint.
 *
 *           <p>User: Kelly Tisdell Date: 4/10/12</p>
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class CartEndpoint extends BaseEndpoint {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerService")
  protected CustomerService customerService;

  /** DOCUMENT ME! */
  @Resource(name = "blOfferService")
  protected OfferService offerService;

  /** DOCUMENT ME! */
  @Resource(name = "blOrderService")
  protected OrderService orderService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request     DOCUMENT ME!
   * @param   promoCode   DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  WebApplicationException  DOCUMENT ME!
   */
  public OrderWrapper addOfferCode(HttpServletRequest request,
    String promoCode,
    boolean priceOrder) {
    Order cart = CartState.getCart();

    if (cart == null) {
      throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
          "Cart could not be found").build());
    }

    OfferCode offerCode = offerService.lookupOfferCodeByCode(promoCode);

    if (offerCode == null) {
      throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
          "Offer Code could not be found").build());
    }

    try {
      cart = orderService.addOfferCode(cart, offerCode, priceOrder);

      OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
      wrapper.wrapDetails(cart, request);

      return wrapper;
    } catch (PricingException e) {
      throw new WebApplicationException(e,
        Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
          "An error occured pricing the cart.").build());
    } catch (OfferMaxUseExceededException e) {
      throw new WebApplicationException(e,
        Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(
          "The offer (promo) code provided has exceeded its max usages: " + promoCode).build());
    }


  } // end method addOfferCode

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method takes in a categoryId and productId as path parameters. In addition, query parameters can be supplied
   * including:
   *
   * <ul>
   *   <li>skuId</li>
   *   <li>quantity</li>
   *   <li>priceOrder</li>
   *   <li>You must provide a ProductId OR ProductId with product options. Product options can be posted as form or
   *     querystring parameters. You must pass in the ProductOption attributeName as the key and the ProductOptionValue
   *     attributeValue as the value. See {@link org.broadleafcommerce.core.web.api.endpoint.catalog.CatalogEndpoint}.
   *   </li>
   * </ul>
   *
   * @param   request     DOCUMENT ME!
   * @param   uriInfo     DOCUMENT ME!
   * @param   productId   DOCUMENT ME!
   * @param   categoryId  DOCUMENT ME!
   * @param   quantity    DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  OrderWrapper
   *
   * @throws  WebApplicationException  DOCUMENT ME!
   */
  public OrderWrapper addProductToOrder(HttpServletRequest request,
    UriInfo uriInfo,
    Long productId,
    Long categoryId,
    int quantity,
    boolean priceOrder) {
    Order cart = CartState.getCart();

    if (cart != null) {
      try {
        // We allow product options to be submitted via form post or via query params.  We need to take
        // the product options and build a map with them...
        MultivaluedMap<String, String> multiValuedMap = uriInfo.getQueryParameters();
        HashMap<String, String>        productOptions = new HashMap<String, String>();

        // Fill up a map of key values that will represent product options
        Set<String> keySet = multiValuedMap.keySet();

        for (String key : keySet) {
          if (multiValuedMap.getFirst(key) != null) {
            // Product options should be returned with "productOption." as a prefix. We'll look for those, and
            // remove the prefix.
            if (key.startsWith("productOption.")) {
              productOptions.put(StringUtils.removeStart(key, "productOption."), multiValuedMap.getFirst(key));
            }
          }
        }

        OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();
        orderItemRequestDTO.setProductId(productId);
        orderItemRequestDTO.setCategoryId(categoryId);
        orderItemRequestDTO.setQuantity(quantity);

        // If we have product options set them on the DTO
        if (productOptions.size() > 0) {
          orderItemRequestDTO.setItemAttributes(productOptions);
        }

        Order order = orderService.addItem(cart.getId(), orderItemRequestDTO, priceOrder);
        order = orderService.save(order, priceOrder);

        OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
        wrapper.wrapDetails(order, request);

        return wrapper;
      } catch (PricingException e) {
        throw new WebApplicationException(e,
          Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
            "An error occured pricing the order.").build());
      } catch (AddToCartException e) {
        if (e.getCause() != null) {
          throw new WebApplicationException(e,
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
              "" + e.getCause()).build());
        }

        throw new WebApplicationException(e,
          Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
            "An error occured adding the item to the cart." + e.getCause()).build());
      } // end try-catch
    } // end if

    throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
        "Cart could not be found").build());
  } // end method addProductToOrder

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Create a new {@code Order} for {@code Customer}.
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  the cart for the customer
   */
  public OrderWrapper createNewCartForCustomer(HttpServletRequest request) {
    Customer customer = CustomerState.getCustomer(request);

    if (customer == null) {
      customer = customerService.createCustomerFromId(null);
    }

    Order cart = orderService.findCartForCustomer(customer);

    if (cart == null) {
      cart = orderService.createNewCartForCustomer(customer);
      CartState.setCart(cart);
    }

    OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
    wrapper.wrapDetails(cart, request);

    return wrapper;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Search for {@code Order} by {@code Customer}.
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  the cart for the customer
   *
   * @throws  WebApplicationException  DOCUMENT ME!
   */
  public OrderWrapper findCartForCustomer(HttpServletRequest request) {
    Order cart = CartState.getCart();

    if (cart != null) {
      OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
      wrapper.wrapDetails(cart, request);

      return wrapper;
    }

    throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
        "Cart could not be found").build());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request     DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  WebApplicationException  DOCUMENT ME!
   */
  public OrderWrapper removeAllOfferCodes(HttpServletRequest request,
    boolean priceOrder) {
    Order cart = CartState.getCart();

    if (cart == null) {
      throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
          "Cart could not be found").build());
    }

    try {
      cart = orderService.removeAllOfferCodes(cart, priceOrder);

      OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
      wrapper.wrapDetails(cart, request);

      return wrapper;
    } catch (PricingException e) {
      throw new WebApplicationException(e,
        Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
          "An error occured pricing the cart.").build());
    }

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request     DOCUMENT ME!
   * @param   itemId      DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  WebApplicationException  DOCUMENT ME!
   */
  public OrderWrapper removeItemFromOrder(HttpServletRequest request,
    Long itemId,
    boolean priceOrder) {
    Order cart = CartState.getCart();

    if (cart != null) {
      try {
        Order order = orderService.removeItem(cart.getId(), itemId, priceOrder);
        order = orderService.save(order, priceOrder);

        OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
        wrapper.wrapDetails(order, request);

        return wrapper;
      } catch (PricingException e) {
        throw new WebApplicationException(e,
          Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
            "An error occured pricing the cart.").build());
      } catch (RemoveFromCartException e) {
        if (e.getCause() instanceof ItemNotFoundException) {
          throw new WebApplicationException(e,
            Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
              "Could not find order item id " + itemId).build());
        } else {
          throw new WebApplicationException(e,
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
              "An error occured removing the item to the cart.").build());
        }
      }
    } // end if

    throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
        "Cart could not be found").build());

  } // end method removeItemFromOrder

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request     DOCUMENT ME!
   * @param   promoCode   DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  WebApplicationException  DOCUMENT ME!
   */
  public OrderWrapper removeOfferCode(HttpServletRequest request,
    String promoCode,
    boolean priceOrder) {
    Order cart = CartState.getCart();

    if (cart == null) {
      throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
          "Cart could not be found").build());
    }

    OfferCode offerCode = offerService.lookupOfferCodeByCode(promoCode);

    if (offerCode == null) {
      throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
          "Offer code was invalid or could not be found.").build());
    }

    try {
      cart = orderService.removeOfferCode(cart, offerCode, priceOrder);

      OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
      wrapper.wrapDetails(cart, request);

      return wrapper;
    } catch (PricingException e) {
      throw new WebApplicationException(e,
        Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
          "An error occured pricing the cart.").build());
    }

  } // end method removeOfferCode

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request     DOCUMENT ME!
   * @param   itemId      DOCUMENT ME!
   * @param   quantity    DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  WebApplicationException  DOCUMENT ME!
   */
  public OrderWrapper updateItemQuantity(HttpServletRequest request,
    Long itemId,
    Integer quantity,
    boolean priceOrder) {
    Order cart = CartState.getCart();

    if (cart != null) {
      try {
        OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();
        orderItemRequestDTO.setOrderItemId(itemId);
        orderItemRequestDTO.setQuantity(quantity);

        Order order = orderService.updateItemQuantity(cart.getId(), orderItemRequestDTO, priceOrder);
        order = orderService.save(order, priceOrder);

        OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
        wrapper.wrapDetails(order, request);

        return wrapper;
      } catch (UpdateCartException e) {
        if (e.getCause() instanceof ItemNotFoundException) {
          throw new WebApplicationException(e,
            Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
              "Could not find order item id " + itemId).build());
        } else {
          throw new WebApplicationException(e,
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
              "An error occured removing the item to the cart.").build());
        }
      } catch (RemoveFromCartException e) {
        if (e.getCause() instanceof ItemNotFoundException) {
          throw new WebApplicationException(e,
            Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
              "Could not find order item id " + itemId).build());
        } else {
          throw new WebApplicationException(e,
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
              "An error occured removing the item to the cart.").build());
        }
      } catch (PricingException pe) {
        throw new WebApplicationException(pe,
          Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
            "An error occured pricing the cart.").build());
      } // end try-catch
    } // end if

    throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
        "Cart could not be found").build());
  } // end method updateItemQuantity
} // end class CartEndpoint
