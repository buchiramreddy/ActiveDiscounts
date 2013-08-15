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

package org.broadleafcommerce.profile.core.service.validator;

import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Component("blResetPasswordValidator")
public class ResetPasswordValidator implements Validator {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String validPasswordRegex = RegistrationValidator.DEFAULT_VALID_PASSWORD_REGEX;

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
   * @see  org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
   */
  @Override public void validate(Object target, Errors errors) { }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  username         DOCUMENT ME!
   * @param  password         DOCUMENT ME!
   * @param  confirmPassword  DOCUMENT ME!
   * @param  errors           DOCUMENT ME!
   */
  public void validate(String username, String password, String confirmPassword, Errors errors) {
    if ((password == null) || "".equals(password)) {
      errors.reject("password", "password.required");
    }

    if ((username == null) || "".equals(username)) {
      errors.reject("username", "username.required");
    }

    if (!errors.hasErrors()) {
      if (!password.matches(validPasswordRegex)) {
        errors.rejectValue("password", "password.invalid", null, null);
      } else {
        if (!password.equals(confirmPassword)) {
          errors.rejectValue("password", "passwordConfirm.invalid", null, null);
        }
      }
    }
  }
} // end class ResetPasswordValidator
