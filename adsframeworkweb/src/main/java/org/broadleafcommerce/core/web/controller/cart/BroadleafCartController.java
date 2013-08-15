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

package org.broadleafcommerce.core.web.controller.cart;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.service.exception.OfferMaxUseExceededException;
import org.broadleafcommerce.core.order.domain.NullOrderImpl;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.exception.UpdateCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.order.CartState;
import org.broadleafcommerce.core.web.order.model.AddToCartItem;

import org.broadleafcommerce.profile.web.core.CustomerState;

import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.ui.Model;


/**
 * In charge of performing the various modify cart operations.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class BroadleafCartController extends AbstractCartController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static String cartView         = "cart/cart";

  /** DOCUMENT ME! */
  protected static String cartPageRedirect = "redirect:/cart";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Takes in an item request, adds the item to the customer's current cart, and returns.
   *
   * <p>If the method was invoked via an AJAX call, it will render the "ajax/cart" template. Otherwise, it will perform
   * a 302 redirect to "/cart"</p>
   *
   * @param   request      DOCUMENT ME!
   * @param   response     DOCUMENT ME!
   * @param   model        DOCUMENT ME!
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  takes in an item request, adds the item to the customer's current cart, and returns.
   *
   * @throws  java.io.IOException
   * @throws  org.broadleafcommerce.core.order.service.exception.AddToCartException
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  public String add(HttpServletRequest request, HttpServletResponse response, Model model,
    AddToCartItem itemRequest) throws IOException, AddToCartException, PricingException {
    Order cart = CartState.getCart();

    // If the cart is currently empty, it will be the shared, "null" cart. We must detect this
    // and provision a fresh cart for the current customer upon the first cart add
    if ((cart == null) || (cart instanceof NullOrderImpl)) {
      cart = orderService.createNewCartForCustomer(CustomerState.getCustomer(request));
    }

    updateCartService.validateCart(cart);

    cart = orderService.addItem(cart.getId(), itemRequest, false);
    cart = orderService.save(cart, true);
    CartState.setCart(cart);

    return isAjaxRequest(request) ? getCartView() : getCartPageRedirect();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Attempts to add provided Offer to Cart.
   *
   * @param   request        DOCUMENT ME!
   * @param   response       DOCUMENT ME!
   * @param   model          DOCUMENT ME!
   * @param   customerOffer  DOCUMENT ME!
   *
   * @return  the return view
   *
   * @throws  java.io.IOException
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   * @throws  org.broadleafcommerce.core.order.service.exception.ItemNotFoundException
   * @throws  org.broadleafcommerce.core.offer.service.exception.OfferMaxUseExceededException
   */
  public String addPromo(HttpServletRequest request, HttpServletResponse response, Model model,
    String customerOffer) throws IOException, PricingException {
    Order cart = CartState.getCart();

    Boolean promoAdded = false;
    String  exception  = "";

    OfferCode offerCode = offerService.lookupOfferCodeByCode(customerOffer);

    if (offerCode != null) {
      try {
        orderService.addOfferCode(cart, offerCode, false);
        promoAdded = true;
        cart       = orderService.save(cart, true);
      } catch (OfferMaxUseExceededException e) {
        exception = "Use Limit Exceeded";
      }
    } else {
      exception = "Invalid Code";
    }

    CartState.setCart(cart);

    if (isAjaxRequest(request)) {
      Map<String, Object> extraData = new HashMap<String, Object>();
      extraData.put("promoAdded", promoAdded);
      extraData.put("exception", exception);
      model.addAttribute("blcextradata", new ObjectMapper().writeValueAsString(extraData));

      return getCartView();
    } else {
      model.addAttribute("exception", exception);

      return getCartView();
    }

  } // end method addPromo

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Takes in an item request, adds the item to the customer's current cart, and returns.
   *
   * <p>Calls the addWithOverrides method on the orderService which will honor the override prices on the AddToCartItem
   * request object.</p>
   *
   * <p>Implementors must secure this method to avoid accidentally exposing the ability for malicious clients to
   * override prices by hacking the post parameters.</p>
   *
   * @param   request      DOCUMENT ME!
   * @param   response     DOCUMENT ME!
   * @param   model        DOCUMENT ME!
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  takes in an item request, adds the item to the customer's current cart, and returns.
   *
   * @throws  java.io.IOException
   * @throws  org.broadleafcommerce.core.order.service.exception.AddToCartException
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  public String addWithPriceOverride(HttpServletRequest request, HttpServletResponse response, Model model,
    AddToCartItem itemRequest) throws IOException, AddToCartException, PricingException {
    Order cart = CartState.getCart();

    // If the cart is currently empty, it will be the shared, "null" cart. We must detect this
    // and provision a fresh cart for the current customer upon the first cart add
    if ((cart == null) || (cart instanceof NullOrderImpl)) {
      cart = orderService.createNewCartForCustomer(CustomerState.getCustomer(request));
    }

    updateCartService.validateCart(cart);

    cart = orderService.addItemWithPriceOverrides(cart.getId(), itemRequest, false);
    cart = orderService.save(cart, true);
    CartState.setCart(cart);

    return isAjaxRequest(request) ? getCartView() : getCartPageRedirect();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Renders the cart page.
   *
   * <p>If the method was invoked via an AJAX call, it will render the "ajax/cart" template. Otherwise, it will render
   * the "cart" template.</p>
   *
   * <p>Will reprice the order if the currency has been changed.</p>
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  renders the cart page.
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  public String cart(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
    return getCartView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Cancels the current cart and redirects to the homepage.
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  cancels the current cart and redirects to the homepage.
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  public String empty(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
    Order cart = CartState.getCart();
    orderService.cancelOrder(cart);
    CartState.setCart(null);

    return "redirect:/";
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCartPageRedirect() {
    return cartPageRedirect;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCartView() {
    return cartView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Takes in an item request, updates the quantity of that item in the cart, and returns.
   *
   * <p>If the method was invoked via an AJAX call, it will render the "ajax/cart" template. Otherwise, it will perform
   * a 302 redirect to "/cart"</p>
   *
   * @param   request      DOCUMENT ME!
   * @param   response     DOCUMENT ME!
   * @param   model        DOCUMENT ME!
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  takes in an item request, updates the quantity of that item in the cart, and returns.
   *
   * @throws  java.io.IOException
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   */
  public String remove(HttpServletRequest request, HttpServletResponse response, Model model,
    AddToCartItem itemRequest) throws IOException, PricingException, RemoveFromCartException {
    Order cart = CartState.getCart();

    cart = orderService.removeItem(cart.getId(), itemRequest.getOrderItemId(), false);
    cart = orderService.save(cart, true);
    CartState.setCart(cart);

    if (isAjaxRequest(request)) {
      Map<String, Object> extraData = new HashMap<String, Object>();
      extraData.put("cartItemCount", cart.getItemCount());
      extraData.put("productId", itemRequest.getProductId());
      model.addAttribute("blcextradata", new ObjectMapper().writeValueAsString(extraData));

      return getCartView();
    } else {
      return getCartPageRedirect();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Removes offer from cart.
   *
   * @param   request      DOCUMENT ME!
   * @param   response     DOCUMENT ME!
   * @param   model        DOCUMENT ME!
   * @param   offerCodeId  DOCUMENT ME!
   *
   * @return  the return view
   *
   * @throws  java.io.IOException
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   * @throws  org.broadleafcommerce.core.order.service.exception.ItemNotFoundException
   * @throws  org.broadleafcommerce.core.offer.service.exception.OfferMaxUseExceededException
   */
  public String removePromo(HttpServletRequest request, HttpServletResponse response, Model model,
    Long offerCodeId) throws IOException, PricingException {
    Order cart = CartState.getCart();

    OfferCode offerCode = offerService.findOfferCodeById(offerCodeId);

    orderService.removeOfferCode(cart, offerCode, false);
    cart = orderService.save(cart, true);
    CartState.setCart(cart);

    return isAjaxRequest(request) ? getCartView() : getCartPageRedirect();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Takes in an item request and updates the quantity of that item in the cart. If the quantity was passed in as 0, it
   * will remove the item.
   *
   * <p>If the method was invoked via an AJAX call, it will render the "ajax/cart" template. Otherwise, it will perform
   * a 302 redirect to "/cart"</p>
   *
   * @param   request      DOCUMENT ME!
   * @param   response     DOCUMENT ME!
   * @param   model        DOCUMENT ME!
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  takes in an item request and updates the quantity of that item in the cart.
   *
   * @throws  java.io.IOException
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   * @throws  org.broadleafcommerce.core.order.service.exception.UpdateCartException
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   */
  public String updateQuantity(HttpServletRequest request, HttpServletResponse response, Model model,
    AddToCartItem itemRequest) throws IOException, UpdateCartException, PricingException, RemoveFromCartException {
    Order cart = CartState.getCart();

    cart = orderService.updateItemQuantity(cart.getId(), itemRequest, true);
    cart = orderService.save(cart, false);
    CartState.setCart(cart);

    if (isAjaxRequest(request)) {
      Map<String, Object> extraData = new HashMap<String, Object>();
      extraData.put("productId", itemRequest.getProductId());
      extraData.put("cartItemCount", cart.getItemCount());
      model.addAttribute("blcextradata", new ObjectMapper().writeValueAsString(extraData));

      return getCartView();
    } else {
      return getCartPageRedirect();
    }
  }

} // end class BroadleafCartController
