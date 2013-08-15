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

package org.broadleafcommerce.core.payment.domain;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.CustomerPayment;
import org.broadleafcommerce.profile.core.domain.Phone;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PaymentInfo extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order getOrder();

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  void setOrder(Order order);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Address getAddress();

  /**
   * DOCUMENT ME!
   *
   * @param  address  DOCUMENT ME!
   */
  void setAddress(Address address);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Phone getPhone();

  /**
   * DOCUMENT ME!
   *
   * @param  phone  DOCUMENT ME!
   */
  void setPhone(Phone phone);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getAmount();

  /**
   * DOCUMENT ME!
   *
   * @param  amount  DOCUMENT ME!
   */
  void setAmount(Money amount);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getReferenceNumber();

  /**
   * DOCUMENT ME!
   *
   * @param  referenceNumber  DOCUMENT ME!
   */
  void setReferenceNumber(String referenceNumber);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentInfoType getType();

  /**
   * DOCUMENT ME!
   *
   * @param  type  DOCUMENT ME!
   */
  void setType(PaymentInfoType type);

  /**
   * DOCUMENT ME!
   *
   * @param  amountItems  DOCUMENT ME!
   */
  void setAmountItems(List<AmountItem> amountItems);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<AmountItem> getAmountItems();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCustomerIpAddress();

  /**
   * DOCUMENT ME!
   *
   * @param  customerIpAddress  DOCUMENT ME!
   */
  void setCustomerIpAddress(String customerIpAddress);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, String> getAdditionalFields();

  /**
   * DOCUMENT ME!
   *
   * @param  additionalFields  DOCUMENT ME!
   */
  void setAdditionalFields(Map<String, String> additionalFields);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, String[]> getRequestParameterMap();

  /**
   * DOCUMENT ME!
   *
   * @param  requestParameterMap  DOCUMENT ME!
   */
  void setRequestParameterMap(Map<String, String[]> requestParameterMap);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Referenced createEmptyReferenced();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<PaymentInfoDetail> getPaymentInfoDetails();

  /**
   * DOCUMENT ME!
   *
   * @param  details  DOCUMENT ME!
   */
  void setPaymentInfoDetails(List<PaymentInfoDetail> details);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getPaymentCapturedAmount();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getPaymentCreditedAmount();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getReverseAuthAmount();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BroadleafCurrency getCurrency();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerPayment getCustomerPayment();

  /**
   * DOCUMENT ME!
   *
   * @param  customerPayment  DOCUMENT ME!
   */
  void setCustomerPayment(CustomerPayment customerPayment);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<PaymentResponseItem> getPaymentResponseItems();

  /**
   * DOCUMENT ME!
   *
   * @param  paymentResponseItems  DOCUMENT ME!
   */
  void setPaymentResponseItems(List<PaymentResponseItem> paymentResponseItems);
} // end interface PaymentInfo
