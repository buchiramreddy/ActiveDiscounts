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

package org.broadleafcommerce.core.rating.dao;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.rating.domain.ReviewDetail;
import org.broadleafcommerce.core.rating.domain.ReviewDetailImpl;
import org.broadleafcommerce.core.rating.domain.ReviewFeedback;

import org.broadleafcommerce.profile.core.domain.Customer;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blReviewDetailDao")
public class ReviewDetailDaoImpl implements ReviewDetailDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.rating.dao.ReviewDetailDao#readReviewDetailById(java.lang.Long)
   */
  @Override public ReviewDetail readReviewDetailById(Long reviewId) {
    return em.find(ReviewDetailImpl.class, reviewId);
  }

  /**
   * @see  org.broadleafcommerce.core.rating.dao.ReviewDetailDao#saveReviewDetail(org.broadleafcommerce.core.rating.domain.ReviewDetail)
   */
  @Override public ReviewDetail saveReviewDetail(ReviewDetail reviewDetail) {
    return em.merge(reviewDetail);
  }

  /**
   * @see  org.broadleafcommerce.core.rating.dao.ReviewDetailDao#readReviewByCustomerAndItem(org.broadleafcommerce.profile.core.domain.Customer,
   *       java.lang.String)
   */
  @Override public ReviewDetail readReviewByCustomerAndItem(Customer customer, String itemId) {
    final Query query = em.createNamedQuery("BC_READ_REVIEW_DETAIL_BY_CUSTOMER_ID_AND_ITEM_ID");
    query.setParameter("customerId", customer.getId());
    query.setParameter("itemId", itemId);

    ReviewDetail reviewDetail = null;

    try {
      reviewDetail = (ReviewDetail) query.getSingleResult();
    } catch (NoResultException nre) {
      // ignore
    }

    return reviewDetail;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.dao.ReviewDetailDao#create()
   */
  @Override public ReviewDetail create() {
    return (ReviewDetail) entityConfiguration.createEntityInstance(ReviewDetail.class.getName());
  }

  /**
   * @see  org.broadleafcommerce.core.rating.dao.ReviewDetailDao#createFeedback()
   */
  @Override public ReviewFeedback createFeedback() {
    return (ReviewFeedback) entityConfiguration.createEntityInstance(ReviewFeedback.class.getName());
  }
} // end class ReviewDetailDaoImpl
