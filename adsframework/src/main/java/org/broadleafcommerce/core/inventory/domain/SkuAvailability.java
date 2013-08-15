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

import java.io.Serializable;

import java.util.Date;

import org.broadleafcommerce.core.inventory.service.type.AvailabilityStatusType;


/**
 * Implementations of this interface are used to hold data about SKU availability.<br>
 * <br>
 * You should implement this class if you want to make significant changes to how the class is persisted. If you just
 * want to add additional fields then you should extend
 * {@link org.broadleafcommerce.core.inventory.domain.SkuAvailabilityImpl}.
 *
 * @see         {@link org.broadleafcommerce.core.inventory.domain.SkuAvailabilityImpl}
 * @author      bpolster
 * @deprecated  This is no longer required and is instead implemented as a third-party inventory module
 * @version     $Revision$, $Date$
 */
@Deprecated public interface SkuAvailability extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the data the SKU will be available. This property may return null which has an implementation specific
   * meaning.
   *
   * @return  the data the SKU will be available.
   */
  Date getAvailabilityDate();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns an implementation specific availability status. This property can return null.
   *
   * @return  an implementation specific availability status.
   */
  AvailabilityStatusType getAvailabilityStatus();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the getQuantityOnHand() - getReserveQuantity(). Preferred implementation is to return null if
   * getQuantityOnHand() is null and to treat a null in getReserveQuantity() as ZERO.
   *
   * @return  the getQuantityOnHand() - getReserveQuantity().
   */
  Integer getAvailableQuantity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the id of this SkuAvailability.
   *
   * @return  the id of this SkuAvailability.
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the USPSLocation id of this skuAvailability. SKU availability records may or may not be location specific
   * and using null locations are a common implementation model.
   *
   * @return  the USPSLocation id of this skuAvailability.
   */
  Long getLocationId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the number of this items that are currently in stock and available for sell. Returning null has an
   * implementation specific meaning.
   *
   * @return  the number of this items that are currently in stock and available for sell.
   */
  Integer getQuantityOnHand();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the reserve quantity. Nulls will be treated the same as 0. Implementations may want to manage a reserve
   * quantity at each location so that the available quantity for purchases is the quantityOnHand - reserveQuantity.
   *
   * @return  the reserve quantity.
   */
  Integer getReserveQuantity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the id of this SKU associated with SkuAvailability record.
   *
   * @return  the id of this SKU associated with SkuAvailability record.
   */
  Long getSkuId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the date the SKU will be available. Setting to null is allowed and has an implementation specific meaning.
   *
   * @param  availabilityDate  DOCUMENT ME!
   */
  void setAvailabilityDate(Date availabilityDate);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the availability status.
   *
   * @param  status  DOCUMENT ME!
   */
  void setAvailabilityStatus(AvailabilityStatusType status);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the id of this SkuAvailability record.
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the USPSLocation id of this skuAvailability. SKU availability records may or may not be location specific and
   * using null locations are a common implementation model.
   *
   * @param  id  DOCUMENT ME!
   */
  void setLocationId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the quantity on hand. Setting to null is allowed and has an implementation specific meaning.
   *
   * @param  quantityOnHand  DOCUMENT ME!
   */
  void setQuantityOnHand(Integer quantityOnHand);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the reserve quantity. Implementations may want to manage a reserve quantity at each location so that the
   * available quantity for purchases is the quantityOnHand - reserveQuantity.
   *
   * @param  reserveQuantity  DOCUMENT ME!
   */
  void setReserveQuantity(Integer reserveQuantity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the id of this SKU.
   *
   * @param  id  DOCUMENT ME!
   */
  void setSkuId(Long id);
} // end interface SkuAvailability
