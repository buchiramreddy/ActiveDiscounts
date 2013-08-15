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


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface Store extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getId();

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(String id);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getName();

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   */
  void setName(String name);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getAddress1();

  /**
   * DOCUMENT ME!
   *
   * @param  address1  DOCUMENT ME!
   */
  void setAddress1(String address1);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getAddress2();

  /**
   * DOCUMENT ME!
   *
   * @param  address2  DOCUMENT ME!
   */
  void setAddress2(String address2);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCity();

  /**
   * DOCUMENT ME!
   *
   * @param  city  DOCUMENT ME!
   */
  void setCity(String city);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getZip();

  /**
   * DOCUMENT ME!
   *
   * @param  zip  DOCUMENT ME!
   */
  void setZip(String zip);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCountry();

  /**
   * DOCUMENT ME!
   *
   * @param  country  DOCUMENT ME!
   */
  void setCountry(String country);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getPhone();

  /**
   * DOCUMENT ME!
   *
   * @param  phone  DOCUMENT ME!
   */
  void setPhone(String phone);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Double getLongitude();

  /**
   * DOCUMENT ME!
   *
   * @param  longitude  DOCUMENT ME!
   */
  void setLongitude(Double longitude);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Double getLatitude();

  /**
   * DOCUMENT ME!
   *
   * @param  latitude  DOCUMENT ME!
   */
  void setLatitude(Double latitude);

  /**
   * DOCUMENT ME!
   *
   * @param  state  DOCUMENT ME!
   */
  void setState(String state);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getState();

} // end interface Store
