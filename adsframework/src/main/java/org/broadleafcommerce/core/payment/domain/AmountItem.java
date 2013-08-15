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

import java.math.BigDecimal;


/**
 * This class is useful if you wish to delineate in the paymentinfo the various components that make up the total being
 * charged. This is optional and not all payment modules support.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface AmountItem extends Serializable {
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
  String getShortDescription();

  /**
   * DOCUMENT ME!
   *
   * @param  shortDescription  DOCUMENT ME!
   */
  void setShortDescription(String shortDescription);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getDescription();

  /**
   * DOCUMENT ME!
   *
   * @param  description  DOCUMENT ME!
   */
  void setDescription(String description);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BigDecimal getUnitPrice();

  /**
   * DOCUMENT ME!
   *
   * @param  unitPrice  DOCUMENT ME!
   */
  void setUnitPrice(BigDecimal unitPrice);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getQuantity();

  /**
   * DOCUMENT ME!
   *
   * @param  quantity  DOCUMENT ME!
   */
  void setQuantity(Long quantity);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentInfo getPaymentInfo();

  /**
   * DOCUMENT ME!
   *
   * @param  paymentInfo  DOCUMENT ME!
   */
  void setPaymentInfo(PaymentInfo paymentInfo);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getSystemId();

  /**
   * DOCUMENT ME!
   *
   * @param  systemId  DOCUMENT ME!
   */
  void setSystemId(String systemId);

} // end interface AmountItem
