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

package org.broadleafcommerce.core.web.controller.account.validator;

import org.broadleafcommerce.common.security.util.PasswordChange;

import org.broadleafcommerce.profile.web.core.CustomerState;

import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Component("blChangePasswordValidator")
public class ChangePasswordValidator implements Validator {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final String DEFAULT_VALID_PASSWORD_REGEX = "[0-9A-Za-z]{4,15}";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String validPasswordRegex = DEFAULT_VALID_PASSWORD_REGEX;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getValidPasswordRegex() {
    return validPasswordRegex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  validPasswordRegex  DOCUMENT ME!
   */
  public void setValidPasswordRegex(String validPasswordRegex) {
    this.validPasswordRegex = validPasswordRegex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.validation.Validator#supports(java.lang.Class)
   */
  @Override public boolean supports(Class<?> clazz) {
    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordChange  DOCUMENT ME!
   * @param  errors          DOCUMENT ME!
   */
  public void validate(PasswordChange passwordChange, Errors errors) {
    String currentPassword = passwordChange.getCurrentPassword();
    String password        = passwordChange.getNewPassword();
    String passwordConfirm = passwordChange.getNewPasswordConfirm();

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", "currentPassword.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "newPassword.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPasswordConfirm", "newPasswordConfirm.required");

    if (!errors.hasErrors()) {
      // validate current password
      if (!currentPassword.equals(CustomerState.getCustomer().getPassword())) {
        errors.rejectValue("currentPassword", "currentPassword.invalid");
      }

      // password and confirm password fields must be equal
      if (!passwordConfirm.equals(password)) {
        errors.rejectValue("newPasswordConfirm", "newPasswordConfirm.invalid");
      }

      // restrict password characteristics
      if (!password.matches(validPasswordRegex)) {
        errors.rejectValue("newPassword", "newPassword.invalid");
      }
    }

  } // end method validate

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
   */
  @Override public void validate(Object target, Errors errors) { }

} // end class ChangePasswordValidator
