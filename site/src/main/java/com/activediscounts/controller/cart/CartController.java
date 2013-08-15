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

package com.activediscounts.controller.cart;


import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.ProductOptionValidationException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.exception.RequiredAttributeNotProvidedException;
import org.broadleafcommerce.core.order.service.exception.UpdateCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.cart.BroadleafCartController;
import org.broadleafcommerce.core.web.order.CartState;
import org.broadleafcommerce.core.web.order.model.AddToCartItem;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BroadleafCartController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /*
   * The Heat Clinic does not support adding products with required product options from a category browse page
   * when JavaScript is disabled. When this occurs, we will redirect the user to the full product details page
   * for the given product so that the required options may be chosen.
   */
  /**
   * DOCUMENT ME!
   *
   * @param   request             DOCUMENT ME!
   * @param   response            DOCUMENT ME!
   * @param   model               DOCUMENT ME!
   * @param   redirectAttributes  DOCUMENT ME!
   * @param   addToCartItem       DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IOException         DOCUMENT ME!
   * @throws  PricingException    DOCUMENT ME!
   * @throws  AddToCartException  DOCUMENT ME!
   */
  @RequestMapping(
    value    = "/add",
    produces = { "text/html", "*/*" }
  )
  public String add(HttpServletRequest request, HttpServletResponse response, Model model,
    RedirectAttributes redirectAttributes,
    @ModelAttribute("addToCartItem") AddToCartItem addToCartItem) throws IOException, PricingException,
    AddToCartException {
    try {
      return super.add(request, response, model, addToCartItem);
    } catch (AddToCartException e) {
      Product product = catalogService.findProductById(addToCartItem.getProductId());

      return "redirect:" + product.getUrl();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /*
   * The Heat Clnic does not show the cart when a product is added. Instead, when the product is added via an AJAX
   * POST that requests JSON, we only need to return a few attributes to update the state of the page. The most
   * efficient way to do this is to call the regular add controller method, but instead return a map that contains
   * the necessary attributes. By using the @ResposeBody tag, Spring will automatically use Jackson to convert the
   * returned object into JSON for easy processing via JavaScript.
   */
  /**
   * DOCUMENT ME!
   *
   * @param   request        DOCUMENT ME!
   * @param   response       DOCUMENT ME!
   * @param   model          DOCUMENT ME!
   * @param   addToCartItem  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IOException         DOCUMENT ME!
   * @throws  PricingException    DOCUMENT ME!
   * @throws  AddToCartException  DOCUMENT ME!
   */
  @RequestMapping(
    value    = "/add",
    produces = "application/json"
  )
  @ResponseBody public Map<String, Object> addJson(HttpServletRequest request, HttpServletResponse response,
    Model model,
    @ModelAttribute("addToCartItem") AddToCartItem addToCartItem) throws IOException, PricingException,
    AddToCartException {
    Map<String, Object> responseMap = new HashMap<String, Object>();

    try {
      super.add(request, response, model, addToCartItem);

      if ((addToCartItem.getItemAttributes() == null) || (addToCartItem.getItemAttributes().size() == 0)) {
        responseMap.put("productId", addToCartItem.getProductId());
      }

      responseMap.put("productName", catalogService.findProductById(addToCartItem.getProductId()).getName());
      responseMap.put("quantityAdded", addToCartItem.getQuantity());
      responseMap.put("cartItemCount", String.valueOf(CartState.getCart().getItemCount()));

      if ((addToCartItem.getItemAttributes() == null) || (addToCartItem.getItemAttributes().size() == 0)) {
        // We don't want to return a productId to hide actions for when it is a product that has multiple
        // product options. The user may want the product in another version of the options as well.
        responseMap.put("productId", addToCartItem.getProductId());
      }
    } catch (AddToCartException e) {
      if (e.getCause() instanceof RequiredAttributeNotProvidedException) {
        responseMap.put("error", "allOptionsRequired");
      } else if (e.getCause() instanceof ProductOptionValidationException) {
        ProductOptionValidationException exception = (ProductOptionValidationException) e.getCause();
        responseMap.put("error", "productOptionValidationError");
        responseMap.put("errorCode", exception.getErrorCode());
        responseMap.put("errorMessage", exception.getMessage());
        // blMessages.getMessage(exception.get, lfocale))
      } else {
        throw e;
      }
    } // end try-catch

    return responseMap;
  } // end method addJson

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.cart.BroadleafCartController#addPromo(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model, java.lang.String)
   */
  @Override
  @RequestMapping("/promo")
  public String addPromo(HttpServletRequest request, HttpServletResponse response, Model model,
    @RequestParam("promoCode") String customerOffer) throws IOException, PricingException {
    return super.addPromo(request, response, model, customerOffer);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.cart.BroadleafCartController#cart(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model)
   */
  @Override
  @RequestMapping("")
  public String cart(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
    return super.cart(request, response, model);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.cart.BroadleafCartController#empty(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model)
   */
  @Override
  @RequestMapping("/empty")
  public String empty(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
    // return super.empty(request, response, model);
    return "ajaxredirect:/";

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.cart.BroadleafCartController#remove(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model, org.broadleafcommerce.core.web.order.model.AddToCartItem)
   */
  @Override
  @RequestMapping("/remove")
  public String remove(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("addToCartItem") AddToCartItem addToCartItem) throws IOException, PricingException,
    RemoveFromCartException {
    return super.remove(request, response, model, addToCartItem);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.cart.BroadleafCartController#removePromo(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model, java.lang.Long)
   */
  @Override
  @RequestMapping("/promo/remove")
  public String removePromo(HttpServletRequest request, HttpServletResponse response, Model model,
    @RequestParam("offerCodeId") Long offerCodeId) throws IOException, PricingException {
    return super.removePromo(request, response, model, offerCodeId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request             DOCUMENT ME!
   * @param   response            DOCUMENT ME!
   * @param   model               DOCUMENT ME!
   * @param   redirectAttributes  DOCUMENT ME!
   * @param   addToCartItem       DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IOException              DOCUMENT ME!
   * @throws  PricingException         DOCUMENT ME!
   * @throws  UpdateCartException      DOCUMENT ME!
   * @throws  RemoveFromCartException  DOCUMENT ME!
   */
  @RequestMapping("/updateQuantity")
  public String updateQuantity(HttpServletRequest request, HttpServletResponse response, Model model,
    RedirectAttributes redirectAttributes,
    @ModelAttribute("addToCartItem") AddToCartItem addToCartItem) throws IOException, PricingException,
    UpdateCartException, RemoveFromCartException {
    return super.updateQuantity(request, response, model, addToCartItem);
  }

} // end class CartController
