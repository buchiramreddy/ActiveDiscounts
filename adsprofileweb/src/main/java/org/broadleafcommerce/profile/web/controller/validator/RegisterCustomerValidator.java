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

package org.broadleafcommerce.profile.web.controller.validator;

import javax.annotation.Resource;

import org.apache.commons.validator.GenericValidator;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;

import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * DOCUMENT ME!
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
@Component("blRegisterCustomerValidator")
public class RegisterCustomerValidator implements Validator {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource(name = "blCustomerService")
  private CustomerService customerService;

  private String validatePasswordExpression = "[0-9A-Za-z]{4,15}";

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new RegisterCustomerValidator object.
   */
  public RegisterCustomerValidator() { }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getValidatePasswordExpression() {
    return validatePasswordExpression;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  validatePasswordExpression  DOCUMENT ME!
   */
  public void setValidatePasswordExpression(String validatePasswordExpression) {
    this.validatePasswordExpression = validatePasswordExpression;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.validation.Validator#supports(java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean supports(Class clazz) {
    return clazz.equals(RegisterCustomerForm.class);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
   */
  @Override public void validate(Object obj, Errors errors) {
    validate(obj, errors, false);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /*
   *         errors.pushNestedPath("customer");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.required");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "emailAddress.required");
      errors.popNestedPath();

      if (errors.hasErrors()){
          if (!passwordConfirm.equals(password)) {
              errors.rejectValue("passwordConfirm", "invalid");
          }
          if (!customer.getFirstName().matches(validNameRegex)) {
              errors.rejectValue("firstName", "firstName.invalid", null, null);
          }

          if (!customer.getLastName().matches(validNameRegex)) {
              errors.rejectValue("lastName", "lastName.invalid", null, null);
          }

          if (!customer.getPassword().matches(validPasswordRegex)) {
              errors.rejectValue("password", "password.invalid", null, null);
          }

          if (!password.equals(passwordConfirm)) {
              errors.rejectValue("password", "passwordConfirm.invalid", null, null);
          }

          if (!GenericValidator.isEmail(customer.getEmailAddress())) {
              errors.rejectValue("emailAddress", "emailAddress.invalid", null, null);
          }
      }
   */

  /**
   * DOCUMENT ME!
   *
   * @param  obj                  DOCUMENT ME!
   * @param  errors               DOCUMENT ME!
   * @param  useEmailForUsername  DOCUMENT ME!
   */
  public void validate(Object obj, Errors errors, boolean useEmailForUsername) {
    RegisterCustomerForm form = (RegisterCustomerForm) obj;

    Customer customerFromDb = customerService.readCustomerByUsername(form.getCustomer().getUsername());

    if (customerFromDb != null) {
      if (useEmailForUsername) {
        errors.rejectValue("customer.emailAddress", "emailAddress.used", null, null);
      } else {
        errors.rejectValue("customer.username", "username.used", null, null);
      }
    }

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "passwordConfirm.required");

    errors.pushNestedPath("customer");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "emailAddress.required");
    errors.popNestedPath();


    if (!errors.hasErrors()) {
      if (!form.getPassword().matches(getValidatePasswordExpression())) {
        errors.rejectValue("password", "password.invalid", null, null);
      }

      if (!form.getPassword().equals(form.getPasswordConfirm())) {
        errors.rejectValue("password", "passwordConfirm.invalid", null, null);
      }

      if (!GenericValidator.isEmail(form.getCustomer().getEmailAddress())) {
        errors.rejectValue("customer.emailAddress", "emailAddress.invalid", null, null);
      }
    }
  } // end method validate
} // end class RegisterCustomerValidator
