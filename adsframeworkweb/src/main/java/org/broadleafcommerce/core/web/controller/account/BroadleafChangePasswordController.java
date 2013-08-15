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

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.security.util.PasswordChange;
import org.broadleafcommerce.common.web.controller.BroadleafAbstractController;

import org.broadleafcommerce.core.web.controller.account.validator.ChangePasswordValidator;

import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.web.core.CustomerState;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * This controller handles password changes for a customer's account.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BroadleafChangePasswordController extends BroadleafAbstractController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static String changePasswordView     = "account/changePassword";

  /** DOCUMENT ME! */
  protected static String changePasswordRedirect = "redirect:/account/password";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blChangePasswordValidator")
  protected ChangePasswordValidator changePasswordValidator;

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerService")
  protected CustomerService customerService;

  /** DOCUMENT ME! */
  protected String passwordChangedMessage = "Password successfully changed";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getChangePasswordRedirect() {
    return changePasswordRedirect;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getChangePasswordView() {
    return changePasswordView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPasswordChangedMessage() {
    return passwordChangedMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request             DOCUMENT ME!
   * @param   model               DOCUMENT ME!
   * @param   form                DOCUMENT ME!
   * @param   result              DOCUMENT ME!
   * @param   redirectAttributes  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  public String processChangePassword(HttpServletRequest request, Model model, ChangePasswordForm form,
    BindingResult result, RedirectAttributes redirectAttributes) throws ServiceException {
    PasswordChange passwordChange = new PasswordChange(CustomerState.getCustomer().getUsername());
    passwordChange.setCurrentPassword(form.getCurrentPassword());
    passwordChange.setNewPassword(form.getNewPassword());
    passwordChange.setNewPasswordConfirm(form.getNewPasswordConfirm());
    changePasswordValidator.validate(passwordChange, result);

    if (result.hasErrors()) {
      return getChangePasswordView();
    }

    customerService.changePassword(passwordChange);

    return getChangePasswordRedirect();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   model    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String viewChangePassword(HttpServletRequest request, Model model) {
    return getChangePasswordView();
  }

} // end class BroadleafChangePasswordController
