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

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The purpose of this entity is to simply enforce the not null constraints on the two columns in this join table.
 * Otherwise, during DDL creation, Hibernate was creating incompatible SQL for MS SQLServer.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_QUAL_CRIT_OFFER_XREF")
public class CriteriaOfferXref {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** The category id. */
  @EmbeddedId CriteriaOfferXrefPK criteriaOfferXrefPK = new CriteriaOfferXrefPK();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public CriteriaOfferXrefPK getCriteriaOfferXrefPK() {
    return criteriaOfferXrefPK;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  criteriaOfferXrefPK  DOCUMENT ME!
   */
  public void setCriteriaOfferXrefPK(final CriteriaOfferXrefPK criteriaOfferXrefPK) {
    this.criteriaOfferXrefPK = criteriaOfferXrefPK;
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  public static class CriteriaOfferXrefPK implements Serializable {
    //~ Static fields/initializers -------------------------------------------------------------------------------------

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    //~ Instance fields ------------------------------------------------------------------------------------------------

    @JoinColumn(name = "OFFER_ID")
    @ManyToOne(
      targetEntity = OfferImpl.class,
      optional     = false
    )
    protected Offer offer = new OfferImpl();

    @JoinColumn(name = "OFFER_ITEM_CRITERIA_ID")
    @ManyToOne(
      targetEntity = OfferItemCriteriaImpl.class,
      optional     = false
    )
    protected OfferItemCriteria offerCriteria = new OfferItemCriteriaImpl();

    //~ Methods --------------------------------------------------------------------------------------------------------

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

      CriteriaOfferXrefPK other = (CriteriaOfferXrefPK) obj;

      if (offer == null) {
        if (other.offer != null) {
          return false;
        }
      } else if (!offer.equals(other.offer)) {
        return false;
      }

      if (offerCriteria == null) {
        if (other.offerCriteria != null) {
          return false;
        }
      } else if (!offerCriteria.equals(other.offerCriteria)) {
        return false;
      }

      return true;
    } // end method equals

    //~ ----------------------------------------------------------------------------------------------------------------

    public Offer getOffer() {
      return offer;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public OfferItemCriteria getOfferCriteria() {
      return offerCriteria;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public int hashCode() {
      final int prime  = 31;
      int       result = 1;
      result = (prime * result) + ((offer == null) ? 0 : offer.hashCode());
      result = (prime * result) + ((offerCriteria == null) ? 0 : offerCriteria.hashCode());

      return result;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setOffer(Offer offer) {
      this.offer = offer;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setOfferCriteria(OfferItemCriteria offerCriteria) {
      this.offerCriteria = offerCriteria;
    }

  } // end class CriteriaOfferXrefPK
} // end class CriteriaOfferXref
