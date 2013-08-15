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

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentInfoImpl;
import org.broadleafcommerce.core.payment.domain.PaymentLog;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blPaymentInfoDao")
public class PaymentInfoDaoImpl implements PaymentInfoDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.payment.dao.PaymentInfoDao#save(org.broadleafcommerce.core.payment.domain.PaymentInfo)
   */
  @Override public PaymentInfo save(PaymentInfo paymentInfo) {
    return em.merge(paymentInfo);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.PaymentInfoDao#save(org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem save(PaymentResponseItem paymentResponseItem) {
    return em.merge(paymentResponseItem);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.PaymentInfoDao#save(org.broadleafcommerce.core.payment.domain.PaymentLog)
   */
  @Override public PaymentLog save(PaymentLog log) {
    return em.merge(log);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.PaymentInfoDao#readPaymentInfoById(java.lang.Long)
   */
  @Override public PaymentInfo readPaymentInfoById(Long paymentId) {
    return (PaymentInfo) em.find(PaymentInfoImpl.class, paymentId);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.PaymentInfoDao#readPaymentInfosForOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<PaymentInfo> readPaymentInfosForOrder(Order order) {
    Query query = em.createNamedQuery("BC_READ_ORDERS_PAYMENTS_BY_ORDER_ID");
    query.setParameter("orderId", order.getId());

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.PaymentInfoDao#create()
   */
  @Override public PaymentInfo create() {
    return ((PaymentInfo) entityConfiguration.createEntityInstance(
          "org.broadleafcommerce.core.payment.domain.PaymentInfo"));
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.PaymentInfoDao#createResponseItem()
   */
  @Override public PaymentResponseItem createResponseItem() {
    return ((PaymentResponseItem) entityConfiguration.createEntityInstance(
          "org.broadleafcommerce.core.payment.domain.PaymentResponseItem"));
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.PaymentInfoDao#createLog()
   */
  @Override public PaymentLog createLog() {
    return ((PaymentLog) entityConfiguration.createEntityInstance(
          "org.broadleafcommerce.core.payment.domain.PaymentLog"));
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.PaymentInfoDao#delete(org.broadleafcommerce.core.payment.domain.PaymentInfo)
   */
  @Override public void delete(PaymentInfo paymentInfo) {
    if (!em.contains(paymentInfo)) {
      paymentInfo = readPaymentInfoById(paymentInfo.getId());
    }

    em.remove(paymentInfo);
  }
} // end class PaymentInfoDaoImpl
