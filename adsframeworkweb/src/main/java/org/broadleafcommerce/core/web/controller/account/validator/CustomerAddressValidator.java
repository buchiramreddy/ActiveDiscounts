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

import org.broadleafcommerce.core.web.controller.account.CustomerAddressForm;

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
@Component("blCustomerAddressValidator")
public class CustomerAddressValidator implements Validator {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.validation.Validator#supports(java.lang.Class)
   */
  @Override
  @SuppressWarnings("rawtypes")
  public boolean supports(Class clazz) {
    return clazz.equals(CustomerAddressValidator.class);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
   */
  @Override public void validate(Object obj, Errors errors) {
    CustomerAddressForm form = (CustomerAddressForm) obj;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.addressLine1", "addressLine1.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.city", "city.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postalCode", "postalCode.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.firstName", "firstName.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.lastName", "lastName.required");

    if (form.getAddress().getCountry() == null) {
      errors.rejectValue("address.country", "country.required", null, null);
    }

  }
} // end class CustomerAddressValidator
