/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.activediscounts.controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.core.web.controller.account.BroadleafRegisterController;

import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * The controller responsible for registering a customer.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Controller
@RequestMapping("/register")
public class RegisterController extends BroadleafRegisterController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafRegisterController#initCustomerRegistrationForm()
   */
  @ModelAttribute("registrationForm")
  @Override public RegisterCustomerForm initCustomerRegistrationForm() {
    return super.initCustomerRegistrationForm();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request               DOCUMENT ME!
   * @param   response              DOCUMENT ME!
   * @param   model                 DOCUMENT ME!
   * @param   registerCustomerForm  DOCUMENT ME!
   * @param   errors                DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  @RequestMapping(method = RequestMethod.POST)
  public String processRegister(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("registrationForm") RegisterCustomerForm registerCustomerForm, BindingResult errors)
    throws ServiceException {
    return super.processRegister(registerCustomerForm, errors, request, response, model);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request               DOCUMENT ME!
   * @param   response              DOCUMENT ME!
   * @param   model                 DOCUMENT ME!
   * @param   registerCustomerForm  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(method = RequestMethod.GET)
  public String register(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("registrationForm") RegisterCustomerForm registerCustomerForm) {
    return super.register(registerCustomerForm, request, response, model);
  }
} // end class RegisterController
