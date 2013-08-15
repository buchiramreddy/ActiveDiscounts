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

package org.broadleafcommerce.core.web.controller.checkout;

import javax.annotation.Resource;

import org.broadleafcommerce.common.web.controller.BroadleafAbstractController;

import org.broadleafcommerce.core.checkout.service.CheckoutService;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.core.order.service.FulfillmentOptionService;
import org.broadleafcommerce.core.order.service.OrderMultishipOptionService;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.payment.service.BroadleafPaymentInfoTypeService;
import org.broadleafcommerce.core.payment.service.PaymentInfoFactory;
import org.broadleafcommerce.core.payment.service.SecurePaymentInfoService;
import org.broadleafcommerce.core.pricing.service.FulfillmentPricingService;
import org.broadleafcommerce.core.web.checkout.validator.BillingInfoFormValidator;
import org.broadleafcommerce.core.web.checkout.validator.MultishipAddAddressFormValidator;
import org.broadleafcommerce.core.web.checkout.validator.OrderInfoFormValidator;
import org.broadleafcommerce.core.web.checkout.validator.ShippingInfoFormValidator;

import org.broadleafcommerce.profile.core.service.AddressService;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.broadleafcommerce.profile.core.service.CustomerAddressService;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.core.service.StateService;


/**
 * An abstract controller that provides convenience methods and resource declarations for its children. Operations that
 * are shared between controllers that deal with checkout belong here.
 *
 * @author   elbertbautista
 * @version  $Revision$, $Date$
 */
public abstract class AbstractCheckoutController extends BroadleafAbstractController {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAddressService")
  protected AddressService addressService;

  /** DOCUMENT ME! */
  @Resource(name = "blBillingInfoFormValidator")
  protected BillingInfoFormValidator billingInfoFormValidator;

  /** DOCUMENT ME! */
  @Resource(name = "blCheckoutService")
  protected CheckoutService checkoutService;

  /** DOCUMENT ME! */
  @Resource(name = "blCountryService")
  protected CountryService countryService;

  /* Factories */
  /** DOCUMENT ME! */
  @Resource(name = "blCreditCardPaymentInfoFactory")
  protected PaymentInfoFactory creditCardPaymentInfoFactory;

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerAddressService")
  protected CustomerAddressService customerAddressService;

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerService")
  protected CustomerService customerService;

  /** DOCUMENT ME! */
  @Resource(name = "blFulfillmentGroupService")
  protected FulfillmentGroupService fulfillmentGroupService;

  /** DOCUMENT ME! */
  @Resource(name = "blFulfillmentOptionService")
  protected FulfillmentOptionService fulfillmentOptionService;

  /** DOCUMENT ME! */
  @Resource(name = "blFulfillmentPricingService")
  protected FulfillmentPricingService fulfillmentPricingService;

  /** DOCUMENT ME! */
  @Resource(name = "blMultishipAddAddressFormValidator")
  protected MultishipAddAddressFormValidator multishipAddAddressFormValidator;

  /** DOCUMENT ME! */
  @Resource(name = "blOrderInfoFormValidator")
  protected OrderInfoFormValidator orderInfoFormValidator;

  /** DOCUMENT ME! */
  @Resource(name = "blOrderMultishipOptionService")
  protected OrderMultishipOptionService orderMultishipOptionService;

  /* Services */
  /** DOCUMENT ME! */
  @Resource(name = "blOrderService")
  protected OrderService orderService;

  /** DOCUMENT ME! */
  @Resource(name = "blPaymentInfoTypeService")
  protected BroadleafPaymentInfoTypeService paymentInfoTypeService;

  /** DOCUMENT ME! */
  @Resource(name = "blSecurePaymentInfoService")
  protected SecurePaymentInfoService securePaymentInfoService;

  /* Validators */
  /** DOCUMENT ME! */
  @Resource(name = "blShippingInfoFormValidator")
  protected ShippingInfoFormValidator shippingInfoFormValidator;

  /** DOCUMENT ME! */
  @Resource(name = "blStateService")
  protected StateService stateService;

} // end class AbstractCheckoutController
