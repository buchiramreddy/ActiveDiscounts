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

package org.broadleafcommerce.common.security.util;

import java.io.Serializable;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class PasswordReset implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String  email;
  private boolean passwordChangeRequired      = false;
  private int     passwordLength              = 22;
  private boolean sendResetEmailReliableAsync = false;

  private String username;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new PasswordReset object.
   */
  public PasswordReset() { }

  /**
   * Creates a new PasswordReset object.
   *
   * @param  username  DOCUMENT ME!
   */
  public PasswordReset(String username) {
    this.username = username;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getEmail() {
    return email;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean getPasswordChangeRequired() {
    return passwordChangeRequired;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getPasswordLength() {
    return passwordLength;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getUsername() {
    return username;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isSendResetEmailReliableAsync() {
    return sendResetEmailReliableAsync;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  email  DOCUMENT ME!
   */
  public void setEmail(String email) {
    this.email = email;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordChangeRequired  DOCUMENT ME!
   */
  public void setPasswordChangeRequired(boolean passwordChangeRequired) {
    this.passwordChangeRequired = passwordChangeRequired;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordLength  DOCUMENT ME!
   */
  public void setPasswordLength(int passwordLength) {
    this.passwordLength = passwordLength;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sendResetEmailReliableAsync  DOCUMENT ME!
   */
  public void setSendResetEmailReliableAsync(boolean sendResetEmailReliableAsync) {
    this.sendResetEmailReliableAsync = sendResetEmailReliableAsync;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  username  DOCUMENT ME!
   */
  public void setUsername(String username) {
    this.username = username;
  }
} // end class PasswordReset
