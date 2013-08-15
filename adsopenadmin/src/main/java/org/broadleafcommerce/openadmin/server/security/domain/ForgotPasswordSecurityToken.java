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

package org.broadleafcommerce.openadmin.server.security.domain;

import java.io.Serializable;

import java.util.Date;


/**
 * DOCUMENT ME!
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface ForgotPasswordSecurityToken extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Return the userId that this token was created for.
   *
   * @return  return the userId that this token was created for.
   */
  Long getAdminUserId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Date the token was created.
   *
   * @return  date the token was created.
   */
  Date getCreateDate();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the security token.
   *
   * @return  the security token.
   */
  String getToken();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Date the token was used to reset the password.
   *
   * @return  date the token was used to reset the password.
   */
  Date getTokenUsedDate();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns true if the token has already been used.
   *
   * @return  true if the token has already been used.
   */
  boolean isTokenUsedFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Store the userId that this token is associated with.
   *
   * @param  adminUserId  DOCUMENT ME!
   */
  void setAdminUserId(Long adminUserId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set the generation date for the token.
   *
   * @param  date  DOCUMENT ME!
   */
  void setCreateDate(Date date);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the security token.
   *
   * @param  token  DOCUMENT ME!
   */
  void setToken(String token);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set the date the token was used to reset the password.
   *
   * @param  date  DOCUMENT ME!
   */
  void setTokenUsedDate(Date date);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the token used flag.
   *
   * @param  tokenUsed  DOCUMENT ME!
   */
  void setTokenUsedFlag(boolean tokenUsed);
} // end interface ForgotPasswordSecurityToken
