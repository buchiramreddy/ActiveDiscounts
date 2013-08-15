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

package org.broadleafcommerce.core.rating.service;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.core.rating.domain.RatingSummary;
import org.broadleafcommerce.core.rating.domain.ReviewDetail;
import org.broadleafcommerce.core.rating.service.type.RatingSortType;
import org.broadleafcommerce.core.rating.service.type.RatingType;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface RatingService {
  /**
   * DOCUMENT ME!
   *
   * @param   rating  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  RatingSummary saveRatingSummary(RatingSummary rating);

  /**
   * DOCUMENT ME!
   *
   * @param  rating  DOCUMENT ME!
   */
  void deleteRatingSummary(RatingSummary rating);

  /**
   * DOCUMENT ME!
   *
   * @param   itemId  DOCUMENT ME!
   * @param   type    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  RatingSummary readRatingSummary(String itemId, RatingType type);

  /**
   * DOCUMENT ME!
   *
   * @param   itemIds  DOCUMENT ME!
   * @param   type     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, RatingSummary> readRatingSummaries(List<String> itemIds, RatingType type);

  /**
   * DOCUMENT ME!
   *
   * @param  itemId    DOCUMENT ME!
   * @param  type      DOCUMENT ME!
   * @param  customer  DOCUMENT ME!
   * @param  rating    DOCUMENT ME!
   */
  void rateItem(String itemId, RatingType type, Customer customer, Double rating);

  /**
   * DOCUMENT ME!
   *
   * @param   itemId  DOCUMENT ME!
   * @param   type    DOCUMENT ME!
   * @param   start   DOCUMENT ME!
   * @param   finish  DOCUMENT ME!
   * @param   sortBy  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<ReviewDetail> readReviews(String itemId, RatingType type, int start, int finish, RatingSortType sortBy);

  /**
   * DOCUMENT ME!
   *
   * @param  itemId      DOCUMENT ME!
   * @param  type        DOCUMENT ME!
   * @param  customer    DOCUMENT ME!
   * @param  rating      DOCUMENT ME!
   * @param  reviewText  DOCUMENT ME!
   */
  void reviewItem(String itemId, RatingType type, Customer customer, Double rating, String reviewText);

  /**
   * DOCUMENT ME!
   *
   * @param  reviewId  DOCUMENT ME!
   * @param  customer  DOCUMENT ME!
   * @param  helpful   DOCUMENT ME!
   */
  void markReviewHelpful(Long reviewId, Customer customer, Boolean helpful);

  /**
   * Reads a ReviewDetail by the given customer and the itemId.
   *
   * @param   customer  DOCUMENT ME!
   * @param   itemId    DOCUMENT ME!
   *
   * @return  review, or null if review is not found
   */
  ReviewDetail readReviewByCustomerAndItem(Customer customer, String itemId);

} // end interface RatingService
