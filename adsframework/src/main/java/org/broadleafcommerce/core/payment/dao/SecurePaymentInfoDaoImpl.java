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

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.encryption.EncryptionModule;
import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfo;
import org.broadleafcommerce.core.payment.domain.CreditCardPaymentInfo;
import org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blSecurePaymentInfoDao")
public class SecurePaymentInfoDaoImpl implements SecurePaymentInfoDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blSecurePU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEncryptionModule")
  protected EncryptionModule encryptionModule;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.payment.dao.SecurePaymentInfoDao#save(org.broadleafcommerce.core.payment.domain.Referenced)
   */
  @Override public Referenced save(Referenced securePaymentInfo) {
    return em.merge(securePaymentInfo);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.SecurePaymentInfoDao#createBankAccountPaymentInfo()
   */
  @Override public BankAccountPaymentInfo createBankAccountPaymentInfo() {
    BankAccountPaymentInfo response = (BankAccountPaymentInfo) entityConfiguration.createEntityInstance(
        "org.broadleafcommerce.core.payment.domain.BankAccountPaymentInfo");
    response.setEncryptionModule(encryptionModule);

    return response;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.SecurePaymentInfoDao#createGiftCardPaymentInfo()
   */
  @Override public GiftCardPaymentInfo createGiftCardPaymentInfo() {
    GiftCardPaymentInfo response = (GiftCardPaymentInfo) entityConfiguration.createEntityInstance(
        "org.broadleafcommerce.core.payment.domain.GiftCardPaymentInfo");
    response.setEncryptionModule(encryptionModule);

    return response;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.SecurePaymentInfoDao#createCreditCardPaymentInfo()
   */
  @Override public CreditCardPaymentInfo createCreditCardPaymentInfo() {
    CreditCardPaymentInfo response = (CreditCardPaymentInfo) entityConfiguration.createEntityInstance(
        "org.broadleafcommerce.core.payment.domain.CreditCardPaymentInfo");
    response.setEncryptionModule(encryptionModule);

    return response;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.SecurePaymentInfoDao#findBankAccountInfo(java.lang.String)
   */
  @Override
  @SuppressWarnings("unchecked")
  public BankAccountPaymentInfo findBankAccountInfo(String referenceNumber) {
    Query query = em.createNamedQuery("BC_READ_BANK_ACCOUNT_BY_REFERENCE_NUMBER");
    query.setParameter("referenceNumber", referenceNumber);

    List<BankAccountPaymentInfo> infos    = query.getResultList();
    BankAccountPaymentInfo       response = ((infos == null) || (infos.size() == 0)) ? null : infos.get(0);

    if (response != null) {
      response.setEncryptionModule(encryptionModule);
    }

    return response;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.SecurePaymentInfoDao#findCreditCardInfo(java.lang.String)
   */
  @Override
  @SuppressWarnings("unchecked")
  public CreditCardPaymentInfo findCreditCardInfo(String referenceNumber) {
    Query query = em.createNamedQuery("BC_READ_CREDIT_CARD_BY_REFERENCE_NUMBER");
    query.setParameter("referenceNumber", referenceNumber);

    List<CreditCardPaymentInfo> infos    = query.getResultList();
    CreditCardPaymentInfo       response = ((infos == null) || (infos.size() == 0)) ? null : infos.get(0);

    if (response != null) {
      response.setEncryptionModule(encryptionModule);
    }

    return response;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.SecurePaymentInfoDao#findGiftCardInfo(java.lang.String)
   */
  @Override
  @SuppressWarnings("unchecked")
  public GiftCardPaymentInfo findGiftCardInfo(String referenceNumber) {
    Query query = em.createNamedQuery("BC_READ_GIFT_CARD_BY_REFERENCE_NUMBER");
    query.setParameter("referenceNumber", referenceNumber);

    List<GiftCardPaymentInfo> infos    = query.getResultList();
    GiftCardPaymentInfo       response = ((infos == null) || (infos.size() == 0)) ? null : infos.get(0);

    if (response != null) {
      response.setEncryptionModule(encryptionModule);
    }

    return response;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.dao.SecurePaymentInfoDao#delete(org.broadleafcommerce.core.payment.domain.Referenced)
   */
  @Override public void delete(Referenced securePaymentInfo) {
    if (!em.contains(securePaymentInfo)) {
      securePaymentInfo = em.find(securePaymentInfo.getClass(), securePaymentInfo.getId());
    }

    em.remove(securePaymentInfo);
  }

} // end class SecurePaymentInfoDaoImpl
