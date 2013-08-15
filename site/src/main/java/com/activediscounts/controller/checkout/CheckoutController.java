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

package com.activediscounts.controller.checkout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.checkout.model.BillingInfoForm;
import org.broadleafcommerce.core.web.checkout.model.OrderInfoForm;
import org.broadleafcommerce.core.web.checkout.model.OrderMultishipOptionForm;
import org.broadleafcommerce.core.web.checkout.model.ShippingInfoForm;
import org.broadleafcommerce.core.web.controller.checkout.BroadleafCheckoutController;
import org.broadleafcommerce.core.web.order.CartState;

import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.web.core.CustomerState;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Controller
@RequestMapping("/checkout")
public class CheckoutController extends BroadleafCheckoutController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /*
   * The Checkout page for Heat Clinic will have the shipping information pre-populated
   * with an address if the fulfillment group has an address and fulfillment option
   * associated with it. It also assumes that there is only one payment info of type
   * credit card on the order. If so, then the billing address will be pre-populated.
   */
  /**
   * DOCUMENT ME!
   *
   * @param   request             DOCUMENT ME!
   * @param   response            DOCUMENT ME!
   * @param   model               DOCUMENT ME!
   * @param   orderInfoForm       DOCUMENT ME!
   * @param   shippingForm        DOCUMENT ME!
   * @param   billingForm         DOCUMENT ME!
   * @param   redirectAttributes  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping("")
  public String checkout(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("orderInfoForm") OrderInfoForm orderInfoForm,
    @ModelAttribute("shippingInfoForm") ShippingInfoForm shippingForm,
    @ModelAttribute("billingInfoForm") BillingInfoForm billingForm, RedirectAttributes redirectAttributes) {
    prepopulateCheckoutForms(CartState.getCart(), orderInfoForm, shippingForm, billingForm);

    return super.checkout(request, response, model, redirectAttributes);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request        DOCUMENT ME!
   * @param   response       DOCUMENT ME!
   * @param   model          DOCUMENT ME!
   * @param   orderInfoForm  DOCUMENT ME!
   * @param   shippingForm   DOCUMENT ME!
   * @param   billingForm    DOCUMENT ME!
   * @param   result         DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  CheckoutException  DOCUMENT ME!
   * @throws  PricingException   DOCUMENT ME!
   * @throws  ServiceException   DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/complete",
    method = RequestMethod.POST
  )
  public String completeSecureCreditCardCheckout(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("orderInfoForm") OrderInfoForm orderInfoForm,
    @ModelAttribute("shippingInfoForm") ShippingInfoForm shippingForm,
    @ModelAttribute("billingInfoForm") BillingInfoForm billingForm,
    BindingResult result) throws CheckoutException, PricingException, ServiceException {
    prepopulateCheckoutForms(CartState.getCart(), null, shippingForm, billingForm);

    return super.completeSecureCreditCardCheckout(request, response, model, billingForm, result);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.checkout.BroadleafCheckoutController#convertToSingleship(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model)
   */
  @Override
  @RequestMapping(
    value  = "/singleship",
    method = RequestMethod.GET
  )
  public String convertToSingleship(HttpServletRequest request, HttpServletResponse response, Model model)
    throws PricingException {
    return super.convertToSingleship(request, response, model);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.checkout.BroadleafCheckoutController#saveGlobalOrderDetails(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model, org.broadleafcommerce.core.web.checkout.model.OrderInfoForm, org.springframework.validation.BindingResult)
   */
  @Override
  @RequestMapping(
    value  = "/savedetails",
    method = RequestMethod.POST
  )
  public String saveGlobalOrderDetails(HttpServletRequest request, Model model,
    @ModelAttribute("orderInfoForm") OrderInfoForm orderInfoForm, BindingResult result) throws ServiceException {
    return super.saveGlobalOrderDetails(request, model, orderInfoForm, result);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.checkout.BroadleafCheckoutController#saveMultiship(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model,
   *       org.broadleafcommerce.core.web.checkout.model.OrderMultishipOptionForm,
   *       org.springframework.validation.BindingResult)
   */
  @Override
  @RequestMapping(
    value  = "/multiship",
    method = RequestMethod.POST
  )
  public String saveMultiship(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("orderMultishipOptionForm") OrderMultishipOptionForm orderMultishipOptionForm,
    BindingResult result) throws PricingException, ServiceException {
    return super.saveMultiship(request, response, model, orderMultishipOptionForm, result);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.checkout.BroadleafCheckoutController#saveMultishipAddAddress(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model,
   *       org.broadleafcommerce.core.web.checkout.model.ShippingInfoForm,
   *       org.springframework.validation.BindingResult)
   */
  @Override
  @RequestMapping(
    value  = "/add-address",
    method = RequestMethod.POST
  )
  public String saveMultishipAddAddress(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("addressForm") ShippingInfoForm addressForm, BindingResult result) throws ServiceException {
    return super.saveMultishipAddAddress(request, response, model, addressForm, result);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request        DOCUMENT ME!
   * @param   response       DOCUMENT ME!
   * @param   model          DOCUMENT ME!
   * @param   orderInfoForm  DOCUMENT ME!
   * @param   billingForm    DOCUMENT ME!
   * @param   shippingForm   DOCUMENT ME!
   * @param   result         DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   * @throws  ServiceException  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/singleship",
    method = RequestMethod.POST
  )
  public String saveSingleShip(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("orderInfoForm") OrderInfoForm orderInfoForm,
    @ModelAttribute("billingInfoForm") BillingInfoForm billingForm,
    @ModelAttribute("shippingInfoForm") ShippingInfoForm shippingForm,
    BindingResult result) throws PricingException, ServiceException {
    prepopulateOrderInfoForm(CartState.getCart(), orderInfoForm);

    return super.saveSingleShip(request, response, model, shippingForm, result);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request                   DOCUMENT ME!
   * @param   response                  DOCUMENT ME!
   * @param   model                     DOCUMENT ME!
   * @param   orderMultishipOptionForm  DOCUMENT ME!
   * @param   result                    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/multiship",
    method = RequestMethod.GET
  )
  public String showMultiship(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("orderMultishipOptionForm") OrderMultishipOptionForm orderMultishipOptionForm,
    BindingResult result) throws PricingException {
    return super.showMultiship(request, response, model);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request      DOCUMENT ME!
   * @param   response     DOCUMENT ME!
   * @param   model        DOCUMENT ME!
   * @param   addressForm  DOCUMENT ME!
   * @param   result       DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/add-address",
    method = RequestMethod.GET
  )
  public String showMultishipAddAddress(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("addressForm") ShippingInfoForm addressForm, BindingResult result) {
    return super.showMultishipAddAddress(request, response, model);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.checkout.BroadleafCheckoutController#initBinder(javax.servlet.http.HttpServletRequest,
   *       org.springframework.web.bind.ServletRequestDataBinder)
   */
  @InitBinder @Override protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
    throws Exception {
    super.initBinder(request, binder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  cart           DOCUMENT ME!
   * @param  orderInfoForm  DOCUMENT ME!
   * @param  shippingForm   DOCUMENT ME!
   * @param  billingForm    DOCUMENT ME!
   */
  protected void prepopulateCheckoutForms(Order cart, OrderInfoForm orderInfoForm, ShippingInfoForm shippingForm,
    BillingInfoForm billingForm) {
    List<FulfillmentGroup> groups = cart.getFulfillmentGroups();

    prepopulateOrderInfoForm(cart, orderInfoForm);

    if (CollectionUtils.isNotEmpty(groups) && (groups.get(0).getFulfillmentOption() != null)) {
      // if the cart has already has fulfillment information
      shippingForm.setAddress(groups.get(0).getAddress());
      shippingForm.setFulfillmentOption(groups.get(0).getFulfillmentOption());
      shippingForm.setFulfillmentOptionId(groups.get(0).getFulfillmentOption().getId());
    } else {
      // check for a default address for the customer
      CustomerAddress defaultAddress = customerAddressService.findDefaultCustomerAddress(CustomerState.getCustomer()
          .getId());

      if (defaultAddress != null) {
        shippingForm.setAddress(defaultAddress.getAddress());
        shippingForm.setAddressName(defaultAddress.getAddressName());
      }
    }

    if (cart.getPaymentInfos() != null) {
      for (PaymentInfo paymentInfo : cart.getPaymentInfos()) {
        if (PaymentInfoType.CREDIT_CARD.equals(paymentInfo.getType())) {
          billingForm.setAddress(paymentInfo.getAddress());
        }
      }
    }
  } // end method prepopulateCheckoutForms

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  cart           DOCUMENT ME!
   * @param  orderInfoForm  DOCUMENT ME!
   */
  protected void prepopulateOrderInfoForm(Order cart, OrderInfoForm orderInfoForm) {
    if (orderInfoForm != null) {
      orderInfoForm.setEmailAddress(cart.getEmailAddress());
    }
  }

} // end class CheckoutController
