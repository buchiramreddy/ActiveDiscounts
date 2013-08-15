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
package org.broadleafcommerce.core.web.api.wrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * <p>This is a JAXB wrapper that encapsulates the mapping between a PaymentInfoWrapper and a ReferencedWrapper</p>
 *
 * <p>This is just a container, and will not be wrapped or unwrapped from a BLC entity
 * </p>User: Elbert Bautista Date: 4/26/12
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "paymentReferenceMap")
public class PaymentReferenceMapWrapper extends BaseWrapper {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected PaymentInfoWrapper paymentInfo;

  /** DOCUMENT ME! */
  @XmlElement protected ReferencedWrapper referenced;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PaymentInfoWrapper getPaymentInfoWrapper() {
    return paymentInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ReferencedWrapper getReferencedWrapper() {
    return referenced;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  paymentInfo  DOCUMENT ME!
   */
  public void setPaymentInfoWrapper(PaymentInfoWrapper paymentInfo) {
    this.paymentInfo = paymentInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  referenced  DOCUMENT ME!
   */
  public void setReferencedWrapper(ReferencedWrapper referenced) {
    this.referenced = referenced;
  }
} // end class PaymentReferenceMapWrapper
