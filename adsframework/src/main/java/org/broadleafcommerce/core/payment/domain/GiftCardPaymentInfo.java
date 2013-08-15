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
public interface GiftCardPaymentInfo extends Referenced {
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
   * The pin.
   *
   * @return  the pin
   */
  String getPin();

  /**
   *
   * DOCUMENT ME!
   *
   * @param  pin  the pin to set
   */
  void setPin(String pin);
} // end interface GiftCardPaymentInfo
