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

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.time.SystemTime;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.dao.PaymentInfoDao;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentLog;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blPaymentInfoService")
public class PaymentInfoServiceImpl implements PaymentInfoService {
  /** DOCUMENT ME! */
  @Resource(name = "blPaymentInfoDao")
  protected PaymentInfoDao paymentInfoDao;

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentInfoService#save(org.broadleafcommerce.core.payment.domain.PaymentInfo)
   */
  @Override public PaymentInfo save(PaymentInfo paymentInfo) {
    return paymentInfoDao.save(paymentInfo);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentInfoService#save(org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem save(PaymentResponseItem paymentResponseItem) {
    return paymentInfoDao.save(paymentResponseItem);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentInfoService#save(org.broadleafcommerce.core.payment.domain.PaymentLog)
   */
  @Override public PaymentLog save(PaymentLog log) {
    return paymentInfoDao.save(log);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentInfoService#readPaymentInfoById(java.lang.Long)
   */
  @Override public PaymentInfo readPaymentInfoById(Long paymentId) {
    return paymentInfoDao.readPaymentInfoById(paymentId);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentInfoService#readPaymentInfosForOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public List<PaymentInfo> readPaymentInfosForOrder(Order order) {
    return paymentInfoDao.readPaymentInfosForOrder(order);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentInfoService#create()
   */
  @Override public PaymentInfo create() {
    return paymentInfoDao.create();
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentInfoService#delete(org.broadleafcommerce.core.payment.domain.PaymentInfo)
   */
  @Override public void delete(PaymentInfo paymentInfo) {
    paymentInfoDao.delete(paymentInfo);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentInfoService#createLog()
   */
  @Override public PaymentLog createLog() {
    return paymentInfoDao.createLog();
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentInfoService#createResponseItem()
   */
  @Override public PaymentResponseItem createResponseItem() {
    PaymentResponseItem returnItem = paymentInfoDao.createResponseItem();
    returnItem.setTransactionTimestamp(SystemTime.asDate());

    return returnItem;
  }

} // end class PaymentInfoServiceImpl
