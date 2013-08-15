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

import org.broadleafcommerce.core.rating.domain.RatingDetail;
import org.broadleafcommerce.core.rating.domain.RatingSummary;
import org.broadleafcommerce.core.rating.domain.ReviewDetail;
import org.broadleafcommerce.core.rating.service.type.RatingType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface RatingSummaryDao {
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
  List<RatingSummary> readRatingSummaries(List<String> itemIds, RatingType type);

  /**
   * DOCUMENT ME!
   *
   * @param   summary  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  RatingSummary saveRatingSummary(RatingSummary summary);

  /**
   * DOCUMENT ME!
   *
   * @param  summary  DOCUMENT ME!
   */
  void deleteRatingSummary(RatingSummary summary);

  /**
   * DOCUMENT ME!
   *
   * @param   customerId       DOCUMENT ME!
   * @param   ratingSummaryId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  RatingDetail readRating(Long customerId, Long ratingSummaryId);

  /**
   * DOCUMENT ME!
   *
   * @param   customerId       DOCUMENT ME!
   * @param   ratingSummaryId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ReviewDetail readReview(Long customerId, Long ratingSummaryId);
} // end interface RatingSummaryDao
