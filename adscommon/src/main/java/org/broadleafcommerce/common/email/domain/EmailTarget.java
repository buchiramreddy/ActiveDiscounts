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


/**
 * The EmailTarget interface is used to specify the recipients of the email.
 *
 * @see      org.broadleafcommerce.common.email.domain.EmailTargetImpl
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface EmailTarget extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String[] getBCCAddresses();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String[] getCCAddresses();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getEmailAddress();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  BCCAddresses  DOCUMENT ME!
   */
  void setBCCAddresses(String[] BCCAddresses);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ccAddresses  DOCUMENT ME!
   */
  void setCCAddresses(String[] ccAddresses);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  emailAddress  DOCUMENT ME!
   */
  void setEmailAddress(String emailAddress);

} // end interface EmailTarget
