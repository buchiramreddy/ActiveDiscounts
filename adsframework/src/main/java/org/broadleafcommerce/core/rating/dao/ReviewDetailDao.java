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

import org.broadleafcommerce.core.rating.domain.ReviewDetail;
import org.broadleafcommerce.core.rating.domain.ReviewFeedback;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface ReviewDetailDao {
  /**
   * DOCUMENT ME!
   *
   * @param   reviewId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ReviewDetail readReviewDetailById(Long reviewId);

  /**
   * DOCUMENT ME!
   *
   * @param   reviewDetail  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ReviewDetail saveReviewDetail(ReviewDetail reviewDetail);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ReviewDetail create();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ReviewFeedback createFeedback();

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   * @param   itemId    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ReviewDetail readReviewByCustomerAndItem(Customer customer, String itemId);

} // end interface ReviewDetailDao
