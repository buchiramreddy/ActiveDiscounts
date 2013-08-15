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

package org.broadleafcommerce.profile.web.controller;

import java.util.List;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.security.MergeCartProcessor;

import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.ChallengeQuestionService;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.web.controller.validator.RegisterCustomerValidator;
import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;
import org.broadleafcommerce.profile.web.core.service.LoginService;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Controller("blRegisterCustomerController")
@RequestMapping("/registerCustomer")
/**
 * @Deprecated - Use BroadleafRegisterController instead
 * RegisterCustomerController is used to register a customer.
 *
 * This controller simply calls the RegistrationCustomerValidator which can be extended for custom validation and
 * then calls saveCustomer.
 */
public class RegisterCustomerController {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blChallengeQuestionService")
  protected ChallengeQuestionService challengeQuestionService;

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerService")
  protected CustomerService customerService;

  // URLs For success and failure
  /** DOCUMENT ME! */
  protected String displayRegistrationFormView = "/account/registration/registerCustomer";

  /** DOCUMENT ME! */
  @Resource(name = "blLoginService")
  protected LoginService loginService;

  /** DOCUMENT ME! */
  @Resource(name = "blMergeCartProcessor")
  protected MergeCartProcessor mergeCartProcessor;

  /** DOCUMENT ME! */
  @Resource(name = "blRegisterCustomerValidator")
  protected RegisterCustomerValidator registerCustomerValidator;

  /** DOCUMENT ME! */
  protected String registrationErrorView = displayRegistrationFormView;

  /** DOCUMENT ME! */
  protected String registrationSuccessView = "redirect:/registerCustomer/registerCustomerSuccess.htm";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @ModelAttribute("challengeQuestions")
  public List<ChallengeQuestion> getChallengeQuestions() {
    return challengeQuestionService.readChallengeQuestions();
      // return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDisplayRegistrationFormView() {
    return displayRegistrationFormView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public RegisterCustomerValidator getRegisterCustomerValidator() {
    return registerCustomerValidator;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getRegistrationErrorView() {
    return registrationErrorView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getRegistrationSuccessView() {
    return registrationSuccessView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  binder  DOCUMENT ME!
   */
  @InitBinder public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(ChallengeQuestion.class, new CustomChallengeQuestionEditor(challengeQuestionService));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @ModelAttribute("registerCustomerForm")
  public RegisterCustomerForm initCustomerRegistrationForm() {
    RegisterCustomerForm customerRegistrationForm = new RegisterCustomerForm();
    Customer             customer                 = customerService.createNewCustomer();
    customerRegistrationForm.setCustomer(customer);

    return customerRegistrationForm;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "registerCustomer",
    method = { RequestMethod.GET }
  )
  public String registerCustomer() {
    return getDisplayRegistrationFormView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   registerCustomerForm  DOCUMENT ME!
   * @param   errors                DOCUMENT ME!
   * @param   request               DOCUMENT ME!
   * @param   response              DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "registerCustomer",
    method = { RequestMethod.POST }
  )
  public ModelAndView registerCustomer(
    @ModelAttribute("registerCustomerForm") RegisterCustomerForm registerCustomerForm,
    BindingResult errors, HttpServletRequest request, HttpServletResponse response) {
    registerCustomerValidator.validate(registerCustomerForm, errors);

    if (!errors.hasErrors()) {
      customerService.registerCustomer(registerCustomerForm.getCustomer(), registerCustomerForm.getPassword(),
        registerCustomerForm.getPasswordConfirm());

      Authentication auth = loginService.loginCustomer(registerCustomerForm.getCustomer());
      mergeCartProcessor.execute(request, response, auth);

      return new ModelAndView(getRegistrationSuccessView());
    } else {
      return new ModelAndView(getRegistrationErrorView());
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "registerCustomerSuccess",
    method = { RequestMethod.GET }
  )
  public String registerCustomerSuccess() {
    return "/account/registration/registerCustomerSuccess";
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  displayRegistrationFormView  DOCUMENT ME!
   */
  public void setDisplayRegistrationFormView(String displayRegistrationFormView) {
    this.displayRegistrationFormView = displayRegistrationFormView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  registerCustomerValidator  DOCUMENT ME!
   */
  public void setRegisterCustomerValidator(RegisterCustomerValidator registerCustomerValidator) {
    this.registerCustomerValidator = registerCustomerValidator;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  registrationErrorView  DOCUMENT ME!
   */
  public void setRegistrationErrorView(String registrationErrorView) {
    this.registrationErrorView = registrationErrorView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  registrationSuccessView  DOCUMENT ME!
   */
  public void setRegistrationSuccessView(String registrationSuccessView) {
    this.registrationSuccessView = registrationSuccessView;
  }

} // end class RegisterCustomerController
