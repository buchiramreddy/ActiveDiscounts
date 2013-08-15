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

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface ZipCode {
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
  Integer getZipcode();

  /**
   * DOCUMENT ME!
   *
   * @param  zipcode  DOCUMENT ME!
   */
  void setZipcode(Integer zipcode);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getZipState();

  /**
   * DOCUMENT ME!
   *
   * @param  zipState  DOCUMENT ME!
   */
  void setZipState(String zipState);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getZipCity();

  /**
   * DOCUMENT ME!
   *
   * @param  zipCity  DOCUMENT ME!
   */
  void setZipCity(String zipCity);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  double getZipLongitude();

  /**
   * DOCUMENT ME!
   *
   * @param  zipLongitude  DOCUMENT ME!
   */
  void setZipLongitude(double zipLongitude);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  double getZipLatitude();

  /**
   * DOCUMENT ME!
   *
   * @param  zipLatitude  DOCUMENT ME!
   */
  void setZipLatitude(double zipLatitude);

} // end interface ZipCode
