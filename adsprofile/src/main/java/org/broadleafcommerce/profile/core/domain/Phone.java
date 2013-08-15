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

package org.broadleafcommerce.profile.core.domain;

import java.io.Serializable;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface Phone extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getPhoneNumber();

  /**
   * DOCUMENT ME!
   *
   * @param  phoneNumber  DOCUMENT ME!
   */
  void setPhoneNumber(String phoneNumber);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isDefault();

  /**
   * DOCUMENT ME!
   *
   * @param  isDefault  DOCUMENT ME!
   */
  void setDefault(boolean isDefault);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isActive();

  /**
   * DOCUMENT ME!
   *
   * @param  isActive  DOCUMENT ME!
   */
  void setActive(boolean isActive);
} // end interface Phone
