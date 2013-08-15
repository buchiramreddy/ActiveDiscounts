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

import org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfo;
import org.broadleafcommerce.core.payment.domain.CreditCardPaymentInfo;
import org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface SecurePaymentInfoDao {
  /**
   * DOCUMENT ME!
   *
   * @param   referenceNumber  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BankAccountPaymentInfo findBankAccountInfo(String referenceNumber);

  /**
   * DOCUMENT ME!
   *
   * @param   referenceNumber  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CreditCardPaymentInfo findCreditCardInfo(String referenceNumber);

  /**
   * DOCUMENT ME!
   *
   * @param   referenceNumber  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  GiftCardPaymentInfo findGiftCardInfo(String referenceNumber);

  /**
   * DOCUMENT ME!
   *
   * @param   securePaymentInfo  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Referenced save(Referenced securePaymentInfo);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BankAccountPaymentInfo createBankAccountPaymentInfo();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  GiftCardPaymentInfo createGiftCardPaymentInfo();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CreditCardPaymentInfo createCreditCardPaymentInfo();

  /**
   * DOCUMENT ME!
   *
   * @param  securePaymentInfo  DOCUMENT ME!
   */
  void delete(Referenced securePaymentInfo);

} // end interface SecurePaymentInfoDao
