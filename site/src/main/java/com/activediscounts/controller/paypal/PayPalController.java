package com.activediscounts.controller.paypal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

import org.broadleafcommerce.vendor.paypal.web.controller.BroadleafPayPalController;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created with IntelliJ IDEA. User: srihithreddy Date: 8/10/13 Time: 11:32 PM To change this template use File |
 * Settings | File Templates.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Controller public class PayPalController extends BroadleafPayPalController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  // This is the URL that will initiate the checkout process with PayPal.
  /**
   * @see  org.broadleafcommerce.vendor.paypal.web.controller.BroadleafPayPalController#paypalCheckout(javax.servlet.http.HttpServletRequest)
   */
  @Override
  @RequestMapping("/paypal/checkout")
  public String paypalCheckout(HttpServletRequest request) throws PaymentException {
    return super.paypalCheckout(request);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  // This is the URL that PayPal will redirect back to on callback.
  // This should match ${paypal.return.url} in your properties file.
  // For example:  ${paypal.return.url}=http://localhost:8080/mycompany/paypal/process
  /**
   * @see  org.broadleafcommerce.vendor.paypal.web.controller.BroadleafPayPalController#paypalProcess(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model, java.lang.String, java.lang.String)
   */
  @Override
  @RequestMapping("/paypal/process")
  public String paypalProcess(HttpServletRequest request, HttpServletResponse response, Model model,
    @RequestParam String token,
    @RequestParam("PayerID") String payerID) throws CheckoutException, PricingException {
    return super.paypalProcess(request, response, model, token, payerID);
  }

} // end class PayPalController
