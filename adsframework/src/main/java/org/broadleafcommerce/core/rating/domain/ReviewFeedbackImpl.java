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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;

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
@Table(name = "BLC_REVIEW_FEEDBACK")
public class ReviewFeedbackImpl implements ReviewFeedback {
  /** DOCUMENT ME! */
  @Column(name = "REVIEW_FEEDBACK_ID")
  @GeneratedValue(generator = "ReviewFeedbackId")
  @GenericGenerator(
    name       = "ReviewFeedbackId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "ReviewFeedbackImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.rating.domain.ReviewFeedbackImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Index(
    name        = "REVIEWFEED_CUSTOMER_INDEX",
    columnNames = { "CUSTOMER_ID" }
  )
  @JoinColumn(name = "CUSTOMER_ID")
  @ManyToOne(
    targetEntity = CustomerImpl.class,
    optional     = false
  )
  protected Customer customer;

  /** DOCUMENT ME! */
  @Column(
    name     = "IS_HELPFUL",
    nullable = false
  )
  protected Boolean isHelpful = false;

  /** DOCUMENT ME! */
  @Index(
    name        = "REVIEWFEED_DETAIL_INDEX",
    columnNames = { "REVIEW_DETAIL_ID" }
  )
  @JoinColumn(name = "REVIEW_DETAIL_ID")
  @ManyToOne(
    optional     = false,
    targetEntity = ReviewDetailImpl.class
  )
  protected ReviewDetail reviewDetail;

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewFeedback#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewFeedback#getCustomer()
   */
  @Override public Customer getCustomer() {
    return customer;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewFeedback#getReviewDetail()
   */
  @Override public ReviewDetail getReviewDetail() {
    return reviewDetail;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewFeedback#getIsHelpful()
   */
  @Override public Boolean getIsHelpful() {
    return isHelpful;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewFeedback#setIsHelpful(java.lang.Boolean)
   */
  @Override public void setIsHelpful(Boolean isHelpful) {
    this.isHelpful = isHelpful;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewFeedback#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewFeedback#setCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewFeedback#setReviewDetail(org.broadleafcommerce.core.rating.domain.ReviewDetail)
   */
  @Override public void setReviewDetail(ReviewDetail reviewDetail) {
    this.reviewDetail = reviewDetail;
  }

} // end class ReviewFeedbackImpl
