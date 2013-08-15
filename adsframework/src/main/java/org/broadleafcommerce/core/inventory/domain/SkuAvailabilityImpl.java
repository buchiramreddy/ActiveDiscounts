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

package org.broadleafcommerce.core.inventory.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.broadleafcommerce.core.inventory.service.type.AvailabilityStatusType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * The Class SkuAvailabilityImpl is the default implementation of
 * {@link org.broadleafcommerce.core.inventory.domain.SkuAvailability}.<br>
 * <br>
 * This class is retrieved using the AvailabilityService. The service allows availability to be be location specific
 * (e.g. for store specific inventory availability)<br>
 * <br>
 * This implementation uses a Hibernate implementation of JPA configured through annotations. The Entity references the
 * following tables: BLC_SKU_AVAILABILITY
 *
 * @see         {@link org.broadleafcommerce.core.catalog.domain.Sku}
 * @author      bpolster
 * @deprecated  This is no longer required and is instead implemented as a third-party inventory module
 * @version     $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blInventoryElements"
)
@Deprecated @Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SKU_AVAILABILITY")
public class SkuAvailabilityImpl implements SkuAvailability {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** The date this product will be available. */
  @AdminPresentation(
    friendlyName = "SkuAvailabilityImpl_Available_Date",
    group        = "SkuAvailabilityImpl_Description"
  )
  @Column(name = "AVAILABILITY_DATE")
  protected Date availabilityDate;

  /** The description. */
  @AdminPresentation(
    friendlyName         = "SkuAvailabilityImpl_Availability_Status",
    group                = "SkuAvailabilityImpl_Description",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.inventory.service.type.AvailabilityStatusType"
  )
  @Column(name = "AVAILABILITY_STATUS")
  @Index(
    name        = "SKUAVAIL_STATUS_INDEX",
    columnNames = { "AVAILABILITY_STATUS" }
  )
  protected String availabilityStatus;

  /** The id. */
  @AdminPresentation(
    friendlyName = "SkuAvailabilityImpl_Sku_Availability_ID",
    group        = "SkuAvailabilityImpl_Primary_Key",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "SKU_AVAILABILITY_ID")
  @GeneratedValue(generator = "SkuAvailabilityId")
  @GenericGenerator(
    name       = "SkuAvailabilityId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SkuAvailabilityImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.inventory.domain.SkuAvailabilityImpl"
      )
    }
  )
  @Id protected Long id;

  /** The retail price. */
  @AdminPresentation(
    friendlyName = "SkuAvailabilityImpl_Location_ID",
    group        = "SkuAvailabilityImpl_Description"
  )
  @Column(name = "LOCATION_ID")
  @Index(
    name        = "SKUAVAIL_LOCATION_INDEX",
    columnNames = { "LOCATION_ID" }
  )
  protected Long locationId;

  /** The quantity on hand. */
  @AdminPresentation(
    friendlyName = "SkuAvailabilityImpl_Quantity_On_Hand",
    group        = "SkuAvailabilityImpl_Description"
  )
  @Column(name = "QTY_ON_HAND")
  protected Integer quantityOnHand;

  /** The reserve quantity. */
  @AdminPresentation(
    friendlyName = "SkuAvailabilityImpl_Reserve_Quantity",
    group        = "SkuAvailabilityImpl_Description"
  )
  @Column(name = "RESERVE_QTY")
  protected Integer reserveQuantity;

  /** The sale price. */
  @AdminPresentation(
    friendlyName = "SkuAvailabilityImpl_Sku_ID",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "SKU_ID")
  @Index(
    name        = "SKUAVAIL_SKU_INDEX",
    columnNames = { "SKU_ID" }
  )
  protected Long skuId;

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

    SkuAvailabilityImpl other = (SkuAvailabilityImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (locationId == null) {
      if (other.locationId != null) {
        return false;
      }
    } else if (!locationId.equals(other.locationId)) {
      return false;
    }

    if (skuId == null) {
      if (other.skuId != null) {
        return false;
      }
    } else if (!skuId.equals(other.skuId)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#getAvailabilityDate()
   */
  @Override public Date getAvailabilityDate() {
    return availabilityDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#getAvailabilityStatus()
   */
  @Override public AvailabilityStatusType getAvailabilityStatus() {
    return AvailabilityStatusType.getInstance(availabilityStatus);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the getQuantityOnHand() - getReserveQuantity(). Preferred implementation is to return null if
   * getQuantityOnHand() is null and to treat a null in getReserveQuantity() as ZERO.
   *
   * @return  the getQuantityOnHand() - getReserveQuantity().
   */
  @Override public Integer getAvailableQuantity() {
    if ((getQuantityOnHand() == null) || (getReserveQuantity() == null)) {
      return getQuantityOnHand();
    } else {
      return getQuantityOnHand() - getReserveQuantity();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#getLocationId()
   */
  @Override public Long getLocationId() {
    return locationId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#getQuantityOnHand()
   */
  @Override public Integer getQuantityOnHand() {
    return quantityOnHand;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the reserve quantity. Nulls will be treated the same as 0. Implementations may want to manage a reserve
   * quantity at each location so that the available quantity for purchases is the quantityOnHand - reserveQuantity.
   *
   * @return  the reserve quantity.
   */
  @Override public Integer getReserveQuantity() {
    return reserveQuantity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#getSkuId()
   */
  @Override public Long getSkuId() {
    return skuId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((locationId == null) ? 0 : locationId.hashCode());
    result = (prime * result) + ((skuId == null) ? 0 : skuId.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#setAvailabilityDate(java.util.Date)
   */
  @Override public void setAvailabilityDate(Date availabilityDate) {
    this.availabilityDate = availabilityDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#setAvailabilityStatus(org.broadleafcommerce.core.inventory.service.type.AvailabilityStatusType)
   */
  @Override public void setAvailabilityStatus(final AvailabilityStatusType availabilityStatus) {
    if (availabilityStatus != null) {
      this.availabilityStatus = availabilityStatus.getType();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#setLocationId(java.lang.Long)
   */
  @Override public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#setQuantityOnHand(java.lang.Integer)
   */
  @Override public void setQuantityOnHand(Integer qoh) {
    this.quantityOnHand = qoh;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the reserve quantity. Implementations may want to manage a reserve quantity at each location so that the
   * available quantity for purchases is the quantityOnHand - reserveQuantity.
   *
   * @param  reserveQuantity  DOCUMENT ME!
   */
  @Override public void setReserveQuantity(Integer reserveQuantity) {
    this.reserveQuantity = reserveQuantity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.inventory.domain.SkuAvailability#setSkuId(java.lang.Long)
   */
  @Override public void setSkuId(Long skuId) {
    this.skuId = skuId;
  }
} // end class SkuAvailabilityImpl
