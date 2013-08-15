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

import org.broadleafcommerce.core.web.controller.account.BroadleafUpdateAccountController;
import org.broadleafcommerce.core.web.controller.account.UpdateAccountForm;

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
@RequestMapping("/account")
public class UpdateAccountController extends BroadleafUpdateAccountController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafUpdateAccountController#processUpdateAccount(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model, org.broadleafcommerce.core.web.controller.account.UpdateAccountForm,
   *       org.springframework.validation.BindingResult,
   *       org.springframework.web.servlet.mvc.support.RedirectAttributes)
   */
  @Override
  @RequestMapping(method = RequestMethod.POST)
  public String processUpdateAccount(HttpServletRequest request, Model model,
    @ModelAttribute("updateAccountForm") UpdateAccountForm form, BindingResult result,
    RedirectAttributes redirectAttributes) throws ServiceException {
    return super.processUpdateAccount(request, model, form, result, redirectAttributes);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafUpdateAccountController#viewUpdateAccount(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model, org.broadleafcommerce.core.web.controller.account.UpdateAccountForm)
   */
  @Override
  @RequestMapping(method = RequestMethod.GET)
  public String viewUpdateAccount(HttpServletRequest request, Model model,
    @ModelAttribute("updateAccountForm") UpdateAccountForm form) {
    return super.viewUpdateAccount(request, model, form);
  }


} // end class UpdateAccountController
