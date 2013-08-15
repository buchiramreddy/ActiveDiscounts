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

package org.broadleafcommerce.core.offer.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderImpl;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.FALSE,
  friendlyName        = "OfferCodeImpl_baseOfferCode"
)
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_OFFER_CODE")
public class OfferCodeImpl implements OfferCode {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(friendlyName = "OfferCodeImpl_Offer_Code_Id")
  @Column(name = "OFFER_CODE_ID")
  @GeneratedValue(generator = "OfferCodeId")
  @GenericGenerator(
    name       = "OfferCodeId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OfferCodeImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.offer.domain.OfferCodeImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferCodeImpl_Code_Max_Uses",
    order        = 5000
  )
  @Column(name = "MAX_USES")
  protected Integer maxUses;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferCodeImpl_Offer",
    order        = 2000,
    prominent    = true,
    gridOrder    = 2000
  )
  @AdminPresentationToOneLookup
  @Index(
    name        = "OFFERCODE_OFFER_INDEX",
    columnNames = { "OFFER_ID" }
  )
  @JoinColumn(name = "OFFER_ID")
  @ManyToOne(
    targetEntity = OfferImpl.class,
    optional     = false
  )
  protected Offer                               offer;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferCodeImpl_Offer_Code",
    order        = 1000,
    prominent    = true,
    gridOrder    = 1000
  )
  @Column(
    name     = "OFFER_CODE",
    nullable = false
  )
  @Index(
    name        = "OFFERCODE_CODE_INDEX",
    columnNames = { "OFFER_CODE" }
  )
  protected String offerCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferCodeImpl_Code_End_Date",
    order        = 4000
  )
  @Column(name = "END_DATE")
  protected Date offerCodeEndDate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferCodeImpl_Code_Start_Date",
    order        = 3000
  )
  @Column(name = "START_DATE")
  protected Date offerCodeStartDate;

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @JoinTable(
    name               = "BLC_ORDER_OFFER_CODE_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "OFFER_CODE_ID",
        referencedColumnName = "OFFER_CODE_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "ORDER_ID",
        referencedColumnName = "ORDER_ID"
      )
  )
  @ManyToMany(
    fetch        = FetchType.LAZY,
    targetEntity = OrderImpl.class
  )
  protected List<Order> orders = new ArrayList<Order>();

  /** DOCUMENT ME! */
  @Column(name = "USES")
  @Deprecated protected int uses;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    OfferCodeImpl other = (OfferCodeImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (offer == null) {
      if (other.offer != null) {
        return false;
      }
    } else if (!offer.equals(other.offer)) {
      return false;
    }

    if (offerCode == null) {
      if (other.offerCode != null) {
        return false;
      }
    } else if (!offerCode.equals(other.offerCode)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#getEndDate()
   */
  @Override public Date getEndDate() {
    return offerCodeEndDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#getMaxUses()
   */
  @Override public int getMaxUses() {
    return maxUses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#getOffer()
   */
  @Override public Offer getOffer() {
    return offer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#getOfferCode()
   */
  @Override public String getOfferCode() {
    return offerCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#getOrders()
   */
  @Override public List<Order> getOrders() {
    return orders;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#getStartDate()
   */
  @Override public Date getStartDate() {
    return offerCodeStartDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#getUses()
   */
  @Deprecated @Override public int getUses() {
    return uses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((offer == null) ? 0 : offer.hashCode());
    result = (prime * result) + ((offerCode == null) ? 0 : offerCode.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#setEndDate(java.util.Date)
   */
  @Override public void setEndDate(Date endDate) {
    this.offerCodeEndDate = endDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#setMaxUses(int)
   */
  @Override public void setMaxUses(int maxUses) {
    this.maxUses = maxUses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#setOffer(org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public void setOffer(Offer offer) {
    this.offer = offer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#setOfferCode(java.lang.String)
   */
  @Override public void setOfferCode(String offerCode) {
    this.offerCode = offerCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#setOrders(java.util.List)
   */
  @Override public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#setStartDate(java.util.Date)
   */
  @Override public void setStartDate(Date startDate) {
    this.offerCodeStartDate = startDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferCode#setUses(int)
   */
  @Deprecated @Override public void setUses(int uses) {
    this.uses = uses;
  }


} // end class OfferCodeImpl
