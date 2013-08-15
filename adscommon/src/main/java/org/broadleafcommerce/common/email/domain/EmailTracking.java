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

package org.broadleafcommerce.common.email.domain;

import java.io.Serializable;

import java.util.Date;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface EmailTracking extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The dateSent.
   *
   * @return  the dateSent
   */
  Date getDateSent();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The emailAddress.
   *
   * @return  the emailAddress
   */
  String getEmailAddress();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The type.
   *
   * @return  the type
   */
  String getType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  dateSent  the dateSent to set
   */
  void setDateSent(Date dateSent);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  emailAddress  the emailAddress to set
   */
  void setEmailAddress(String emailAddress);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  type  the type to set
   */
  void setType(String type);

} // end interface EmailTracking
