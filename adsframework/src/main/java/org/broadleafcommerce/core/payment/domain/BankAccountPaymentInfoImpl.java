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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.broadleafcommerce.common.encryption.EncryptionModule;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_BANK_ACCOUNT_PAYMENT")
public class BankAccountPaymentInfoImpl implements BankAccountPaymentInfo {
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new BankAccountPaymentInfoImpl object.
   */
  protected BankAccountPaymentInfoImpl() {
    // do not allow direct instantiation -- must at least be package private for bytecode instrumentation
    // this complies with JPA specification requirements for entity construction
  }

  /** DOCUMENT ME! */
  @Transient protected EncryptionModule encryptionModule;

  /** DOCUMENT ME! */
  @Column(name = "PAYMENT_ID")
  @GeneratedValue(generator = "BankPaymentId")
  @GenericGenerator(
    name       = "BankPaymentId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "BankAccountPaymentInfoImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfoImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(
    name     = "REFERENCE_NUMBER",
    nullable = false
  )
  @Index(
    name        = "BANKACCOUNT_INDEX",
    columnNames = { "REFERENCE_NUMBER" }
  )
  protected String referenceNumber;

  /** DOCUMENT ME! */
  @Column(
    name     = "ACCOUNT_NUMBER",
    nullable = false
  )
  protected String accountNumber;

  /** DOCUMENT ME! */
  @Column(
    name     = "ROUTING_NUMBER",
    nullable = false
  )
  protected String routingNumber;

  /**
   * @see  org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfo#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfo#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
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
   * @see  org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfo#getAccountNumber()
   */
  @Override public String getAccountNumber() {
    return encryptionModule.decrypt(accountNumber);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfo#setAccountNumber(java.lang.String)
   */
  @Override public void setAccountNumber(String accountNumber) {
    this.accountNumber = encryptionModule.encrypt(accountNumber);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfo#getRoutingNumber()
   */
  @Override public String getRoutingNumber() {
    return encryptionModule.decrypt(routingNumber);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfo#setRoutingNumber(java.lang.String)
   */
  @Override public void setRoutingNumber(String routingNumber) {
    this.routingNumber = encryptionModule.encrypt(routingNumber);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.Referenced#getEncryptionModule()
   */
  @Override public EncryptionModule getEncryptionModule() {
    return encryptionModule;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.Referenced#setEncryptionModule(org.broadleafcommerce.common.encryption.EncryptionModule)
   */
  @Override public void setEncryptionModule(EncryptionModule encryptionModule) {
    this.encryptionModule = encryptionModule;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((accountNumber == null) ? 0 : accountNumber.hashCode());
    result = (prime * result) + ((referenceNumber == null) ? 0 : referenceNumber.hashCode());
    result = (prime * result) + ((routingNumber == null) ? 0 : routingNumber.hashCode());

    return result;
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    BankAccountPaymentInfoImpl other = (BankAccountPaymentInfoImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (accountNumber == null) {
      if (other.accountNumber != null) {
        return false;
      }
    } else if (!accountNumber.equals(other.accountNumber)) {
      return false;
    }

    if (referenceNumber == null) {
      if (other.referenceNumber != null) {
        return false;
      }
    } else if (!referenceNumber.equals(other.referenceNumber)) {
      return false;
    }

    if (routingNumber == null) {
      if (other.routingNumber != null) {
        return false;
      }
    } else if (!routingNumber.equals(other.routingNumber)) {
      return false;
    }

    return true;
  } // end method equals

} // end class BankAccountPaymentInfoImpl
