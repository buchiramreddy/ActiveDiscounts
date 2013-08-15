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

package org.broadleafcommerce.core.offer.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.common.persistence.Status;
import org.broadleafcommerce.common.time.SystemTime;

import org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer;
import org.broadleafcommerce.core.offer.domain.CandidateItemOffer;
import org.broadleafcommerce.core.offer.domain.CandidateOrderOffer;
import org.broadleafcommerce.core.offer.domain.FulfillmentGroupAdjustment;
import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferImpl;
import org.broadleafcommerce.core.offer.domain.OfferInfo;
import org.broadleafcommerce.core.offer.domain.OrderAdjustment;
import org.broadleafcommerce.core.offer.domain.OrderItemAdjustment;
import org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;

import org.hibernate.ejb.HibernateEntityManager;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blOfferDao")
public class OfferDaoImpl implements OfferDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Date currentDate = SystemTime.asDate();

  /** DOCUMENT ME! */
  protected Long currentDateResolution = 10000L;

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  private String DATE_LOCK = "DATE_LOCK"; // for use in synchronization

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#create()
   */
  @Override public Offer create() {
    return ((Offer) entityConfiguration.createEntityInstance(Offer.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#createCandidateFulfillmentGroupOffer()
   */
  @Override public CandidateFulfillmentGroupOffer createCandidateFulfillmentGroupOffer() {
    return ((CandidateFulfillmentGroupOffer) entityConfiguration.createEntityInstance(
          CandidateFulfillmentGroupOffer.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#createCandidateItemOffer()
   */
  @Override public CandidateItemOffer createCandidateItemOffer() {
    return ((CandidateItemOffer) entityConfiguration.createEntityInstance(CandidateItemOffer.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#createCandidateOrderOffer()
   */
  @Override public CandidateOrderOffer createCandidateOrderOffer() {
    return ((CandidateOrderOffer) entityConfiguration.createEntityInstance(CandidateOrderOffer.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#createFulfillmentGroupAdjustment()
   */
  @Override public FulfillmentGroupAdjustment createFulfillmentGroupAdjustment() {
    return ((FulfillmentGroupAdjustment) entityConfiguration.createEntityInstance(
          FulfillmentGroupAdjustment.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#createOfferInfo()
   */
  @Override public OfferInfo createOfferInfo() {
    return ((OfferInfo) entityConfiguration.createEntityInstance(OfferInfo.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#createOrderAdjustment()
   */
  @Override public OrderAdjustment createOrderAdjustment() {
    return ((OrderAdjustment) entityConfiguration.createEntityInstance(OrderAdjustment.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#createOrderItemAdjustment()
   */
  @Override public OrderItemAdjustment createOrderItemAdjustment() {
    return ((OrderItemAdjustment) entityConfiguration.createEntityInstance(OrderItemAdjustment.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#createOrderItemPriceDetailAdjustment()
   */
  @Override public OrderItemPriceDetailAdjustment createOrderItemPriceDetailAdjustment() {
    return ((OrderItemPriceDetailAdjustment) entityConfiguration.createEntityInstance(
          OrderItemPriceDetailAdjustment.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#delete(org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public void delete(Offer offer) {
    ((Status) offer).setArchived('Y');
    em.merge(offer);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#delete(org.broadleafcommerce.core.offer.domain.OfferInfo)
   */
  @Override public void delete(OfferInfo offerInfo) {
    if (!em.contains(offerInfo)) {
      offerInfo = (OfferInfo) em.find(entityConfiguration.lookupEntityClass(OfferInfo.class.getName()),
          offerInfo.getId());
    }

    em.remove(offerInfo);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#getCurrentDateResolution()
   */
  @Override public Long getCurrentDateResolution() {
    return currentDateResolution;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#readAllOffers()
   */
  @Override public List<Offer> readAllOffers() {
    Query query = em.createNamedQuery("BC_READ_ALL_OFFERS");

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#readOfferById(java.lang.Long)
   */
  @Override public Offer readOfferById(Long offerId) {
    return em.find(OfferImpl.class, offerId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#readOffersByAutomaticDeliveryType()
   */
  @Override public List<Offer> readOffersByAutomaticDeliveryType() {
    // TODO change this to a JPA criteria
    Criteria criteria = ((HibernateEntityManager) em).getSession().createCriteria(OfferImpl.class);

    Date myDate = SystemTime.asDate();
    myDate = getDateFactoringInDateResolution(myDate);

    Calendar c = Calendar.getInstance();
    c.setTime(myDate);
    c.add(Calendar.DATE, +1);
    criteria.add(Restrictions.lt("startDate", c.getTime()));
    c = Calendar.getInstance();
    c.setTime(myDate);
    c.add(Calendar.DATE, -1);
    criteria.add(Restrictions.or(Restrictions.isNull("endDate"), Restrictions.gt("endDate", c.getTime())));
    criteria.add(Restrictions.or(Restrictions.eq("archiveStatus.archived", 'N'),
        Restrictions.isNull("archiveStatus.archived")));

    // Automatically Added or (Automatically Added is null and deliveryType is Automatic)
    criteria.add(Restrictions.or(Restrictions.eq("automaticallyAdded", true),
        Restrictions.and(Restrictions.isNull("automaticallyAdded"),
          Restrictions.eq("deliveryType", "AUTOMATIC"))));

    criteria.setCacheable(true);

    return criteria.list();
  } // end method readOffersByAutomaticDeliveryType

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#save(org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public Offer save(Offer offer) {
    return em.merge(offer);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#save(org.broadleafcommerce.core.offer.domain.OfferInfo)
   */
  @Override public OfferInfo save(OfferInfo offerInfo) {
    return em.merge(offerInfo);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferDao#setCurrentDateResolution(java.lang.Long)
   */
  @Override public void setCurrentDateResolution(Long currentDateResolution) {
    this.currentDateResolution = currentDateResolution;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   currentDate  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Date getDateFactoringInDateResolution(Date currentDate) {
    Date myDate;
    Long myCurrentDateResolution = currentDateResolution;

    synchronized (DATE_LOCK) {
      if ((currentDate.getTime() - this.currentDate.getTime()) > myCurrentDateResolution) {
        this.currentDate = new Date(currentDate.getTime());
        myDate           = currentDate;
      } else {
        myDate = this.currentDate;
      }
    }

    return myDate;
  }
} // end class OfferDaoImpl
