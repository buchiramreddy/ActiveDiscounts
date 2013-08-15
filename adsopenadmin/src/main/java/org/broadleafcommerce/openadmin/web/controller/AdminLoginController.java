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

package org.broadleafcommerce.openadmin.web.controller;

import java.util.List;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.service.GenericResponse;
import org.broadleafcommerce.common.web.controller.BroadleafAbstractController;

import org.broadleafcommerce.openadmin.server.security.domain.AdminMenu;
import org.broadleafcommerce.openadmin.server.security.domain.AdminModule;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.broadleafcommerce.openadmin.server.security.service.navigation.AdminNavigationService;
import org.broadleafcommerce.openadmin.web.form.ResetPasswordForm;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * AdminLoginController handles login related needs for the BLC admin including:
 *
 * <ul>
 *   <li>Forgot Password</li>
 *   <li>Forgot Username</li>
 *   <li>Reset Password</li>
 * </ul>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Controller("blAdminLoginController")
public class AdminLoginController extends BroadleafAbstractController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final String ANONYMOUS_USER_NAME = "anonymousUser";

  // Entry URLs
  /** DOCUMENT ME! */
  protected static String loginView             = "login/login";

  /** DOCUMENT ME! */
  protected static String forgotPasswordView    = "login/forgotPassword";

  /** DOCUMENT ME! */
  protected static String forgotUsernameView    = "login/forgotUsername";

  /** DOCUMENT ME! */
  protected static String resetPasswordView     = "login/resetPassword";

  /** DOCUMENT ME! */
  protected static String changePasswordView    = "login/changePassword";

  /** DOCUMENT ME! */
  protected static String loginRedirect         = "login";

  /** DOCUMENT ME! */
  protected static String resetPasswordRedirect = "resetPassword";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdminNavigationService")
  protected AdminNavigationService adminNavigationService;

  /** DOCUMENT ME! */
  @Resource(name = "blAdminSecurityService")
  protected AdminSecurityService adminSecurityService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String getChangePasswordView() {
    return changePasswordView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String getForgotPasswordView() {
    return forgotPasswordView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String getForgotUsernameView() {
    return forgotUsernameView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String getLoginRedirect() {
    return loginRedirect;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String getLoginView() {
    return loginView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String getResetPasswordRedirect() {
    return resetPasswordRedirect;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String getResetPasswordView() {
    return resetPasswordView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  changePasswordView  DOCUMENT ME!
   */
  public static void setChangePasswordView(String changePasswordView) {
    AdminLoginController.changePasswordView = changePasswordView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  forgotPasswordView  DOCUMENT ME!
   */
  public static void setForgotPasswordView(String forgotPasswordView) {
    AdminLoginController.forgotPasswordView = forgotPasswordView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  forgotUsernameView  DOCUMENT ME!
   */
  public static void setForgotUsernameView(String forgotUsernameView) {
    AdminLoginController.forgotUsernameView = forgotUsernameView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  loginRedirect  DOCUMENT ME!
   */
  public static void setLoginRedirect(String loginRedirect) {
    AdminLoginController.loginRedirect = loginRedirect;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  loginView  DOCUMENT ME!
   */
  public static void setLoginView(String loginView) {
    AdminLoginController.loginView = loginView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  resetPasswordRedirect  DOCUMENT ME!
   */
  public static void setResetPasswordRedirect(String resetPasswordRedirect) {
    AdminLoginController.resetPasswordRedirect = resetPasswordRedirect;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  resetPasswordView  DOCUMENT ME!
   */
  public static void setResetPasswordView(String resetPasswordView) {
    AdminLoginController.resetPasswordView = resetPasswordView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/login",
    method = RequestMethod.GET
  )
  public String baseLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
    return getLoginView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/changePassword",
    method = RequestMethod.GET
  )
  public String changePassword(HttpServletRequest request, HttpServletResponse response, Model model) {
    return getChangePasswordView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/forgotPassword",
    method = RequestMethod.GET
  )
  public String forgotPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
    return getForgotPasswordView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/forgotUsername",
    method = RequestMethod.GET
  )
  public String forgotUsername(HttpServletRequest request, HttpServletResponse response, Model model) {
    return getForgotUsernameView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public AdminSecurityService getAdminSecurityService() {
    return adminSecurityService;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @ModelAttribute("resetPasswordForm")
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
   * DOCUMENT ME!
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = { "/", "/loginSuccess" },
    method = RequestMethod.GET
  )
  public String loginSuccess(HttpServletRequest request, HttpServletResponse response, Model model) {
    AdminMenu adminMenu = adminNavigationService.buildMenu(getPersistentAdminUser());

    if (!adminMenu.getAdminModules().isEmpty()) {
      AdminModule        first    = adminMenu.getAdminModules().get(0);
      List<AdminSection> sections = first.getSections();

      if (!sections.isEmpty()) {
        AdminSection adminSection = sections.get(0);

        return "redirect:" + adminSection.getUrl();
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request            DOCUMENT ME!
   * @param   response           DOCUMENT ME!
   * @param   model              DOCUMENT ME!
   * @param   resetPasswordForm  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/changePassword",
    method = RequestMethod.POST
  )
  public String processchangePassword(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm) {
    GenericResponse errorResponse = adminSecurityService.changePassword(resetPasswordForm.getUsername(),
        resetPasswordForm.getOldPassword(),
        resetPasswordForm.getPassword(),
        resetPasswordForm.getConfirmPassword());

    if (errorResponse.getHasErrors()) {
      setErrors(errorResponse, request);

      return getChangePasswordView();
    } else {
      return redirectToLoginWithMessage("passwordReset");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   email    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/forgotUsername",
    method = RequestMethod.POST
  )
  public String processForgotUserName(HttpServletRequest request,
    @RequestParam("emailAddress") String email) {
    GenericResponse errorResponse = adminSecurityService.sendForgotUsernameNotification(email);

    if (errorResponse.getHasErrors()) {
      setErrors(errorResponse, request);

      return getForgotUsernameView();
    } else {
      return redirectToLoginWithMessage("usernameSent");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request            DOCUMENT ME!
   * @param   response           DOCUMENT ME!
   * @param   model              DOCUMENT ME!
   * @param   resetPasswordForm  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/resetPassword",
    method = RequestMethod.POST
  )
  public String processResetPassword(HttpServletRequest request, HttpServletResponse response, Model model,
    @ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm) {
    GenericResponse errorResponse = adminSecurityService.resetPasswordUsingToken(
        resetPasswordForm.getUsername(),
        resetPasswordForm.getToken(),
        resetPasswordForm.getPassword(),
        resetPasswordForm.getConfirmPassword());

    if (errorResponse.getHasErrors()) {
      setErrors(errorResponse, request);

      return getResetPasswordView();
    } else {
      return redirectToLoginWithMessage("passwordReset");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   username  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/sendResetPassword",
    method = RequestMethod.POST
  )
  public String processSendResetPasswordEmail(HttpServletRequest request, HttpServletResponse response,
    @RequestParam("username") String username) {
    GenericResponse errorResponse = adminSecurityService.sendResetPasswordNotification(username);

    if (errorResponse.getHasErrors()) {
      setErrors(errorResponse, request);

      return getForgotPasswordView();
    } else {
      request.getSession(true).setAttribute("forgot_password_username", username);

      return redirectToResetPasswordWithMessage("passwordTokenSent");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/resetPassword",
    method = RequestMethod.GET
  )
  public String resetPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
    return getResetPasswordView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  adminSecurityService  DOCUMENT ME!
   */
  public void setAdminSecurityService(AdminSecurityService adminSecurityService) {
    this.adminSecurityService = adminSecurityService;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected AdminUser getPersistentAdminUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();

    if (ctx != null) {
      Authentication auth = ctx.getAuthentication();

      if ((auth != null) && !auth.getName().equals(ANONYMOUS_USER_NAME)) {
        UserDetails temp = (UserDetails) auth.getPrincipal();

        return adminSecurityService.readAdminUserByUserName(temp.getUsername());
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   message  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String redirectToLoginWithMessage(String message) {
    StringBuffer url = new StringBuffer("redirect:").append(loginRedirect).append("?messageCode=").append(message);

    return url.toString();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   message  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String redirectToResetPasswordWithMessage(String message) {
    StringBuffer url = new StringBuffer("redirect:").append(resetPasswordRedirect).append("?messageCode=").append(
        message);

    return url.toString();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  response  DOCUMENT ME!
   * @param  request   DOCUMENT ME!
   */
  protected void setErrors(GenericResponse response, HttpServletRequest request) {
    String errorCode = response.getErrorCodesList().get(0);
    request.setAttribute("errorCode", errorCode);
  }

} // end class AdminLoginController
