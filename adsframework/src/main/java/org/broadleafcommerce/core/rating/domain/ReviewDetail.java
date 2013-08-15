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

package org.broadleafcommerce.core.rating.domain;

import java.util.Date;
import java.util.List;

import org.broadleafcommerce.core.rating.service.type.ReviewStatusType;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface ReviewDetail {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer getCustomer();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getReviewText();

  /**
   * DOCUMENT ME!
   *
   * @param  reviewText  DOCUMENT ME!
   */
  void setReviewText(String reviewText);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Date getReviewSubmittedDate();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Integer helpfulCount();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Integer notHelpfulCount();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ReviewStatusType getStatus();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  RatingSummary getRatingSummary();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  RatingDetail getRatingDetail();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<ReviewFeedback> getReviewFeedback();

} // end interface ReviewDetail
