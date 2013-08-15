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
import org.broadleafcommerce.common.service.GenericResponse;
import org.broadleafcommerce.common.web.controller.BroadleafAbstractController;

import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.core.service.validator.ResetPasswordValidator;
import org.broadleafcommerce.profile.web.core.service.LoginService;

import org.springframework.security.core.Authentication;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;


/**
 * The controller responsible for login and login related activities including forgot username, forgot password, and
 * reset password.
 *
 * <p>The actual login processing is done via Spring Security.</p>
 *
 * @author   apazzolini
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class BroadleafLoginController extends BroadleafAbstractController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static String loginView                 = "authentication/login";

  /** DOCUMENT ME! */
  protected static String forgotPasswordView        = "authentication/forgotPassword";

  /** DOCUMENT ME! */
  protected static String forgotUsernameView        = "authentication/forgotUsername";

  /** DOCUMENT ME! */
  protected static String forgotPasswordSuccessView = "authentication/forgotPasswordSuccess";

  /** DOCUMENT ME! */
  protected static String resetPasswordView        = "authentication/resetPassword";

  /** DOCUMENT ME! */
  protected static String resetPasswordErrorView   = "authentication/resetPasswordError";

  /** DOCUMENT ME! */
  protected static String resetPasswordSuccessView = "redirect:/";

  /** DOCUMENT ME! */
  protected static String resetPasswordFormView = "authentication/resetPasswordForm";

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
  @Resource(name = "blResetPasswordValidator")
  protected ResetPasswordValidator resetPasswordValidator;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Redirects to te forgot password view.
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  the return view
   */
  public String forgotPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
    return getForgotPasswordView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the forgot username view.
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  the return view
   */
  public String forgotUsername(HttpServletRequest request, HttpServletResponse response, Model model) {
    return getForgotUsernameView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The view returned after a successful forgotPassword email has been sent.
   *
   * @return  the view returned after a successful forgotPassword email has been sent.
   */
  public String getForgotPasswordSuccessView() {
    return forgotPasswordSuccessView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The view displayed for the forgot password form.
   *
   * @return  the view displayed for the forgot password form.
   */
  public String getForgotPasswordView() {
    return forgotPasswordView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The view displayed for the forgot username form.
   *
   * @return  the view displayed for the forgot username form.
   */
  public String getForgotUsernameView() {
    return forgotUsernameView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The view representing the login page.
   *
   * @return  the view representing the login page.
   */
  public String getLoginView() {
    return loginView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * View user is directed to if they try to access the resetPasswordForm with an invalid token.
   *
   * @return  the error view
   */
  public String getResetPasswordErrorView() {
    return resetPasswordErrorView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The view name to use for the reset password model..
   *
   * @return  the view name to use for the reset password model..
   */
  public String getResetPasswordFormView() {
    return resetPasswordFormView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   scheme   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getResetPasswordPort(HttpServletRequest request, String scheme) {
    if ("http".equalsIgnoreCase(scheme) && (request.getServerPort() != 80)) {
      return ":" + request.getServerPort();
    } else if ("https".equalsIgnoreCase(scheme) && (request.getServerPort() != 443)) {
      return ":" + request.getServerPort();
    }

    return ""; // no port required
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getResetPasswordScheme(HttpServletRequest request) {
    return request.getScheme();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * View that a user is sent to after a successful reset password operations. Should be a redirect (e.g. start with
   * "redirect:" since this will cause the entire SpringSecurity pipeline to be fulfilled.
   *
   * @return  view that a user is sent to after a successful reset password operations.
   */
  public String getResetPasswordSuccessView() {
    return resetPasswordSuccessView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getResetPasswordUrl(HttpServletRequest request) {
    String url = request.getScheme() + "://" + request.getServerName()
      + getResetPasswordPort(request, request.getScheme() + "/");

    if ((request.getContextPath() != null) && !"".equals(request.getContextPath())) {
      url = url + request.getContextPath() + getResetPasswordView();
    } else {
      url = url + getResetPasswordView();
    }

    return url;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The view displayed for the reset password form.
   *
   * @return  the view displayed for the reset password form.
   */
  public String getResetPasswordView() {
    return resetPasswordView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Initializes the reset password by ensuring that the passed in token URL parameter initializes the hidden form
   * field.
   *
   * <p>Also, if the reset password request is in the same session as the forgotPassword request, the username will
   * auto-populate</p>
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  the return view
   */
  public ResetPasswordForm initResetPasswordForm(HttpServletRequest request) {
    ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
    String            username          = (String) request.getSession(true).getAttribute("forgot_password_username");
    String            token             = request.getParameter("token");
    resetPasswordForm.setToken(token);
    resetPasswordForm.setUsername(username);

    return resetPasswordForm;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Redirects to the login view.
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  the return view
   */
  public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
    if (StringUtils.isNotBlank(request.getParameter("successUrl"))) {
      model.addAttribute("successUrl", request.getParameter("successUrl"));
    }

    return getLoginView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Looks up the passed in username and sends an email to the address on file with a reset password token.
   *
   * <p>Returns error codes for invalid username.</p>
   *
   * @param   username  DOCUMENT ME!
   * @param   request   DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  the return view
   */
  public String processForgotPassword(String username, HttpServletRequest request, Model model) {
    GenericResponse errorResponse = customerService.sendForgotPasswordNotification(username,
        getResetPasswordUrl(request));

    if (errorResponse.getHasErrors()) {
      String errorCode = errorResponse.getErrorCodesList().get(0);
      model.addAttribute("errorCode", errorCode);

      return getForgotPasswordView();
    } else {
      request.getSession(true).setAttribute("forgot_password_username", username);

      return getForgotPasswordSuccessView();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Looks up an account by email address and if found, sends an email with the associated username.
   *
   * @param   email     DOCUMENT ME!
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  the return view
   */
  public String processForgotUsername(String email, HttpServletRequest request, HttpServletResponse response,
    Model model) {
    GenericResponse errorResponse = customerService.sendForgotUsernameNotification(email);

    if (errorResponse.getHasErrors()) {
      String errorCode = errorResponse.getErrorCodesList().get(0);
      request.setAttribute("errorCode", errorCode);

      return getForgotUsernameView();
    } else {
      return buildRedirectToLoginWithMessage("usernameSent");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Processes the reset password token and allows the user to change their password. Ensures that the password and
   * confirm password match, that the token is valid, and that the token matches the provided email address.
   *
   * @param   resetPasswordForm  DOCUMENT ME!
   * @param   request            DOCUMENT ME!
   * @param   response           DOCUMENT ME!
   * @param   model              DOCUMENT ME!
   * @param   errors             DOCUMENT ME!
   *
   * @return  the return view
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  public String processResetPassword(ResetPasswordForm resetPasswordForm, HttpServletRequest request,
    HttpServletResponse response, Model model, BindingResult errors) throws ServiceException {
    GenericResponse errorResponse = new GenericResponse();
    resetPasswordValidator.validate(resetPasswordForm.getUsername(), resetPasswordForm.getPassword(),
      resetPasswordForm.getPasswordConfirm(), errors);

    if (errorResponse.getHasErrors()) {
      return getResetPasswordView();
    }

    errorResponse = customerService.resetPasswordUsingToken(
        resetPasswordForm.getUsername(),
        resetPasswordForm.getToken(),
        resetPasswordForm.getPassword(),
        resetPasswordForm.getPasswordConfirm());

    if (errorResponse.getHasErrors()) {
      String errorCode = errorResponse.getErrorCodesList().get(0);
      request.setAttribute("errorCode", errorCode);

      return getResetPasswordView();
    } else {
      // The reset password was successful, so log this customer in.
      Authentication auth = loginService.loginCustomer(resetPasswordForm.getUsername(),
          resetPasswordForm.getPassword());
      mergeCartProcessor.execute(request, response, auth);

      return getResetPasswordSuccessView();
    }
  } // end method processResetPassword

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Displays the reset password view. Expects a valid resetPasswordToken to exist that was generated by
   * {@link processForgotPassword} or similar. Returns an error view if the token is invalid or expired.
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  the return view
   */
  public String resetPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
    ResetPasswordForm resetPasswordForm = initResetPasswordForm(request);
    model.addAttribute("resetPasswordForm", resetPasswordForm);

    GenericResponse errorResponse = customerService.checkPasswordResetToken(resetPasswordForm.getToken());

    if (errorResponse.getHasErrors()) {
      String errorCode = errorResponse.getErrorCodesList().get(0);
      request.setAttribute("errorCode", errorCode);

      return getResetPasswordErrorView();
    } else {
      return getResetPasswordView();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * By default, redirects to the login page with a message.
   *
   * @param   message  DOCUMENT ME!
   *
   * @return  the return view
   */
  protected String buildRedirectToLoginWithMessage(String message) {
    StringBuffer url = new StringBuffer("redirect:").append(getLoginView()).append("?messageCode=").append(message);

    return url.toString();
  }

} // end class BroadleafLoginController
