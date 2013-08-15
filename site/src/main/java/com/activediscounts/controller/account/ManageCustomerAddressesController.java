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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController;
import org.broadleafcommerce.core.web.controller.account.CustomerAddressForm;

import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.domain.State;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/account/addresses")
public class ManageCustomerAddressesController extends BroadleafManageCustomerAddressesController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController#addCustomerAddress(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model, org.broadleafcommerce.core.web.controller.account.CustomerAddressForm,
   *       org.springframework.validation.BindingResult,
   *       org.springframework.web.servlet.mvc.support.RedirectAttributes)
   */
  @Override
  @RequestMapping(method = RequestMethod.POST)
  public String addCustomerAddress(HttpServletRequest request, Model model,
    @ModelAttribute("customerAddressForm") CustomerAddressForm form, BindingResult result,
    RedirectAttributes redirectAttributes) throws ServiceException {
    return super.addCustomerAddress(request, model, form, result, redirectAttributes);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController#removeCustomerAddress(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model, java.lang.Long, org.springframework.web.servlet.mvc.support.RedirectAttributes)
   */
  @Override
  @RequestMapping(
    value  = "/{customerAddressId}",
    method = RequestMethod.POST,
    params = "removeAddress=Remove"
  )
  public String removeCustomerAddress(HttpServletRequest request, Model model,
    @PathVariable("customerAddressId") Long customerAddressId, RedirectAttributes redirectAttributes) {
    return super.removeCustomerAddress(request, model, customerAddressId, redirectAttributes);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController#updateCustomerAddress(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model, java.lang.Long,
   *       org.broadleafcommerce.core.web.controller.account.CustomerAddressForm,
   *       org.springframework.validation.BindingResult,
   *       org.springframework.web.servlet.mvc.support.RedirectAttributes)
   */
  @Override
  @RequestMapping(
    value  = "/{customerAddressId}",
    method = RequestMethod.POST
  )
  public String updateCustomerAddress(HttpServletRequest request, Model model,
    @PathVariable("customerAddressId") Long customerAddressId,
    @ModelAttribute("customerAddressForm") CustomerAddressForm form, BindingResult result,
    RedirectAttributes redirectAttributes) throws ServiceException {
    return super.updateCustomerAddress(request, model, customerAddressId, form, result, redirectAttributes);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController#viewCustomerAddress(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model, java.lang.Long)
   */
  @Override
  @RequestMapping(
    value  = "/{customerAddressId}",
    method = RequestMethod.GET
  )
  public String viewCustomerAddress(HttpServletRequest request, Model model,
    @PathVariable("customerAddressId") Long customerAddressId) {
    return super.viewCustomerAddress(request, model, customerAddressId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController#viewCustomerAddresses(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model)
   */
  @Override
  @RequestMapping(method = RequestMethod.GET)
  public String viewCustomerAddresses(HttpServletRequest request, Model model) {
    return super.viewCustomerAddresses(request, model);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController#initBinder(javax.servlet.http.HttpServletRequest,
   *       org.springframework.web.bind.ServletRequestDataBinder)
   */
  @InitBinder @Override protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
    throws Exception {
    super.initBinder(request, binder);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController#populateCountries()
   */
  @ModelAttribute("countries")
  @Override protected List<Country> populateCountries() {
    return super.populateCountries();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController#populateCustomerAddresses()
   */
  @ModelAttribute("customerAddresses")
  @Override protected List<CustomerAddress> populateCustomerAddresses() {
    return super.populateCustomerAddresses();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController#populateStates()
   */
  @ModelAttribute("states")
  @Override protected List<State> populateStates() {
    return super.populateStates();
  }


} // end class ManageCustomerAddressesController
