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

package org.broadleafcommerce.core.payment.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.module.PaymentResponse;
import org.broadleafcommerce.core.payment.service.module.PaymentResponseImpl;
import org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse;
import org.broadleafcommerce.core.payment.service.workflow.PaymentSeed;
import org.broadleafcommerce.core.workflow.SequenceProcessor;
import org.broadleafcommerce.core.workflow.WorkflowException;

import org.springframework.stereotype.Service;


/**
 * Execute the payment workflow independently of the checkout workflow.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Service("blCompositePaymentService")
public class CompositePaymentServiceImpl implements CompositePaymentService {
  /** DOCUMENT ME! */
  @Resource(name = "blPaymentWorkflow")
  protected SequenceProcessor paymentWorkflow;

  /**
   * @see  org.broadleafcommerce.core.payment.service.CompositePaymentService#executePayment(org.broadleafcommerce.core.order.domain.Order,
   *       java.util.Map, org.broadleafcommerce.core.payment.service.module.PaymentResponse)
   */
  @Override public CompositePaymentResponse executePayment(Order order, Map<PaymentInfo, Referenced> payments,
    PaymentResponse response) throws PaymentException {
    /*
     * TODO add validation that checks the order and payment information for
     * validity.
     */
    try {
      PaymentSeed seed = new PaymentSeed(order, payments, response);
      paymentWorkflow.doActivities(seed);

      return seed;
    } catch (WorkflowException e) {
      Throwable cause = null;

      while (e.getCause() != null) {
        if ((cause != null) && cause.equals(e.getCause())) {
          break;
        }

        cause = e.getCause();
      }

      if ((cause != null) && PaymentException.class.isAssignableFrom(cause.getClass())) {
        throw (PaymentException) cause;
      }

      throw new PaymentException("Unable to execute payment for order -- id: " + order.getId(), e);
    }
  } // end method executePayment

  /**
   * @see  org.broadleafcommerce.core.payment.service.CompositePaymentService#executePayment(org.broadleafcommerce.core.order.domain.Order,
   *       java.util.Map)
   */
  @Override public CompositePaymentResponse executePayment(Order order, Map<PaymentInfo, Referenced> payments)
    throws PaymentException {
    return executePayment(order, payments, new PaymentResponseImpl());
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.CompositePaymentService#executePayment(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public CompositePaymentResponse executePayment(Order order) throws PaymentException {
    return executePayment(order, null);
  }

  // This convenience method is utilized for those implementations that are not storing secure information (credit card information), such as PayPal and Braintree
  // It will construct a PaymentInfo based on the implementation of PaymentInfoFactory with an empty Referenced and pass it to the workflow.
  /**
   * @see  org.broadleafcommerce.core.payment.service.CompositePaymentService#executePaymentForGateway(org.broadleafcommerce.core.order.domain.Order,
   *       org.broadleafcommerce.core.payment.service.PaymentInfoFactory)
   */
  @Override public CompositePaymentResponse executePaymentForGateway(Order order, PaymentInfoFactory paymentInfoFactory)
    throws PaymentException {
    Map<PaymentInfo, Referenced> payments    = new HashMap<PaymentInfo, Referenced>();
    PaymentInfo                  paymentInfo = paymentInfoFactory.constructPaymentInfo(order);
    payments.put(paymentInfo, paymentInfo.createEmptyReferenced());

    order.getPaymentInfos().add(paymentInfo);

    return executePayment(order, payments);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SequenceProcessor getPaymentWorkflow() {
    return paymentWorkflow;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  paymentWorkflow  DOCUMENT ME!
   */
  public void setPaymentWorkflow(SequenceProcessor paymentWorkflow) {
    this.paymentWorkflow = paymentWorkflow;
  }
} // end class CompositePaymentServiceImpl
