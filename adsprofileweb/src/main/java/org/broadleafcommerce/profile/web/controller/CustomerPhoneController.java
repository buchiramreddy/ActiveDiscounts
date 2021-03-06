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

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.profile.core.domain.CustomerPhone;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.service.CustomerPhoneService;
import org.broadleafcommerce.profile.web.controller.validator.CustomerPhoneValidator;
import org.broadleafcommerce.profile.web.controller.validator.PhoneValidator;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.broadleafcommerce.profile.web.core.model.PhoneNameForm;
import org.broadleafcommerce.profile.web.core.util.PhoneFormatter;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Provides access and mutator functions to manage a customer's phones.
 *
 * @author   sconlon
 * @version  $Revision$, $Date$
 */
@Controller("blCustomerPhoneController")
@RequestMapping("/myaccount/phone")
public class CustomerPhoneController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final String prefix   = "myAccount/phone/customerPhones";
  private static final String redirect = "redirect:/myaccount/phone/viewPhone.htm";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource(name = "blCustomerPhoneService")
  private CustomerPhoneService   customerPhoneService;
  @Resource(name = "blCustomerPhoneValidator")
  private CustomerPhoneValidator customerPhoneValidator;
  @Resource(name = "blCustomerState")
  private CustomerState          customerState;

  /* ??? -
   * Will these static defaults alter their ability to be
   * overwritten via the appContext?  TODO scc: test this scenario
   * */
  private String              deletePhoneSuccessView      = redirect;
  @Resource(name = "blEntityConfiguration")
  private EntityConfiguration entityConfiguration;
  private String              makePhoneDefaultSuccessView = redirect;
  @Resource(name = "blPhoneFormatter")
  private PhoneFormatter      phoneFormatter;
  @Resource(name = "blPhoneValidator")
  private PhoneValidator      phoneValidator;
  private String              savePhoneErrorView   = prefix;
  private String              savePhoneSuccessView = prefix;
  private String              viewPhoneErrorView   = prefix;
  private String              viewPhoneSuccessView = prefix;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Completely deletes the customerPhone with the given customerPhoneId from the database.
   *
   * @param   customerPhoneId  DOCUMENT ME!
   * @param   request          DOCUMENT ME!
   *
   * @return  completely deletes the customerPhone with the given customerPhoneId from the database.
   */
  @RequestMapping(
    value  = "deletePhone",
    method = { RequestMethod.GET, RequestMethod.POST }
  )
  public String deletePhone(@RequestParam(required = true) Long customerPhoneId, HttpServletRequest request) {
    customerPhoneService.deleteCustomerPhoneById(customerPhoneId);

    request.setAttribute("phone.deletedPhone", "true");

    return deletePhoneSuccessView + customerPhoneId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Called before each and every request comes into the controller, and is placed on the request for use by those
   * methods.
   *
   * @param   request  DOCUMENT ME!
   * @param   model    DOCUMENT ME!
   *
   * @return  called before each and every request comes into the controller, and is placed on the request for use by
   *          those methods.
   */
  @ModelAttribute("phoneNameForm")
  public PhoneNameForm initPhoneNameForm(HttpServletRequest request, Model model) {
    PhoneNameForm form = new PhoneNameForm();
    form.setPhone((Phone) entityConfiguration.createEntityInstance("org.broadleafcommerce.profile.core.domain.Phone"));

    return form;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the passed in customerPhoneId as the default phone for the user.
   *
   * @param   customerPhoneId  DOCUMENT ME!
   * @param   request          DOCUMENT ME!
   *
   * @return  sets the passed in customerPhoneId as the default phone for the user.
   */
  @RequestMapping(
    value  = "makePhoneDefault",
    method = { RequestMethod.GET, RequestMethod.POST }
  )
  public String makePhoneDefault(@RequestParam(required = true) Long customerPhoneId, HttpServletRequest request) {
    CustomerPhone customerPhone = customerPhoneService.readCustomerPhoneById(customerPhoneId);
    customerPhoneService.makeCustomerPhoneDefault(customerPhone.getId(), customerPhone.getCustomer().getId());

    request.setAttribute("phone.madePhoneDefault", "true");

    return makePhoneDefaultSuccessView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new phone if no customerPhoneId & phoneId are passed in; otherwise, it creates a new customerPhone object
   * otherwise. If they are passed in, it is assumed that there is an update.
   *
   * @param   phoneNameForm    DOCUMENT ME!
   * @param   errors           DOCUMENT ME!
   * @param   request          DOCUMENT ME!
   * @param   customerPhoneId  DOCUMENT ME!
   * @param   phoneId          DOCUMENT ME!
   *
   * @return  creates a new phone if no customerPhoneId & phoneId are passed in; otherwise, it creates a new
   *          customerPhone object otherwise.
   */
  @RequestMapping(
    value  = "savePhone",
    method = { RequestMethod.GET, RequestMethod.POST }
  )
  public String savePhone(@ModelAttribute("phoneNameForm") PhoneNameForm phoneNameForm, BindingResult errors,
    HttpServletRequest request,
    @RequestParam(required = false) Long customerPhoneId,
    @RequestParam(required = false) Long phoneId) {
    if (GenericValidator.isBlankOrNull(phoneNameForm.getPhoneName())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneName", "phoneName.required");
    }

    if (phoneId != null) {
      phoneNameForm.getPhone().setId(phoneId);
    }

    phoneFormatter.formatPhoneNumber(phoneNameForm.getPhone());
    errors.pushNestedPath("phone");
    phoneValidator.validate(phoneNameForm.getPhone(), errors);
    errors.popNestedPath();

    if (!errors.hasErrors()) {
      CustomerPhone customerPhone = (CustomerPhone) entityConfiguration.createEntityInstance(
          "org.broadleafcommerce.profile.core.domain.CustomerPhone");
      customerPhone.setCustomer(customerState.getCustomer(request));
      customerPhone.setPhoneName(phoneNameForm.getPhoneName());
      customerPhone.setPhone(phoneNameForm.getPhone());

      if ((customerPhoneId != null) && (customerPhoneId > 0)) {
        customerPhone.setId(customerPhoneId);
      }

      customerPhoneValidator.validate(customerPhone, errors);

      if (!errors.hasErrors()) {
        customerPhone = customerPhoneService.saveCustomerPhone(customerPhone);
        request.setAttribute("customerPhoneId", customerPhone.getId());
        request.setAttribute("phoneId", customerPhone.getPhone().getId());
      }

      return savePhoneSuccessView;
    } else {
      return savePhoneErrorView;
    }
  } // end method savePhone

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerPhoneService  DOCUMENT ME!
   */
  public void setCustomerPhoneService(CustomerPhoneService customerPhoneService) {
    this.customerPhoneService = customerPhoneService;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerPhoneValidator  DOCUMENT ME!
   */
  public void setCustomerPhoneValidator(CustomerPhoneValidator customerPhoneValidator) {
    this.customerPhoneValidator = customerPhoneValidator;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerState  DOCUMENT ME!
   */
  public void setCustomerState(CustomerState customerState) {
    this.customerState = customerState;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  deletePhoneSuccessView  DOCUMENT ME!
   */
  public void setdeletePhoneSuccessView(String deletePhoneSuccessView) {
    this.deletePhoneSuccessView = deletePhoneSuccessView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entityConfiguration  DOCUMENT ME!
   */
  public void setEntityConfiguration(EntityConfiguration entityConfiguration) {
    this.entityConfiguration = entityConfiguration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  makePhoneDefaultSuccessView  DOCUMENT ME!
   */
  public void setmakePhoneDefaultSuccessView(String makePhoneDefaultSuccessView) {
    this.makePhoneDefaultSuccessView = makePhoneDefaultSuccessView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  phoneFormatter  DOCUMENT ME!
   */
  public void setPhoneFormatter(PhoneFormatter phoneFormatter) {
    this.phoneFormatter = phoneFormatter;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  phoneValidator  DOCUMENT ME!
   */
  public void setPhoneValidator(PhoneValidator phoneValidator) {
    this.phoneValidator = phoneValidator;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  savePhoneErrorView  DOCUMENT ME!
   */
  public void setsavePhoneErrorView(String savePhoneErrorView) {
    this.savePhoneErrorView = savePhoneErrorView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  savePhoneSuccessView  DOCUMENT ME!
   */
  public void setsavePhoneSuccessView(String savePhoneSuccessView) {
    this.savePhoneSuccessView = savePhoneSuccessView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  viewPhoneErrorView  DOCUMENT ME!
   */
  public void setviewPhoneErrorView(String viewPhoneErrorView) {
    this.viewPhoneErrorView = viewPhoneErrorView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  viewPhoneSuccessView  DOCUMENT ME!
   */
  public void setviewPhoneSuccessView(String viewPhoneSuccessView) {
    this.viewPhoneSuccessView = viewPhoneSuccessView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Provides a blank template for a new Customer Phone to be created if no customerPhoneId is provided. Otherwise, when
   * a customerPhoneId is provided, the associated customerPhone object is retrieved, and placed on the request.
   *
   * @param   customerPhoneId  DOCUMENT ME!
   * @param   request          DOCUMENT ME!
   * @param   phoneNameForm    DOCUMENT ME!
   * @param   errors           DOCUMENT ME!
   *
   * @return  provides a blank template for a new Customer Phone to be created if no customerPhoneId is provided.
   */
  @RequestMapping(
    value  = "viewPhone",
    method = { RequestMethod.GET, RequestMethod.POST }
  )
  public String viewPhone(@RequestParam(required = false) Long customerPhoneId, HttpServletRequest request,
    @ModelAttribute("phoneNameForm") PhoneNameForm phoneNameForm, BindingResult errors) {
    if (customerPhoneId == null) {
      return viewPhoneSuccessView;
    } else {
      Long          currCustomerId = customerState.getCustomer(request).getId();
      CustomerPhone cPhone         = customerPhoneService.readCustomerPhoneById(customerPhoneId);

      if (cPhone != null) {
        // TODO: verify this is the current customers phone
        // ? - do we really need this since we read the phone with the currCustomerId?
        if (!cPhone.getCustomer().getId().equals(currCustomerId)) {
          return viewPhoneErrorView;
        }

        phoneNameForm.setPhone(cPhone.getPhone());
        phoneNameForm.setPhoneName(cPhone.getPhoneName());
        request.setAttribute("customerPhoneId", cPhone.getId());
        request.setAttribute("phoneId", cPhone.getPhone().getId());

        return viewPhoneSuccessView;
      } else {
        return viewPhoneErrorView;
      }
    }
  } // end method viewPhone
} // end class CustomerPhoneController
