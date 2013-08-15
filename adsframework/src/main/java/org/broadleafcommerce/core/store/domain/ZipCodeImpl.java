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

package org.broadleafcommerce.core.store.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Index;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ZIP_CODE")
public class ZipCodeImpl implements Serializable, ZipCode {
  private static final long serialVersionUID = 1L;

  @Column(
    name     = "ZIP_CODE_ID",
    nullable = false
  )
  @Id private String id;

  @Column(
    name       = "ZIPCODE",
    insertable = false,
    updatable  = false
  )
  @Index(
    name        = "ZIPCODE_ZIP_INDEX",
    columnNames = { "ZIPCODE" }
  )
  private Integer zipcode;

  @Column(
    name       = "ZIP_STATE",
    insertable = false,
    updatable  = false
  )
  @Index(
    name        = "ZIPCODE_STATE_INDEX",
    columnNames = { "ZIP_STATE" }
  )
  private String zipState;

  @Column(name = "ZIP_CITY")
  @Index(
    name        = "ZIPCODE_CITY_INDEX",
    columnNames = { "ZIP_CITY" }
  )
  private String zipCity;

  @Column(name = "ZIP_LONGITUDE")
  @Index(
    name        = "ZIPCODE_LONGITUDE_INDEX",
    columnNames = { "ZIP_LONGITUDE" }
  )
  private double zipLongitude;

  @Column(name = "ZIP_LATITUDE")
  @Index(
    name        = "ZIPCODE_LATITUDE_INDEX",
    columnNames = { "ZIP_LATITUDE" }
  )
  private double zipLatitude;

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#getId()
   */
  @Override public String getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#setId(java.lang.String)
   */
  @Override public void setId(String id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#getZipcode()
   */
  @Override public Integer getZipcode() {
    return zipcode;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#setZipcode(java.lang.Integer)
   */
  @Override public void setZipcode(Integer zipcode) {
    this.zipcode = zipcode;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#getZipState()
   */
  @Override public String getZipState() {
    return zipState;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#setZipState(java.lang.String)
   */
  @Override public void setZipState(String zipState) {
    this.zipState = zipState;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#getZipCity()
   */
  @Override public String getZipCity() {
    return zipCity;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#setZipCity(java.lang.String)
   */
  @Override public void setZipCity(String zipCity) {
    this.zipCity = zipCity;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#getZipLongitude()
   */
  @Override public double getZipLongitude() {
    return zipLongitude;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#setZipLongitude(double)
   */
  @Override public void setZipLongitude(double zipLongitude) {
    this.zipLongitude = zipLongitude;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#getZipLatitude()
   */
  @Override public double getZipLatitude() {
    return zipLatitude;
  }

  /**
   * @see  org.broadleafcommerce.core.store.domain.ZipCode#setZipLatitude(double)
   */
  @Override public void setZipLatitude(double zipLatitude) {
    this.zipLatitude = zipLatitude;
  }

} // end class ZipCodeImpl
