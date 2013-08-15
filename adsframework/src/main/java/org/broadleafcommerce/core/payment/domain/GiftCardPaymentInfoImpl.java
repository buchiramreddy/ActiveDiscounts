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
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_GIFT_CARD_PAYMENT")
public class GiftCardPaymentInfoImpl implements GiftCardPaymentInfo {
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new GiftCardPaymentInfoImpl object.
   */
  protected GiftCardPaymentInfoImpl() {
    // do not allow direct instantiation -- must at least be package private
    // for bytecode instrumentation
    // this complies with JPA specification requirements for entity
    // construction
  }

  /** DOCUMENT ME! */
  @Transient protected EncryptionModule encryptionModule;

  /** DOCUMENT ME! */
  @Column(name = "PAYMENT_ID")
  @GeneratedValue(generator = "GiftCardPaymentId")
  @GenericGenerator(
    name       = "GiftCardPaymentId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "GiftCardPaymentInfoImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfoImpl"
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
    name        = "GIFTCARD_INDEX",
    columnNames = { "REFERENCE_NUMBER" }
  )
  protected String referenceNumber;

  /** DOCUMENT ME! */
  @Column(
    name     = "PAN",
    nullable = false
  )
  protected String pan;

  /** DOCUMENT ME! */
  @Column(name = "PIN")
  protected String pin;

  /**
   * @see  org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfo#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfo#getPan()
   */
  @Override public String getPan() {
    return encryptionModule.decrypt(pan);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfo#getPin()
   */
  @Override public String getPin() {
    return encryptionModule.decrypt(pin);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfo#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfo#setPan(java.lang.String)
   */
  @Override public void setPan(String pan) {
    this.pan = encryptionModule.encrypt(pan);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfo#setPin(java.lang.String)
   */
  @Override public void setPin(String pin) {
    this.pin = encryptionModule.encrypt(pin);
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
    result = (prime * result) + ((pan == null) ? 0 : pan.hashCode());
    result = (prime * result) + ((pin == null) ? 0 : pin.hashCode());
    result = (prime * result) + ((referenceNumber == null) ? 0 : referenceNumber.hashCode());

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

    GiftCardPaymentInfoImpl other = (GiftCardPaymentInfoImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (pan == null) {
      if (other.pan != null) {
        return false;
      }
    } else if (!pan.equals(other.pan)) {
      return false;
    }

    if (pin == null) {
      if (other.pin != null) {
        return false;
      }
    } else if (!pin.equals(other.pin)) {
      return false;
    }

    if (referenceNumber == null) {
      if (other.referenceNumber != null) {
        return false;
      }
    } else if (!referenceNumber.equals(other.referenceNumber)) {
      return false;
    }

    return true;
  } // end method equals

} // end class GiftCardPaymentInfoImpl
