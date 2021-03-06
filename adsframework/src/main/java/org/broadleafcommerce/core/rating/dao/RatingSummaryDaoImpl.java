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

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.common.util.dao.BatchRetrieveDao;

import org.broadleafcommerce.core.rating.domain.RatingDetail;
import org.broadleafcommerce.core.rating.domain.RatingSummary;
import org.broadleafcommerce.core.rating.domain.RatingSummaryImpl;
import org.broadleafcommerce.core.rating.domain.ReviewDetail;
import org.broadleafcommerce.core.rating.service.type.RatingType;

import org.hibernate.ejb.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blRatingSummaryDao")
public class RatingSummaryDaoImpl extends BatchRetrieveDao implements RatingSummaryDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.rating.dao.RatingSummaryDao#deleteRatingSummary(org.broadleafcommerce.core.rating.domain.RatingSummary)
   */
  @Override public void deleteRatingSummary(final RatingSummary summary) {
    RatingSummary lSummary = summary;

    if (!em.contains(lSummary)) {
      lSummary = (RatingSummary) em.find(RatingSummaryImpl.class, lSummary.getId());
    }

    em.remove(lSummary);
  }

  /**
   * @see  org.broadleafcommerce.core.rating.dao.RatingSummaryDao#saveRatingSummary(org.broadleafcommerce.core.rating.domain.RatingSummary)
   */
  @Override public RatingSummary saveRatingSummary(final RatingSummary summary) {
    summary.resetAverageRating();

    return em.merge(summary);
  }

  /**
   * @see  org.broadleafcommerce.core.rating.dao.RatingSummaryDao#readRatingSummaries(java.util.List, org.broadleafcommerce.core.rating.service.type.RatingType)
   */
  @Override public List<RatingSummary> readRatingSummaries(final List<String> itemIds, final RatingType type) {
    final Query query = em.createNamedQuery("BC_READ_RATING_SUMMARIES_BY_ITEM_ID_AND_TYPE");
    query.setParameter("ratingType", type.getType());

    List<RatingSummary> ratings = batchExecuteReadQuery(query, itemIds, "itemIds");

    return ratings;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.dao.RatingSummaryDao#readRatingSummary(java.lang.String, org.broadleafcommerce.core.rating.service.type.RatingType)
   */
  @Override public RatingSummary readRatingSummary(final String itemId, final RatingType type) {
    final Query query = em.createNamedQuery("BC_READ_RATING_SUMMARY_BY_ITEM_ID_AND_TYPE");
    query.setParameter("itemId", itemId);
    query.setParameter("ratingType", type.getType());
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    RatingSummary ratingSummary = null;

    try {
      ratingSummary = (RatingSummary) query.getSingleResult();
    } catch (NoResultException e) {
      // ignore
    }

    return ratingSummary;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.dao.RatingSummaryDao#readRating(java.lang.Long, java.lang.Long)
   */
  @Override public RatingDetail readRating(final Long customerId, final Long ratingSummaryId) {
    final Query query = em.createNamedQuery("BC_READ_RATING_DETAIL_BY_CUSTOMER_ID_AND_RATING_SUMMARY_ID");
    query.setParameter("customerId", customerId);
    query.setParameter("ratingSummaryId", ratingSummaryId);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    RatingDetail ratingDetail = null;

    try {
      ratingDetail = (RatingDetail) query.getSingleResult();
    } catch (NoResultException e) {
      // ignore
    }

    return ratingDetail;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.dao.RatingSummaryDao#readReview(java.lang.Long, java.lang.Long)
   */
  @Override public ReviewDetail readReview(final Long customerId, final Long ratingSummaryId) {
    final Query query = em.createNamedQuery("BC_READ_REVIEW_DETAIL_BY_CUSTOMER_ID_AND_RATING_SUMMARY_ID");
    query.setParameter("customerId", customerId);
    query.setParameter("ratingSummaryId", ratingSummaryId);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    ReviewDetail reviewDetail = null;

    try {
      reviewDetail = (ReviewDetail) query.getSingleResult();
    } catch (NoResultException e) {
      // ignore
    }

    return reviewDetail;
  }

} // end class RatingSummaryDaoImpl
