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
@Table(name = "BLC_STORE")
public class StoreImpl implements Store {
  private static final long serialVersionUID = 1L;

  @Column(
    name     = "STORE_ID",
    nullable = false
  )
  @Id private String id;

  @Column(name = "STORE_NAME")
  @Index(
    name        = "STORE_NAME_INDEX",
    columnNames = { "STORE_NAME" }
  )
  private String name;

  @Column(name = "ADDRESS_1")
  private String address1;

  @Column(name = "ADDRESS_2")
  private String address2;

  @Column(name = "STORE_CITY")
  @Index(
    name        = "STORE_CITY_INDEX",
    columnNames = { "STORE_CITY" }
  )
  private String city;

  @Column(name = "STORE_STATE")
  @Index(
    name        = "STORE_STATE_INDEX",
    columnNames = { "STORE_STATE" }
  )
  private String state;

  @Column(name = "STORE_ZIP")
  @Index(
    name        = "STORE_ZIP_INDEX",
    columnNames = { "STORE_ZIP" }
  )
  private String zip;

  @Column(name = "STORE_COUNTRY")
  @Index(
    name        = "STORE_COUNTRY_INDEX",
    columnNames = { "STORE_COUNTRY" }
  )
  private String country;

  @Column(name = "STORE_PHONE")
  private String phone;

  @Column(name = "LATITUDE")
  @Index(
    name        = "STORE_LATITUDE_INDEX",
    columnNames = { "LATITUDE" }
  )
  private Double latitude;

  @Column(name = "LONGITUDE")
  @Index(
    name        = "STORE_LONGITUDE_INDEX",
    columnNames = { "LONGITUDE" }
  )
  private Double longitude;

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getId()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getId()
   */
  @Override public String getId() {
    return id;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setId(java.lang.String)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setId(java.lang.String)
   */
  @Override public void setId(String id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getName()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getName()
   */
  @Override public String getName() {
    return name;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setName(java.lang.String)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getAddress1()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getAddress1()
   */
  @Override public String getAddress1() {
    return address1;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setAddress1(java.lang.String)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setAddress1(java.lang.String)
   */
  @Override public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getAddress2()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getAddress2()
   */
  @Override public String getAddress2() {
    return address2;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setAddress2(java.lang.String)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setAddress2(java.lang.String)
   */
  @Override public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getCity()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getCity()
   */
  @Override public String getCity() {
    return city;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setCity(java.lang.String)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setCity(java.lang.String)
   */
  @Override public void setCity(String city) {
    this.city = city;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getZip()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getZip()
   */
  @Override public String getZip() {
    return zip;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setZip(java.lang.String)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setZip(java.lang.String)
   */
  @Override public void setZip(String zip) {
    this.zip = zip;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getCountry()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getCountry()
   */
  @Override public String getCountry() {
    return country;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setCountry(java.lang.String)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setCountry(java.lang.String)
   */
  @Override public void setCountry(String country) {
    this.country = country;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getPhone()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getPhone()
   */
  @Override public String getPhone() {
    return phone;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setPhone(java.lang.String)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setPhone(java.lang.String)
   */
  @Override public void setPhone(String phone) {
    this.phone = phone;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getLongitude()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getLongitude()
   */
  @Override public Double getLongitude() {
    return longitude;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setLongitude(java.lang.Float)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setLongitude(java.lang.Float)
   */
  @Override public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getLatitude()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getLatitude()
   */
  @Override public Double getLatitude() {
    return latitude;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setLatitude(java.lang.Float)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setLatitude(java.lang.Float)
   */
  @Override public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setState(java.lang.String)
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#setState(java.lang.String)
   */
  @Override public void setState(String state) {
    this.state = state;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getState()
   */
  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.store.domain.Store#getState()
   */
  @Override public String getState() {
    return state;
  }

} // end class StoreImpl
