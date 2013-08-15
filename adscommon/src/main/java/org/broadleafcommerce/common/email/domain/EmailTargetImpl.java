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

import java.util.Arrays;


/**
 * Basic implementation of EmailTarget.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class EmailTargetImpl implements EmailTarget {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String[] bccAddresses;

  /** DOCUMENT ME! */
  protected String[] ccAddresses;

  /** DOCUMENT ME! */
  protected String   emailAddress;

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

    EmailTargetImpl other = (EmailTargetImpl) obj;

    if (!Arrays.equals(bccAddresses, other.bccAddresses)) {
      return false;
    }

    if (!Arrays.equals(ccAddresses, other.ccAddresses)) {
      return false;
    }

    if (emailAddress == null) {
      if (other.emailAddress != null) {
        return false;
      }
    } else if (!emailAddress.equals(other.emailAddress)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   * @see org.broadleafcommerce.common.email.domain.EmailTarget#getBCCAddresses()
   */

  @Override public String[] getBCCAddresses() {
    return bccAddresses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   * @see org.broadleafcommerce.common.email.domain.EmailTarget#getCCAddresses()
   */

  @Override public String[] getCCAddresses() {
    return ccAddresses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   * @see org.broadleafcommerce.common.email.domain.EmailTarget#getEmailAddress()
   */

  @Override public String getEmailAddress() {
    return emailAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + Arrays.hashCode(bccAddresses);
    result = (prime * result) + Arrays.hashCode(ccAddresses);
    result = (prime * result) + ((emailAddress == null) ? 0 : emailAddress.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   * @see
   * org.broadleafcommerce.common.email.domain.EmailTarget#setBCCAddresses(java.lang
   * .String[])
   */

  @Override public void setBCCAddresses(String[] bccAddresses) {
    this.bccAddresses = bccAddresses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   * @see
   * org.broadleafcommerce.common.email.domain.EmailTarget#setCCAddresses(java.lang
   * .String[])
   */

  @Override public void setCCAddresses(String[] ccAddresses) {
    this.ccAddresses = ccAddresses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   * @see
   * org.broadleafcommerce.common.email.domain.EmailTarget#setEmailAddress(java.lang
   * .String)
   */

  @Override public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

} // end class EmailTargetImpl
