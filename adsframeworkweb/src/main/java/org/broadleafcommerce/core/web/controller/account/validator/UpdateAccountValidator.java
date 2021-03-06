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

import javax.annotation.Resource;

import org.apache.commons.validator.GenericValidator;

import org.broadleafcommerce.core.web.controller.account.UpdateAccountForm;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;
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
@Component("blUpdateAccountValidator")
public class UpdateAccountValidator implements Validator {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerService")
  CustomerService customerService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

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
   * @param  form    DOCUMENT ME!
   * @param  errors  DOCUMENT ME!
   */
  public void validate(UpdateAccountForm form, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "emailAddress.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.required");

    if (!errors.hasErrors()) {
      // is this a valid email address?
      if (!GenericValidator.isEmail(form.getEmailAddress())) {
        errors.rejectValue("emailAddress", "emailAddress.invalid");
      }

      // check email address to see if it is already in use by another customer
      Customer customerMatchingNewEmail = customerService.readCustomerByEmail(form.getEmailAddress());

      if ((customerMatchingNewEmail != null)
            && (CustomerState.getCustomer().getId() != customerMatchingNewEmail.getId())) {
        // customer found with new email entered, and it is not the current customer
        errors.rejectValue("emailAddress", "emailAddress.used");
      }

    }

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
   */
  @Override public void validate(Object target, Errors errors) { }

} // end class UpdateAccountValidator
