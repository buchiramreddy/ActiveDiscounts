/*
 * Copyright 2008-2013 the original author or authors.
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

package org.broadleafcommerce.core.web.controller.account;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.security.MergeCartProcessor;
import org.broadleafcommerce.common.web.controller.BroadleafAbstractController;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.web.controller.validator.RegisterCustomerValidator;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;
import org.broadleafcommerce.profile.web.core.service.LoginService;

import org.springframework.security.core.Authentication;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;


/**
 * The controller responsible for registering a customer.
 *
 * <p>Uses a component registered with the name blCustomerValidator to perform validation of the submitted customer.</p>
 *
 * <p>Uses the property "useEmailForLogin" to determine if the username should be defaulted to the email address if no
 * username is supplied.</p>
 *
 * @author   apazzolini
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class BroadleafRegisterController extends BroadleafAbstractController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static String registerSuccessView = "ajaxredirect:/";

  /** DOCUMENT ME! */
  protected static String registerView = "authentication/register";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerService")
  protected CustomerService customerService;

  /** DOCUMENT ME! */
  @Resource(name = "blLoginService")
  protected LoginService loginService;

  /** DOCUMENT ME! */
  @Resource(name = "blMergeCartProcessor")
  protected MergeCartProcessor mergeCartProcessor;

  /** DOCUMENT ME! */
  @Resource(name = "blRegisterCustomerValidator")
  protected RegisterCustomerValidator registerCustomerValidator;

  private boolean useEmailForLogin = true;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the view that will be returned from this controller when the registration is successful. The success view
   * should be a redirect (e.g. start with "redirect:" since this will cause the entire SpringSecurity pipeline to be
   * fulfilled.
   *
   * <p>By default, returns "redirect:/"</p>
   *
   * @return  the register success view
   */
  public String getRegisterSuccessView() {
    return registerSuccessView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the view that will be used to display the registration page.
   *
   * <p>By default, returns "/register"</p>
   *
   * @return  the register view
   */
  public String getRegisterView() {
    return registerView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public RegisterCustomerForm initCustomerRegistrationForm() {
    Customer customer = CustomerState.getCustomer();

    if ((customer == null) || !customer.isAnonymous()) {
      customer = customerService.createCustomerFromId(null);
    }

    RegisterCustomerForm customerRegistrationForm = new RegisterCustomerForm();
    customerRegistrationForm.setCustomer(customer);

    return customerRegistrationForm;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isUseEmailForLogin() {
    return useEmailForLogin;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   registerCustomerForm  DOCUMENT ME!
   * @param   errors                DOCUMENT ME!
   * @param   request               DOCUMENT ME!
   * @param   response              DOCUMENT ME!
   * @param   model                 DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  public String processRegister(RegisterCustomerForm registerCustomerForm, BindingResult errors,
    HttpServletRequest request, HttpServletResponse response, Model model) throws ServiceException {
    if (useEmailForLogin) {
      Customer customer = registerCustomerForm.getCustomer();
      customer.setUsername(customer.getEmailAddress());
    }

    registerCustomerValidator.validate(registerCustomerForm, errors, useEmailForLogin);

    if (!errors.hasErrors()) {
      Customer newCustomer = customerService.registerCustomer(registerCustomerForm.getCustomer(),
          registerCustomerForm.getPassword(), registerCustomerForm.getPasswordConfirm());
      assert (newCustomer != null);

      // The next line needs to use the customer from the input form and not the customer returned after registration
      // so that we still have the unencoded password for use by the authentication mechanism.
      Authentication auth = loginService.loginCustomer(registerCustomerForm.getCustomer());
      mergeCartProcessor.execute(request, response, auth);

      String redirectUrl = registerCustomerForm.getRedirectUrl();

      if (StringUtils.isNotBlank(redirectUrl) && redirectUrl.contains(":")) {
        redirectUrl = null;
      }

      return StringUtils.isBlank(redirectUrl) ? getRegisterSuccessView() : ("redirect:" + redirectUrl);
    } else {
      return getRegisterView();
    }
  } // end method processRegister

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   registerCustomerForm  DOCUMENT ME!
   * @param   request               DOCUMENT ME!
   * @param   response              DOCUMENT ME!
   * @param   model                 DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String register(RegisterCustomerForm registerCustomerForm, HttpServletRequest request,
    HttpServletResponse response, Model model) {
    String redirectUrl = request.getParameter("successUrl");

    if (StringUtils.isNotBlank(redirectUrl)) {
      registerCustomerForm.setRedirectUrl(redirectUrl);
    }

    return getRegisterView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  useEmailForLogin  DOCUMENT ME!
   */
  public void setUseEmailForLogin(boolean useEmailForLogin) {
    this.useEmailForLogin = useEmailForLogin;
  }


} // end class BroadleafRegisterController
