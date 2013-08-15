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

import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;
import org.broadleafcommerce.core.workflow.WorkflowException;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface SecurePaymentInfoService {
  /**
   * DOCUMENT ME!
   *
   * @param   referenceNumber  DOCUMENT ME!
   * @param   paymentInfoType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  WorkflowException  DOCUMENT ME!
   */
  Referenced findSecurePaymentInfo(String referenceNumber, PaymentInfoType paymentInfoType) throws WorkflowException;

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
   * @param   paymentInfoType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Referenced create(PaymentInfoType paymentInfoType);

  /**
   * DOCUMENT ME!
   *
   * @param  securePaymentInfo  DOCUMENT ME!
   */
  void remove(Referenced securePaymentInfo);

  /**
   * DOCUMENT ME!
   *
   * @param   referenceNumber  DOCUMENT ME!
   * @param   paymentInfoType  DOCUMENT ME!
   *
   * @throws  WorkflowException  DOCUMENT ME!
   */
  void findAndRemoveSecurePaymentInfo(String referenceNumber, PaymentInfoType paymentInfoType) throws WorkflowException;

} // end interface SecurePaymentInfoService
