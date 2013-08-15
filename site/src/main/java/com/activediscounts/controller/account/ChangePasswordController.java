/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.activediscounts.controller.account;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.core.web.controller.account.BroadleafChangePasswordController;
import org.broadleafcommerce.core.web.controller.account.ChangePasswordForm;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Controller
@RequestMapping("/account/password")
public class ChangePasswordController extends BroadleafChangePasswordController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafChangePasswordController#processChangePassword(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model, org.broadleafcommerce.core.web.controller.account.ChangePasswordForm,
   *       org.springframework.validation.BindingResult,
   *       org.springframework.web.servlet.mvc.support.RedirectAttributes)
   */
  @Override
  @RequestMapping(method = RequestMethod.POST)
  public String processChangePassword(HttpServletRequest request, Model model,
    @ModelAttribute("changePasswordForm") ChangePasswordForm form, BindingResult result,
    RedirectAttributes redirectAttributes) throws ServiceException {
    return super.processChangePassword(request, model, form, result, redirectAttributes);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   model    DOCUMENT ME!
   * @param   form     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(method = RequestMethod.GET)
  public String viewChangePassword(HttpServletRequest request, Model model,
    @ModelAttribute("changePasswordForm") ChangePasswordForm form) {
    return super.viewChangePassword(request, model);
  }

} // end class ChangePasswordController
