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
@Table(name = "BLC_RATING_DETAIL")
public class RatingDetailImpl implements RatingDetail {
  @Column(name = "RATING_DETAIL_ID")
  @GeneratedValue(generator = "RatingDetailId")
  @GenericGenerator(
    name       = "RatingDetailId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "RatingDetailImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.rating.domain.RatingDetailImpl"
      )
    }
  )
  @Id private Long id;

  /** DOCUMENT ME! */
  @Column(
    name     = "RATING",
    nullable = false
  )
  protected Double rating;

  /** DOCUMENT ME! */
  @Column(
    name     = "RATING_SUBMITTED_DATE",
    nullable = false
  )
  protected Date ratingSubmittedDate;

  /** DOCUMENT ME! */
  @Index(
    name        = "RATING_CUSTOMER_INDEX",
    columnNames = { "CUSTOMER_ID" }
  )
  @JoinColumn(name = "CUSTOMER_ID")
  @ManyToOne(
    targetEntity = CustomerImpl.class,
    optional     = false
  )
  protected Customer customer;

  /** DOCUMENT ME! */
  @JoinColumn(name = "RATING_SUMMARY_ID")
  @ManyToOne(
    optional     = false,
    targetEntity = RatingSummaryImpl.class
  )
  protected RatingSummary ratingSummary;

  /**
   * Creates a new RatingDetailImpl object.
   */
  public RatingDetailImpl() { }

  /**
   * Creates a new RatingDetailImpl object.
   *
   * @param  ratingSummary        DOCUMENT ME!
   * @param  rating               DOCUMENT ME!
   * @param  ratingSubmittedDate  DOCUMENT ME!
   * @param  customer             DOCUMENT ME!
   */
  public RatingDetailImpl(RatingSummary ratingSummary, Double rating, Date ratingSubmittedDate, Customer customer) {
    super();
    this.ratingSummary       = ratingSummary;
    this.rating              = rating;
    this.ratingSubmittedDate = ratingSubmittedDate;
    this.customer            = customer;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.RatingDetail#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.RatingDetail#getRating()
   */
  @Override public Double getRating() {
    return rating;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.RatingDetail#getRatingSubmittedDate()
   */
  @Override public Date getRatingSubmittedDate() {
    return ratingSubmittedDate;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.RatingDetail#getCustomer()
   */
  @Override public Customer getCustomer() {
    return customer;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.RatingDetail#setRating(java.lang.Double)
   */
  @Override public void setRating(Double newRating) {
    this.rating = newRating;
  }

  /**
   * @see  org.broadleafcommerce.core.rating.domain.RatingDetail#getRatingSummary()
   */
  @Override public RatingSummary getRatingSummary() {
    return ratingSummary;
  }
} // end class RatingDetailImpl
