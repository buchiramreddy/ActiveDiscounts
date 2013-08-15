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

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CreditCardPaymentInfo extends Referenced {
  /**
   * The id.
   *
   * @return  the id
   */
  @Override Long getId();

  /**
   *
   * DOCUMENT ME!
   *
   * @param  id  the id to set
   */
  @Override void setId(Long id);

  /**
   * The pan.
   *
   * @return  the pan
   */
  String getPan();

  /**
   *
   * DOCUMENT ME!
   *
   * @param  pan  the pan to set
   */
  void setPan(String pan);

  /**
   * The expirationMonth.
   *
   * @return  the expirationMonth
   */
  Integer getExpirationMonth();

  /**
   *
   * DOCUMENT ME!
   *
   * @param  expirationMonth  the expirationMonth to set
   */
  void setExpirationMonth(Integer expirationMonth);

  /**
   * The expirationYear.
   *
   * @return  the expirationYear
   */
  Integer getExpirationYear();

  /**
   *
   * DOCUMENT ME!
   *
   * @param  expirationYear  the expirationYear to set
   */
  void setExpirationYear(Integer expirationYear);

  /**
   * The nameOnCard.
   *
   * @return  the nameOnCard
   */
  String getNameOnCard();

  /**
   *
   * DOCUMENT ME!
   *
   * @param  nameOnCard  the name on the card to set
   */
  void setNameOnCard(String nameOnCard);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCvvCode();

  /**
   * DOCUMENT ME!
   *
   * @param  cvvCode  DOCUMENT ME!
   */
  void setCvvCode(String cvvCode);
} // end interface CreditCardPaymentInfo
