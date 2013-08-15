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

package org.broadleafcommerce.core.payment.dao;

import java.util.List;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentLog;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PaymentInfoDao {
  /**
   * DOCUMENT ME!
   *
   * @param   paymentId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentInfo readPaymentInfoById(Long paymentId);

  /**
   * DOCUMENT ME!
   *
   * @param   paymentInfo  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentInfo save(PaymentInfo paymentInfo);

  /**
   * DOCUMENT ME!
   *
   * @param   paymentResponseItem  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentResponseItem save(PaymentResponseItem paymentResponseItem);

  /**
   * DOCUMENT ME!
   *
   * @param   log  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentLog save(PaymentLog log);

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<PaymentInfo> readPaymentInfosForOrder(Order order);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentInfo create();

  /**
   * DOCUMENT ME!
   *
   * @param  paymentInfo  DOCUMENT ME!
   */
  void delete(PaymentInfo paymentInfo);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentResponseItem createResponseItem();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentLog createLog();

} // end interface PaymentInfoDao
