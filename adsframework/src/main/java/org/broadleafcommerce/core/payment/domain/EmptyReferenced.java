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

import org.broadleafcommerce.common.encryption.EncryptionModule;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class EmptyReferenced implements Referenced {
  private String referenceNumber;

  /**
   * @see  org.broadleafcommerce.core.payment.domain.Referenced#getEncryptionModule()
   */
  @Override public EncryptionModule getEncryptionModule() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.Referenced#getReferenceNumber()
   */
  @Override public String getReferenceNumber() {
    return referenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.Referenced#setReferenceNumber(java.lang.String)
   */
  @Override public void setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.Referenced#setEncryptionModule(org.broadleafcommerce.common.encryption.EncryptionModule)
   */
  @Override public void setEncryptionModule(EncryptionModule encryptionModule) {
    // do nothing
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.Referenced#getId()
   */
  @Override public Long getId() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.Referenced#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    // do nothing
  }
} // end class EmptyReferenced
