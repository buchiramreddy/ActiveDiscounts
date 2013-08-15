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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.broadleafcommerce.core.rating.service.type.ReviewStatusType;

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
@Table(name = "BLC_REVIEW_DETAIL")
public class ReviewDetailImpl implements ReviewDetail {
  @Column(name = "REVIEW_DETAIL_ID")
  @GeneratedValue(generator = "ReviewDetailId")
  @GenericGenerator(
    name       = "ReviewDetailId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "ReviewDetailImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.rating.domain.ReviewDetailImpl"
      )
    }
  )
  @Id private Long id;

  /** DOCUMENT ME! */
  @Index(
    name        = "REVIEWDETAIL_CUSTOMER_INDEX",
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
    name     = "REVIEW_SUBMITTED_DATE",
    nullable = false
  )
  protected Date reivewSubmittedDate;

  /** DOCUMENT ME! */
  @Column(
    name     = "REVIEW_TEXT",
    nullable = false
  )
  protected String reviewText;

  /** DOCUMENT ME! */
  @Column(
    name     = "REVIEW_STATUS",
    nullable = false
  )
  @Index(
    name        = "REVIEWDETAIL_STATUS_INDEX",
    columnNames = { "REVIEW_STATUS" }
  )
  protected String reviewStatus;

  /** DOCUMENT ME! */
  @Column(
    name     = "HELPFUL_COUNT",
    nullable = false
  )
  protected Integer helpfulCount;

  /** DOCUMENT ME! */
  @Column(
    name     = "NOT_HELPFUL_COUNT",
    nullable = false
  )
  protected Integer notHelpfulCount;

  /** DOCUMENT ME! */
  @Index(
    name        = "REVIEWDETAIL_SUMMARY_INDEX",
    columnNames = { "RATING_SUMMARY_ID" }
  )
  @JoinColumn(name = "RATING_SUMMARY_ID")
  @ManyToOne(
    optional     = false,
    targetEntity = RatingSummaryImpl.class
  )
  protected RatingSummary ratingSummary;

  /** DOCUMENT ME! */
  @OneToMany(
    mappedBy     = "reviewDetail",
    targetEntity = ReviewFeedbackImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<ReviewFeedback> reviewFeedback;

  /** DOCUMENT ME! */
  @Index(
    name        = "REVIEWDETAIL_RATING_INDEX",
    columnNames = { "RATING_DETAIL_ID" }
  )
  @JoinColumn(name = "RATING_DETAIL_ID")
  @OneToOne(targetEntity = RatingDetailImpl.class)
  protected RatingDetail ratingDetail;

  /**
   * Creates a new ReviewDetailImpl object.
   */
  public ReviewDetailImpl() { }

  /**
   * Creates a new ReviewDetailImpl object.
   *
   * @param  customer             DOCUMENT ME!
   * @param  reivewSubmittedDate  DOCUMENT ME!
   * @param  ratingDetail         DOCUMENT ME!
   * @param  reviewText           DOCUMENT ME!
   * @param  ratingSummary        DOCUMENT ME!
   */
  public ReviewDetailImpl(Customer customer, Date reivewSubmittedDate, RatingDetail ratingDetail, String reviewText,
    RatingSummary ratingSummary) {
    super();
    this.customer            = customer;
    this.reivewSubmittedDate = reivewSubmittedDate;
    this.reviewText          = reviewText;
    this.ratingSummary       = ratingSummary;
    this.reviewFeedback      = new ArrayList<ReviewFeedback>();
    this.helpfulCount        = Integer.valueOf(0);
    this.notHelpfulCount     = Integer.valueOf(0);
    this.reviewStatus        = ReviewStatusType.PENDING.getType();
    this.ratingDetail        = ratingDetail;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#getReviewSubmittedDate()
   */
  @Override public Date getReviewSubmittedDate() {
    return reivewSubmittedDate;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#getReviewText()
   */
  @Override public String getReviewText() {
    return reviewText;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#setReviewText(java.lang.String)
   */
  @Override public void setReviewText(String reviewText) {
    this.reviewText = reviewText;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#getStatus()
   */
  @Override public ReviewStatusType getStatus() {
    return new ReviewStatusType(reviewStatus);
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#getCustomer()
   */
  @Override public Customer getCustomer() {
    return customer;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#helpfulCount()
   */
  @Override public Integer helpfulCount() {
    return helpfulCount;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#notHelpfulCount()
   */
  @Override public Integer notHelpfulCount() {
    return notHelpfulCount;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#getRatingSummary()
   */
  @Override public RatingSummary getRatingSummary() {
    return ratingSummary;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#getRatingDetail()
   */
  @Override public RatingDetail getRatingDetail() {
    return ratingDetail;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.ReviewDetail#getReviewFeedback()
   */
  @Override public List<ReviewFeedback> getReviewFeedback() {
    return (reviewFeedback == null) ? new ArrayList<ReviewFeedback>() : reviewFeedback;
  }

} // end class ReviewDetailImpl
