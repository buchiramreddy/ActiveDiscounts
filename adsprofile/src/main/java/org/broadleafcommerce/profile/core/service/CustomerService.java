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

package org.broadleafcommerce.profile.core.service;

import java.util.List;

import org.broadleafcommerce.common.security.util.PasswordChange;
import org.broadleafcommerce.common.security.util.PasswordReset;
import org.broadleafcommerce.common.service.GenericResponse;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.handler.PasswordUpdatedHandler;
import org.broadleafcommerce.profile.core.service.listener.PostRegistrationObserver;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CustomerService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  postRegisterListeners  DOCUMENT ME!
   */
  void addPostRegisterListener(PostRegistrationObserver postRegisterListeners);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   passwordChange  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer changePassword(PasswordChange passwordChange);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Verifies that the passed in token is valid.
   *
   * <p>Returns responseCodes of "invalidToken", "tokenUsed", and "tokenExpired".</p>
   *
   * @param   token  DOCUMENT ME!
   *
   * @return  verifies that the passed in token is valid.
   */
  GenericResponse checkPasswordResetToken(String token);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer createCustomer();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a <code>Customer</code> by first looking in the database, otherwise creating a new non-persisted <code>
   * Customer.</code>
   *
   * @param   customerId  the id of the customer to lookup
   *
   * @return  either a <code>Customer</code> from the database if it exists, or a new non-persisted <code>
   *          Customer</code>
   */
  Customer createCustomerFromId(Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a non-persisted <code>Customer</code>. Typically used with registering a new customer.
   *
   * @return  a non-persisted <code>Customer</code>.
   */
  Customer createNewCustomer();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long findNextCustomerId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<PasswordUpdatedHandler> getPasswordChangedHandlers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<PasswordUpdatedHandler> getPasswordResetHandlers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   emailAddress  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer readCustomerByEmail(String emailAddress);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   userId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer readCustomerById(Long userId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer readCustomerByUsername(String customerName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer         DOCUMENT ME!
   * @param   password         DOCUMENT ME!
   * @param   passwordConfirm  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer registerCustomer(Customer customer, String password, String passwordConfirm);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  postRegisterListeners  DOCUMENT ME!
   */
  void removePostRegisterListener(PostRegistrationObserver postRegisterListeners);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   passwordReset  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer resetPassword(PasswordReset passwordReset);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer saveCustomer(Customer customer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   * @param   register  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer saveCustomer(Customer customer, boolean register);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordChangedHandlers  DOCUMENT ME!
   */
  void setPasswordChangedHandlers(List<PasswordUpdatedHandler> passwordChangedHandlers);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordResetHandlers  DOCUMENT ME!
   */
  void setPasswordResetHandlers(List<PasswordUpdatedHandler> passwordResetHandlers);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Updates the password for the passed in customer only if the passed in token is valid for that customer.
   *
   * @param   username         Username of the customer
   * @param   token            Valid reset token
   * @param   password         new password
   * @param   confirmPassword  DOCUMENT ME!
   *
   * @return  Response can contain errors including (invalidUsername, inactiveUser, invalidToken, invalidPassword,
   *          tokenExpired)
   */
  GenericResponse resetPasswordUsingToken(String username, String token, String password, String confirmPassword);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Generates an access token and then emails the user.
   *
   * @param   userName           - the user to send a reset password email to.
   * @param   forgotPasswordUrl  - Base url to include in the email.
   *
   * @return  Response can contain errors including (invalidEmail, invalidUsername, inactiveUser)
   */
  GenericResponse sendForgotPasswordNotification(String userName, String forgotPasswordUrl);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Looks up the corresponding Customer and emails the address on file with the associated username.
   *
   * @param   emailAddress  DOCUMENT ME!
   *
   * @return  Response can contain errors including (notFound)
   */
  GenericResponse sendForgotUsernameNotification(String emailAddress);

} // end interface CustomerService
