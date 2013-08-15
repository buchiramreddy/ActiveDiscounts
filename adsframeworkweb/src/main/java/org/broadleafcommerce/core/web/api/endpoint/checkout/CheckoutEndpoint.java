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

package org.broadleafcommerce.core.web.api.endpoint.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.core.checkout.service.CheckoutService;
import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.CompositePaymentService;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;
import org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.api.endpoint.BaseEndpoint;
import org.broadleafcommerce.core.web.api.wrapper.OrderWrapper;
import org.broadleafcommerce.core.web.api.wrapper.PaymentReferenceMapWrapper;
import org.broadleafcommerce.core.web.api.wrapper.PaymentResponseItemWrapper;
import org.broadleafcommerce.core.web.order.CartState;


/**
 * This endpoint depends on JAX-RS to provide checkout services. It should be extended by components that actually wish
 * to provide an endpoint. The annotations such as @Path, @Scope, @Context, @PathParam, @QueryParam,
 *
 * @GET      , @POST, @PUT, and @DELETE are purposely not provided here to allow implementors finer control over the
 *           details of the endpoint.
 *
 *           <p>User: Kelly Tisdell Date: 4/10/12</p>
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class CheckoutEndpoint extends BaseEndpoint {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(CheckoutEndpoint.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCheckoutService")
  protected CheckoutService checkoutService;

  // This service is backed by the entire payment workflow configured in the application context.
  // It is the entry point for engaging the payment workflow
  /** DOCUMENT ME! */
  @Resource(name = "blCompositePaymentService")
  protected CompositePaymentService compositePaymentService;

  /** DOCUMENT ME! */
  @Resource(name = "blOrderService")
  protected OrderService orderService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  // This should only be called for modules that need to engage the workflow directly without doing a complete checkout.
  // e.g. PayPal for doing an authorize and retrieving the redirect: url to PayPal
  /**
   * DOCUMENT ME!
   *
   * @param   request     DOCUMENT ME!
   * @param   mapWrapper  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  WebApplicationException  DOCUMENT ME!
   */
  public PaymentResponseItemWrapper executePayment(HttpServletRequest request, PaymentReferenceMapWrapper mapWrapper) {
    Order cart = CartState.getCart();

    if (cart != null) {
      try {
        Map<PaymentInfo, Referenced> payments    = new HashMap<PaymentInfo, Referenced>();
        PaymentInfo                  paymentInfo = mapWrapper.getPaymentInfoWrapper().unwrap(request, context);
        Referenced                   referenced  = mapWrapper.getReferencedWrapper().unwrap(request, context);
        payments.put(paymentInfo, referenced);

        CompositePaymentResponse compositePaymentResponse = compositePaymentService.executePayment(cart, payments);
        PaymentResponseItem      responseItem             = compositePaymentResponse.getPaymentResponse()
          .getResponseItems().get(paymentInfo);

        PaymentResponseItemWrapper paymentResponseItemWrapper = context.getBean(PaymentResponseItemWrapper.class);
        paymentResponseItemWrapper.wrapDetails(responseItem, request);

        return paymentResponseItemWrapper;

      } catch (PaymentException e) {
        throw new WebApplicationException(e,
          Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
            "An error occured with payment.").build());
      }
    }

    throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
        "Cart could not be found").build());

  } // end method executePayment

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request      DOCUMENT ME!
   * @param   mapWrappers  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  WebApplicationException  DOCUMENT ME!
   */
  public OrderWrapper performCheckout(HttpServletRequest request, List<PaymentReferenceMapWrapper> mapWrappers) {
    Order cart = CartState.getCart();

    if (cart != null) {
      try {
        if ((mapWrappers != null) && !mapWrappers.isEmpty()) {
          Map<PaymentInfo, Referenced> payments = new HashMap<PaymentInfo, Referenced>();
          orderService.removePaymentsFromOrder(cart, PaymentInfoType.CREDIT_CARD);

          for (PaymentReferenceMapWrapper mapWrapper : mapWrappers) {
            PaymentInfo paymentInfo = mapWrapper.getPaymentInfoWrapper().unwrap(request, context);
            paymentInfo.setOrder(cart);

            Referenced referenced = mapWrapper.getReferencedWrapper().unwrap(request, context);

            if (cart.getPaymentInfos() == null) {
              cart.setPaymentInfos(new ArrayList<PaymentInfo>());
            }

            cart.getPaymentInfos().add(paymentInfo);
            payments.put(paymentInfo, referenced);
          }

          CheckoutResponse response = checkoutService.performCheckout(cart, payments);
          Order            order    = response.getOrder();
          OrderWrapper     wrapper  = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
          wrapper.wrapDetails(order, request);

          return wrapper;
        } // end if
      } catch (CheckoutException e) {
        cart.setStatus(OrderStatus.IN_PROCESS);

        try {
          orderService.save(cart, false);
        } catch (PricingException e1) {
          LOG.error("An unexpected error occured saving / pricing the cart.", e1);
        }

        throw new WebApplicationException(e,
          Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(
            "An error occured during checkout.").build());
      } // end try-catch
    } // end if

    throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(
        "Cart could not be found").build());

  } // end method performCheckout
} // end class CheckoutEndpoint
